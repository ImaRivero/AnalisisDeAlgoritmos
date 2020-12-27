/*
	IPN - ESCOM
	Análisis de Algoritmos
	Prof: Benjamín Luna Benoso
	Grupo: 3CV1
	Práctca 7: Programación Dinámica
	---------------------------------------------
    Creación: 25/Diciembre/2020
    Creado por: 
        + Omar Imanol Rivero Ronquillo
		+ Luis Eduardo Valle Martínez
		
	Funcionamiento
	>>>>>>>>>>>>>>
		El programa permite la ejecución de 2 algoritmos desarrollados
		con el principio de la programación dinámica: Subsecuencia común más larga 
		y Multiplicación de una secuencia de matrices
		
		Es necesario indicar el algoritmo que se quiere evaluar mediante
		el ingreso de parámetros precedidos por un guión donde:
			-c : Subsecuencia común más larga
			-m : Multiplicación de secuencia de matrices
			
	Compilación
	+++++++++++
		$ javac SeptimaPractica.java

	Ejecución LCS
	+++++++++++++
		$ java -[c/m] SeptimaPractica archivo_datos

	Ejecución Multiplicación de matrices
	++++++++++++++++++++++++++++++++++++
		$ java -cp :../Bibliotecas/jfreechart-1.0.19.jar:../Bibliotecas/jcommon-1.0.23.jar SeptimaPractica -k ../Archivos/ObjetosMochila
*/

import java.util.*;

import java.lang.Math;

public class SeptimaPractica {
	// Constantes de colores para impresión en consola
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_BLUE = "\u001B[34m";

	// Imprime un mensaje de error y termina la ejecución del programa
	public static void error(String mensaje) {
		System.out.printf(ANSI_YELLOW + "\nERROR >> %s" + ANSI_RESET + "\n", mensaje);
		System.exit(-1);
	}

	// Imprime un mensaje con cierta estructura en la consola
	public static void mensaje(String mensaje) {
		System.out.printf(ANSI_GREEN + "\n --- %s ---" + ANSI_RESET + "\n", mensaje);
	}

	public static void main(String[] args) {

		if (args.length > 0) { // Verifica que se hayan ingresado argumentos de ejecución
			String rutaArchivo = args[0];
			int argumentos = 0;
			/*
			 * Argumentos//decimal: 
			 * 	- c/LCS = 1
			 * 	- m/Multiplicación de matrices = 2
			 */

			if (args.length == 1) // Solo se ingresa un argumento
				error("Debe ser ingresado al menos un argumento para especificar el algoritmo y/o la ruta de un archivo con los valores del tamaño de los arreglos");
			else { // Se debe identifican los argumentos ingresados
				if (args[0].indexOf('-') >= 0) {
					rutaArchivo = args[1];

					for (char argumento : args[0].toCharArray()) {
						switch (argumento) {
							case 'm':
								argumentos += 2;
								break;
							case 'c':
								argumentos += 1;
								break;
						}
					}
				}
			}
			/*
				Ejecución de los algoritmos
			*/

			// Ejecución de los algoritmo según los bits activos de los argumentos ingresados
			if ((argumentos & 1) > 0) { // Algoritmo LCS
				LCS lcs = new LCS();
				lcs.init();
			}

			if ((argumentos & 2) > 0) { // Algoritmo Multipliación de una secuencia de Matrices
				ArrayList<Object> dimensiones = Archivos.archivo(rutaArchivo).getDimensionesMatrices();
				String nombreMatrices =(String)dimensiones.remove(0);
				Integer P[] = dimensiones.toArray(new Integer[dimensiones.size()]);
				
				MSM msm = new MSM();
				msm.secuenciaMatrices(P);
				msm.imprimirMatrices();
				System.out.print("\nConfiguración de paréntesis : ");
				msm.imprimirParentesis(nombreMatrices,0,P.length-2);
				System.out.println("\n");
			}

		} else
			SeptimaPractica.error("Son necesarios argumentos para la ejecución del programa\n" + ANSI_GREEN
					+ "i.e: $ java SeptimaPractica -[c/m] archivo_valores");
	}
}
