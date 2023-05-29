package Ejercicio4;

import java.util.List;
import java.util.Scanner;

public class PruebaPalabras {
	public static void main(String[] args) {
		Palabras palabras = new Palabras();
		Scanner scanner = new Scanner(System.in);
		String comando = "";

		while (!comando.equals("fin")) {
			System.out.println("Introduce un comando (añadir, lista, borrar, fin):");
			comando = scanner.nextLine();

			switch (comando) {
			case "añadir":
				System.out.println("Introduce las palabras separadas por espacios:");
				String palabrasAñadir = scanner.nextLine();
				palabras.addPalabras(palabrasAñadir);
				break;

			case "lista":
				System.out.println("Introduce la longitud de las palabras:");
				int longitud = scanner.nextInt();
				scanner.nextLine();
				List<String> palabrasDeLongitud = palabras.palabrasDeLongitud(longitud);
				if (palabrasDeLongitud.size() == 0) {
					System.out.println("No hay palabras de longitud " + longitud);
				} else {
					System.out.println("Palabras de longitud " + longitud + " en orden alfabético:");
					for (String palabra : palabrasDeLongitud) {
						System.out.println(palabra);
					}
				}
				break;

			case "borrar":
				System.out.println("¿Desea borrar todas las palabras? (S/N)");
				String respuesta = scanner.nextLine();
				if (respuesta.equalsIgnoreCase("S")) {
					palabras.eliminarTodasLasPalabras();
					System.out.println("Todas las palabras han sido borradas.");
				} else {
					System.out.println("Introduce las palabras separadas por espacios:");
					String palabrasBorrar = scanner.nextLine();
					palabras.eliminarTodasLasPalabras();
					palabras.addPalabras(palabrasBorrar);
					System.out.println("Las palabras han sido sustituidas.");
				}
				break;

			case "fin":
				palabras.eliminarTodasLasPalabras();
				System.out.println("Todas las palabras han sido borradas.");
				break;

			default:
				System.out.println("Comando no válido.");
				break;
			}
		}

		scanner.close();
	}
}
