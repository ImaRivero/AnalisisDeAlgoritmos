/*
	IPN - ESCOM
	Análisis de Algoritmos
	Prof: Benjamín Luna Benoso
	Grupo: 3CV1
	Práctca 4: Divide y vencerás
	---------------------------------------------
    Creación: 1/Diciembre/2020
    Creado por: 
        + Omar Imanol Rivero Ronquillo
		+ Luis Eduardo Valle Martínez
		
	Funcionamiento
	>>>>>>>>>>>>>>
		Programa que permite graficar la complejidad de 2 algoritmo 
		populares de ordenamiento(MergeSort, QuickSort), mediante
		el conteo del número de operaciones realizadas para diferentes
		tamaños de arreglos.

		Es necesario inidicar el algoritmo que se quiere evaluar mediante
		el ingreso de parámetros precedidos por un guión donde:
			-m : Merge
			-M : MergeSort
			-p : Partition
			-q : QuickSort

		Es necesario indicar la lista de valores de tamaños para los
		arreglos que se van a evaluar, de forma que como segundo parámetro
		del programa se indica la ruta absoluta o relativa, al archivo
		que debe contener números enteros separados por comas.

		Se implementa además una clase que permite crear arreglos con valores
		aleatorios entre 1 y 9 de un tamaño indicado, así como arreglos
		semi-ordenados indicando solamente el tamaño y el límite que divide a
		los 2 subarreglos ordenados.

		Al terminar la ejecución, se abre una pantalla automáticamente que
		muestra una gráfica elaborada a partir de los valores obtenidos, y
		los pares ordenados utilizados para el grafo se mostrarán en la
		consola precedidos por una 'P' y el número de punto.
*/

import java.util.*;

import javax.swing.text.StyledEditorKit;

import java.lang.Math;

public class CuartaPractica {
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
			 * 	- m//Merge = 1
			 * 	- M//MergeSort = 2
			 * 	- p//Partition = 4
			 * 	- q//QuickSort = 8
			 */

			if (args.length == 1) // Solo se ingresa un argumento
				error("Debe ser ingresado al menos un argumento para especificar el algoritmo y la ruta de un archivo con los valores del tamaño de los arreglos");
			else { // Se debe identifican los argumentos ingresados
				if (rutaArchivo.indexOf('-') >= 0) {
					rutaArchivo = args[1];

					for (char argumento : args[0].toCharArray()) {
						switch (argumento) {
							case 'm':
								argumentos += 1;
								break;
							case 'M':
								argumentos += 2;
								break;
							case 'p':
								argumentos += 4;
								break;
							case 'q':
								argumentos += 8;
								break;
						}
					}
				}
			}

			/*
				Ejecución de los algoritmos de ordenamiento
			*/
			ArrayList<Integer> listaN = Archivos.archivo(rutaArchivo).getValores(); // Lista de valores de n
			Graficas grafica = new Graficas(1, 1);

			// Ejecución de los algoritmo según los bits activos de los argumentos ingresados
			if ((argumentos & 1) > 0) { // Algoritmo Merge
				int indiceColeccion = -1,
				punto = 1;

				System.out.println("\n Merge  \n---------------");
				for (Integer n : listaN) {
					int[]A = Arreglos.crearArregloParcialmenteArreglado(n,(int)n/2);

					AlgoritmosOrdenamiento.numeroOperaciones = 0;
					A = AlgoritmosOrdenamiento.Merge(A,0,(int)(n/2)-1,n-1);

					System.out.printf("P%d( %d ,%d )\n",punto,n,(int)AlgoritmosOrdenamiento.numeroOperaciones);
					punto ++;

					indiceColeccion = grafica.agregarParOrdenado(
						"Merge", 
						(double) n,
						AlgoritmosOrdenamiento.numeroOperaciones, 
						indiceColeccion);
				}

				grafica.crearGrafica(
					"Graficación del tamaño n del arreglo vs instrucciones ejecutadas para el algoritmo Merge",
					"Valor(n)", 
					"NúmeroOperaciones", 
					indiceColeccion);
			}

			if ((argumentos & 2) > 0) { // Algoritmo MergeSort
				int indiceColeccion = -1,
				punto = 1;

				System.out.println("\n MergeSort  \n---------------");
				for (Integer n : listaN) {
					int[]A = Arreglos.crearArreglo(n);

					AlgoritmosOrdenamiento.numeroOperaciones = 0;
					A = AlgoritmosOrdenamiento.MergeSort(A,0,n-1);

					System.out.printf("P%d( %d ,%g )\n",punto,n,AlgoritmosOrdenamiento.numeroOperaciones);
					punto ++;

					indiceColeccion = grafica.agregarParOrdenado(
						"MergeSort", 
						(double) n,
						AlgoritmosOrdenamiento.numeroOperaciones, 
						indiceColeccion);
				}

				grafica.crearGrafica(
					"Graficación del tamaño n del arreglo vs instrucciones ejecutadas para el algoritmo MergeSort",
					"Valor(n)", 
					"NúmeroOperaciones", 
					indiceColeccion);
			}

