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
import java.util.HashMap;

public class Kruskal {
    
    protected ArrayList<Object[]> colaPrioridad;
    protected ArrayList<Object[]> componenteConexa;
    protected ArrayList<Object[]> minimoRecorrimiento;
    protected int costoMinimoRecorrimiento;
    public int numeroOperaciones;

    public Kruskal(){
        colaPrioridad = new ArrayList<Object[]>();
        componenteConexa = new ArrayList<Object[]>();
        minimoRecorrimiento = new ArrayList<Object[]>();
        costoMinimoRecorrimiento = 0;
    }

    /**
     * Mediante el principio de búsqueda binaria se van
     * acomodando los elementos en la cola de forma
     * ascendente según el valor del costo del vertice
     * entre los 2 nodos
     * @param elemento
     * @param inicio
     * @param fin
     * @return
     */
    private boolean agregarAColaPrioridad(Object elemento[],int inicio,int fin){
        numeroOperaciones ++;
        if(inicio > fin){ // Caso base
            if(fin == colaPrioridad.size()-1){
                colaPrioridad.add(elemento);
                numeroOperaciones ++;
            }else{
                colaPrioridad.add(inicio,elemento);
                numeroOperaciones ++;
            }
            return true;
        }

        int medio =(int)(fin+inicio)/2;
        int valorMedio =(int)colaPrioridad.get(medio)[2];
        numeroOperaciones ++;
        if(valorMedio == (int)elemento[2]){ // Tienen el mismo costo
            int contador = 1,
            salir = 0;
            Object elementoAux[]=new Object[3];
            boolean invalido = false;
            
            elementoAux = colaPrioridad.get(medio);
            if(elementoAux[0].equals(elemento[1])&&elementoAux[1].equals(elemento[0])){
                salir +=2;
                invalido = true;
            }

            while(salir < 2){
                salir = 0;
                if((contador+medio) <= (colaPrioridad.size()-1)){
                    elementoAux = colaPrioridad.get(contador+medio);
                    if(elementoAux[2]==elemento[2]){
                        if(elementoAux[0].equals(elemento[1])&&elementoAux[1].equals(elemento[0])){
                            salir +=2;
                            invalido = true;
                        }
                    }else salir++;
                }else salir++;

                if((medio-contador) >= 0){
                    elementoAux = colaPrioridad.get(medio-contador);
                    if(elementoAux[2]==elemento[2]){
                        if(elementoAux[0].equals(elemento[1])&&elementoAux[1].equals(elemento[0])){
                            salir +=2;
                            invalido = true;
                        }
                    }else salir++;
                }else salir++;

                contador ++;
            }

            if(!invalido){
                colaPrioridad.add(medio,elemento);
                numeroOperaciones ++;
            }
            return true;
        }else if(valorMedio > (int)elemento[2]){ // El elemento tiene un menor costo
            agregarAColaPrioridad(elemento, inicio, medio-1);
            numeroOperaciones ++;
        }else{ // El elemento un costo mayor
            agregarAColaPrioridad(elemento, medio+1, fin);
            numeroOperaciones ++;
        }
        return true;  
    }

    public void imprimirColaPrioridad(){
        for(Object elemento[]:colaPrioridad){
            System.out.printf("%s , %s , %d\n",elemento[0],elemento[1],elemento[2]);
        }
    }

    /**
     * Retorna si existe un componente con la llave ingresada
     * @param llave
     * @return
     */
    protected boolean existeComponenteConexa(String llave){
        for(Object componente[]:componenteConexa){
            if(componente[0].equals(llave))
                return true;
        }
        return false;
    }

    /**
     * Regresa el valor del segundo campo de las
     * componentes conexas, que indica si el nodo
     * ya ha sido utilizado en los mostrados en la
     * solución
     * @param llave
     * @return nodoUtilizado
     */
    protected boolean esComponenteConexa(String llave){
        for(Object componente[]:componenteConexa){
            if(componente[0].equals(llave))
                return (Boolean)componente[1];
        }
        return false;
    }

