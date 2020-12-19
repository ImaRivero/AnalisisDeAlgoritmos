public class ObjetoKnapsack{
    
    protected String nombre;
    protected double peso,valor;

    public ObjetoKnapsack(String nombre,double peso,double valor){
        this.nombre = nombre;
        this.peso = peso;
        this.valor = valor;
    }

    public ObjetoKnapsack(String nombre,String valores[]){
        this.nombre = nombre;
        this.peso = Double.parseDouble(valores[0]);
        this.valor = Double.parseDouble(valores[1]);
    }

    public String getNombre() {
        return nombre;
    }

    public double getPeso() {
        return peso;
    }

    public double getValor() {
        return valor;
    }

    public double getValorPeso(){
        return valor/peso;
    }
}