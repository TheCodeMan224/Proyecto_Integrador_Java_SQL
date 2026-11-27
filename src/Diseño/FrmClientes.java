package Diseño;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Componentes.PanelBotonesCRUD;

/** Mantenimiento visual de clientes con datos temporales en memoria. */
public class FrmClientes extends JFrame {
    private final JTextField nombre = Estilo.campo(), apellidos = Estilo.campo(), limite = Estilo.campo();
    private final JComboBox<String> sexo = new JComboBox<>(new String[]{"Selecciona…", "Femenino", "Masculino", "Otro"});
    private final JComboBox<String> tipo = new JComboBox<>(DatosDemo.TIPOS);
    private final DefaultTableModel modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellidos", "Sexo", "Límite de crédito", "Tipo de cliente"}, 0) { public boolean isCellEditable(int r, int c) { return false; } };
    private final JTable tabla = new JTable(modelo);
    private int idSeleccionado = -1;

    public FrmClientes() {
        super("Envíos · Clientes"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(1120, 760); setMinimumSize(new Dimension(930, 650)); setLocationRelativeTo(null);
        JPanel root = Estilo.pagina(); setContentPane(root);
        JButton volver = Estilo.boton("←  Acceso", false); volver.addActionListener(e -> { new FrmLogin().setVisible(true); dispose(); });
        JButton consulta = Estilo.boton("Ver resultados  →", false); consulta.setPreferredSize(new Dimension(132, 32)); consulta.setFont(new Font("Segoe UI", Font.BOLD, 11)); consulta.setForeground(Color.BLACK); consulta.addActionListener(e -> { new FrmConsultaClientes().setVisible(true); dispose(); });
        root.add(Estilo.encabezado("Clientes", "Registra, actualiza y administra la información de tus clientes.", consulta), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 18)); centro.setOpaque(false);
        JPanel form = Estilo.tarjeta(); form.setLayout(new BorderLayout(0, 17));
        JPanel formHead = new JPanel(new BorderLayout()); formHead.setOpaque(false); JPanel títulos = new JPanel(new GridLayout(2, 1, 0, 4)); títulos.setOpaque(false); títulos.add(new JLabel("Datos del cliente")); títulos.add(Estilo.subtitulo("Los campos marcados son obligatorios.")); formHead.add(títulos, BorderLayout.CENTER); formHead.add(volver, BorderLayout.EAST); form.add(formHead, BorderLayout.NORTH);
        JPanel campos = new JPanel(new GridLayout(2, 3, 16, 14)); campos.setOpaque(false);
        campos.add(campo("Nombre *", nombre)); campos.add(campo("Apellidos *", apellidos)); campos.add(campo("Sexo *", sexo)); campos.add(campo("Límite de crédito *", limite)); campos.add(campo("Tipo de cliente *", tipo));
        JLabel tip = Estilo.subtitulo("El límite acepta importes decimales."); campos.add(tip); form.add(campos, BorderLayout.CENTER);
        PanelBotonesCRUD botones = new PanelBotonesCRUD(); botones.setOpaque(false);
        botones.getBtnNuevo().addActionListener(e -> limpiar());
        botones.getBtnGuardar().addActionListener(e -> guardar());
        botones.getBtnEliminar().addActionListener(e -> eliminar());
        botones.getBtnLimpiar().addActionListener(e -> limpiar());
        botones.getBtnBuscar().addActionListener(e -> { new FrmConsultaClientes().setVisible(true); dispose(); });
        form.add(botones, BorderLayout.SOUTH); centro.add(form, BorderLayout.NORTH);

        JPanel listado = Estilo.tarjeta(); listado.setLayout(new BorderLayout(0, 12)); JPanel listHead = new JPanel(new BorderLayout()); listHead.setOpaque(false); listHead.add(new JLabel("Clientes registrados"), BorderLayout.WEST); listHead.add(Estilo.subtitulo("Selecciona una fila para cargar sus datos."), BorderLayout.EAST); listado.add(listHead, BorderLayout.NORTH);
        Estilo.tabla(tabla); tabla.setAutoCreateRowSorter(true); tabla.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) cargarSeleccion(); });
        listado.add(new JScrollPane(tabla), BorderLayout.CENTER); centro.add(listado, BorderLayout.CENTER); root.add(centro, BorderLayout.CENTER);
        refrescar();
    }
    private JPanel campo(String titulo, JComponent c) { JPanel p = new JPanel(new BorderLayout(0, 6)); p.setOpaque(false); p.add(Estilo.etiqueta(titulo), BorderLayout.NORTH); if (c instanceof JComboBox) { c.setPreferredSize(new Dimension(220,38)); } p.add(c, BorderLayout.CENTER); return p; }
    private void refrescar() { modelo.setRowCount(0); for (DatosDemo.Cliente c : DatosDemo.CLIENTES) modelo.addRow(c.fila()); }
    private void guardar() {
        String n = nombre.getText().trim(), a = apellidos.getText().trim(), l = limite.getText().trim().replace(",", "");
        if (n.isEmpty() || a.isEmpty() || sexo.getSelectedIndex() == 0 || l.isEmpty() || tipo.getSelectedItem() == null) { JOptionPane.showMessageDialog(this, "Completa todos los campos obligatorios.", "Revisa la captura", JOptionPane.WARNING_MESSAGE); return; }
        double cantidad; try { cantidad = Double.parseDouble(l); if (!Double.isFinite(cantidad) || cantidad < 0) throw new NumberFormatException(); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Escribe un límite de crédito numérico igual o mayor que cero.", "Importe no válido", JOptionPane.WARNING_MESSAGE); limite.requestFocus(); return; }
        if (idSeleccionado < 0) DatosDemo.CLIENTES.add(new DatosDemo.Cliente(DatosDemo.nuevoId(), n, a, (String)sexo.getSelectedItem(), cantidad, (String)tipo.getSelectedItem()));
        else for (DatosDemo.Cliente c : DatosDemo.CLIENTES) if (c.id == idSeleccionado) { c.nombre=n; c.apellidos=a; c.sexo=(String)sexo.getSelectedItem(); c.limite=cantidad; c.tipo=(String)tipo.getSelectedItem(); break; }
        refrescar(); limpiar(); JOptionPane.showMessageDialog(this, "Datos guardados en la vista previa local.", "Cliente guardado", JOptionPane.INFORMATION_MESSAGE);
    }
    private void cargarSeleccion() { int view = tabla.getSelectedRow(); if (view < 0) return; int row = tabla.convertRowIndexToModel(view); idSeleccionado = (int)modelo.getValueAt(row,0); nombre.setText((String)modelo.getValueAt(row,1)); apellidos.setText((String)modelo.getValueAt(row,2)); sexo.setSelectedItem(modelo.getValueAt(row,3)); limite.setText(((String)modelo.getValueAt(row,4)).replace("$", "").replace(",", "")); tipo.setSelectedItem(modelo.getValueAt(row,5)); }
    private void eliminar() { if (idSeleccionado < 0) { JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla.", "Sin selección", JOptionPane.WARNING_MESSAGE); return; } int ok = JOptionPane.showConfirmDialog(this, "¿Eliminar el cliente seleccionado de los datos temporales?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION); if (ok == JOptionPane.YES_OPTION) { DatosDemo.CLIENTES.removeIf(c -> c.id == idSeleccionado); refrescar(); limpiar(); } }
    private void limpiar() { idSeleccionado=-1; tabla.clearSelection(); nombre.setText(""); apellidos.setText(""); limite.setText(""); sexo.setSelectedIndex(0); if (tipo.getItemCount()>0) tipo.setSelectedIndex(0); }
}
