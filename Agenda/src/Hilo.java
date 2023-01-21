import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Hilo extends Thread {

    private final Scanner teclado = new Scanner(System.in);
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    public Hilo(DataInputStream in, DataOutputStream out, ObjectInputStream inObject, ObjectOutputStream outObject) {
        this.dataInputStream = in;
        this.dataOutputStream = out;
        this.objectInputStream = inObject;
        this.objectOutputStream = outObject;
    }

    private Tarea menuTarea() {
        String nombre;
        String apellido;
        int edad = 0;
        Date fecha = new Date();
        Tarea tarea;
        Persona persona;

        System.out.println("Nombre: ");
        nombre = teclado.next();
        System.out.println("Apellido: ");
        apellido = teclado.next();
        System.out.println("Edad: ");
        try {
            edad = teclado.nextInt();
        } catch (InputMismatchException ex) {
            System.err.println("Error de carcter,no es un numero");
            teclado.next();
        }
        persona = new Persona(nombre, apellido, edad);
        tarea = new Tarea();
        tarea.setPersona(persona);
        System.out.println("***TAREA A REGISTRAR***");
        System.out.println("Tarea: ");
        teclado.nextLine();
        tarea.setDescripcionTarea(teclado.nextLine());
        tarea.setFecha(fecha);
        return tarea;
    }

    private void leerTareas(ArrayList<Tarea> tareas) {
        System.out.println("***REGISTRO DE TAREAS***");

        for (Tarea a : tareas) {
            System.out.println("Nombre: " + a.getPersona().getNombre());
            System.out.println("Apellido: " + a.getPersona().getApellido());
            System.out.println("Edad: " + a.getPersona().getEdad());
            System.out.println("Fecha:" + a.getFecha());
            System.out.println("\n" + "Tarea: " + a.getDescripcionTarea());
            System.out.println("\n" + "--------------------------------------");
        }
    }

    @Override
    public void run() {
        int seleccion;

        try {
            String menuServer = dataInputStream.readUTF();
            System.out.println(menuServer);

            try {
                seleccion = teclado.nextInt();
                while (seleccion > 2 || seleccion <= 0) {

                    System.out.println("Introduce un numero valido");
                    seleccion = teclado.nextInt();
                }
            } catch (InputMismatchException ex) {
                System.out.println("Error... introduce un numero");
                seleccion = 2;
                teclado.next();
            }

            dataOutputStream.writeInt(seleccion);

            if (seleccion == 1) {
                ArrayList<Tarea> almacenadas = (ArrayList) objectInputStream.readObject();
                //ArrayList<Tarea> almacenadas = (ArrayList) objectInputStream.readObject();
                leerTareas(almacenadas);
            } else {
                String a = dataInputStream.readUTF();
                System.out.println(a);
                objectOutputStream.writeObject(menuTarea());
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}