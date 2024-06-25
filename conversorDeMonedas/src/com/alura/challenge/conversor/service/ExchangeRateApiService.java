package com.alura.challenge.conversor.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * ExchangeRateApiService es una clase que se encarga de obtener la tasa de cambio entre dos monedas
 * desde la API de ExchangeRate-API. Utiliza HttpClient para realizar solicitudes HTTP
 * y Gson para parsear las respuestas JSON.
 */
public class ExchangeRateApiService {
    // URL de la API para obtener la tasa de cambio. Reemplaza YOUR-API-KEY con tu clave de API.
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/YOUR-API-KEY/pair/";

    /**
     * getExchangeRate realiza una solicitud a la API de ExchangeRate-API para obtener la tasa de cambio
     * entre dos monedas.
     *
     * @param fromCurrencyCode El código de la moneda de origen.
     * @param toCurrencyCode El código de la moneda de destino.
     * @return La tasa de cambio entre las dos monedas. Si ocurre un error, devuelve 0.0.
     */
    public double getExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        try {
            // Construcción de la URL completa para la solicitud
            String urlString = API_URL.replace("YOUR-API-KEY", "b43b83ba451639252c59dec9") + fromCurrencyCode + "/" + toCurrencyCode;

            // Creación del cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Creación de la solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .build();

            // Envío de la solicitud y recepción de la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parseo de la respuesta JSON
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            // Obtención de la tasa de cambio
            return jsonObject.get("conversion_rate").getAsDouble();
        } catch (Exception e) {
            // Manejo de excepciones: imprime la traza de la excepción y devuelve 0.0
            e.printStackTrace();
            return 0.0;
        }
    }
}