/*
    IPN - ESCOM
    Análisis de Algoritmos
    Prof: Benjamín Luna Benoso
    Grupo: 3CV1
    Práctca 6: Programación Dinámica
    ---------------------------------------------
    Creación: 18/Diciembre/2020
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
    public Archivos(String r){
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
            SextaPractica.error(e.getMessage());
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
            SextaPractica.error(e.getMessage());
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

    public ArrayList<Integer> getPesoMaximoMochila(){
        ArrayList<Integer> pesos = new ArrayList<Integer>();
        for(String linea:getLineas())
            pesos.add(Integer.parseInt(linea.substring(0,linea.indexOf("$"))));
        
        return pesos;
    }

    // Regresa un ArrayList de ObjetoKnapsack por cada fila del archivo
    public ArrayList<ArrayList<ObjetoKnapsack>> getObjetosMochila(){
        ArrayList<ArrayList<ObjetoKnapsack>> conjuntoObjetos = new ArrayList<ArrayList<ObjetoKnapsack>>();
        for(String linea:getLineas()){
            ArrayList<ObjetoKnapsack> objetos = new ArrayList<ObjetoKnapsack>();
            linea = linea.substring(linea.indexOf('$')+1);
            for(String objeto:linea.split(";")){
                String nombre = objeto.substring(0,objeto.indexOf('('));
                ObjetoKnapsack objetoAux = new ObjetoKnapsack(nombre,objeto.substring(objeto.indexOf("(")+1,objeto.indexOf(")")).split(","));
                objetos.add(objetoAux);
            }
            conjuntoObjetos.add(objetos);
        }
        
        return conjuntoObjetos;
    }
	

}
