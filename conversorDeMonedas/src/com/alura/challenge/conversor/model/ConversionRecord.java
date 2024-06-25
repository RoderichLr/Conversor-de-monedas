package com.alura.challenge.conversor.model;

import java.time.LocalDateTime;

/**
 * ConversionRecord es una clase inmutable que representa un registro de una conversión de moneda.
 * Utiliza el formato de "record" lo que proporciona una forma concisa
 * de definir una clase de datos.
 *
 * @param timestamp   La fecha y hora en que se realizó la conversión.
 * @param fromCurrency El código de la moneda de origen.
 * @param toCurrency   El código de la moneda de destino.
 * @param amount       La cantidad de dinero que se convirtió.
 * @param result       El resultado de la conversión en la moneda de destino.
 */
public record ConversionRecord(LocalDateTime timestamp, String fromCurrency, String toCurrency, double amount, double result) {
}