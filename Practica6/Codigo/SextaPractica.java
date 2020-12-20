/*
	IPN - ESCOM
	Análisis de Algoritmos
	Prof: Benjamín Luna Benoso
	Grupo: 3CV1
	Práctca 6:Algoritmos Greedy. Programación Dinámica
	---------------------------------------------
    Creación: 17/Diciembre/2020
    Creado por: 
        + Omar Imanol Rivero Ronquillo
		+ Luis Eduardo Valle Martínez
		
	Funcionamiento
	>>>>>>>>>>>>>>
		El programa permite la ejecución de 2 algoritmos desarrollados
		con el principio de la programación dinámica: Fibonacci(Top-down,Bottom-up)
		y la variante de la Mochila 0-1(Knapsack)
		
		Es necesario indicar el algoritmo que se quiere evaluar mediante
		el ingreso de parámetros precedidos por un guión donde:
			-f : Algoritmo de Fibonacci
			-k : Algoritmo de Mochila 0-1
			
	Compilación
	+++++++++++
		$ javac -cp .:../Bibliotecas/jfreechart-1.0.19.jar:../Bibliotecas/jcommon-1.0.23.jar SextaPractica.java

	Ejecución Fibonacci
	+++++++++++++++++
		$ java -cp .:../Bibliotecas/jfreechart-1.0.19.jar:../Bibliotecas/jcommon-1.0.23.jar SextaPractica -f ../Archivos/ValoresFibonacci

	Ejecución Mochila 0-1
	+++++++++++++++++
		$ java -cp :../Bibliotecas/jfreechart-1.0.19.jar:../Bibliotecas/jcommon-1.0.23.jar SextaPractica -k ../Archivos/ObjetosMochila
*/

import java.util.*;

import java.lang.Math;

public class SextaPractica {
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
			 * 	- f/Fibonacci = 1
			 * 	- k/Mochila 0-1 = 2
			 */

			if (args.length == 1) // Solo se ingresa un argumento
				error("Debe ser ingresado al menos un argumento para especificar el algoritmo y/o la ruta de un archivo con los valores del tamaño de los arreglos");
			else { // Se debe identifican los argumentos ingresados
				if (args[0].indexOf('-') >= 0) {
					rutaArchivo = args[1];

					for (char argumento : args[0].toCharArray()) {
						switch (argumento) {
							case 'k':
								argumentos += 2;
								break;
							case 'f':
								argumentos += 1;
								break;
						}
					}
				}
			}
			/*
				Ejecución de los algoritmos
			*/
			Graficas grafica = new Graficas(1, 1);

			// Ejecución de los algoritmo según los bits activos de los argumentos ingresados
			if ((argumentos & 1) > 0) { // Algoritmo Fibonacci
				new ControladorFibonacci().init(rutaArchivo);
			}

			if ((argumentos & 2) > 0) { // Algoritmo Mochila 0-1
				int indiceColeccion = -1;
				System.out.println("\n Mochila 0-1 \n---------------");
				ArrayList<Integer> pesosMochila = Archivos.archivo(rutaArchivo).getPesoMaximoMochila();
				ArrayList<ArrayList<ObjetoKnapsack>> conjuntoObjetos = Archivos.archivo(rutaArchivo).getObjetosMochila();

				for(int i=0;i<pesosMochila.size();i++){ // Se obtiene el conjunto de conjunto de objetos para la mochila
					
					ArrayList<ObjetoKnapsack> objetos = conjuntoObjetos.get(i);
					
					Knapsack mochila = new Knapsack(pesosMochila.get(i));
					mochila.knapsack(objetos);
					mochila.imprimirTablaDinamica(objetos);
					mochila.imprimirObjetosIngresados(mochila.test(objetos));
					System.out.printf("P%d( %d ,%d )\n",i,(int)objetos.size(),(int)mochila.numeroOperaciones);

					indiceColeccion = grafica.agregarParOrdenado(
						"Knapsack", 
						(double)objetos.size(),
						mochila.numeroOperaciones, 
						indiceColeccion);
				}

				grafica.crearGrafica(
					"Graficación del número de objetos vs instrucciones ejecutadas para el algoritmo de la mochila completa",
					"Valor(n)", 
					"NúmeroOperaciones", 
					indiceColeccion);
				grafica.mostrarGrafica();
			}

			

		} else
			SextaPractica.error("Son necesarios argumentos para la ejecución del programa\n" + ANSI_GREEN
					+ "i.e: $ java SextaPractica -[f,k] archivo_valores");
	}
}
