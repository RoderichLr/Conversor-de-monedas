package com.alura.challenge.conversor.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * CurrencyDataFetcher es una clase que se encarga de obtener los códigos de las monedas
 * desde la API de ExchangeRate-API. Utiliza HttpClient para realizar solicitudes HTTP
 * y Gson para parsear las respuestas JSON.
 */
public class CurrencyDataFetcher {
    // URL de la API para obtener los códigos de moneda. Reemplaza YOUR-API-KEY con tu clave de API.
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/YOUR-API-KEY/codes";

    /**
     * fetchCurrencyCodes realiza una solicitud a la API de ExchangeRate-API para obtener los códigos
     * de las monedas y sus nombres asociados.
     *
     * @return Un mapa donde las claves son los códigos de moneda y los valores son los nombres de las monedas.
     */
    public Map<String, String> fetchCurrencyCodes() {
        Map<String, String> currencyCodes = new HashMap<>();
        try {
            // Creación del cliente HTTP
            HttpClient client = HttpClient.newHttpClient();
            // Creación de la solicitud HTTP con la URL de la API
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL.replace("YOUR-API-KEY", "b43b83ba451639252c59dec9")))
                    .build();

            // Envío de la solicitud y recepción de la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parseo de la respuesta JSON
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray supportedCodes = jsonObject.getAsJsonArray("supported_codes");

            // Iteración sobre los códigos soportados y almacenamiento en el mapa
            for (JsonElement element : supportedCodes) {
                JsonArray codePair = element.getAsJsonArray();
                String code = codePair.get(0).getAsString();
                String name = codePair.get(1).getAsString();
                currencyCodes.put(code, name);
            }
        } catch (Exception e) {
            // Manejo de excepciones: imprime la traza de la excepción
            e.printStackTrace();
        }

        return currencyCodes;
    }
}