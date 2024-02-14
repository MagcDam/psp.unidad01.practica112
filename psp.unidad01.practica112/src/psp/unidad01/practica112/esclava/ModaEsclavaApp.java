package psp.unidad01.practica112.esclava;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ModaEsclavaApp {

	public static void main(String[] args) {
		/*
		 * Recibe los valores del maestro
		 */
		Scanner sc = new Scanner(System.in);
		
		List<String> contenidoFichero = new ArrayList<>();
		
		while (sc.hasNextLine()) {
			String linea = sc.nextLine();
			if (linea.isEmpty()) {
				break;
			}
			contenidoFichero.add(linea);
		}
		sc.close();
		
		List<Integer> ficheroInt = stringToInt(contenidoFichero);
		Map<Integer, Integer> mapaNumeros = ficheroToMapa(ficheroInt);
		
        // Imprime el mapa en formato clave=valor en la salida estándar
        for (Map.Entry<Integer, Integer> entry : mapaNumeros.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }

	}
	
	/**
	 * Metodo que cambia los valores de String a Integer
	 * @param contenidoFichero
	 * @return
	 */
	public static List<Integer> stringToInt(List<String> contenidoFichero) {
		List<Integer> listaNumeros = new ArrayList<>();
		
		for (String cadena : contenidoFichero) {
			int numero = Integer.parseInt(cadena);
			listaNumeros.add(numero);
		}
		
		return listaNumeros;
	}

	/**
	 * Metodo que mete los valores en un mapa sin duplicados
	 * @param ficheroInt
	 * @return
	 */
	public static Map<Integer, Integer> ficheroToMapa(List<Integer> ficheroInt) {
		Map<Integer, Integer> mapaNumeros = new HashMap<>();
		
		for (int numero : ficheroInt) {
			if (mapaNumeros.containsKey(numero)) {
				mapaNumeros.put(numero, mapaNumeros.get(numero) + 1);
			} else {
				mapaNumeros.put(numero, 1);
			}
		}
		
		return mapaNumeros;
	}
	

	
}
