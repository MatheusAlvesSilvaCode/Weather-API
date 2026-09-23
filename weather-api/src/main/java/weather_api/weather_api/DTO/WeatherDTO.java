package weather_api.weather_api.DTO;

public record WeatherDTO(
        String city,
        double tempmax,
        double tempmin,
        double temp,
        double humidity,
        String conditions
) {}
