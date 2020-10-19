public class PrimeraPractica{

    public static void main(String args[]){
        int ent1[] = {0,1,1};
        int ent2[] = {0,1,1};
        int res[] = Suma(ent1, ent2, 3);
        for(int i = 0; i < 3; i++){
            System.out.print(res[i]);
        }
    }

    public static int[] Suma(int[] A, int[] B, int r){

        int[] C = new int[r];
        int acarreo = 0;

        for(int i = r-1; i >= 0; i--){
            if(acarreo == 0){
                if(A[i] == B[i] && A[i] == 1){
                    C[i] = 0;
                    acarreo = 1;
                }
                else{
                    C[i] = 1;
                    acarreo = 0;
                }
            }
            else {
                if(A[i] == B[i]){
                    C[i] = 1;
                    if(A[i] == 1)
                        acarreo = 1;
                    else
                        acarreo = 0;
                }
                else{
                    C[i] = 0;
                    acarreo = 1;
                }
            }
        }
        return C;
    }
}