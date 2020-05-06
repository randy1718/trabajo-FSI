package demanda.entities;

import java.util.List;
import java.util.Map;

public class ProductData {

    private final String nombre;
    private final List<Integer> ventas; // Mes: Ventas

    public ProductData(String nombre, List<Integer> ventas) {
        this.nombre = nombre;
        this.ventas = ventas;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Integer> getVentas() {
        return ventas;
    }

    @Override public String toString() {
        return "ProductData{" +
                "nombre='" + nombre + '\'' +
                ", ventas=" + ventas +
                '}';
    }
}
