# Aplicación de Conversor de Monedas

Esta es una aplicación sencilla basada en consola para la conversión de monedas. Permite a los usuarios seleccionar monedas, ingresar un monto y obtener el valor convertido. La aplicación obtiene datos de monedas y tasas de cambio de un servicio externo.

## Características

- **Monedas Principales**: La aplicación soporta monedas principales como CLP, ARS, COP, USD, BOB y BRL.
- **Monedas Adicionales**: Los usuarios pueden elegir ver y convertir monedas adicionales como EUR, GBP, JPY, AUD, CAD y muchas más.
- **Historial de Conversiones**: La aplicación mantiene un historial de conversiones que puede ser visto por el usuario.

## Requisitos

- Java 8 o superior.
- Conexión a Internet para obtener datos de monedas y tasas de cambio.

## Ejecución del Proyecto

Este proyecto está configurado para ser ejecutado en IntelliJ IDEA. Asegúrate de tener instalado IntelliJ IDEA en tu máquina antes de continuar.

### Pasos para Ejecutar el Proyecto en IntelliJ IDEA

1. **Clonar el Repositorio**:
   - Abre IntelliJ IDEA.
   - Clona el repositorio desde GitHub usando la opción de `Checkout from Version Control` en el menú principal.

2. **Importar el Proyecto**:
   - En IntelliJ IDEA, selecciona `File` > `Open...` y navega hasta el directorio donde clonaste el repositorio.
   - Selecciona el directorio del proyecto y haz clic en `Open`.

3. **Configurar el Proyecto**:
   - IntelliJ IDEA debería reconocer automáticamente el proyecto como un proyecto de Java.
   - Verifica la configuración del SDK de Java en `File` > `Project Structure...` > `Project` > `Project SDK`. Asegúrate de tener configurado un JDK compatible.

4. **Compilar y Ejecutar**:
   - Una vez configurado, puedes compilar y ejecutar la aplicación principal (`CurrencyConverterApp.java`) desde IntelliJ IDEA.

5. **Explorar y Contribuir**:
   - Explora el código, realiza cambios y contribuciones según sea necesario.

## Uso

1. **Selecciona Monedas**: La aplicación te mostrará una lista de monedas disponibles para conversión. Puedes elegir entre las monedas principales o ver más monedas adicionales.
2. **Moneda de Origen**: Selecciona la moneda de origen ingresando el número correspondiente.
3. **Moneda de Destino**: Selecciona la moneda de destino ingresando el número correspondiente.
4. **Monto a Convertir**: Ingresa el monto que deseas convertir.
5. **Resultado**: La aplicación mostrará el monto convertido.
6. **Historial de Conversiones**: Al finalizar, puedes optar por ver el historial de conversiones realizadas.

## Ejemplo de Ejecución

Bienvenido al convertidor de monedas  
Monedas disponibles:

- CLP - Peso Chileno
- ARS - Peso Argentino
- COP - Peso Colombiano
- USD - Dólar Estadounidense
- BOB - Boliviano
- BRL - Real Brasileño

Si desea ver más monedas, escriba 'mas'  

==================  
Seleccione el número de la moneda de origen: 1  
Seleccione el número de la moneda de destino: 4  
Ingrese el monto a convertir: 1000  
Monto convertido: 1.23 USD  
¿Desea realizar otra conversión? (si/no): no  
¿Desea ver el historial de conversiones? (si/no): si  
Historial de conversiones:  
De CLP a USD: 1000.0 convertido a 1.23  



