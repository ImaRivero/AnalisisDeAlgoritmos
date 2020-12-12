/*
    IPN - ESCOM
    Análisis de Algoritmos
    Prof: Benjamín Luna Benoso
    Grupo: 3CV1
    Práctica 5: Algoritmos Greedy
    ---------------------------------------------
    Creación: 10/Diciembre/2020
    Creado por: 
        + Omar Imanol Rivero Ronquillo
        + Luis Eduardo Valle Martínez
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashMap;

// Definicion de objeto para los nodos del arbol
class NodoHuffman{
    int frec;
    char car;
    NodoHuffman der;
    NodoHuffman izq;
}

class ComparadorNodos implements Comparator<NodoHuffman>{
    public int compare(NodoHuffman x,  NodoHuffman y){
        return x.frec - y.frec;
    }
}


public class Huffman {
   
    // De la forma llave = caracter, contenido = codificacion de huffman
    static HashMap<Character, String> diccionario;
    int numOperaciones;

    public Huffman(){
        //numOperaciones = 0;
        diccionario = new HashMap<>();
    }

    /**
     * Metodo recursivo que imprime el arbol generado por la codificacion de Huffman
     * @param raiz Nodo raiz del arbol
     * @param s String que contiene la codificacion de huffman
     */
    public static void impArbol(NodoHuffman raiz, String s){
        if(raiz.izq == null && raiz.der == null && raiz.car != '$'){
            System.out.println(raiz.car + "\t|\t" + s);
            return;
        }
        impArbol(raiz.izq, s + "0");
        impArbol(raiz.der, s + "1");
    }

    /**
     * Método recursivo que recorre el arbol y genera un diccionario con los caracteres
     * y su codificacion de Huffman
     * @param raiz Raiz del arbol/subarbol
     * @param s codificacion de Huffman
     */
    public static void guardarDicc(NodoHuffman raiz, String s){
        if(raiz.izq == null && raiz.der == null && raiz.car != '$'){
            diccionario.put(raiz.car, s);
            return;
        }
        guardarDicc(raiz.izq, s + "0");
        guardarDicc(raiz.der, s + "1");
    }


    /**
     * Implementacion del algoritmo de Huffman
     * @param entrada cadena de entrada
     * @return nodo raiz del arbol
     */
    public NodoHuffman crearArbol(String entrada){
        /*
        int n = 4; // charlist.size()
        char[] charArray = { 'A', 'B', 'C', 'D' };
        int[] charfreq = { 5, 1, 6, 3 };
        */
        ArrayList<Character> charlist = charList(entrada);

        PriorityQueue<NodoHuffman> pq = new PriorityQueue<>(charlist.size(), new ComparadorNodos());
        numOperaciones += 1;

        for(int i = 0; i < charlist.size(); i++){
            NodoHuffman nh = new NodoHuffman();
            
            nh.car = charlist.get(i);
            nh.frec = charFrec(entrada, charlist.get(i));
            nh.der = null;
            nh.izq = null;

            pq.add(nh);

            numOperaciones += 1;
        }

        NodoHuffman raiz = null;
        numOperaciones += 1;

        while (pq.size() > 1){
            NodoHuffman nh_x = pq.peek();
            pq.poll();

            NodoHuffman nh_y = pq.peek();
            pq.poll();

            NodoHuffman nh_gen = new NodoHuffman();
            nh_gen.frec = nh_x.frec + nh_y.frec;
            nh_gen.car = '$';
            nh_gen.izq = nh_x;
            nh_gen.der = nh_y;
            raiz = nh_gen;

            pq.add(nh_gen);

            numOperaciones += 1;
        }
        /*
        System.out.println("Char\t|\tHuffman");
        System.out.println("--------------------");
        impArbol(raiz, "");
        */
        guardarDicc(raiz, "");
    
        numOperaciones += 1;
        return raiz;
    }

    /**
     * Crea ArrayList de caracteres sin repeticion
     * @param s Entrada de texto original
     * @return  ArrayList de caracteres
     */
    public ArrayList<Character> charList(String s){
        ArrayList<Character> out = new ArrayList<>();
        char[] charStr = s.toCharArray();
        for(char c : charStr){
            if(!out.contains(c))
                out.add(c);
        }
        return out;
    }

    /**
     * Método que cuenta las frecuencias de los caracteres dentro del texto
     * @param s Entrada de texto original
     * @param c Caracter que se contará
     * @return numero de repeticiones
     */
    public static int charFrec(String s, char c){
        int cont = 0;
        char[] charStr = s.toCharArray();
        for(int i = 0; i < s.length(); i++){
            if(charStr[i] == c)
                cont++;
        }
        return cont;
    }

    /**
     * Comprime la entrada de acuerdo a la codificacion de Huffman
     * existente en el diccionario.
     * @param in texto de entrada (plano)
     * @return texto codificado
     */
    public String comprimir(String in){
        String out = "";
        char[] charArr = in.toCharArray();
        for(char key: charArr){
            out += diccionario.get(key);
        }
        return out;
    }
    
    /**
     * Descomprime la cadena codificada de Huffman
     * @param raiz Nodo raiz del arbol
     * @param in Entrada codificada de Huffman
     * @return  Texto plano
     */
    public String descomprimir(NodoHuffman raiz, String in){
        String out = "";
        NodoHuffman aux = raiz;
        char[] charArr = in.toCharArray();
        for(int i = 0; i < in.length(); i++){
            if(charArr[i] == '0')
                aux = aux.izq;
            else
                aux = aux.der;
            
            //Cuando alcanzamos una hoja
            if(aux.izq == null && aux.der == null){
                out += aux.car;
                aux = raiz;
            }
        }
        return out;
    }

    /**
     * Calcula la tasa de compresión comparando la entrada original con
     * la salida de la codificación de Huffman.
     * 
     * @param orig texto plano
     * @param comp codificacion de Huffman
     * @return poncentaje de compresion
     */
    public float tasaDeCompresion(String orig, String comp){

        // Cada caracter de la cadena original tiene 8 bits en utf-8
        float numBitsOrig = orig.length() * 8;
        // Cada caracter en la cadena comprimida representa un bit
        float numBitsComp = comp.length();

        return (numBitsComp * 100) / numBitsOrig;
    }
}
