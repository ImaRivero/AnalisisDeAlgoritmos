/*
    Fecha: 25/Octubre/2020
    Creado por: 
        + Omar Imanol Rivero Ronquillo
        + Luis Eduardo Valle Martínez
*/

// Clase definitoria de un par de enteros
public class Par{
    
    private int[] par;

    private Par(int a, int b){
        par = new int[2];
        par[0] = a;
        par[1] = b;
    }

    public static Par crearPar(int a,int b){
        return new Par(a,b);
    }

    public int get(int index){
        if(index>=0 && index<2)
            return par[index];
        return -1;
    }

    public void set(int a, int index){
        par[index] = a;
    }

}