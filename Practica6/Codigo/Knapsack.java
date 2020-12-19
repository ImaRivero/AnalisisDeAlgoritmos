import java.util.ArrayList;

public class Knapsack {
    protected double tablaDinamica[][];
    protected int pesoMaximo;

    public double numeroOperaciones;

    public Knapsack(int pesoMaximo){
        this.pesoMaximo = pesoMaximo;
        numeroOperaciones = 0.0;
    }

    /**
     * Crea la tabla dińamica restringida por los pesos de los objetos y el peso
     * máximo soportado por la mochila, donde los valores de las celdas son el 
     * valor máximo posible obtenible para cada peso desde 0 hasta el máximo
     * considerando los objetos anteriores y actual correspondiente a la fila
     * @param objetos
     */
    public void knapsack(ArrayList<ObjetoKnapsack> objetos){
        numeroOperaciones = 0;
        // Tabla restringida en las filas por los pesos de los objetos y el peso ascendete hasta el peso maximo de la mochila en las columnas
        // Los valores en cada celda corresponde al valor máximo óptimo considerando los respectivos objetos de la fila actual y anteriores
        tablaDinamica = new double[objetos.size()+1][pesoMaximo+1];
        numeroOperaciones += 1;
        // Inicialización de la tabla dinámica
        for(int i=0;i<pesoMaximo;i++)
            tablaDinamica[0][i] = 0.0;
        // Llenado de la tabla
        for(int numObjeto=1;numObjeto<=objetos.size();numObjeto++){
            ObjetoKnapsack objeto = objetos.get(numObjeto-1);
            numeroOperaciones += 1;
            for(int peso=0;peso<=pesoMaximo;peso++){
                numeroOperaciones += 1;
                if(peso<objeto.getPeso())// El peso del objeto es menor al de la columna en la tabla
                    tablaDinamica[numObjeto][peso] = tablaDinamica[numObjeto-1][peso];
                else{
                    if(tablaDinamica[numObjeto-1][peso] >= tablaDinamica[numObjeto-1][(int)(peso-objeto.getPeso())]+objeto.getValor())
                        // El valor óptimo con los objetos anteriores es mayor que cuando se considera el objeto actual
                        tablaDinamica[numObjeto][peso] = tablaDinamica[numObjeto-1][peso];
                    else
                        // El valor óptimo considerando el objeto actual es mayor que con los objetos anteriores
                        tablaDinamica[numObjeto][peso] = tablaDinamica[numObjeto-1][(int)(peso-objeto.getPeso())]+objeto.getValor();
                }    
            }
        }
    }

    /**
     * Retorna un arreglo con los objetos que son ingresados a la mochila
     * @param objetos
     * @return objetosIngresados
     */
    public ArrayList<ObjetoKnapsack> test(ArrayList<ObjetoKnapsack> objetos){
        int numObjeto = tablaDinamica.length-1;
        int peso = pesoMaximo;
        ArrayList<ObjetoKnapsack> objetosIngresados = new ArrayList<ObjetoKnapsack>();

        while(numObjeto > 0){
            if(tablaDinamica[numObjeto][peso] != tablaDinamica[numObjeto-1][peso]){
                objetosIngresados.add(objetos.get(numObjeto-1));
                peso =(int)(peso - objetos.get(numObjeto-1).getPeso());
            }
            numObjeto --;
        }

        return objetosIngresados;
    }

    public void imprimirTablaDinamica(ArrayList<ObjetoKnapsack> objetos){
        // Línea divisora encabezado
        System.out.print("\n=========");
        for(int i=0;i<tablaDinamica[0].length;i++)
            System.out.printf("======");
        System.out.println();
        // Valores encabezado
        System.out.printf("%8s|","W");
        for(int i=0;i<tablaDinamica[0].length;i++)
            System.out.printf("%5d|",i);
        // Línea divisora encabezado
        System.out.print("\n=========");
        for(int i=0;i<tablaDinamica[0].length;i++)
            System.out.printf("======");
        System.out.println();
        // Valores de las celdas
        for(int i=1;i<tablaDinamica.length;i++){
            System.out.printf("%8s|","W"+i+"="+objetos.get(i-1).getPeso());
            for(int j=0;j<tablaDinamica[0].length;j++)
                System.out.printf("%5.2g|",tablaDinamica[i][j]);
            if(i<tablaDinamica.length-1)
                System.out.println();
        }
        // Línea divisora final
        System.out.print("\n=========");
        for(int i=0;i<tablaDinamica[0].length;i++)
            System.out.printf("======");
        System.out.println();
    }

    public void imprimirObjetosIngresados(ArrayList<ObjetoKnapsack> objetosIngresados){
        System.out.print("Objetos ingresados:{");
        for(ObjetoKnapsack objeto:objetosIngresados)
            System.out.printf(" %s, ",objeto.getNombre());
        System.out.println("}");
    }
}