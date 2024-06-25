package com.alura.challenge.conversor.util;

import com.alura.challenge.conversor.model.ConversionRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * ConversionHistory es una clase que mantiene un registro del historial de conversiones de moneda.
 * Proporciona métodos para añadir nuevas entradas al historial y para imprimir el historial completo.
 */
public class ConversionHistory {
    // Lista para almacenar los registros de conversión
    private final List<ConversionRecord> history;

    /**
     * Constructor que inicializa la lista de historial de conversiones.
     */
    public ConversionHistory() {
        history = new ArrayList<>();
    }

    /**
     * Añade una nueva entrada al historial de conversiones.
     *
     * @param fromCurrency El código de la moneda de origen.
     * @param toCurrency   El código de la moneda de destino.
     * @param amount       La cantidad de dinero convertida.
     * @param result       El resultado de la conversión.
     */
    public void addEntry(String fromCurrency, String toCurrency, double amount, double result) {
        // Crea un nuevo registro de conversión y lo añade al historial
        ConversionRecord record = new ConversionRecord(java.time.LocalDateTime.now(), fromCurrency, toCurrency, amount, result);
        history.add(record);
    }

    /**
     * Imprime el historial de conversiones.
     */
    public void printHistory() {
        System.out.println("Historial de Conversiones:");
        for (ConversionRecord record : history) {
            System.out.println(record);
        }
    }
}
