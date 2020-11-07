/*
    IPN - ESCOM
    Análisis de Algoritmos
    Prof: Benjamín Luna Benoso
    Grupo: 3CV1
    Práctca 2: Funciones Recursivas vs Iterativas
    ---------------------------------------------
    Creación: 25/Octubre/2020
    Creado por: 
        + Omar Imanol Rivero Ronquillo
        + Luis Eduardo Valle Martínez
*/
        
import java.util.*;
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

    private String eliminarEspacios(String cadena){
        cadena = cadena.replaceAll("\\s+",""); // Elimina los espacios, tab y saltos de línea
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

            cadena = eliminarEspacios(cadena);
            lector.close();
        }catch(Exception e){
            TercerPractica.error(e.getMessage());
        }

        return cadena;
    }

    // Regresa los valores separados por coma del archivo en un array de enteros
    public ArrayList<Integer> getValores(){
        ArrayList<Integer> valores = new ArrayList<Integer>();
        // Se separa la cadena en las cantidad separadas por comas y se guardan como enteros en 
        for(String cantidad:getCadena().split(","))
                valores.add(new Integer(Integer.parseInt(cantidad)));

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
}