package Ejercicio4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Palabras {
    
    private Set<String> palabras;
    
    // Constructor sin parámetros
    public Palabras() {
        palabras = new HashSet<String>();
    }
    
    // Constructor que toma un String y almacena las palabras que contiene
    public Palabras(String cadena) {
        palabras = new HashSet<String>();
        String[] palabrasCadena = cadena.split("\\s+");
        for (String palabra : palabrasCadena) {
            palabras.add(palabra.toLowerCase());
        }
    }
    
    // Método para añadir una palabra
    public void addPalabra(String palabra) {
        palabras.add(palabra.toLowerCase());
    }
    
    // Método para añadir las palabras que formen parte de una cadena de caracteres
    public void addPalabras(String cadena) {
        String[] palabrasCadena = cadena.split("\\s+");
        for (String palabra : palabrasCadena) {
            palabras.add(palabra.toLowerCase());
        }
    }
    
    // Método para comprobar si una palabra está contenida en la estructura de datos
    public boolean contienePalabra(String palabra) {
        return palabras.contains(palabra.toLowerCase());
    }
    
    // Método para obtener la lista de palabras de una determinada longitud en orden alfabético
    public List<String> palabrasDeLongitud(int longitud) {
        List<String> palabrasDeLongitud = new ArrayList<String>();
        for (String palabra : palabras) {
            if (palabra.length() == longitud) {
                palabrasDeLongitud.add(palabra);
            }
        }
        TreeSet<String> palabrasOrdenadas = new TreeSet<String>(palabrasDeLongitud);
        palabrasDeLongitud = new ArrayList<String>(palabrasOrdenadas);
        return palabrasDeLongitud;
    }
    
    // Método para obtener la cantidad de palabras almacenadas
    public int cantidadDePalabras() {
        return palabras.size();
    }
    
    // Método para eliminar una palabra
    public void eliminarPalabra(String palabra) {
        palabras.remove(palabra.toLowerCase());
    }
    
    // Método para eliminar todas las palabras
    public void eliminarTodasLasPalabras() {
        palabras.clear();
    }
    
    // Método para obtener todas las palabras almacenadas en la estructura de datos
    public List<String> obtenerTodasLasPalabras() {
        List<String> todasLasPalabras = new ArrayList<String>(palabras);
        return todasLasPalabras;
    }
}
