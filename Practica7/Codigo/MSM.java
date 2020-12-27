public class MSM {

    private int M[][]; // Almacena el número de operaciones de las asociaciones óptimas
    private int S[][]; // Servirá para guardar las decisiones en cada iteración

    public void secuenciaMatrices(Integer P[]){
        int n = P.length - 1;
        int j = 0;
        int q = 0;
        this.M = new int[n][n];
        this.S = new int[n][n];

        for(int i=0;i<n;i++){// Inicialización de la diagonal principal de las matrices
            this.M[i][i] = 0;
            this.S[i][i] = 0;
        }
        for(int l=1;l<n+1;l++){
            for(int i=0;i<(n-l);i++){
                j = i + l;
                M[i][j] = -1;
                for(int k=i;k<=j-1;k++){
                    q = M[i][k] + M[k+1][j] + (P[i]*P[k+1]*P[j+1]);
                    if(q < M[i][j] || M[i][j] == -1){
                        M[i][j] = q;
                        S[i][j] = k;
                    }
                }
            }
        }
    }

    public void imprimirMatrices(){
        
        System.out.print("Matriz S:\n------------------------\n");
        for(int fila[]:S){
            for(int valor:fila)
            System.out.printf("%5d|",valor);
            System.out.println();
        }
        System.out.println("------------------------");
        
        System.out.print("\nMatriz M:\n------------------------\n");
        for(int fila[]:M){
            for(int valor:fila)
                System.out.printf("%5d|",valor);
            System.out.println();
        }
        System.out.println("------------------------");
    }

    public void imprimirParentesis(String nombreMatrices,int i,int j){
        if(i==j)
            System.out.printf("%s%d",nombreMatrices,i+1);
        else{
            System.out.print("(");
            imprimirParentesis(nombreMatrices,i,this.S[i][j]);
            imprimirParentesis(nombreMatrices,this.S[i][j]+1,j);
            System.out.print(")");
        }
    }
}