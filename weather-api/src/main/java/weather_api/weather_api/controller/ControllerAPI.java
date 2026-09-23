package weather_api.weather_api.controller;
import weather_api.weather_api.DTO.WeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weather_api.weather_api.service.WeatherService;

@RestController
@RequestMapping(value="/weather")
public class ControllerAPI {

    @Autowired
    WeatherService weatherService;

    @GetMapping("/{city}")
    public WeatherDTO getByCity(@PathVariable("city") String city){
        return weatherService.getWeatherByCity(city);
    }
}
