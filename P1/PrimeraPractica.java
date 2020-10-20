/*
    18/Octubre/2020
    Creado por: 
        + Omar Imanol Rivero Ronquillo
        + Luis Eduardo Valle Martínez
*/

import java.lang.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.Dimension;

// Librerías de graficación
import org.jfree.chart.*;
import org.jfree.data.xy.*;
import org.jfree.chart.plot.*;

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
                $ args[1](Obligatorio) - Puede tomar valor 1 o 2
                    ** 1: Se ejecutara el programa de sumas binarias
                    ** 2: Se ejecutara el programa del algoritmo de Euclides

                $ args[2](Obligatorio) - Nombre de un archivo con los datos para ser evaluados

                $ args[3](Opcional) - Permite la elección del o los parámetros que se graficarán para el algoritmo de Euclides
                    ** 0/inexistente: Se graficarán los valores de m y n
                    ** 1: Se grafican solo los valores de m
                    ** 2: Se grafican solo los valores de n

                i.e.
                    >> usr$ java PrimeraPractica 1 numerosBinarios.txt
                    >> usr$ java PrimeraPractica 2 sucesionFibonacci.txt 1

            Archivo
            =======
                - Solo se aceptan números enteros, comas y barra '/'(solo en el caso de Euclides)
                - No se aceptan caracteres alfabéticos u otros caracteres especiales
                - Se ignoran espacios, tabulaciones y saltos de línea
                - El archivo puede tener cualquier extensión

                Suma Binaria
                ------------
                    - Se deberan ingresar las cantidades potencia separadas por coma únicamente

                        i.e.
                            Archivo >> 5,8,63,34

                Euclides
                --------
                    - El par de valores deben estar separadas por una coma
                    - Las parejas de números se separan por barra '/'

                        i.e.
                            Archivo >> 5,2|10,9


        */
        PrimeraPractica practica = new PrimeraPractica();
        Graficas graficas = new Graficas();
        int[][] valoresXTiempo;

        if(args.length >= 2){
            int tiempoProceso;
            // Valores de los argumentos
            int programa =Integer.parseInt(args[0]);
            int parametroGraficacionEuclides = 0;
            String ruta = args[1];

            if(args.length == 3){
                if(programa == 2){
                    parametroGraficacionEuclides = Integer.parseInt(args[3]);
                }else practica.error("La combinación de 3 parámetros solo es válido cuando el número del programa es 2(Euclides)");
            }

            // Se obtienen los valores del archivo
            Archivos archivo = new Archivos(ruta);
            ArrayList<Object> valores = archivo.getValores((programa == 1)?true:false);

            valoresXTiempo = new int[valores.size()*((parametroGraficacionEuclides == 0 && programa == 2)?2:1)][2]; // Se asigna la cantidad de pares ordenados para la graficación

            if(programa == 1){ // Suma binaria
                
                for(int i=0;i<valores.size();i++){
                    int r = (int)Math.pow(2,(int)valores.get(i));
                    
                    // Se obtiene la cadena binaria
                    int[] a = Binarios.generarCadenaBinaria(r);
                    int[] b = Binarios.generarCadenaBinaria(r);

                    // Empieza el cálculo del tiempo y el proceso
                    tiempoProceso =(int)System.nanoTime();
                    int[] c = Binarios.suma(a,b,r);
                    tiempoProceso =(int)System.nanoTime() - tiempoProceso;

                    System.out.printf("A:%s , B:%s, C:%s\n\n",Arrays.toString(a),Arrays.toString(b),Arrays.toString(c));

                    int[] parOrdenado = {r,(int)tiempoProceso};
                    valoresXTiempo[i] = parOrdenado; // Se agrega el par ordenado
                }

                graficas.agregarDatosGraficacion("r vs tiempo",valoresXTiempo);
                graficas.configurarGrafica("Suma binaria de 2 ","Tamaño de arreglos(r)","Tiempo de proceso(t)");
            }else if (programa == 2){ // Algoritmo de Euclides
                int j=0;
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
                graficas.configurarGrafica("Mínimo Común Divisor de 2 números enteros con el algoritmo de Euclides","Tamaño de arreglos(r)","Tiempo de proceso(t)");
            }else practica.error("No es válido el número del programa especificado como parámetro");

            // Graficación de los pares ordenados en valoresXTiempo
            for(int[] par:valoresXTiempo)

            graficas.mostrarGrafica();
        }else practica.error("Deben de ingresarse los argumentos obligatorios\n" + ANSI_GREEN + "(Ejemplo) usr$ PrimeraPractica #programa archivoCantidades.txt\n");
    }
}

class Graficas{
    protected JFrame ventana;
    protected JPanel panel;

    protected XYSeriesCollection coleccionDatos;
    protected JFreeChart grafica;
    protected ChartPanel panelGrafica;


    public Graficas(){
        coleccionDatos = new XYSeriesCollection();

        ventana = new JFrame();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(960,590);

        panel = new JPanel();
        ventana.add(panel);
    }

    public void mostrarGrafica(){
        ventana.setVisible(true);
    }

    public void configurarGrafica(String titulo, String etiquetaX, String etiquetaY){
        grafica = ChartFactory.createXYLineChart(
            titulo,
            etiquetaX,
            etiquetaY,
            coleccionDatos,
            PlotOrientation.VERTICAL,
            true, true, false
            );

        panelGrafica = new ChartPanel(grafica);
        panelGrafica.setPreferredSize(new java.awt.Dimension(960,540));
        panel.add(panelGrafica);
    }

    public void agregarDatosGraficacion(String nombreGrafo, int[][] paresOrdenados){
        XYSeries datos = new XYSeries(nombreGrafo);

        for(int[] par: paresOrdenados) // Se asigna los datos al conjunto
            datos.add((long)par[0],(long)par[1]);

        coleccionDatos.addSeries(datos);
    }

}

// Clase privada para el manejo de los archivos con las cantidades a evaluar
class Archivos{
    protected String ruta;

    // Se debe especificar la ruta del archivo en el constructor
    public Archivos(String r){
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
    public ArrayList<Object> getValores(boolean bin){
        ArrayList<Object> valores = new ArrayList<Object>();
        int longitud = 0;

        if(bin){ // Archivo con una lísta de números para la suma binaria
            for(String cantidad:getPares().split(","))
                valores.add(new Integer(Integer.parseInt(cantidad)));
        }else{ 
            for(String par:getPares().split("/")){ // Se divide la cadena por pares
                ArrayList auxValor = new ArrayList<Integer>();

                for(String val: par.split(",")) // Se divide el par en cada cantidad
                    auxValor.add(Integer.parseInt(val)); // Se guardan como enteros los valores del par
                valores.add(auxValor);
            }
        }
        return valores;
    }
}

// Clase con los métodos y para realizar la suma de cadenas binarias
class Binarios{

    // Algoritmo alternativo de suma binaria utilizando operaciones booleanas AND,OR y XOR
    public static int[] suma(int[] A,int[] B, int r){
        int c[] = new int[r+1];
        int acarreo = 0;
        for (int i=r-1; i>=0; i--){
            c[i+1] = acarreo ^ A[i] ^ B[i]; // Valor de la suma mediante el uso de la operación XOR
            acarreo = (acarreo & A[i]) | (A[i] & B[i]); // Se asigna el valor del acarreo
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
class MCD{

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