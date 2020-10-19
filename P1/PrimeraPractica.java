/*
    18/Octubre/2020
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
    
    // Imprime un mensaje de error y termina la ejecución del programa
    public static void error(String mensaje){
        System.out.printf(ANSI_YELLOW + "ERROR >> %s" + ANSI_RESET,mensaje);
        System.exit(-1);
    }

    public static void main(String args[]){
        /*
            Argumentos
            ==========
                args[1](Obligatorio) - Puede tomar valor 1 o 2
                    ** 1: Se ejecutara el programa de sumas binarias
                    ** 2: Se ejecutara el programa del algoritmo de Euclides

                args[2](Obligatorio) - Nombre de un archivo con los datos para ser evaluados

                i.e.
                    >> usr$ java PrimeraPractica 1 numerosBinarios.txt
                    >> usr$ java PrimeraPractica 2 sucesionFibonacci.txt

            Archivo
            =======
                - Solo se aceptan números enteros, comas y barra '/'
                - No se aceptan caracteres alfabéticos u otros caracteres especiales
                - Se ignoran espacios, tabulaciones y saltos de línea
                - El archivo puede tener cualquier extensión

                - El par de valores deben estar separadas por una coma
                - Las parejas de números se separan por barra '/'

                    i.e.
                        Archivo >> 5,2|10,9

                Suma Binaria
                ------------
                    - Se debera ingresar la pareja de números en decimal


        */
        PrimeraPractica p = new PrimeraPractica();
        int[][] valoresXTiempo;

        if(args.length == 2){
            int tiempoProceso;
            // Valores de los argumentos
            int programa =Integer.parseInt(args[0]);
            String ruta = args[1];

            // Se obtienen los valores del archivo
            Archivos archivo = new Archivos(ruta);
            ArrayList<ArrayList> valores = archivo.getValores();

            valoresXTiempo = new int[valores.size(),2]; // Se asigna la cantidad de pares ordenados para la graficación

            if(programa == 1){ // Suma binaria
                for(int i=0;i<valores.size();i++){
                    ArrayList pares = valores.get(i);
                    
                    int r = Binarios.longitudNumeroMayor((int)pares.get(0),(int)pares.get(1)); // Se obtienen el tamaño potencia 2 de los arreglos según el número más grande 
                    
                    // Se obtiene la cadena binaria
                    int[] a = Binarios.aCadenaBinaria((int)pares.get(0),r); 
                    int[] b = Binarios.aCadenaBinaria((int)pares.get(1),r);

                    // Empieza el cálculo del proceso
                    tiempoProceso = System.currentTimeNano();
                    Binarios.suma(a,b,r);
                    tiempoProceso -= System.currentTimeNano();

                    valoresXTiempo[i] = {r,tiempoProceso}; // Se agrega el par ordenado
                }
            }else if (programa == 2){ // Algoritmo de Euclides
                for(ArrayList pares: valores){
                    int m = pares.get(0);
                    int n = pares.get(1);

                    // Empieza el cálculo del proceso
                    tiempoProceso = System.currentTimeNano();
                    MCM.Euclides(m,n);
                    tiempoProceso -= System.currentTimeNano();

                    valoresXTiempo[i] = {r,tiempoProceso}; // Se agrega el par ordenado
                }
            }else p.error("No es válido el número del programa especificado como parámetro");

            // Graficación de los pares ordenados en valoresXTiempo
        }else p.error("Deben de ingresarse los argumentos obligatorios\n" + ANSI_GREEN + "(Ejemplo) usr$ PrimeraPractica #programa archivoCantidades.txt\n");
    }
}

// Clase privada para el manejo de los archivos con las cantidades a evaluar
class Archivos{
    protected String ruta;

    // Se debe especificar la ruta del archivo en el constructor
    Archivos(String r){
        ruta = r;
    }

    private String eliminarEspacios(String cadena){
        cadena = cadena.replaceAll("\\s+",""); // Elimina los espacios, tab y saltos de línea
        return cadena;
    }

    // Convierte en un array los pares reconocidos del archivo
    private String getPares(){
        String cadena = "";
        try{ 
            // Se abre el archivo para su lectura
            File archivo = new File(ruta);
            Scanner lector = new Scanner(archivo);
            while(lector.hasNextLine())
                cadena += lector.nextLine();

            cadena = eliminarEspacios(cadena);
        }catch(Exception e){
            PrimeraPractica.error("Falló en leer el archivo");
        }

        return cadena;
    }

    // Regresa los valores del archivo en un ArrayList con los pares de enteros
    public ArrayList<ArrayList> getValores(){
        ArrayList<ArrayList> valores = new ArrayList<ArrayList>();
        int longitud = 0;

        for(String par:getPares().split("/")){ // Se divide la cadena por pares
            ArrayList auxValor = new ArrayList<Integer>();

            for(String val: par.split(",")) // Se divide el par en cada cantidad
                auxValor.add(Integer.parseInt(val)); // Se guardan como enteros los valores del par
            valores.add(auxValor);
        }
        return valores;
    }
}

// Clase con los métodos y para realizar la suma de cadenas binarias
class Binarios{
    
    // Algoritmo de suma binaria para 2 cadenas binarias de tamaño r
    public static int[] Suma(int[] A, int[] B, int r){

        int[] C = new int[r];
        int acarreo = 0;

        for(int i = r-1; i >= 0; i--){
            if(acarreo == 0){
                if(A[i] == B[i] && A[i] == 1){
                    C[i] = 0;
                    acarreo = 1;
                }
                else{
                    C[i] = 1;
                    acarreo = 0;
                }
            }
            else {
                if(A[i] == B[i]){
                    C[i] = 1;
                    if(A[i] == 1)
                        acarreo = 1;
                    else
                        acarreo = 0;
                }
                else{
                    C[i] = 0;
                    acarreo = 1;
                }
            }
        }
        return C;
    }

    // Algoritmo alternativo de suma binaria utilizando operaciones booleanas AND,OR y XOR
    public static int[] suma(int[] A,int[] B, int r){
        int C[] = new int[r];
        int acarreo = 0;
        for (int i=r-1; i>=0; i--){
            C[i] = acarreo ^ A[i] ^ B[i]; // Valor de la suma mediante el uso de la operación XOR
            acarreo = (acarreo & A[i]) | (A[i] & B[i]); // Se asigna el valor del acarreo
        }
        return C;
    }

    // Regresa el tamaño que deberán tener los arreglos en base al mayor número de los 2
    public static int longitudNumeroMayor(int a, int b){
        a = (a>b)?a:b;
        return (int)Math.round(Math.log10(a)/Math.log10(2))+1;
    }

    // Convierte el valor de una decimal a su representación binaria en un arreglo de enteros
    public static int[] aCadenaBinaria(int numero, int longitud){
        int[] cadena = new int[longitud];
        for (int i=longitud-1, j=1; i>=0; i--,j<<=1){ // Se realiza un recorrimiento de bits a la derecha del '1' y se aplica AND para conocer si el bit está activo
            int op = j&numero;
            cadena[i] = (op > 0)?1:0; // Cuando el bit de j y el número estén activo entonces el valor de op será mayor a 0 y corresponde un 1 a la cadena binaria
        }
        return cadena;
    }
}


//Clase que contiene el algoritmo de Euclides como método estático
class MCM{

    // Algoritmo de Euclides para el cálculo del mínimo común múltiplo de 2 números enteros
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