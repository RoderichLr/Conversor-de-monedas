package com.alura.challenge.conversor.service;

/**
 * CurrencyConverter es una clase que proporciona funcionalidades para convertir cantidades
 * entre diferentes monedas utilizando la tasa de cambio obtenida de ExchangeRateApiService.
 */
public class CurrencyConverter {
    // Servicio para obtener la tasa de cambio entre monedas
    private final ExchangeRateApiService exchangeRateApiService;

    /**
     * Constructor que inicializa CurrencyConverter con una instancia de ExchangeRateApiService.
     *
     * @param exchangeRateApiService Servicio para obtener las tasas de cambio.
     */
    public CurrencyConverter(ExchangeRateApiService exchangeRateApiService) {
        this.exchangeRateApiService = exchangeRateApiService;
    }

    /**
     * Convierte una cantidad de una moneda a otra utilizando la tasa de cambio obtenida.
     *
     * @param amount           La cantidad de dinero a convertir.
     * @param fromCurrencyCode El código de la moneda de origen.
     * @param toCurrencyCode   El código de la moneda de destino.
     * @return La cantidad convertida en la moneda de destino.
     */
    public double convert(double amount, String fromCurrencyCode, String toCurrencyCode) {
        // Obtención de la tasa de cambio entre las dos monedas
        double exchangeRate = exchangeRateApiService.getExchangeRate(fromCurrencyCode, toCurrencyCode);

        // Cálculo de la cantidad convertida
        return amount * exchangeRate;
    }
}