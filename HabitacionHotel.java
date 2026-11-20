public class HabitacionHotel {

    // ----- Atributos privados -----
    private int numero;
    private double precioNoche;
    private int capacidad;
    private boolean ocupada;

    // ----- Constructor por defecto: 101; 120000; 2; false -----
    public HabitacionHotel() {
        this.numero = 101;
        this.precioNoche = 120000;
        this.capacidad = 2;
        this.ocupada = false;
    }

    // ----- Constructor con parámetros -----
    public HabitacionHotel(int numero, double precioNoche, int capacidad, boolean ocupada) {
        this.numero = numero;
        this.precioNoche = precioNoche;
        this.capacidad = capacidad;
        this.ocupada = ocupada;
    }

    // ----- Getters -----
    public int getNumero() {
        return numero;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    // ----- Setters -----
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    // ----- Método de comportamiento 1 -----
    
    public boolean reservar(int personas) {
        if (personas <= 0) {
            System.out.println("Error: la cantidad de personas debe ser mayor que 0.");
            return false;
        }
        if (ocupada) {
            System.out.println("Error: la habitación " + numero + " ya está ocupada.");
            return false;
        }
        if (personas > capacidad) {
            System.out.println("Error: la habitación " + numero + " tiene capacidad para "
                    + capacidad + " persona(s) y se solicitaron " + personas + ".");
            return false;
        }
        ocupada = true;
        System.out.println("Reserva exitosa: habitación " + numero + " para " + personas + " persona(s).");
        return true;
    }

    
    public double calcularHospedaje(int noches) {
        if (noches <= 0) {
            System.out.println("Error: el número de noches debe ser mayor que 0.");
            return -1;
        }
        double costo = precioNoche * noches;
        if (noches >= 5) {
            double descuento = costo * 0.10;
            costo -= descuento;
            System.out.println("Se aplicó un descuento del 10% (-$" + String.format("%,.0f", descuento) + ").");
        }
        return costo;
    }

    // ----- Representación en texto -----
    @Override
    public String toString() {
        return "Habitación " + numero
                + " | Precio/noche: $" + String.format("%,.0f", precioNoche)
                + " | Capacidad: " + capacidad
                + " | Estado: " + (ocupada ? "Ocupada" : "Disponible");
    }
}
