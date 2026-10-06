# 🌤 Weather API (Spring Boot & Redis)

URL project: https://roadmap.sh/projects/weather-api-wrapper-service

> A project developed for [roadmap.sh](https://roadmap.sh/) with the goal of building a REST API in Java (Spring Boot) that consumes data from a third-party weather service (Visual Crossing), implements caching with **Redis** for performance optimization, and uses environment variables for security.

---

## 🚀 Technologies Used

* **Java 17+**
* **Spring Boot** (Spring Web, RestClient)
* **Spring Data Redis** (StringRedisTemplate)
* **Jackson** (JSON Processing)
* **Visual Crossing Weather API** (Weather data provider)
* **Maven** (Dependency Management)

---

## 📌 Features

* **Weather Query by City:** REST endpoint to fetch current conditions, maximum/minimum temperatures, and humidity.
* **Smart Caching with Redis:** Reduces unnecessary calls to the external API and speeds up frequent responses.
* **Error Handling:** Appropriate responses for connection failures or invalid cities.
* **Secure Configuration:** Uses environment variables to hide sensitive API keys.

---

## ⚙️ Prerequisites

Make sure you have the following installed on your machine:
* **Java Development Kit (JDK 17 or higher)**
* **Maven**
* **Docker** (Optional, but recommended to run Redis easily)
* A free API key from [Visual Crossing API](https://www.visualcrossing.com/)

---

## 🔑 Setup and Execution

### 1. Clone the Repository
```bash
git clone [https://github.com/your-username/your-repository.git](https://github.com/your-username/your-repository.git)
cd your-repository
