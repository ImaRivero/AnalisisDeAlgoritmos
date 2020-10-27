/*
    Fecha: 25/Octubre/2020
    Creado por: 
        + Omar Imanol Rivero Ronquillo
        + Luis Eduardo Valle Martínez
*/
import java.lang.*;
import java.io.*;
import java.util.*;

public class SegundaPractica{
    // Constantes de colores para impresión en consola
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    
    // Imprime un mensaje de error y termina la ejecución del programa
    public static void error(String mensaje){
        System.out.printf(ANSI_YELLOW + "\nERROR >> %s" + ANSI_RESET + "\n",mensaje);
        System.exit(-1);
    }

    // Imprime un mensaje con cierta estructura en la consola
    public static void mensaje(String mensaje){
        System.out.printf(ANSI_GREEN + "\n --- %s ---" + ANSI_RESET + "\n",mensaje);
    }

	public static void main(String[] args) {
		if(args.length > 0){
			String rutaArchivo = args[0];
			int argumentos = 0;
			if(args.length == 1){
				if(rutaArchivo.indexOf('-') < 0)
					SegundaPractica.error("Es necesario ingresar la ruta del archivo con los valores de ejecución");
				argumentos = 3;
			}else{
				if(rutaArchivo.indexOf('-') >= 0){
					rutaArchivo = args[1];
					for(char argumento:args[0].toCharArray()){
						switch(argumento){
							case 'p':
								argumentos += 1;
								break;
							case 'c':
								argumentos += 2;
								break;
						}
					}
				}
			}

			double tiempoProceso = 0 ;
			ArrayList<Par> valores = Archivos.archivo(rutaArchivo).getValoresPar();
			Graficas grafica = new Graficas(1,2);


			// Ahora se ejecutan los programas según los bits activos de los argumentos ingresados
			if((argumentos & 1) > 0){ // Programa de los productos
				int producto = 0;
				int indiceColeccion = -1;

				// Primera ejecución para evitar errores
				Producto.prod1(1,1);
				Producto.prod2(1,1);
				Producto.prod3(1,1);

				for(Par par:valores){
					//Primer Algoritmo
					tiempoProceso = System.nanoTime();
					producto = Producto.prod1(par.get(0),par.get(1));
					tiempoProceso = (System.nanoTime()-tiempoProceso)/1000;
					indiceColeccion = grafica.agregarParOrdenado("Grafica prod1",(double)par.get(1),tiempoProceso,indiceColeccion);
					System.out.println(producto);

					//Segundo Algoritmo
					tiempoProceso = System.nanoTime();
					producto = Producto.prod2(par.get(0),par.get(1));
					tiempoProceso = (System.nanoTime()-tiempoProceso)/1000;
					indiceColeccion = grafica.agregarParOrdenado("Grafica prod2",(double)par.get(1),tiempoProceso,indiceColeccion);
					System.out.println(producto);

					//Tercer Algoritmo
					tiempoProceso = System.nanoTime();
					producto = Producto.prod3(par.get(0),par.get(1));
					tiempoProceso = (System.nanoTime()-tiempoProceso)/1000;
					indiceColeccion = grafica.agregarParOrdenado("Grafica prod3",(double)par.get(1),tiempoProceso,indiceColeccion);
					System.out.println(producto);
				}

				grafica.crearGrafica("Graficación de los valores n vs tiempo para los algoritmos de producto","Valor(n)","Tiempo de proceso(t en µs)",indiceColeccion);
			}	

			if((argumentos & 2) > 0){ // Programa de los cocientes
				int cociente = 0;
				int indiceColeccion = -1;

				// Primera ejecución para evitar errores
				Cociente.div1(10,10);
				Cociente.div2(10,10);
				Cociente.div3(10,10);


				for(Par par:valores){
					//Primer Algoritmo
					tiempoProceso = System.nanoTime();
					cociente = Cociente.div1(par.get(0),par.get(1));
					tiempoProceso = (System.nanoTime()-tiempoProceso)/1000;
					indiceColeccion = grafica.agregarParOrdenado("Grafica div1",(double)par.get(1),tiempoProceso,indiceColeccion);
					System.out.println(cociente);

					//Segundo Algoritmo
					tiempoProceso = System.nanoTime();
					cociente = Cociente.div2(par.get(0),par.get(1));
					tiempoProceso = (System.nanoTime()-tiempoProceso)/1000;
					indiceColeccion = grafica.agregarParOrdenado("Grafica div2",(double)par.get(1),tiempoProceso,indiceColeccion);
					System.out.println(cociente);

					//Tercer Algoritmo
					tiempoProceso = System.nanoTime();
					cociente = Cociente.div3(par.get(0),par.get(1));
					tiempoProceso = (System.nanoTime()-tiempoProceso)/1000;
					indiceColeccion = grafica.agregarParOrdenado("Grafica div3",(double)par.get(1),tiempoProceso,indiceColeccion);
					System.out.println(cociente);

				}

				grafica.crearGrafica("Graficación de los valores n vs tiempo para los algoritmos de cociente","Divisor(div)","Tiempo de proceso(t en µs)",indiceColeccion);
			}

			grafica.mostrarGrafica();

		}else SegundaPractica.error("Son necesarios argumentos para la ejecución del programa\n" + ANSI_GREEN + "i.e: $ java SegundaPractica -[c,p](opcional) archivo_valores_ejecución");
	}
}


// Clase con la tercia de algoritmos para obtener el productor de 2 enteros
class Producto{

	// Primer algoritmo iterativo para obtener el producto
	public static int prod1(int m, int n){
		int r = 0;
		while(n>0){
			r+=m;
			n--;
		}
		return r;
	}

	// Segundo algoritmo iterativo para obtener el producto
	public static int prod2(int m, int n){
		int r = 0;
		while(n>0){
			if((n & 1)>0) r+=m;
			m*=2;
			n/=2;
		}
		return r;
	}

	// Algoritmo recursivo para obtener el producto
	public static int prod3(int a, int b){
		if(b==1) return a;
		else return a+prod3(a,b-1);
	}
}

//Clase con la tercia de algoritmos para obtener el cociente de 2 enteros
class Cociente{

	public static int residuo = 0;

	// Primer algoritmo iterativo para obtener el cociente de 2 números
	public static int div1(int n, int div){
		int q = 0;
		while(n>=div){
			n-=div;
			q++;
		}
		Cociente.residuo = n;
		return q;
	}

	// Segundo algoritmo iterativo para obtener el cociente de 2 números
	public static int div2(int n, int div){
		int dd=div;
		int q = 0;
		int r = n;
		while(dd<=n) dd*=2;
		while(dd>div){
			dd/=2;
			q*=2;
			if(dd<=r){
				r-=dd;
				q++;
			}
		}
		Cociente.residuo = r;
		return q;
	}

	// Algoritmo recursivo para obtener el cociente de 2 números
	public static int div3(int n, int div){
		if(div>n){
			Cociente.residuo = n;
			return 0;
		}else return 1+div3(n-div,div);
	}
} 