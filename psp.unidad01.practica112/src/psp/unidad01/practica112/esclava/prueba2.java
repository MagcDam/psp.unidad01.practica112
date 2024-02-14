package psp.unidad01.practica112.esclava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class prueba2 {
	
	private String ruta = "archivoprueba\\\\serie.txt";
	
	public static void main(String[] args) {
		/*
		 * Recibe los valores del maestro
		 */
		List<String> contenidoFichero = new ArrayList<>();
		String linea;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Introduce el contenido del archivo (pulsa Enter dos veces para finalizar):");
			while (!(linea = br.readLine()).isEmpty()) {
				contenidoFichero.add(linea);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Integer> ficheroInt = stringToInt(contenidoFichero);
		Map<Integer, Integer> mapaNumeros = ficheroToMapa(ficheroInt);
		
		//No se si es la mejor manera de pasar el mapa
		System.out.println(mapaNumeros);
		System.out.flush();
		System.out.close();

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
