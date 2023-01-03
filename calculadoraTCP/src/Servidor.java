import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    static int numero1;
    static int numero2;
    static double resultado;

    public static void main(String[] args) throws IOException {
        //VARIABLES
        final int PORT = 5001;
        Socket socket = null;
        ServerSocket serverSocket = new ServerSocket(PORT);
        DataInputStream in;
        DataOutputStream out;

        while (true) {
            socket = serverSocket.accept();
            String mensaje = """
                    ¿Que operacion desea realizar?
                    1.-sumar.
                    2.-restar.
                    3.-multiplicar.
                    4.-dividir.
                    """;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF(mensaje);

            int operacion = in.readInt();

            switch (operacion) {
                case 1 -> {
                    pedirNumeros(out, in);
                    resultado = numero1 + numero2;
                    out.writeDouble(resultado);
                }
                case 2 -> {
                    pedirNumeros(out, in);
                    resultado = numero1 - numero2;
                    out.writeDouble(resultado);
                }
                case 3 -> {
                    pedirNumeros(out, in);
                    resultado = numero1 * numero2;
                    out.writeDouble(resultado);
                }
                case 4 -> {
                    try {
                        pedirNumeros(out, in);
                        //tenemos que hacer Cast a un numero entero a double para que guarde los decimanes, si no va a dar 0
                        //aunque el resultado de dividir 2 enteros sea double con decimales.
                        if (numero2==0){
                            out.writeDouble(0);
                        }else {
                            resultado = (double) numero1 / numero2;
                            System.out.println(resultado);
                            out.writeDouble(resultado);
                        }

                    } catch (ArithmeticException arithmeticException) {
                        System.out.println("Estas diviendo 0 y el resultado es:");
                        resultado = 0;
                        out.writeDouble(resultado);
                    }
                }
                default -> System.out.println("numero introducido erroneo");
            }
        }
    }

    public static void pedirNumeros(DataOutputStream outputStream, DataInputStream inputStream) throws IOException {

        //primer numero
        String mensaje = "Introduce el primer numero";
        outputStream.writeUTF(mensaje);
        numero1 = inputStream.readInt();

        //segundo numero
        mensaje = "Introduce el segundo numero";
        outputStream.writeUTF(mensaje);
        numero2 = inputStream.readInt();
    }
}