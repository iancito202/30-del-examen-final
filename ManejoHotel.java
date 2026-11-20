import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase administradora: contiene main(), usa Scanner y gestiona
 * varias habitaciones con un ArrayList.
 */
public class ManejoHotel {

    private static final Scanner sc = new Scanner(System.in);
    private static final ArrayList<HabitacionHotel> habitaciones = new ArrayList<>();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1:
                    crearPorDefecto();
                    break;
                case 2:
                    crearConParametros();
                    break;
                case 3:
                    modificarAtributos();
                    break;
                case 4:
                    visualizar();
                    break;
                case 5:
                    ejecutarReservar();
                    break;
                case 6:
                    ejecutarCalcularHospedaje();
                    break;
                case 7:
                    System.out.println("Saliendo del programa. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 7);
    }

    // ================= MENÚ =================
    private static void mostrarMenu() {
        System.out.println("\n===== GESTIÓN HOTELERA =====");
        System.out.println("1. Crear objeto por defecto");
        System.out.println("2. Crear objeto con parámetros");
        System.out.println("3. Modificar atributos del objeto");
        System.out.println("4. Visualizar objetos registrados");
        System.out.println("5. Ejecutar método de comportamiento 1 (reservar)");
        System.out.println("6. Ejecutar método de comportamiento 2 (calcular hospedaje)");
        System.out.println("7. Salir");
    }

    // ================= OPCIÓN 1 =================
    private static void crearPorDefecto() {
        HabitacionHotel h = new HabitacionHotel();
        if (existeNumero(h.getNumero(), -1)) {
            System.out.println("Error: ya existe una habitación con el número " + h.getNumero()
                    + ". Modifíquela primero (opción 3) antes de crear otra por defecto.");
            return;
        }
        habitaciones.add(h);
        System.out.println("Habitación por defecto creada en la posición " + (habitaciones.size() - 1) + ".");
    }

    // ================= OPCIÓN 2 =================
    private static void crearConParametros() {
        int numero = leerEntero("Número de habitación: ");
        if (numero <= 0) {
            System.out.println("Error: el número debe ser mayor que 0.");
            return;
        }
        if (existeNumero(numero, -1)) {
            System.out.println("Error: ya existe una habitación con ese número.");
            return;
        }
        double precio = leerDouble("Precio por noche: ");
        if (precio <= 0) {
            System.out.println("Error: el precio debe ser mayor que 0.");
            return;
        }
        int capacidad = leerEntero("Capacidad (personas): ");
        if (capacidad <= 0) {
            System.out.println("Error: la capacidad debe ser mayor que 0.");
            return;
        }
        boolean ocupada = leerBoolean("¿Está ocupada? (s/n): ");

        habitaciones.add(new HabitacionHotel(numero, precio, capacidad, ocupada));
        System.out.println("Habitación creada en la posición " + (habitaciones.size() - 1) + ".");
    }

    // ================= OPCIÓN 3 =================
    private static void modificarAtributos() {
        HabitacionHotel h = pedirHabitacion();
        if (h == null) return;
        int pos = habitaciones.indexOf(h);

        System.out.println("Habitación actual: " + h);
        System.out.println("¿Qué desea modificar?");
        System.out.println("1. Número");
        System.out.println("2. Precio por noche");
        System.out.println("3. Capacidad");
        System.out.println("4. Estado de ocupación");
        int op = leerEntero("Opción: ");

        switch (op) {
            case 1:
                int numero = leerEntero("Nuevo número: ");
                if (numero <= 0) {
                    System.out.println("Error: el número debe ser mayor que 0.");
                } else if (existeNumero(numero, pos)) {
                    System.out.println("Error: ya existe otra habitación con ese número.");
                } else {
                    h.setNumero(numero);
                    System.out.println("Número actualizado.");
                }
                break;
            case 2:
                double precio = leerDouble("Nuevo precio por noche: ");
                if (precio <= 0) {
                    System.out.println("Error: el precio debe ser mayor que 0.");
                } else {
                    h.setPrecioNoche(precio);
                    System.out.println("Precio actualizado.");
                }
                break;
            case 3:
                int capacidad = leerEntero("Nueva capacidad: ");
                if (capacidad <= 0) {
                    System.out.println("Error: la capacidad debe ser mayor que 0.");
                } else {
                    h.setCapacidad(capacidad);
                    System.out.println("Capacidad actualizada.");
                }
                break;
            case 4:
                h.setOcupada(leerBoolean("¿Está ocupada? (s/n): "));
                System.out.println("Estado actualizado.");
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    // ================= OPCIÓN 4 =================
    private static void visualizar() {
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones registradas.");
            return;
        }
        System.out.println("\n--- Habitaciones registradas ---");
        for (int i = 0; i < habitaciones.size(); i++) {
            System.out.println("[" + i + "] " + habitaciones.get(i));
        }
    }

    // ================= OPCIÓN 5 =================
    private static void ejecutarReservar() {
        HabitacionHotel h = pedirHabitacion();
        if (h == null) return;
        int personas = leerEntero("Cantidad de personas: ");
        h.reservar(personas);
    }

    // ================= OPCIÓN 6 =================
    private static void ejecutarCalcularHospedaje() {
        HabitacionHotel h = pedirHabitacion();
        if (h == null) return;
        int noches = leerEntero("Cantidad de noches: ");
        double total = h.calcularHospedaje(noches);
        if (total >= 0) {
            System.out.println("Costo total del hospedaje: $" + String.format("%,.0f", total));
        }
    }

    // ================= AUXILIARES =================

    /** Pide la posición y devuelve la habitación, o null si no existe. */
    private static HabitacionHotel pedirHabitacion() {
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones registradas. Cree una primero.");
            return null;
        }
        int pos = leerEntero("Ingrese la posición de la habitación (0 a " + (habitaciones.size() - 1) + "): ");
        if (pos < 0 || pos >= habitaciones.size()) {
            System.out.println("Error: la posición " + pos + " no existe.");
            return null;
        }
        return habitaciones.get(pos);
    }

    /** Verifica si un número de habitación ya está en uso (ignorando una posición). */
    private static boolean existeNumero(int numero, int posIgnorar) {
        for (int i = 0; i < habitaciones.size(); i++) {
            if (i != posIgnorar && habitaciones.get(i).getNumero() == numero) {
                return true;
            }
        }
        return false;
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
            }
        }
    }

    private static boolean leerBoolean(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String r = sc.nextLine().trim().toLowerCase();
            if (r.equals("s") || r.equals("si") || r.equals("sí")) return true;
            if (r.equals("n") || r.equals("no")) return false;
            System.out.println("Responda 's' o 'n'.");
        }
    }
}
