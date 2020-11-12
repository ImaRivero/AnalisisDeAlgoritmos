/*
	IPN - ESCOM
	Análisis de Algoritmos
	Prof: Benjamín Luna Benoso
	Grupo: 3CV1
	Práctca 2: Funciones Recursivas vs Iterativas
	---------------------------------------------
    Creación: 6/Noviembre/2020
    Creado por: 
        + Omar Imanol Rivero Ronquillo
        + Luis Eduardo Valle Martínez
*/

import java.security.Principal;
import java.util.*;

public class TercerPractica {
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
			 * Argumentos(decimal): 
			 * 	- f(función de fibonacci) = 4 
			 * 		+ i(iterativo) = 1 
			 * 		+ r(recursivo) = 2 
			 * - p(función número perfecto) = 32
			 * 		+ e(¿es perfecto?) = 8
			 * 		+ l(lista de n perfectos) = 16
			 */

			if (args.length == 1) { // Cuando se ingresa un solo argumento debe ser la ruta del archivo de forma
									// obligatoria
				if (rutaArchivo.indexOf('-') < 0)
					TercerPractica.error("Es necesario ingresar la ruta del archivo con los valores de ejecución");
				argumentos = 45; // Fibonacci iterativo e identificación si es número perfecto
			} else { // Se debe identificar el orden correcto de los argumentos y el valor de estos
				if (rutaArchivo.indexOf('-') >= 0) {
					rutaArchivo = args[1];
					for (char argumento : args[0].toCharArray()) {
						switch (argumento) {
							case 'f':
								argumentos += 4;
								break;
							case 'i':
								if ((argumentos & 4) > 0)
									argumentos += 1;
								else
									TercerPractica.error(
											"Debes de colocar el argumento 'f' antes de indicar el tipo de función fibonacci");
								break;
							case 'r':
								if ((argumentos & 4) > 0)
									argumentos += 2;
								else
									TercerPractica.error(
											"Debes de colocar el argumento 'f' antes de indicar el tipo de función fibonacci");
								break;
							case 'p':
								argumentos += 32;
								break;
							case 'e':
								if ((argumentos & 32) > 0)
									argumentos += 8;
								else
									TercerPractica.error(
											"Debes de colocar el argumento 'p' antes de indicar el tipo de función de los números perfectos");
								break;
							case 'l':
								if ((argumentos & 32) > 0)
									argumentos += 16;
								else
									TercerPractica.error(
											"Debes de colocar el argumento 'p' antes de indicar el tipo de función de los números perfectos");
								break;
						}
					}
				}
			}

			ArrayList<Integer> valores = Archivos.archivo(rutaArchivo).getValores();
			Graficas grafica = new Graficas(1, 2);

			// Ahora se ejecutan los programas según los bits activos de los argumentos
			// ingresados
			if ((argumentos & 4) > 0) { // Sucesión de Fibonacci
				int numeroSucesion = 0, indiceColeccion = -1;

				for (Integer valor : valores) {
					if ((argumentos & 1) > 0) { // Fibonacci iterativo
						numeroSucesion = Fibonacci.iterativo((int) valor);
						System.out.printf("Fibonacci i(%d) = %d\n", valor, numeroSucesion);
						indiceColeccion = grafica.agregarParOrdenado("Fibonacci iterativo", (double) valor,
								Fibonacci.numeroOperaciones, indiceColeccion);
					}

					if ((argumentos & 2) > 0) { // Fibonacci recursivo
						numeroSucesion = Fibonacci.recursivo((int) valor);
						System.out.printf("Fibonacci r(%d) = %d\n", valor, numeroSucesion);
						indiceColeccion = grafica.agregarParOrdenado("Fibonacci recursivo", (double) valor,
								Fibonacci.numeroOperaciones, indiceColeccion);
						Fibonacci.resetNumeroOperaciones();
					}
				}

				grafica.crearGrafica("Graficación de los valores n vs tiempo para las funciones de fibonacci",
						"Valor(n)", "NúmeroOperaciones", indiceColeccion);
			}

