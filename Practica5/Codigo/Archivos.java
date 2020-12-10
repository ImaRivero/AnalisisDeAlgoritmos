/*
    IPN - ESCOM
    Análisis de Algoritmos
    Prof: Benjamín Luna Benoso
    Grupo: 3CV1
    Práctca 5: Algoritmos Greedy
    ---------------------------------------------
    Creación: 9/Diciembre/2020
    Creado por: 
        + Omar Imanol Rivero Ronquillo
        + Luis Eduardo Valle Martínez
*/
        
import java.util.*;

import sun.security.krb5.internal.TransitedEncoding;

import java.io.*;

// Clase para el manejo de los archivos con las cantidades a evaluar
public class Archivos{
    protected Archivos archivo = null;
    protected String ruta;

    // Se debe especificar la ruta del archivo en el constructor
    private Archivos(String r){
        ruta = r;
    }

    private Archivos(){}

    public static Archivos archivo(String r){
        Archivos aux = new Archivos();
        if(aux.archivo == null)
            aux.archivo = new Archivos(r);
        else
            aux.archivo.ruta = r;
        return aux.archivo;
    }

    private String eliminarTodosEspacios(String cadena){
        cadena = cadena.replaceAll("\\s+",""); // Elimina los espacios, tab y saltos de línea
        return cadena;
    }

    private String eliminarEspacios(String cadena){
        cadena = cadena.replaceAll(" ",""); // Elimina los espacios, tab y saltos de línea
        return cadena;
    }

    // Obtiene la cadena de información que contiene el archivo
    private String getCadena(){
        String cadena = "";
        try{ 
            // Se abre el archivo para su lectura
            File archivo = new File(ruta);
            Scanner lector = new Scanner(archivo);
            while(lector.hasNextLine())
                cadena += lector.nextLine();

            cadena = eliminarTodosEspacios(cadena);
            lector.close();
        }catch(Exception e){
            CuartaPractica.error(e.getMessage());
        }
        return cadena;
    }
    
    private ArrayList<String> getLineas(){
        ArrayList<String> lineas = new ArrayList<String>();
        try{ 
            // Se abre el archivo para su lectura
            File archivo = new File(ruta);
            Scanner lector = new Scanner(archivo);
            while(lector.hasNextLine())
                lineas.add(eliminarEspacios(lector.nextLine()));
            lector.close();
        }catch(Exception e){
            AjustePuntos.error(e.getMessage());
        }
        return lineas;
    }


    // Regresa los valores separados por coma del archivo en un array de enteros
    public ArrayList<Integer> getValores(){
        ArrayList<Integer> valores = new ArrayList<Integer>();
        // Se separa la cadena en las cantidad separadas por comas y se guardan como enteros en 
        for(String cantidad:getCadena().split(","))
                valores.add( Integer.parseInt(cantidad));

        return valores;
    }

    // Regresa los valores del archivo en un ArrayList con los pares de enteros
    public ArrayList<Par> getValoresPar(){
        ArrayList<Par> valores = new ArrayList<Par>();     
        for(String par:getCadena().split("/")){ // Se divide la cadena por pares
            String[] numeros = par.split(",");
            valores.add(Par.crearPar(Integer.parseInt(numeros[0]),Integer.parseInt(numeros[1])));
        }
        return valores;
    }

    public Arbol getArbol(){
        Arbol arbol = new Arbol();
        for(String linea:getLineas()){
            String elementos[] = linea.split("{");
            Nodo nodo = arbol.getNodo(elementos[0]); // Se obtienen los elementos antes del paréntesis, posiblemente conteniendo el nodo y el peso
            elementos[1].replace("}", "");
            for(String transicion:elementos[1].split(",")){
                elementos = transicion.split("$");
                nodo.agregarTransicion(arbol.getNodo(elementos[0]),Integer.parseInt(elementos[1]));
            }
        }
        return valores;
    }
}