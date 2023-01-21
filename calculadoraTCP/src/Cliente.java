import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {

        //VARIABLES
        final String HOST = "localhost";
        final int PUERTO = 5001;
        int operacion;
        Scanner teclado = new Scanner(System.in);
        Socket socket = new Socket(HOST, PUERTO);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        String mensaje = in.readUTF();

        while (true) {
            try {
                System.out.println(mensaje);
                operacion = teclado.nextInt();
                if (operacion <= 0 || operacion > 5) {
                    System.err.println("un numero del 1 al 5");
                } else {
                    out.writeInt(operacion);
                    break;
                }
            } catch (InputMismatchException e) {
                System.err.println("INTRODUCE UN NUMERO NO LETRA!!!!");
                //Esta linea arregla el problema de: Cuando se reinicia el bucle se hace de forma infinita y no deja
                //introducir datos, lo que hace es "vaciar el buffer permitiendo la entrada de datos otra vez"
                teclado.nextLine();
            }
        }

        //PIRMER NUMERO (se aplica de la misma forma para el segundo numero)
        //recibir primera respuesta
        mensaje = in.readUTF();

        while (true) {
            try {
                System.out.println(mensaje);
                //Introducimos el numero a enviar al servidor
                int num = teclado.nextInt();
                out.writeInt(num);
                break;
            } catch (InputMismatchException e) {
                System.err.println("INTRODUCE UN NUMERO NO LETRA!!!!");
                teclado.nextLine();
            }
        }

        //SEGUNDO NUMERO
        mensaje = in.readUTF();

        while (true) {
            try {
                System.out.println(mensaje);
                int num = teclado.nextInt();
                out.writeInt(num);
                break;
            } catch (InputMismatchException e) {
                System.err.println("INTRODUCE UN NUMERO NO LETRA!!!!");
                teclado.nextLine();
            }
        }

        //RECIBIR RESULTADO
        double resultado = in.readDouble();
        String cero = "NaN";
        if (cero.equalsIgnoreCase(String.valueOf(resultado)) || resultado == 0) {
            System.out.println("Al dividir 0 el resultado siempre va a ser: 0");
        } else {
            System.out.println("El resultado es: " + resultado);
        }
    }
}
    