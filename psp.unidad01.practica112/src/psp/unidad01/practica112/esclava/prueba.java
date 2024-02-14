package psp.unidad01.practica112.esclava;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class prueba {

    public static void main(String[] args) {
        String rutaArchivo = "archivoprueba\\serie.txt";
        Map<Integer, Integer> mapaNumeros = leerArchivo(rutaArchivo);
        int claveM = valorMasRepetido(mapaNumeros);
        int valorM = cantidadRepeticiones(mapaNumeros);
        System.out.println("El valor que mas se repite es " + claveM + " con " + valorM + " veces");
    }

    public static Map<Integer, Integer> leerArchivo(String rutaArchivo) {
        Map<Integer, Integer> mapaNumeros = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                int numero = Integer.parseInt(linea);
                if (mapaNumeros.containsKey(numero)) {
                    // Si el número ya está en el mapa, aumenta su cantidad en 1
                    mapaNumeros.put(numero, mapaNumeros.get(numero) + 1);
                } else {
                    // Si el número no está en el mapa, lo agrega con cantidad 1
                    mapaNumeros.put(numero, 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mapaNumeros;
    }
    
	public static int valorMasRepetido(Map<Integer, Integer> mapaNumeros) {
		int valorM = 0;
		int claveM = 0;
		
		
		for (Map.Entry<Integer, Integer> entry : mapaNumeros.entrySet()) {
			Integer clave = entry.getKey();
			Integer valor = entry.getValue();
			
			if (valorM == 0 || valor.compareTo(valorM) > 0) {
				valorM = valor;
				claveM = clave;
			}
		}
		
		return claveM;
		
	}
	
	public static int cantidadRepeticiones(Map<Integer, Integer> mapaNumeros) {
		int valorM = 0;
		int claveM = 0;
		
		
		for (Map.Entry<Integer, Integer> entry : mapaNumeros.entrySet()) {
			Integer clave = entry.getKey();
			Integer valor = entry.getValue();
			
			if (valorM == 0 || valor.compareTo(valorM) > 0) {
				valorM = valor;
				claveM = clave;
			}
		}
		
		return valorM;
	}
	
}
