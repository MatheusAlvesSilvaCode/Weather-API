package weather_api.weather_api.service;
import weather_api.weather_api.DTO.WeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class WeatherService {

    private final RestClient restClient;

    private final String apiKey;
    private final ObjectMapper objectMapper;


    public WeatherService(@Value("${spring.weather.api.key}") String apiKey) {
        this.restClient = RestClient.create();
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper();
    }

    public WeatherDTO getWeatherByCity(String city) {
//        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
//                + city + "?unitGroup=metric&key=" + apiKey + "&contentType=json";
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

            return new WeatherDTO( //TODO: ajustar isso, no request está vindo todo estrnaho, morou ?
                    root.path("address").asString(),
                    current.path("tempmax").asDouble(),
                    today.path("tempmin").asDouble(),
                    today.path("temp").asDouble(),
                    current.path("humidity").asDouble(),
                    current.path("conditions").asString()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error getting weather by city: " + city, e);
        }
    }
}
