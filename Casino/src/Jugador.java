public class Jugador {
    public static int numeros = 0;
    public int num;
    public double dinero;

    public Jugador() {
        numeros++;
        num = numeros;
        this.dinero = 50;
    }
}