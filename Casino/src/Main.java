import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
        Jugador jugador3 = new Jugador();
        Jugador jugador4 = new Jugador();
        ArrayList<Jugador> jugadores = new ArrayList<>();

        jugadores.add(jugador1);
        jugadores.add(jugador2);
        jugadores.add(jugador3);
        jugadores.add(jugador4);

        Ruleta ruleta = new Ruleta(jugadores);
        ruleta.start();

    }
}

