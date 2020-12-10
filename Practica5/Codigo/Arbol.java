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

import java.util.ArrayList;

public class Arbol {   

    protected ArrayList<Nodo> nodos;

    public Arbol(){
        nodos = new ArrayList<Nodo>();
    }

    public ArrayList<Nodo> getNodos() {
        return nodos;
    }

    public void setNodos(ArrayList<Nodo> nodos) {
        this.nodos = nodos;
    }

    public void agregarNodo(Nodo nodo){
        nodos.add(nodo);
    }

    public int numeroNodos(){
        return nodos.size();
    }
    
    /**
     * Regresa la instancia del nodo si existe, de otra forma regresa
     * la instancia a un nodo creado a partir del nombre y peso ingresados
     * @param nombreNodo
     * @param peso
     * @return
     */
    public Nodo getNodo(String nombreNodo,Integer peso){
        if(!nodos.isEmpty()){
            for(Nodo nodo:nodos){
                if(nodo.getNombre().equals(nombreNodo))
                    return nodo;
            }
        }
        // Si no existe el nodo se crea
        Nodo nodoAux = new Nodo(nombreNodo,peso);
        nodos.add(nodoAux);
        return nodoAux;
    }

    /**
     * Identifica si la cadena contiene el peso del nodo y regresa la
     * instancia de este si existe, de otra forma regresa una instancia
     * de un nodo creado a partir del nombre y posiblemente peso ingresado
     * @param elementosNodo
     * @return nodo
     */    
    public Nodo getNodo(String elementosNodo){
        String elementos[] = elementosNodo.split(":");
        if(elementos.length > 1){
            return getNodo(elementos[0],Integer.parseInt(elementos[1]));
        }
        
        if(!nodos.isEmpty()){
            for(Nodo nodo:nodos){
                if(nodo.getNombre().equals(elementosNodo))
                    return nodo;
            }
        }
        // Si no existe el nodo se crea
        Nodo nodoAux = new Nodo(elementosNodo);
        nodos.add(nodoAux);
        return nodoAux;
    }

    /**
     * Regresa si existe un nodo con el nombre ingresado
     * @param nombreNodo
     * @return existe
     */
    public boolean existe(String nombreNodo){
        for(Nodo nodo:nodos){
            if(nodo.getNombre().equals(nombreNodo))
                return true;
        }
        return false;
    }

    public void imprimirArbol(){
        for(Nodo nodo:nodos){
            System.out.printf("\n %s$%d => ",nodo.getNombre(),nodo.getPeso());
            for(Object transicion[]:nodo.getTransiciones()){
                System.out.printf("%s$%s,",((Nodo)transicion[0]).getNombre(),(int)transicion[1]);
            }
        }
        System.out.println();
    }

}

class Nodo{

    protected String nombre;
    protected int peso;
    protected ArrayList<Object[]> transiciones;
    /* Una transición esta compuesta por un arreglo de 2 elementos Object
            transición => [ Nodo_que_transiciona(Nodo) , Costo_del_Arco(Integer)]
    */

    public Nodo(String nombre){
        this.nombre = nombre;
        this.peso = 0;
        transiciones = new ArrayList<Object[]>();
    }

    public Nodo(String nombre,int peso){
        this.nombre = nombre;
        this.peso = peso;
        transiciones = new ArrayList<Object[]>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public ArrayList<Object[]> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<Object[]> transiciones) {
        this.transiciones = transiciones;
    }

    public void agregarTransicion(Nodo nodoDestino,Integer costo){
        Object transicion[] = {nodoDestino,costo};
        this.transiciones.add(transicion);
    }
}