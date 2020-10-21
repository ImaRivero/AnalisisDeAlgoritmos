/*
    Fecha: 18/Octubre/2020
    Creado por: 
        + Omar Imanol Rivero Ronquillo
        + Luis Eduardo Valle Martínez
*/

import java.util.*;
import java.io.*;

// Clase para el manejo de los archivos con las cantidades a evaluar
public class Archivos{
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
