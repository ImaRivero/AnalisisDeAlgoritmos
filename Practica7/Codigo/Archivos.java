/*
    IPN - ESCOM
    Análisis de Algoritmos
    Prof: Benjamín Luna Benoso
    Grupo: 3CV1
    Práctca 7: Programación Dinámica
    ---------------------------------------------
    Creación: 25/Diciembre/2020
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
            SeptimaPractica.error(e.getMessage());
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
            SeptimaPractica.error(e.getMessage());
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
    
    public ArrayList<Object> getDimensionesMatrices(){
        ArrayList<Object> P = new ArrayList<Object>();
        String cadena = getCadena();
        P.add((String)cadena.subSequence(0, cadena.indexOf("<")));
        cadena = cadena.substring(cadena.indexOf("<")+1,cadena.length()-1);
        for(String dimension:cadena.split(","))
            P.add(Integer.parseInt(dimension));
        return P;
    }

    /**
     * Devuelve la lista de archivos en el directorio de entrada
     * @param dir directorio
     * @return lista de nombres
     */
    public static ArrayList<String> listaArchivos(String dir){
        File[] files = new File(dir).listFiles();
        ArrayList<String> result = new ArrayList<>();
        for(File f: files)
            if(f.isFile())
                result.add(f.getName());
        return result;
    }

    /**
     * Lee el archivo sin hacer modificaciones al mismo
     * @param path directorio donde se encuentra el archivo
     * @param filename  nombre del archivo
     * @return  string con el texto del archivo
     */
    public String leerArchivo(String path, String filename){
        File fil = new File(path + "/" + filename);
        String out = "";

        try{
            Scanner lector = new Scanner(fil);
            while(lector.hasNextLine()){
                out += lector.nextLine();
            }
            out = eliminarTodosEspacios(out);
            lector.close();
        } catch(FileNotFoundException e){
            //QuintaPractica.error(e.getMessage());
            e.printStackTrace();
        }
        
        return out;
    }
}