			if ((argumentos & 32) > 0) { // Número perfecto
				int indiceColeccion = -1;
				boolean esPerfecto = false;
				int[] listaPerfectos;

				for (Integer valor : valores) {

					if((argumentos & 8) > 0){ // función para saber si un número es perfecto
						Double aux[] = new Double[2];
						aux = NumeroPerfecto.esPerfecto(valor);
						indiceColeccion = grafica.agregarParOrdenado("N evaluado como perfecto", (double)valor, aux[1], indiceColeccion);
					}

					if((argumentos & 16) > 0){ // Función para listar los primeros n números perfectos
						HashMap<Integer, ArrayList<Double>> mapa = NumeroPerfecto.mostrarPerfectos3(3);
						for(int i = 0; i < mapa.size(); i++){
							indiceColeccion = grafica.agregarParOrdenado("Primeros 4 números perfectos", mapa.get(i).get(0), mapa.get(i).get(1), indiceColeccion);
						}
					}
				}

				grafica.crearGrafica("Graficación de los valores n vs tiempo para la funcion esPerfecto()",
				"Valor(n)", "NúmeroOperaciones", indiceColeccion);
			}

			grafica.mostrarGrafica();

		} else
			TercerPractica.error("Son necesarios argumentos para la ejecución del programa\n" + ANSI_GREEN
					+ "i.e: $ java TerceraPractica -[f[i,t],p] archivo_valores_ejecución");
	}
}

// Clase con las funciones iterativa y recursiva de fibonacci
class Fibonacci {
	public static int numeroOperaciones = 0;

	public static void resetNumeroOperaciones() {
		numeroOperaciones = 0;
	}

	// Función generadora de la sucesión de fibonacci de forma iterativa
	public static int iterativo(int n) {
		resetNumeroOperaciones();

		int numeroSucesion = 1, numeroAnterior = 0;
		numeroOperaciones++;

		for (int i = 1; i < n; i++) {
			numeroSucesion += numeroAnterior;
			numeroAnterior = numeroSucesion - numeroAnterior;
			numeroOperaciones++;
		}

		numeroOperaciones++;
		return numeroSucesion;
	}

	// Función generadora de la sucesión de fibonacci de forma recursiva
	public static int recursivo(int n) {
		if (n == 0 || n == 1) {
			numeroOperaciones++;
			return n;
		} else {
			numeroOperaciones++;
			return recursivo(n - 1) + recursivo(n - 2);
		}
	}
}

// Clase con las funciones para identificar y enlistar números perfectos
class NumeroPerfecto {

	public static Double[] esPerfecto(double num) {
        Double test[] = new Double[2];
        double numOpAux = 0d;

        double acum = 0d;
        numOpAux++;

        for (int i = 1; i <= (num / 2); i++) {
            numOpAux++;
            if (num % i == 0) {
                acum += i;
                numOpAux++;
            }
        }
        if (acum == num) {
            numOpAux++;
            test[0] = 1d;
            test[1] = numOpAux;
            return test;
        } else {
            numOpAux++;
            test[0] = 0d;
            test[1] = numOpAux;
            return test;
        }
    }


    public static HashMap<Integer, ArrayList<Double>> mostrarPerfectos3(int num) {
        ArrayList<Double> listaAuxiliar[] = new ArrayList[num];
        Double auxPerfecto[] = new Double[2];
        HashMap<Integer, ArrayList<Double>> salida = new HashMap<>();

        double numOpAux = 0d;
        int cont = 0;
        numOpAux++;

        for (double acum = 2; cont < num; acum += 2) {
            numOpAux++; // contador de verificacion de condicion de for
            auxPerfecto = esPerfecto(acum);
            numOpAux++;	// contador de verificacion de condicion de if
            if (auxPerfecto[0] == 1d) {
                numOpAux++; // contador de "impresion" 
                numOpAux++; // contador de incremento de cont
                listaAuxiliar[cont] = new ArrayList<>();
                listaAuxiliar[cont].add(acum); // Numero perfecto
                listaAuxiliar[cont].add(numOpAux);	// Contador de operaciones para conseguirlo
                salida.put(cont, listaAuxiliar[cont]);
                //listaAuxiliar.clear();
                cont++;
            }
            numOpAux = numOpAux + auxPerfecto[1];
        }

        return salida;
    }
}
