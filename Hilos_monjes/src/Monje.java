public class Monje extends Thread {
    private Mesa mesa;
    private int monje;
    private int indice_monje;

    public Monje(Mesa mesa, int monje) {
        this.mesa = mesa;
        this.monje = monje;
        this.indice_monje = monje - 1;
    }

    public void run() {
        while (true) {
            try {
                rezar();
                mesa.coger_tenedor(indice_monje);
                comer();
                mesa.dejar_tenedores(indice_monje);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void rezar() throws InterruptedException {
        System.out.println("El monje: " + monje + " esta rezando");
        sleep((long) (Math.random() * 4000));
    }

    public void comer() throws InterruptedException {
        System.out.println();
        System.out.println("El monje: " + monje + " esta comiendo");

        sleep((long) (Math.random() * 4000));
    }
}