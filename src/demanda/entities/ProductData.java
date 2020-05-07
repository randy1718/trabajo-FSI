package demanda.entities;

import java.util.List;
import java.util.Map;

public class ProductData {

    private final String nombre;
    private final List<Double> ventas; // Mes: Ventas

    public ProductData(String nombre, List<Double> ventas) {
        this.nombre = nombre;
        this.ventas = ventas;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Double> getVentas() {
        return ventas;
    }

    @Override public String toString() {
        return "ProductData{" +
                "nombre='" + nombre + '\'' +
                ", ventas=" + ventas +
                '}';
    }
}
