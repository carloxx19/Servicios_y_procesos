public class Mesa {
    public boolean[] tenedores;

    public Mesa(int num_monjes) {
        this.tenedores = new boolean[num_monjes];
    }

    public int comprobar_izquierda(int i) {
        return i;
    }

    public int comprobar_derecha(int i) {
        if (i == 0) {
            return tenedores.length - 1;
        } else {
            return i - 1;
        }
    }

    public synchronized void coger_tenedor(int monje) throws InterruptedException {

        while (tenedores[comprobar_izquierda(monje)] || tenedores[comprobar_derecha(monje)]) {
            wait();
        }

        tenedores[comprobar_izquierda(monje)] = true;
        tenedores[comprobar_derecha(monje)] = true;
    }

    public synchronized void dejar_tenedores(int monje) {
        tenedores[comprobar_izquierda(monje)] = false;
        tenedores[comprobar_derecha(monje)] = false;

        notifyAll();
    }
}