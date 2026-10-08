import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class AccesoAleatorio {

    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Ejercicio 1:");
        ejercicio1();
        System.out.println("\nEjercicio 2:");
        ejercicio2();
        System.out.println("\nEjercicio 3:");
        ejercicio3();
        System.out.println("\nEjercicio 4:");
        ejercicio4();
        System.out.println("\nEjercicio 5:");
        ejercicio5();
        System.out.println("\nEjercicio 6:");
        ejercicio6();
        System.out.println("\nEjercicio 7:");
        ejercicio7();
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Integer.parseInt(teclado.nextLine().trim());
    }

    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        return Double.parseDouble(teclado.nextLine().trim().replace(',', '.'));
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine();
    }

    private static int leerEnteroEntre0y9() {
        int n;
        do {
            n = leerEntero("Introduce un numero entre 0 y 9: ");
        } while (n < 0 || n > 9);
        return n;
    }

    private static void escribirCadena(RandomAccessFile f, String s, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder(s);
        sb.setLength(longitud);
        for (int i = 0; i < longitud; i++) {
            char c = sb.charAt(i);
            f.writeChar(c == '\0' ? ' ' : c);
        }
    }

    private static String leerCadena(RandomAccessFile f, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append(f.readChar());
        }
        return sb.toString();
    }

    private static void mostrarEnteros(RandomAccessFile f) throws IOException {
        f.seek(0);
        StringBuilder sb = new StringBuilder();
        while (f.getFilePointer() < f.length()) {
            sb.append(f.readInt()).append(" ");
        }
        System.out.println(sb.toString().trim());
    }

    public static void ejercicio1() throws IOException {
        try (RandomAccessFile f = new RandomAccessFile("enteros.dat", "rw")) {
            f.setLength(0);
            for (int i = 0; i < 6; i++) {
                f.writeInt(1);
            }
            for (int i = 0; i < 10; i++) {
                f.writeInt(2);
            }
            mostrarEnteros(f);
            int nuevo = leerEnteroEntre0y9();
            f.seek(0);
            while (f.getFilePointer() < f.length()) {
                long pos = f.getFilePointer();
                int valor = f.readInt();
                if (valor == 2) {
                    f.seek(pos);
                    f.writeInt(nuevo);
                }
            }
            mostrarEnteros(f);
        }
    }

    public static void ejercicio2() throws IOException {
        int numero = leerEnteroEntre0y9();
        int veces = leerEntero("Cantidad de veces: ");
        try (RandomAccessFile f = new RandomAccessFile("enteros.dat", "rw")) {
            f.seek(f.length());
            for (int i = 0; i < veces; i++) {
                f.writeInt(numero);
            }
        }
    }

    public static void ejercicio3() throws IOException {
        int cantidad = leerEntero("Cantidad de registros a anadir: ");
        try (RandomAccessFile f = new RandomAccessFile("juegos.dat", "rw")) {
            f.seek(f.length());
            for (int i = 0; i < cantidad; i++) {
                int orden = leerEntero("Numero de orden: ");
                String nombre = leerTexto("Nombre del juego: ");
                int edad = leerEntero("Edad minima recomendada: ");
                String tematica = leerTexto("Clasificacion segun tematica: ");
                f.writeInt(orden);
                escribirCadena(f, nombre, 10);
                f.writeInt(edad);
                escribirCadena(f, tematica, 10);
            }
            f.seek(0);
            while (f.getFilePointer() < f.length()) {
                int orden = f.readInt();
                String nombre = leerCadena(f, 10);
                int edad = f.readInt();
                String tematica = leerCadena(f, 10);
                System.out.println(orden + " " + nombre + " " + edad + " " + tematica);
            }
        }
    }

    public static void ejercicio4() throws IOException {
        int edad = leerEntero("Edad: ");
        try (RandomAccessFile f = new RandomAccessFile("juegos.dat", "r")) {
            while (f.getFilePointer() < f.length()) {
                f.readInt();
                String nombre = leerCadena(f, 10);
                int edadMinima = f.readInt();
                String tematica = leerCadena(f, 10);
                if (edadMinima <= edad) {
                    System.out.println(nombre + " " + tematica);
                }
            }
        }
    }

    public static void ejercicio5() throws IOException {
        int cantidad = leerEntero("Cantidad de alumnos: ");
        try (RandomAccessFile f = new RandomAccessFile("practicas.dat", "rw")) {
            f.seek(f.length());
            for (int i = 0; i < cantidad; i++) {
                String nombre = leerTexto("Nombre: ");
                String apellidos = leerTexto("Apellidos: ");
                String dni = leerTexto("DNI: ");
                int vehiculo = leerEntero("Vehiculo de practicas: ");
                int clases = leerEntero("Numero de clases de practicas: ");
                escribirCadena(f, nombre, 10);
                escribirCadena(f, apellidos, 35);
                escribirCadena(f, dni, 9);
                f.writeInt(vehiculo);
                f.writeInt(clases);
            }
            f.seek(0);
            while (f.getFilePointer() < f.length()) {
                String nombre = leerCadena(f, 10);
                String apellidos = leerCadena(f, 35);
                String dni = leerCadena(f, 9);
                int vehiculo = f.readInt();
                int clases = f.readInt();
                System.out.println(nombre + " " + apellidos + " " + dni + " " + vehiculo + " " + clases);
            }
        }
    }

    public static void ejercicio6() throws IOException {
        try (RandomAccessFile origen = new RandomAccessFile("practicas.dat", "r");
             RandomAccessFile destino = new RandomAccessFile("precio.dat", "rw")) {
            destino.setLength(0);
            while (origen.getFilePointer() < origen.length()) {
                String nombre = leerCadena(origen, 10);
                leerCadena(origen, 35);
                String dni = leerCadena(origen, 9);
                origen.readInt();
                int clases = origen.readInt();
                escribirCadena(destino, nombre, 10);
                escribirCadena(destino, dni, 9);
                destino.writeInt(clases);
                destino.writeDouble(clases * 45.7);
            }
        }
    }

    public static void ejercicio7() throws IOException {
        double porcentaje = leerDouble("Porcentaje de descuento: ");
        try (RandomAccessFile origen = new RandomAccessFile("precio.dat", "r");
             RandomAccessFile destino = new RandomAccessFile("descuento.dat", "rw")) {
            destino.setLength(0);
            while (origen.getFilePointer() < origen.length()) {
                leerCadena(origen, 10);
                String dni = leerCadena(origen, 9);
                origen.readInt();
                double cantidad = origen.readDouble();
                escribirCadena(destino, dni, 9);
                destino.writeDouble(cantidad);
                destino.writeDouble(cantidad - cantidad * porcentaje / 100);
            }
            destino.seek(0);
            while (destino.getFilePointer() < destino.length()) {
                String dni = leerCadena(destino, 9);
                double anterior = destino.readDouble();
                double conDescuento = destino.readDouble();
                System.out.println(dni + " " + anterior + " " + conDescuento);
            }
        }
    }
}