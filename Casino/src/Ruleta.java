import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Ruleta extends Thread {
    public static double saldo_banco;
    protected ArrayList<Jugador> jugadores;

    public Ruleta(ArrayList<Jugador> jugadores) {
        saldo_banco = 3000;
        this.jugadores = jugadores;
    }

    public void run() {
        int apuesta;
        Random num = new Random();

        System.out.println("""
                ***EL JUEGO EMPIEZA***
                JUGADOR1 EMPIEZA CON 300$
                JUGADOR2 EMPIEZA CON 300$
                JUGADOR3 EMPIEZA CON 300$
                JUGADOR4 EMPIEZA CON 300$
                """);

        while (jugadores.size() > 0) {
            int correcto = num.nextInt(36) + 1;
            System.out.println("||||||||||||||||||||||||||||||||");
            System.out.println("EL NUMERO DE LA RULETA ES: " + correcto);
            for (int pos = 0; pos < jugadores.size(); pos++) {
                apuesta = num.nextInt(36) + 1;
                System.out.println("El jugador " + (pos + 1) + " elige el numero: " + apuesta);
                if (apuesta == correcto) {
                    jugador_Gana(pos);
                } else {
                    jugador_Pierde(pos);
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("EL SALDO DE LOS JUGADORES ESTA AGOTADO , SE TERMINA LA PARTIDA");
    }

    public void jugador_Gana(int num) {
        saldo_banco = saldo_banco - 36;
        jugadores.get(num).dinero = jugadores.get(num).dinero + 360;
        System.out.println("El jugador " + jugadores.get(num).num + " ha ganado 360€. " +
                "\nTiene " + jugadores.get(num).dinero);
    }

    public void jugador_Pierde(int num) {
        saldo_banco = saldo_banco + 10;
        jugadores.get(num).dinero = jugadores.get(num).dinero - 10;
        if (jugadores.get(num).dinero <= 0) {
            System.out.println("Jugador " + (jugadores.get(num).num) + " sin saldo, eliminado");
            jugadores.remove(num);
        } else {
            System.out.println("El jugador " + jugadores.get(num).num + " pierde 10€\nEl jugador " +
                    jugadores.get(num).num + " tiene " + jugadores.get(num).dinero + "€");
            System.out.println(" ");
        }
    }
}