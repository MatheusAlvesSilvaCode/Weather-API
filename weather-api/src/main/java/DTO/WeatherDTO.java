package DTO;

public record WeatherDTO(
        String city,
        String tempmax,
        String tempmin,
        String temp,
        String humidity,
        String conditions
) {}
