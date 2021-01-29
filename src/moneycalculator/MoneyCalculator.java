package moneycalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class MoneyCalculator {

    public static void main(String[] args) throws IOException {
        
        System.out.println("Introduce una cantidad de dólares: ");
        Scanner scanner = new Scanner(System.in);
        double cantidad = scanner.nextDouble();
        double cambio = Cambiar("USD","EUR");
        System.out.println(cantidad + " dolares equivalen a " 
                + cantidad*cambio + " euros");   
    }

    private static double Cambiar(String dinero, String cambio) throws MalformedURLException, IOException {
        if (dinero.equals(cambio)) return 1;
        URL url = new URL("https://api.exchangeratesapi.io/latest?base=" + dinero 
                + "&symbols=" + cambio + "&compact=y");
        
        URLConnection conexion = url.openConnection();
        
        try (BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(conexion.getInputStream()))){
            String linea = reader.readLine();
            String linea1 = linea.substring(linea.indexOf(cambio)+5, linea.indexOf("}"));
            return Double.parseDouble(linea1);
            
        }
    }
}
