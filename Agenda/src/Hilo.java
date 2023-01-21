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
        int edad;
        Date fecha = new Date();
        Tarea tarea;
        Persona persona;

        System.out.println("Nombre: ");
        nombre = teclado.next();
        System.out.println("Apellido: ");
        apellido = teclado.next();

        while (true) {
            try {
                System.out.println("Edad: ");
                edad = teclado.nextInt();
                break;
            } catch (InputMismatchException inputMismatchException) {
                System.err.println("INTRODUCE UN NUMERO, NO LETRA.");
                teclado.nextLine();
            }
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
        int contador = 1;

        for (Tarea a : tareas) {
            System.out.println("***TAREA NUMERO: " + (contador++) + "***");
            System.out.println("Nombre: " + a.getPersona().getNombre() +
                    "\nApellido: " + a.getPersona().getApellido() +
                    "\nEdad: " + a.getPersona().getEdad() +
                    "\nFecha:" + a.getFecha() +
                    "\n\nTarea: " + a.getDescripcionTarea());
            System.out.println("--------------------------------------");
        }
    }

    @Override
    public void run() {

        int seleccion;
        String menuServer;
        try {
            menuServer = dataInputStream.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            try {
                System.out.println(menuServer);
                seleccion = teclado.nextInt();

                if (seleccion == 1) {
                    try {
                        dataOutputStream.writeInt(seleccion);
                        ArrayList<Tarea> almacenadas;
                        almacenadas = (ArrayList) objectInputStream.readObject();
                        leerTareas(almacenadas);
                        break;
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                } else if (seleccion == 2) {
                    try {
                        dataOutputStream.writeInt(seleccion);
                        String pintar;
                        pintar = dataInputStream.readUTF();
                        System.out.println(pintar);
                        objectOutputStream.writeObject(menuTarea());
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    System.err.println("VALOR NUMERICO INTRODUCIDO ERRONEO");
                }

            } catch (InputMismatchException e) {
                System.err.println("INTRODUZCA UN NUMERO ENTERO");
                teclado.nextLine();
            }
        }
    }
}