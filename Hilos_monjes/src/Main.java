public class Main {
    public static void main(String[] args) {
        Mesa mesa = new Mesa(5);
        for (int i = 1; i <= mesa.tenedores.length; i++) {
            Monje monje = new Monje(mesa, i);
            monje.start();
        }
    }
}