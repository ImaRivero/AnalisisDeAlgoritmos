import java.util.ArrayList;

public class Fibonacci {

    double numOpeTop;
    double numOpeBot;
    double numOpeRec;

    /**
     * Función de inicialización para el arreglo
     * de memorización usado en el método
     * calcFiboTop()
     * 
     * @param n tamaño del arreglo
     * @return arreglo para memorizar valores
     */
    public int[] fiboIniArray(int n){
        int[] fibo = new int[n];
        for(int i = 2; i < n; i++){
            fibo[i] = -1;
        }
        fibo[0] = 0;
        fibo[1] = 1;
        return fibo;
    }

    /**
     * Top-Down
     * 
     * Algoritmo para calcular el enésimo término de
     * la sucesión de fibonacci por el método Top-Down.
     * 
     * @param n enésimo término buscado de la sucesión
     * @param fibo arreglo para memorizar valores.
     * @return valor del enésimo término.
     */
    public int calcFiboTop(int n, int[] fibo){
        if(fibo[n] != -1){
            numOpeTop++;
            return fibo[n];
        }
        numOpeTop++;
        fibo[n] = calcFiboTop(n-2, fibo) + calcFiboTop(n-1, fibo);
        numOpeTop++;
        return fibo[n];
    }

    /**
     * Botton-Up
     * 
     * Algoritmo para calcular el enésimo término de 
     * la sucesión de Fibonnaci por el método Bottom-Up.
     * 
     * @param n enésimo término buscado de la sucesión
     * @param fibo arreglo para memorizar valores.
     * @return valor del enésimo término.
     */
    public int calcFiboBottom(int n, int[] fibo){
        if(n <= 1){
            numOpeBot++;
            return 1;
        }
        else{
            fibo[0] = 0;
            fibo[1] = 1;
            numOpeBot += 2;
            for(int i = 2; i <= n; i++){
                numOpeBot++;
                fibo[i] = fibo[i-1] + fibo[i-2];
            }
        }
        numOpeBot++;
        return fibo[n];
    }

    /**
     * Recursivo
     * 
     * Algoritmo para calcular el enésimo término de la sucesión
     * de Fibonacci mediante el enfoque tradicional mediante el
     * uso de una llamada recursiva al método.
     * 
     * @param n enésimo término buscado de la sucesión
     * @return valor del enésimo término.
     */
    public int calcFiboRec(int n){
        if(n <= 1){
            numOpeRec++;
            return n;
        }
        else{
            numOpeRec++;
            return calcFiboRec(n-1) + calcFiboRec(n-2);
        }
    }
}

class ControladorFibonacci{

    Graficas grafica = new Graficas(1, 1);
    
    public void init(String rutaArchivo){
        int indiceColeccion = -1;
        double valor_eje_x;
        double valor_eje_y;

        Archivos archivo = new Archivos(rutaArchivo);
        ArrayList<Integer> valores_fibonacci = archivo.getValores();
        Fibonacci fib = new Fibonacci();

        System.out.println("\n Fibonacci\n---------------");
        System.out.println(">>> Top-Down <<<");
        for(int i = 0; i < valores_fibonacci.size(); i++){
            fib.numOpeTop = 0;
            int[] tabla = fib.fiboIniArray(valores_fibonacci.get(i)+1);
            fib.calcFiboTop(valores_fibonacci.get(i), tabla);
            valor_eje_x = (double)valores_fibonacci.get(i);
            valor_eje_y = fib.numOpeTop;
					
            System.out.printf("P%d( %d ,%d )\n",i,(int)valor_eje_x,(int)valor_eje_y);

            indiceColeccion = grafica.agregarParOrdenado(
                "Fibonacci Top-Down", 
                valor_eje_x,
                valor_eje_y, 
                indiceColeccion);
        }

        System.out.println(">>> Bottom-up <<<");
        for(int i = 0; i < valores_fibonacci.size(); i++){

            fib.numOpeBot = 0;
            int[] tabla = new int[valores_fibonacci.get(i) + 1];
            fib.calcFiboBottom(valores_fibonacci.get(i), tabla);
            valor_eje_x = (double)valores_fibonacci.get(i);
            valor_eje_y = fib.numOpeBot;

            System.out.printf("P%d( %d ,%d )\n",i,(int)valor_eje_x,(int)valor_eje_y);

            indiceColeccion = grafica.agregarParOrdenado(
                "Fibonacci Botton-up", 
                valor_eje_x,
                valor_eje_y, 
                indiceColeccion);
        }

        System.out.println(">>> Recursivo <<<");
        for(int i = 0; i < valores_fibonacci.size(); i++){
            //System.out.printf("P%d( %d ,%d )\n",i,valor_eje_x,valor_eje_y); */
            fib.numOpeRec = 0;
            fib.calcFiboRec(valores_fibonacci.get(i));
            valor_eje_x = (double)valores_fibonacci.get(i);
            valor_eje_y = fib.numOpeRec;

            System.out.printf("P%d( %d ,%d )\n",i,(int)valor_eje_x,(int)valor_eje_y);

            indiceColeccion = grafica.agregarParOrdenado(
                "Fibonacci Recursivo", 
                valor_eje_x,
                valor_eje_y, 
                indiceColeccion);
					
        }
				

        grafica.crearGrafica(
            "Graficación del índice de Fibonacci vs instrucciones ejecutadas para los algoritmos de Fibonacci",
            "Valor(n)", 
            "NúmeroOperaciones", 
            indiceColeccion);

        grafica.mostrarGrafica();
    }
}
