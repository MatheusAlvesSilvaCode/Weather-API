package service;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherService {


    private final RestClient restClient;
    //TODO: Colocar a chave da api no application YAML
    private final String apiKey = "WJQL67F8CPJVARSVL92AU567E";

    public WeatherService(){
        this.restClient = RestClient.create();
    }

    public String getFullWeatherData(String city) {
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + city + "?unitGroup=metric&key=" + apiKey + "&contentType=json";

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);
    }
}
