package Diseño;

import java.util.ArrayList;
import java.util.List;

/** Datos temporales para diseñar y recorrer las vistas antes de integrar los DAOs. */
final class DatosDemo {
    static final String[] TIPOS = {"Minorista", "Mayorista", "Preferente"};
    static final List<Cliente> CLIENTES = new ArrayList<>();
    private static int siguienteId = 4;

    static {
        CLIENTES.add(new Cliente(1, "Ana", "López García", "Femenino", 15000, "Minorista"));
        CLIENTES.add(new Cliente(2, "Carlos", "Ruiz Méndez", "Masculino", 45000, "Mayorista"));
        CLIENTES.add(new Cliente(3, "María", "Torres Díaz", "Femenino", 28000, "Preferente"));
    }

    static int nuevoId() { return siguienteId++; }
    private DatosDemo() { }

    static final class Cliente {
        final int id;
        String nombre, apellidos, sexo, tipo;
        double limite;
        Cliente(int id, String nombre, String apellidos, String sexo, double limite, String tipo) {
            this.id = id; this.nombre = nombre; this.apellidos = apellidos;
            this.sexo = sexo; this.limite = limite; this.tipo = tipo;
        }
        Object[] fila() { return new Object[]{id, nombre, apellidos, sexo, String.format("$%,.2f", limite), tipo}; }
    }
}
