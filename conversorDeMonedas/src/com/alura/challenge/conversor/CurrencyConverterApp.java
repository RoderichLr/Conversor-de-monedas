package com.alura.challenge.conversor;

import com.alura.challenge.conversor.service.CurrencyConverter;
import com.alura.challenge.conversor.service.CurrencyDataFetcher;
import com.alura.challenge.conversor.service.ExchangeRateApiService;
import com.alura.challenge.conversor.util.ConversionHistory;

import java.util.*;

/**
 * CurrencyConverterApp es la clase principal para la aplicación de conversión de moneda.
 * Proporciona una interfaz de consola para que los usuarios seleccionen monedas y realicen conversiones.
 * Utiliza servicios externos para obtener datos de monedas y tasas de cambio.
 */
public class CurrencyConverterApp {
    // Enum para las monedas principales
    private enum PrimaryCurrency {
        CLP, ARS, COP, USD, BOB, BRL
    }

    // Lista de monedas adicionales
    private static final List<String> additionalCurrencies = Arrays.asList(
            "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "HKD", "INR", "KRW",
            "MXN", "NZD", "RUB", "SAR", "SGD", "TRY", "ZAR", "AED", "SEK", "NOK"
    );

    // Inicialización del conversor de moneda y el historial de conversiones
    private static final CurrencyConverter converter = new CurrencyConverter(new ExchangeRateApiService());
    private static final ConversionHistory history = new ConversionHistory();

    // Bandera para mostrar monedas adicionales
    private static boolean showAdditionalCurrencies = false;

    public static void main(String[] args) {
        // Inicio del programa
        CurrencyDataFetcher fetcher = new CurrencyDataFetcher(); // Obtiene datos de moneda
        Map<String, String> currencyData = fetcher.fetchCurrencyCodes(); // Obtiene códigos de moneda

        // Filtrar la lista de monedas principales
        List<String> primaryCurrencyList = getPrimaryCurrencyList();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al convertidor de monedas");
        System.out.println("==================");

        while (true) {
            // Mostrar las monedas disponibles para conversión
            List<String> currentCurrencyList = displayCurrencies(currencyData, primaryCurrencyList);

            String choice = scanner.next();
            if (handleCurrencyListToggle(choice, scanner)) {
                continue;
            }

            // Selección de moneda de origen
            String fromCurrencyCode = selectCurrency(scanner, choice, currentCurrencyList);
            if (fromCurrencyCode == null) {
                continue;
            }

            // Selección de moneda de destino
            System.out.print("Seleccione el número de la moneda de destino: ");
            choice = scanner.next();
            String toCurrencyCode = selectCurrency(scanner, choice, currentCurrencyList);
            if (toCurrencyCode == null) {
                continue;
            }

            // Ingreso del monto a convertir
            System.out.print("Ingrese el monto a convertir: ");
            if (!scanner.hasNextDouble()) {
                showInvalidInputMessage(scanner, "Debe ingresar un número decimal.");
                scanner.next(); // Consumir entrada incorrecta para evitar un bucle infinito
                continue;
            }

            double amount = scanner.nextDouble();

            // Conversión de moneda y muestra del resultado
            double result = converter.convert(amount, fromCurrencyCode, toCurrencyCode);
            System.out.printf("Monto convertido: %.2f %s%n", result, toCurrencyCode);

            // Registro de la conversión en el historial
            history.addEntry(fromCurrencyCode, toCurrencyCode, amount, result);

            // Pregunta para realizar otra conversión
            System.out.print("¿Desea realizar otra conversión? (si/no): ");
            if (!askYesOrNo(scanner)) {
                break;
            }
        }

        // Pregunta para mostrar el historial de conversiones
        System.out.print("¿Desea ver el historial de conversiones? (si/no): ");
        if (askYesOrNo(scanner)) {
            history.printHistory();
        }

        scanner.close();
    }

    /**
     * Obtiene una lista de las monedas principales definidas en el enum PrimaryCurrency.
     *
     * @return Una lista de códigos de monedas principales.
     */
    private static List<String> getPrimaryCurrencyList() {
        List<String> primaryCurrencyList = new ArrayList<>();
        for (PrimaryCurrency currency : PrimaryCurrency.values()) {
            primaryCurrencyList.add(currency.name());
        }
        return primaryCurrencyList;
    }

