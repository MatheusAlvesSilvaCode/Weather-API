package controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.WeatherService;

@Controller
@RequestMapping(value="/wheter")
public class ControllerAPI {

    @Autowired
    WeatherService weatherService;

    @PostMapping("/FullWeatherData")
    public WeatherService getFullWeatherData(String city){
        String jsonResponse = weatherService.getFullWeatherData(city);

        return weatherService;
    }

}
