package Ejercicio6;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;

public class Ejercicio6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TreeMap<String, HashSet<String>> contactos = new TreeMap<>();
        String comando;

        do {
            System.out.print("> ");
            comando = scanner.nextLine().trim();

            if (comando.startsWith("añadir:")) {
                String[] datos = comando.substring(7).split(":");
                if (datos.length == 2) {
                    String nombre = datos[0].trim();
                    String telefono = datos[1].trim();

                    if (contactos.containsKey(nombre)) {
                        HashSet<String> telefonos = contactos.get(nombre);
                        if (telefonos.contains(telefono)) {
                            System.out.println("El teléfono " + telefono + " ya existe para el contacto " + nombre);
                        } else {
                            telefonos.add(telefono);
                            System.out.println("Se ha añadido el teléfono " + telefono + " al contacto " + nombre);
                        }
                    } else {
                        HashSet<String> telefonos = new HashSet<>();
                        telefonos.add(telefono);
                        contactos.put(nombre, telefonos);
                        System.out.println("Se ha creado el contacto " + nombre + " con el teléfono " + telefono);
                    }
                } else {
                    System.out.println("El formato del comando añadir es incorrecto, debe ser añadir:nombre:telefono");
                }
            } else if (comando.startsWith("buscar:")) {
                String nombre = comando.substring(7).trim();

                if (contactos.containsKey(nombre)) {
                    HashSet<String> telefonos = contactos.get(nombre);
                    String[] telefonosArray = new String[telefonos.size()];
                    int i = 0;
                    for (String telefono : telefonos) {
                        telefonosArray[i++] = telefono;
                    }
                    Arrays.sort(telefonosArray);
                    System.out.println(String.join(", ", telefonosArray));
                } else {
                    System.out.println("El contacto " + nombre + " no existe");
                }
            } else if (comando.startsWith("eliminar:")) {
                String nombre = comando.substring(9).trim();

                if (contactos.containsKey(nombre)) {
                    System.out.print("¿Está seguro de que desea eliminar el contacto " + nombre + "? (s/n) ");
                    String respuesta = scanner.nextLine().trim();
                    if (respuesta.equalsIgnoreCase("s")) {
                        contactos.remove(nombre);
                        System.out.println("Se ha eliminado el contacto " + nombre);
                    } else {
                        System.out.println("No se ha eliminado el contacto " + nombre);
                    }
                } else {
                    System.out.println("El contacto " + nombre + " no existe");
                }
            } else if (comando.equals("contactos")) {
                for (String nombre : contactos.keySet()) {
                    System.out.println(nombre + ": " + String.join(", ", contactos.get(nombre)));
                }
            } else if (!comando.equals("salir")) {
                System.out.println("Comando no reconocido");
            }
        } while (!comando.equals("salir"));

        scanner.close();
    }
}
