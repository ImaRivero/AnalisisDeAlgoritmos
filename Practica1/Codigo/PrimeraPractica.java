/*
    Fecha: 18/Octubre/2020
    Creado por: 
        + Omar Imanol Rivero Ronquillo
        + Luis Eduardo Valle Martínez
*/

import java.lang.*;
import java.io.*;
import java.util.*;

public class PrimeraPractica{
    // Constantes de colores para impresión en consola
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    
    // Imprime un mensaje de error y termina la ejecución del programa
    public static void error(String mensaje){
        System.out.printf(ANSI_YELLOW + "\nERROR >> %s" + ANSI_RESET,mensaje);
        System.exit(-1);
    }

    public void mensaje(String mensaje){
        System.out.printf(ANSI_GREEN + "\n --- %s ---" + ANSI_RESET,mensaje);
    }

    public static void main(String args[]){
       
        PrimeraPractica practica = new PrimeraPractica();
        Graficas graficas = new Graficas();
        Scanner entrada = new Scanner(System.in);
        int[][] valoresXTiempo;

        if(args.length >= 2){
            int tiempoProceso;
            // Valores de los argumentos
            int programa =Integer.parseInt(args[0]);
            int parametroGraficacionEuclides = 0;
            String ruta = args[1];

            if(args.length == 3){
                if(programa == 2){
                    parametroGraficacionEuclides = Integer.parseInt(args[2]);
                }else practica.error("La combinación de 3 parámetros solo es válido cuando el número del programa es 2(Euclides)");
            }

            // Se obtienen los valores del archivo
            Archivos archivo = new Archivos(ruta);
            ArrayList<Object> valores = archivo.getValores((programa == 1)?true:false);

            valoresXTiempo = new int[valores.size()*((parametroGraficacionEuclides == 0 && programa == 2)?2:1)][2]; // Se asigna la cantidad de pares ordenados para la graficación


            if(programa == 1){ // Suma binaria
                
                practica.mensaje("Pares Ordenados(tamaño vs tiempo)");

                for(int i=0;i<valores.size();i++){
                    int r = (int)Math.pow(2,(int)valores.get(i));
                    
                    // Se obtiene la cadena binaria
                    int[] a = Binarios.generarCadenaBinaria(r);
                    int[] b = Binarios.generarCadenaBinaria(r);

                    // Empieza el cálculo del tiempo y el proceso
                    tiempoProceso =(int)System.nanoTime();
                    int[] c = Binarios.suma(a,b);
                    tiempoProceso =(int)(System.nanoTime() - tiempoProceso)/1000;
                    System.out.printf("\n%d. (%d,%d)",i+1,a.length,tiempoProceso);

                    int[] parOrdenado = {r,(int)tiempoProceso};
                    valoresXTiempo[i] = parOrdenado; // Se agrega el par ordenado
                }

                graficas.agregarDatosGraficacion("r vs tiempo",valoresXTiempo);
                graficas.configurarGrafica("Suma binaria de 2 números enteros en arreglos de tamaño potencia de 2","Tamaño de arreglos(r)","Tiempo de proceso(t en µs)");

            }else if (programa == 2){ // Algoritmo de Euclides
                int j=0;

                practica.mensaje("Pares Ordenados(m/n vs tiempo)");
                MCD.Euclides(1,1); // La primer ejecución tardaba hasta 1000 vecés más tiempo en ejecutarse independiente del 

                for(int i=0;i<valores.size();i++,j+=((parametroGraficacionEuclides == 0)?2:1)){
                    int[] parOrdenado = new int[2];
                    int[] parOrdenadoExtra = new int[2];

                    ArrayList pares = (ArrayList)valores.get(i);
                    int m = (int)pares.get(0);
                    int n = (int)pares.get(1);

                    // Empieza el cálculo del tiempo y el proceso
                    tiempoProceso =(int)System.nanoTime();
                    MCD.Euclides(m,n);
                    tiempoProceso =(int)System.nanoTime() - tiempoProceso;
                    System.out.printf("\n%d. (%d/%d,%d)",i+1,m,n,tiempoProceso);

                    switch(parametroGraficacionEuclides){
                        case 1:
                            parOrdenado[0] = m;
                            parOrdenado[1] = (int)tiempoProceso;
                            break;
                        case 2:
                            parOrdenado[0] = n;
                            parOrdenado[1] = (int)tiempoProceso;
                            break;
                        default:
                            parOrdenado[0] = m;
                            parOrdenado[1] = (int)tiempoProceso;
                            parOrdenadoExtra[0] = n;
                            parOrdenadoExtra[1] = (int)tiempoProceso;

                    }

                    valoresXTiempo[j] = parOrdenado; // Se agrega el par ordenado
                    if(parametroGraficacionEuclides == 0) valoresXTiempo[j+1] = parOrdenadoExtra; // Se agrega los valores en n cuando se grafican las 2 variables
                }

                /* Se agregan los datos en función del 3° parámetro
                    - Si el parámetro es 0 entonces se agregan 2 gráficas(una para m y otra para n)
                    - De lo contrario solo el de m o n
                */
                if(parametroGraficacionEuclides == 0 && programa == 2){
                    for(int vars=0;vars<2;vars++){ // Se obtienen los pares ordenados de cada variable cuando 'variables' toma valor de 0:n y 1:m
                        int[][] valoresAux = new int[(int)valoresXTiempo.length/2][2];
                        
                        j=0;
                        for(int i=vars; i<valoresXTiempo.length; i+=2,j++){
                            for(int k=0;k<2;k++){
                                valoresAux[j][k] = valoresXTiempo[i][k];
                            }
                        }
                        
                        graficas.agregarDatosGraficacion(((vars == 0)?"m":"n") + " vs tiempo",valoresAux);
                    }
                }else graficas.agregarDatosGraficacion(((parametroGraficacionEuclides == 1)?"m":"n") + " vs tiempo",valoresXTiempo);

                graficas.configurarGrafica("Mínimo Común Divisor de 2 números enteros con el algoritmo de Euclides","Tamaño de arreglos(r)","Tiempo de proceso(t en µs)");
            }else practica.error("No es válido el número del programa especificado como parámetro");

            // Se muestra la gráfica de los pares ordenados
            graficas.mostrarGrafica();
            practica.mensaje("Se muestran la gráfica de los valores de ingreso contra el tiempo en µs");


        }else practica.error("Deben de ingresarse los argumentos obligatorios\n" + ANSI_GREEN + "(Ejemplo) usr$ PrimeraPractica #programa archivoCantidades.txt\n");
    }
}

