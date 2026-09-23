package weather_api.weather_api.service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import weather_api.weather_api.DTO.WeatherDTO;

import java.time.Duration;


@Service
public class WeatherService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;
    private final String apiKey;

    private static final long CACHE_TTL_HOURS = 60L;

    public WeatherService(
            @Value("${spring.weather.api.key}") String apiKey,
            StringRedisTemplate redisTemplate,
            ObjectMapper objectMapper
    ) {
        this.apiKey = apiKey;
        this.restClient = RestClient.create();
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    public WeatherDTO getWeatherByCity(String city) {
        String cacheKey = "weather:" + city.toLowerCase().trim();

        try {
            String cachedJson = redisTemplate.opsForValue().get(cacheKey);
            if (cachedJson != null) {
                return objectMapper.readValue(cachedJson, WeatherDTO.class);
            }
        } catch (Exception e) {
            System.err.println("Aviso: Falha ao consultar o Redis: " + e.getMessage());
        }

        WeatherDTO weatherDTO = fetchFromExternalApi(city);

        try {
            String jsonToCache = objectMapper.writeValueAsString(weatherDTO);
            redisTemplate.opsForValue().set(cacheKey, jsonToCache, Duration.ofHours(CACHE_TTL_HOURS));
        } catch (Exception e) {
            System.err.println("Aviso: Falha ao salvar no Redis: " + e.getMessage());
        }

        return weatherDTO;
    }

    private WeatherDTO fetchFromExternalApi(String city) {
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + city + "?key=" + apiKey;

        String jsonResponse = restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);

        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode current = root.path("currentConditions");
            JsonNode today = root.path("days").get(0);

            return new WeatherDTO(
                    root.path("address").asText(),
                    today.path("tempmax").asDouble(),
                    today.path("tempmin").asDouble(),
                    current.path("temp").asDouble(),
                    current.path("humidity").asDouble(),
                    current.path("conditions").asText()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error to processing : " + city, e);
        }
    }
}