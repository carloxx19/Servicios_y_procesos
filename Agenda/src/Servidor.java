import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    public static void menu(DataOutputStream seleccion) throws IOException {
        seleccion.writeUTF("""
                ****MENU AGENDA****
                1.-Consultar Tareas.
                2.-Registrar Tareas.""");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ServerSocket server = new ServerSocket(1000);
        Socket sc;

        ArrayList<Tarea> tareas = new ArrayList<>();
        while (true) {

            //Enviar y recivir respuestas
            DataInputStream in;
            DataOutputStream out;

            ObjectInputStream inObject;
            ObjectOutputStream outObject;

            int selecMenu;

            System.out.println("SERVER UP.");
            sc = server.accept();
            System.out.println("Cliente conectado");

            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());


            inObject = new ObjectInputStream(sc.getInputStream());
            outObject = new ObjectOutputStream(sc.getOutputStream());

            menu(out);
            selecMenu = in.readInt();

            if (selecMenu == 1) {
                outObject.writeObject(tareas);
            } else {
                //////////
                out.writeUTF("**** REGISTRAR TAREA**** " +
                        "\n***Datos de la persona***");
                Tarea tarea = (Tarea) inObject.readObject();
                tareas.add(tarea);
            }
            outObject.close();
            inObject.close();
            sc.close();
        }
    }
}