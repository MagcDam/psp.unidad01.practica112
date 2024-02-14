package psp.unidad01.practica112.maestra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ModaApp {
	
	private static final String JAR_ESCLAVO = "prueba1.jar";
	
	public static void main(String[] args) throws IOException{
		
		/*
		 * Introducimos la ruta del archivo, en caso de que el archivo  no se encuentre
		 * lanzara un error.
		 */
		if (args.length < 1) {
		  System.err.print("RUTA NO INTRODUCIDA O NO ACCESIBLE");
		  System.exit(0);
		}
		String ruta = args[0];
		
		/*
		 *Definimos la cantidad de procesos 
		 */
		int numProcesadores = numeroProcesosAleatorio();
		
		/*
		 * Creamos la factoria
		 */
		ProcessBuilder builder = new ProcessBuilder("java", "-jar", JAR_ESCLAVO);
		
		/*
		 * Creamos los procesos
		 */
		List<Process> procesos = new ArrayList<>();
		for (int i = 0; i < numProcesadores; i++) {
			procesos.add(builder.start());
		}
		
		/*
		 * Iniciamos los metodos
		 */
		int longitudSerie = longitudSerie(ruta);
		int[] ficheroArray = archivoToArray(ruta);
		
		/*
		 * Pasamos los valores a los esclavos
		 */
		int vuelta = 0;
		for (Process proceso: procesos) {
			BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(proceso.getOutputStream()));
			PrintWriter printer = new PrintWriter(escritor);
			for (int i = vuelta + (longitudSerie / numProcesadores); i < ((vuelta + 1) * (longitudSerie / numProcesadores)); i++) {
				printer.println(ficheroArray[i]);
			}
			printer.println();
			printer.flush();
			printer.close();
			vuelta++;
			System.out.println("pasa");
		}
		
		/*
		 * Recogemos los valores de los esclavos
		 */
		Map<Integer, Integer> mapaNumeros = new HashMap<>();
		
		for (Process proceso: procesos) {
			Map<Integer, Integer> resultadoEsclavo = recibeEsclavo(proceso);
			for(Map.Entry<Integer, Integer> entry : resultadoEsclavo.entrySet()) {
				int clave = entry.getKey();
				int valor = entry.getValue();
				mapaNumeros.put(clave, valor);
			}
		}
		
		int valorM = cantidadRepeticiones(mapaNumeros);
		int claveM = valorMasRepetido(mapaNumeros);
		
		System.out.println("El valor mas repetido es " + claveM + " con un total de " + valorM + " repeticiones.");
		
	}
	
	/**
	 * Metodo que genera un numero aleatorio de procesos para el programa
	 * @return
	 */
	public static int numeroProcesosAleatorio() {
		//Creamos el generador aleatorio
		Random random = new Random();
		
		//Genera un numero aleatorio entre 2 y el maximo que tenga el equipo
		int minProcesos = 2;
		int maxProcesos = Runtime.getRuntime().availableProcessors();
		int numeroProcesos = random.nextInt(maxProcesos - minProcesos + 1) + minProcesos;
		
		return numeroProcesos;
	}
	
	/**
	 * Creamos un metodo que nos cuente la cantidad de lineas que tiene el archivo
	 * @param ruta
	 * @return
	 */
	public static int longitudSerie(String ruta) {
		int cuentaLinea = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
			String linea;
			
			while ((linea = br.readLine()) != null) {
				cuentaLinea++;
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cuentaLinea;
	}
	
	/**
	 * Creamos un metodo que introduzca los valores del archivo en un array
	 * @param ruta
	 * @return
	 */
	public static int[] archivoToArray(String ruta) {
		ArrayList<Integer> listaContenido = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
			String linea;
			
			while((linea = br.readLine()) != null) {
				int numero = Integer.parseInt(linea);
				listaContenido.add(numero);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int[] contenido = new int[listaContenido.size()];
		for (int i = 0; i < listaContenido.size(); i++) {
			contenido[i] = listaContenido.get(i);
		}
		
		return contenido;
	}
	
	/**
	 * Creamos un metodo que nos permita sacar el numero que más veces se repite
	 * @param mapaNumeros
	 * @return
	 */
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
	
	/**
	 * Creamos un metodo que nos permita sacar la cantidad de veces que se repite el numero
	 * @param mapaNumeros
	 * @return
	 */
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
	
	/**
	 * Metodo que recibe los valores de los esclavos y los mete en un mapa
	 * @param proceso
	 * @return
	 */
	public static Map<Integer, Integer> recibeEsclavo(Process proceso) {
		Map<Integer, Integer> mapaNumeros = new HashMap<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
		String linea;
		try {
			while ((linea = br.readLine()) != null) {
				if (!linea.isEmpty()) {
					String[] partes = linea.split("=");
					if (partes.length == 2) {
						int clave = Integer.parseInt(partes[0]);
						int valor = Integer.parseInt(partes[1]);
						mapaNumeros.put(clave, mapaNumeros.getOrDefault(clave, 0) + valor);
					} else {
						System.err.println("Formato incorrecto " + linea);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mapaNumeros;
	}

}
