import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Cliente {
    public static void main(String[] args) throws IOException, InterruptedException {

        Socket socket = new Socket("localhost", 1000);
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        ObjectOutputStream outObject = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());

        Hilo hilo = new Hilo(dataInputStream, dataOutputStream, inObject, outObject);
        hilo.start();
        hilo.join();

        outObject.close();
        inObject.close();
        socket.close();
    }
}