    /**
     * Muestra la lista de monedas disponibles para conversión en la consola.
     *
     * @param currencyData         Datos de las monedas obtenidos de un servicio externo.
     * @param primaryCurrencyList  Lista de las monedas principales.
     * @return La lista de monedas actuales que se están mostrando.
     */
    private static List<String> displayCurrencies(Map<String, String> currencyData, List<String> primaryCurrencyList) {
        System.out.println("Monedas disponibles:");
        System.out.println("Elija una moneda de la siguiente lista:");
        List<String> currentCurrencyList = showAdditionalCurrencies ? getAllCurrencies(currencyData) : primaryCurrencyList;
        for (int i = 0; i < currentCurrencyList.size(); i++) {
            String currencyCode = currentCurrencyList.get(i);
            String currencyName = currencyData.get(currencyCode);
            System.out.printf("%d. %s - %s%n", i + 1, currencyCode, currencyName);
        }

        if (showAdditionalCurrencies) {
            System.out.println("Si desea ver menos monedas, escriba 'menos'");
        } else {
            System.out.println("Si desea ver más monedas, escriba 'mas'");
        }
        System.out.println("==================");
        return currentCurrencyList;
    }

    /**
     * Maneja la lógica para alternar entre mostrar más o menos monedas.
     *
     * @param choice   La elección del usuario.
     * @param scanner  El escáner para leer la entrada del usuario.
     * @return true si la lista de monedas se actualizó, false en caso contrario.
     */
    private static boolean handleCurrencyListToggle(String choice, Scanner scanner) {
        if (choice.equalsIgnoreCase("mas")) {
            if (!showAdditionalCurrencies) {
                showAdditionalCurrencies = true;
            } else {
                showMessageAndWaitForOk(scanner, "Ya está viendo todas las monedas.  Por favor, escriba ok..");
            }
            return true;
        } else if (choice.equalsIgnoreCase("menos")) {
            if (showAdditionalCurrencies) {
                showAdditionalCurrencies = false;
            } else {
                showMessageAndWaitForOk(scanner, "Ya está viendo las monedas principales. Por favor, escriba ok.");
            }
            return true;
        }
        return false;
    }

    /**
     * Selecciona una moneda basándose en la elección del usuario.
     *
     * @param scanner        El escáner para leer la entrada del usuario.
     * @param choice         La elección del usuario.
     * @param currencyList   La lista de monedas disponibles.
     * @return El código de la moneda seleccionada o null si la elección es inválida.
     */
    private static String selectCurrency(Scanner scanner, String choice, List<String> currencyList) {
        if (!choice.matches("\\d+")) {
            showInvalidInputMessage(scanner, "Debe ingresar un número o 'mas' o 'menos'.");
            return null;
        }

        int currencyIndex = Integer.parseInt(choice) - 1;
        if (currencyIndex < 0 || currencyIndex >= currencyList.size()) {
            showInvalidInputMessage(scanner, "Número no válido. Por favor, seleccione un número de la lista.");
            return null;
        }

        return currencyList.get(currencyIndex);
    }

    /**
     * Muestra un mensaje de entrada inválida y espera a que el usuario ingrese 'ok' para continuar.
     *
     * @param scanner  El escáner para leer la entrada del usuario.
     * @param message  El mensaje a mostrar.
     */
    private static void showInvalidInputMessage(Scanner scanner, String message) {
        System.out.println("Entrada no válida. " + message);
        showMessageAndWaitForOk(scanner, "Ingrese 'Ok' para continuar.");
    }

    /**
     * Muestra un mensaje y espera a que el usuario ingrese 'ok' para continuar.
     *
     * @param scanner  El escáner para leer la entrada del usuario.
     * @param message  El mensaje a mostrar.
     */
    private static void showMessageAndWaitForOk(Scanner scanner, String message) {
        System.out.println(message);
        while (!scanner.next().equalsIgnoreCase("ok")) {
            System.out.println("Por favor, ingrese 'ok' para continuar.");
        }
    }

    /**
     * Pregunta al usuario si desea realizar una acción y devuelve true si la respuesta es 'si'.
     *
     * @param scanner  El escáner para leer la entrada del usuario.
     * @return true si el usuario responde 'si', false en caso contrario.
     */
    private static boolean askYesOrNo(Scanner scanner) {
        String choice;
        while (true) {
            choice = scanner.next().toLowerCase();
            if (choice.equals("si") || choice.equals("no")) {
                return choice.equals("si");
            } else {
                System.out.println("Respuesta no válida. Por favor, responda 'si' o 'no'.");
            }
        }
    }

    /**
     * Obtiene todas las monedas disponibles combinando las monedas principales y adicionales.
     *
     * @param currencyData  Datos de las monedas obtenidos de un servicio externo.
     * @return Una lista de todos los códigos de monedas disponibles.
     */
    private static List<String> getAllCurrencies(Map<String, String> currencyData) {
        List<String> allCurrencies = new ArrayList<>(additionalCurrencies);
        for (PrimaryCurrency currency : PrimaryCurrency.values()) {
            allCurrencies.add(currency.name());
        }
        return allCurrencies;
    }
}
