package moneycalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class MoneyCalculator {

    double cantidad;
    double cambio;
    String divisa;
    String divisa2;
    
    public static void main(String[] args) throws IOException {
        MoneyCalculator calcular = new MoneyCalculator();
        calcular.control();
    }
    

    private static double Cambiar(String dinero, String cambio) throws MalformedURLException, IOException {
        if(dinero.equals(cambio)) return 1;
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

    private void control() throws IOException{
        intput();
        process();
        output();
        
    }

    private void intput() {
        System.out.println("Introduzca cantidad: ");
        Scanner scanner = new Scanner (System.in);
        cantidad = scanner.nextDouble();
        
        System.out.println("Introduce una divisa: ");
        divisa = scanner.next();
        
        System.out.println("Introduce una divisa a cambiar: ");
        divisa2 = scanner.next();
        
    }

    private void process() throws IOException{
        cambio = Cambiar(divisa, divisa2);
    }

    private void output() {
        System.out.println(cantidad + " " + divisa + " = " + cantidad*cambio
                + " " + divisa2);
    }
}
