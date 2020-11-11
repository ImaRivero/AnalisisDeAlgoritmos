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
						esPerfecto = NumeroPerfecto.getNumeroPerfecto().isPerfecto(valor);
						if(esPerfecto) {
							// Se van a mostrar los dividendos del número
							System.out.printf("%d es un número perfecto\n  Dividendos \n ------------\n",valor);
							for(Integer dividendo:NumeroPerfecto.getNumeroPerfecto().dividendos)
								System.out.printf("    -> %d\n",dividendo);
						}
						indiceColeccion = grafica.agregarParOrdenado("N evaluado como perfecto", (double) valor,NumeroPerfecto.numeroOperaciones, indiceColeccion);
					}

					if((argumentos & 16) > 0){ // Función para listar los primeros n números perfectos
						listaPerfectos = NumeroPerfecto.getNumeroPerfecto().mostrarPerfectos(valor);
						System.out.printf("\nLista de los %d primeros números perfectos\n----------------------------------------\n",valor);
						for(int i=0;i<valor;i++){
							System.out.printf("%d°. %d\n",i+1,listaPerfectos[i]);
						}
						// Se van a mostrar los dividendos del último número
						System.out.printf("  Dividendos de %d \n ------------\n",listaPerfectos[listaPerfectos.length -1]);
						for(Integer dividendo:NumeroPerfecto.getNumeroPerfecto().dividendos)
							System.out.printf("    -> %d\n",dividendo);
						System.out.println("\n");
						indiceColeccion = grafica.agregarParOrdenado("Primeros n números perfectos", (double) valor,NumeroPerfecto.numeroOperaciones, indiceColeccion);
					}
				}

				grafica.crearGrafica("Graficación de los valores n vs tiempo para las funciones de fibonacci",
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
	protected static ArrayList<Integer> historialPerfectos;
	
	public ArrayList<Integer> dividendos;
	protected static int numeroOperaciones;

	static NumeroPerfecto instancia = null;

	private NumeroPerfecto(){
		historialPerfectos = new ArrayList<Integer>();
		numeroOperaciones = 0;
		dividendos = new ArrayList<Integer>();
	}

	// Permite obtener la única instancia de la clase
	public static NumeroPerfecto getNumeroPerfecto(){
		if(NumeroPerfecto.instancia == null)
			NumeroPerfecto.instancia = new NumeroPerfecto();
		return NumeroPerfecto.instancia;
	}

	public void resetNumeroOperaciones() {
		numeroOperaciones = 0;
	}

	// Permite conocer si un número 'n' es perfecto y se ha guardado en la lista
	public boolean isPerfectoGuardado(int n) {
		if(! historialPerfectos.isEmpty()){
			if (historialPerfectos.get(busquedaBinaria(historialPerfectos, n, historialPerfectos.size() - 1, 0)) == n)
				return true;
		} return false;
	}

	// Agrega
	public void agregarPerfecto(int n) {
		if (historialPerfectos.isEmpty())
			historialPerfectos.add(n);
		else {
			int posicion = busquedaBinaria(historialPerfectos, n, historialPerfectos.size() - 1, 0);
			if (historialPerfectos.get(posicion) != n)
				historialPerfectos.add(posicion, n);
		}
	}

	// Mediante divisiones consecutivas del arreglo encuentra la posición actual del
	// número 'n'
	// En caso de no existir el número en el arreglo regresa la posición donde
	// debería de ser ingresado
	public int busquedaBinaria(ArrayList<Integer> arreglo, int n, int superior, int inferior) {
		if (superior < inferior) { // Indica la posición donde debe de ir el número 'n'
			numeroOperaciones++;
			return (inferior <= arreglo.size()-1)?inferior:superior;
		} else {
			int medio = (int) ((superior + inferior) / 2);
			numeroOperaciones++;

			if (arreglo.get(medio) < n) {
				numeroOperaciones++;
				return busquedaBinaria(arreglo, n, superior, medio + 1);
			} else if (arreglo.get(medio) > n) {
				numeroOperaciones++;
				return busquedaBinaria(arreglo, n, medio - 1, inferior);
			} else {// De otra forma significa que el número de en medio es igual a 'n'
				numeroOperaciones++;
				return medio;
			} 
		}
	}

	// Función que permite identificar si un número es perfecto(la suma de sus
	// dividendos es igual al número)
	public boolean isPerfecto(int n) {
		if (isPerfectoGuardado(n))
			return true;
		else if(n <= 3)
			return false;

		int iterador = 1, sumaDividendos = 0, posicionAux = 1, numOpAux = 0, dividendo = 0;
		double cociente;
		numOpAux++;
		dividendos.clear(); // Se ingresan los 2 primeros dividendos que forzosamente debe de tener un número perfecto
		dividendos.add(1);
		dividendos.add(2);
		numOpAux++;


		while (iterador < dividendos.size()) {// Se iterará cada elemento ingresado en los dividendos mientras a su vez
												// generan más dividendos
			cociente =(double) n;
			dividendo = dividendos.get(iterador);
			numOpAux++;

			while ((cociente % dividendos.get(iterador)) == 0.0) { // Se itera hasta obtener un resido diferente a 0
				cociente =(double)cociente / dividendo;
				numOpAux++;
				posicionAux = busquedaBinaria(dividendos, (int)cociente, dividendos.size() - 1, 0); // Se obtiene la posición
																								// del 'n' en el arreglo
																								// o donde debería
																								// colocarse
				numOpAux += numeroOperaciones;
				resetNumeroOperaciones();
				if (dividendos.get(posicionAux) !=(int)cociente && (int)cociente > 0) { // Se verifica que el elemento no exista ya
					if(dividendos.get(posicionAux) < (int)cociente){
						posicionAux ++;
						numOpAux ++;
					}
					dividendos.add(posicionAux,(int)cociente);
					numOpAux++;
					sumaDividendos += cociente;
					numOpAux++;
				}
			}

			iterador++;
			numOpAux++;
		}

		numOpAux++;
		numeroOperaciones = numOpAux;
		if (sumaDividendos+3 == n) {
			agregarPerfecto(n);
			return true;
		}

		return false;
	}

	// Función que muestra los primeros 'n' números perfectos
	public int[] mostrarPerfectos(int n) {
		int numOpAux = 0, cantidad = 6, nAux = n;
		numOpAux++;
		int[] numerosPerfectos = new int[n];
		numOpAux++;

		while (nAux > 0) {
			if (isPerfecto(cantidad)) {
				numerosPerfectos[n - nAux] = cantidad;
				numOpAux++;
				nAux--;
				numOpAux++;
			}
			numOpAux += numeroOperaciones; // Se suman las operaciones de la función 'isPerfecto'
			cantidad += 2;
			resetNumeroOperaciones();
		}

		numeroOperaciones = numOpAux;
		return numerosPerfectos;
	}

	private double formulaPerfectosPrimos(int n){
		return (Math.pow(2, n-1)) * (Math.pow(2, n) -1);
	}

	// Función que muestra los primeros 'n' números perfectos a partir de la ecuación probada de Euclides
	// númeroPerfecto = 2^(n − 1) * ( 2^n − 1 )
	public int[] mostrarPerfectosPrimos(int n) {
		int numOpAux = 0, nAux = 1, perfecto = 0;
		numOpAux++;
		int[] numerosPerfectos = new int[n];
		numOpAux++;

		while (nAux < n) {
			perfecto = (int)formulaPerfectosPrimos(nAux+1);
			if (isPerfecto(perfecto)) {
				numerosPerfectos[nAux] = perfecto;
				numOpAux++;
			}
			numOpAux += numeroOperaciones; // Se suman las operaciones de la función 'isPerfecto'
			nAux ++;
			resetNumeroOperaciones();
		}

		numeroOperaciones = numOpAux;
		return numerosPerfectos;
	}

	public ArrayList<Long> esPerfecto(long num){
		ArrayList<Long> aux = new ArrayList<>();
		int acum = 0;
		long numOpAux = 0;
		numOpAux++;
		
        for(int i = 1; i <= (num/2); i++){
			numOpAux++;
            if(num % i == 0){
				acum += i;
				numOpAux++;
            }
        }
        if(acum == num){
			numOpAux++;
			aux.set(0, 1l);
			aux.set(1, numOpAux);
			return aux;
		}
        else{
			numOpAux++;
			aux.set(0, 0l);
			aux.set(1, numOpAux);
            return aux;
		}
	}
	
	public ArrayList<Long>[] mostrarPerfectos2(int num){
		ArrayList<Long> lista = new ArrayList<>();
		ArrayList<Long> auxPerfecto = new ArrayList<>();
		ArrayList<Long>[] salida = new ArrayList[2]; // Array de ArrayList

		Long numOpAux = 0l;
		int cont = 0;
		numOpAux++;

		for(long acum = 0l; cont < num; acum++){
			numOpAux++;
			auxPerfecto = esPerfecto(acum);
			if(auxPerfecto.get(0) == 1l){
				lista.add(acum);
				cont++;
			}
			numOpAux = numOpAux + auxPerfecto.get(1);
		}
		salida[0] = lista; // Primer elemento guarda la lista
		salida[1] = new ArrayList<Long>(); 
		salida[1].add(numOpAux); // Segundo tiene como unico elemento del array, el numero de operaciones

		System.out.println("Numero de operaciones");
		System.out.println(numOpAux);
		System.out.println("Lista");
		System.out.println(lista);

		return salida;
	}
}