			if ((argumentos & 4) > 0) { // Algoritmo Partition
				int indiceColeccion = -1,
				punto = 1;

				System.out.println("\n Partition  \n---------------");
				for (Integer n : listaN) {

					//int[]A = Arreglos.crearArreglo(n);
					AlgoritmosOrdenamiento.numeroOperaciones = 0;
					//A = AlgoritmosOrdenamiento.MergeSort(A,0,n-1);

					System.out.printf(" P%d( %d ,%g )\n",punto,n,AlgoritmosOrdenamiento.numeroOperaciones);
					punto ++;

					indiceColeccion = grafica.agregarParOrdenado(
						"Partition", 
						(double) n,
						AlgoritmosOrdenamiento.numeroOperaciones, 
						indiceColeccion);
				}

				grafica.crearGrafica(
					"Graficación del tamaño n del arreglo vs instrucciones ejecutadas para el algoritmo Partition",
					"Valor(n)", 
					"NúmeroOperaciones", 
					indiceColeccion);
			}

			if ((argumentos & 8) > 0) { // Algoritmo QuickSort
				int indiceColeccion = -1,
				punto = 1;

				System.out.println("\n QuickSort  \n---------------");
				for (Integer n : listaN) {

					//int[]A = Arreglos.crearArreglo(n);
					AlgoritmosOrdenamiento.numeroOperaciones = 0;
					//A = AlgoritmosOrdenamiento.MergeSort(A,0,n-1);

					System.out.printf(" P%d( %d ,%d )\n",punto,n,AlgoritmosOrdenamiento.numeroOperaciones);
					punto ++;

					indiceColeccion = grafica.agregarParOrdenado(
						"QuickSort", 
						(double) n,
						AlgoritmosOrdenamiento.numeroOperaciones, 
						indiceColeccion);
				}

				grafica.crearGrafica(
					"Graficación del tamaño n del arreglo vs instrucciones ejecutadas para el algoritmo QuickSort",
					"Valor(n)", 
					"NúmeroOperaciones", 
					indiceColeccion);
			}

			grafica.mostrarGrafica();

		} else
			CuartaPractica.error("Son necesarios argumentos para la ejecución del programa\n" + ANSI_GREEN
					+ "i.e: $ java CuartaPractica -[m,M,p,Q] archivo_valores_n");
	}
}

/*
	Clase definitoria de algoritmos de ordenamiento para arreglos con números
*/
class AlgoritmosOrdenamiento{
	public static double numeroOperaciones;

	public static int[] Merge(int A[], int p, int q, int r){ 
        int n1 = q - p + 1; 
		int n2 = r - q;
		numeroOperaciones += 1;
		
		// Creación e inicialización de los subarreglos
        int L[] = new int [n1]; 
        int R[] = new int [n2]; 
		numeroOperaciones += 1;
		
        for (int i=0; i<n1; ++i){
			L[i] = A[p + i]; 
			numeroOperaciones += 1;
		}
        for (int j=0; j<n2; ++j){
            R[j] = A[q + 1+ j]; 
			numeroOperaciones += 1;
		}
		
        int i = 0, j = 0; 
		numeroOperaciones += 1;
		// Inicio del ordenamiento en el arreglo de entrada A
        int k = p; 
		numeroOperaciones += 1;
        while (i < n1 && j < n2) { 
            if (L[i] <= R[j]){ 
                A[k] = L[i]; 
                i++; 
            }else{ 
                A[k] = R[j]; 
                j++; 
            } 
            k++; 
			numeroOperaciones += 1;
        } 
		// Se termina de ingresar los valores de los subarreglos sin recorrer completamente
        while (i < n1){ 
            A[k] = L[i]; 
            i++; 
            k++; 
			numeroOperaciones += 1;
        } 
  
        while (j < n2){ 
            A[k] = R[j]; 
            j++; 
            k++; 
			numeroOperaciones += 1;
		}
		
		return A;
    }

	public static int[] MergeSort(int[]A,int p,int r){
		if(p<r){
			int q=(int)((p+r)/2);
			numeroOperaciones +=1;		
			
			A = MergeSort(A,p,q);
			numeroOperaciones +=1;		
			A = MergeSort(A,q+1,r);
			numeroOperaciones +=1;		
			A = Merge(A, p, q, r);
		}
		return A;
	}
	
	public static void Partition(){}
	
	public static void QuickSort(){}
}

// Clase con método para la creación de arreglos con números aleatorios
class Arreglos{
	// Crea un arreglo de números enteros entre los límites definidos
	public static int[] crearArreglo(int n){
		int[]arreglo = new int[n];

		for(int i=0;i<n;i++)
			arreglo[i] = (int)(Math.random()*9 + 1);
		
		return arreglo;
	}

	// Crea un arreglo de números enteros con 2 subarreglos ordenados ascendentemente, se dividen los subarreglos por el valor ingresado en separador
	public static int[] crearArregloParcialmenteArreglado(int n,int separador){
		int[] array = crearArreglo(n);
		array = AlgoritmosOrdenamiento.MergeSort(array, 0, separador-1);
		array = AlgoritmosOrdenamiento.MergeSort(array, separador, n-1);
		return array;
	}
}