    /**
     * Retorna si 2 aristas ingresadas con vaĺidas como
     * par resultado, esto según si son conexas o no,
     * y si son conexas que aún no haya sido colocado 
     * al menos una de las aristas como resultado
     * @param arista1
     * @param arista2
     * @return
     */
    protected boolean aristasConexasValidas(String arista1,String arista2){
        boolean valor = false;
        for(int i=0;i<componenteConexa.size();i++){
            if(componenteConexa.get(i)[0].equals(arista1)){
                if(i>0 && i<componenteConexa.size()-1){
                    if(componenteConexa.get(i+1)[0].equals(arista2))
                        return (boolean)componenteConexa.get(i)[1] || (boolean)componenteConexa.get(i+1)[1];
                    else if(componenteConexa.get(i-1)[0].equals(arista2))
                        return (boolean)componenteConexa.get(i)[1] || (boolean)componenteConexa.get(i-1)[1];
                    return true;
                }else{
                    int s = (i+1 == componenteConexa.size())?i-1:i+1;
                    if(componenteConexa.get(s)[0].equals(arista2)){
                        return (boolean)componenteConexa.get(i)[1] || (boolean)componenteConexa.get(s)[1];
                    }else
                        return true;
                }
            }
        }
        return valor;
    }


    /**
     * Cambia el valor de uso a 2 aristas ingresadas
     * siendo la componente[1] igual a falso
     * @param arista1
     * @param arista2
     */
    protected void marcarComponentesConexas(String arista1,String arista2){
        Object componente[]= new Object[3];
        for(int i=0;i<componenteConexa.size();i++){
            if(componenteConexa.get(i)[0].equals(arista1) || componenteConexa.get(i)[0].equals(arista2)){
                componente = componenteConexa.remove(i);
                componente[1] = false;
                componenteConexa.add(i,componente);
            }
        }
    }

    /**
     * Creación del arreglo con las aristas conexas
     * y el valor como no utilizadas aún
     * @param grafo
     */
    public void crearComponenteConexa(Grafo grafo){
        componenteConexa.clear();
        numeroOperaciones ++;
        for(Nodo nodo:grafo.getNodos()){
            Object componente[] = {nodo.getNombre(),true};
            numeroOperaciones ++;
            componenteConexa.add(componente);
            numeroOperaciones ++;
        }
    }

    public void imprimirComponeneteConexa(){
        for(Object componente[]:componenteConexa){
            System.out.printf("%s , %b\n",componente[0],componente[1]);
        }
    }

    public void imprimirMinimoRecorrimiento(){
        System.out.printf("F$%d={",costoMinimoRecorrimiento);
        for(Object resultado[]:minimoRecorrimiento){
            System.out.printf(" (%s,%s) ",resultado[0],resultado[1]);
        }
        System.out.println("}");
    }
    
    public void kruskal(Grafo grafo){
        colaPrioridad.clear();
        componenteConexa.clear();
        minimoRecorrimiento.clear();
        costoMinimoRecorrimiento = 0;        
        numeroOperaciones = 0;
        
        for(Nodo nodo:grafo.getNodos()){
            for(Object transicion[]:nodo.getTransiciones()){
                Object elemento[] = {nodo.getNombre(),((Nodo)transicion[0]).getNombre(),transicion[1]};
                agregarAColaPrioridad(elemento, 0, colaPrioridad.size()-1);
            }
        }
        /* imprimirColaPrioridad(); */

        crearComponenteConexa(grafo);
        /* imprimirComponeneteConexa(); */

        while((!colaPrioridad.isEmpty()) && (minimoRecorrimiento.size()<grafo.numeroNodos()-1)){
            Object[] arista = colaPrioridad.remove(0);
            numeroOperaciones ++;
            if(!arista[0].equals(arista[1])){
                if(aristasConexasValidas((String)arista[0], (String)arista[1])){
                    marcarComponentesConexas((String)arista[0], (String)arista[1]);
                    numeroOperaciones ++;
                    minimoRecorrimiento.add(arista);
                    numeroOperaciones ++;
                    costoMinimoRecorrimiento += (int)arista[2];
                    numeroOperaciones ++;
                }
            }
        }

        imprimirMinimoRecorrimiento();
    }
    
}
