import java.util.ArrayList;
import java.util.Collections;

public class LCS {
    /**
     * Método que implementa el algoritmo del calculo del máximo subarreglo
     * mediante programación dinámica con el enfoque Bottom-Up
     * 
     * @param X String de comparacion
     * @param Y String de comparacion
     * @param m Numero de caracteres de X
     * @param n Numero de caracteres de Y
     * @return longitud del LCS para X[0...m-1], Y[0...n-1]
     */
    public int lcs(char[] X, char[] Y, int m, int n){
        int L[][] = new int[m+1][n+1]; 
        /**
         * L[i][j] contiene la longitud del LCS de X[0..i-1] y Y[0..j-1]
         */
        for (int i=0; i<=m; i++){ 
            for (int j=0; j<=n; j++){ 
                if (i == 0 || j == 0) 
                    L[i][j] = 0; 
                else if (X[i-1] == Y[j-1]) 
                    L[i][j] = L[i-1][j-1] + 1; 
                else
                    L[i][j] = max(L[i-1][j], L[i][j-1]); 
            } 
        } 
        return L[m][n];
    }

    /* Funcion para obtener el máximo de dos enteros */
    int max(int a, int b){ 
      return (a > b)? a : b; 
    }

    public void init(){
        String path = "../Archivos/LCS";
        ArrayList<String> listaArchivos = Archivos.listaArchivos(path);
        Collections.sort(listaArchivos);

        for(int i = 0; i < listaArchivos.size(); i+=2){
            String s1 = Archivos.archivo(listaArchivos.get(i)).leerArchivo(path, listaArchivos.get(i));
            String s2 = Archivos.archivo(listaArchivos.get(i+1)).leerArchivo(path, listaArchivos.get(i+1));

            int len = lcs(s1.toCharArray(), s2.toCharArray(), s1.length(), s2.length());
            float porcent = len*100/s1.length();

            System.out.println(s1.length());
            System.out.println(len);
            System.out.println("Porcentaje de similitud: " + porcent + " %");
            System.out.println("---------------------------------");
        }
    }

    public static void main(String[] args) {
        LCS lcs = new LCS();
        lcs.init();
    }
} 
  
   
  
  
  

  