// Clase con los métodos y para realizar la suma de cadenas binarias
class Binarios{

    // Algoritmo alternativo de suma binaria utilizando operaciones booleanas AND,OR y XOR
    public static int[] suma(int[] A,int[] B){
        int r = A.length; // Tamaño de los arreglos
        int c[] = new int[r+1];
        int acarreo = 0;
        for (int i=r-1; i>=0; i--){
            c[i+1] = acarreo ^ A[i] ^ B[i]; // Valor de la suma mediante el uso de la operación XOR
            acarreo = (acarreo & A[i]) | (acarreo & B[i]) | (A[i] & B[i]); // Se asigna el valor del acarreo
        }
        c[0] = (acarreo > 0)?1:0;
        return c;
    }

    public static int[] generarCadenaBinaria(int r){
        int[] cadena = new int[r];
        for(int i=0;i<r;i++)
            cadena[i] = (int)(Math.random() * 2);
        return cadena;
    }
}


//Clase que contiene el algoritmo de Euclides como método estático
class MCD{

    // Algoritmo de Euclides para el cálculo del máximo común divisor de 2 números enteros
    public static int Euclides(int m,int n){
        int r; // Variable que representa el residuo de la división
        while(n != 0){ // Ciclo que realizará divisiones hasta que el residuo sea igual a 0
            r = m % n;
            m = n;
            n = r;
        }
        return m;
    }

}