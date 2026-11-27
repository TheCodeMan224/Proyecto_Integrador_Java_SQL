package Diseño;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/** Consulta visual con filtros combinables y nombre descriptivo del tipo. */
public class FrmConsultaClientes extends JFrame {
    private final JTextField texto = Estilo.campo();
    private final JComboBox<String> tipo = new JComboBox<>(new String[]{"Todos los tipos", "Minorista", "Mayorista", "Preferente"});
    private final JComboBox<String> sexo = new JComboBox<>(new String[]{"Todos los sexos", "Femenino", "Masculino", "Otro"});
    private final DefaultTableModel modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellidos", "Sexo", "Límite de crédito", "Tipo de cliente"}, 0) { public boolean isCellEditable(int r,int c){return false;} };
    private final JTable tabla = new JTable(modelo);
    private final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);

    public FrmConsultaClientes() {
        super("Envíos · Consulta de clientes"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(1080, 700); setMinimumSize(new Dimension(900, 600)); setLocationRelativeTo(null);
        JPanel root = Estilo.pagina(); setContentPane(root);
        JButton volver = Estilo.boton("←  Clientes", false); volver.addActionListener(e -> { new FrmClientes().setVisible(true); dispose(); });
        root.add(Estilo.encabezado("Consulta de clientes", "Encuentra registros por nombre, apellidos y tipo de cliente.", volver), BorderLayout.NORTH);
        JPanel body = new JPanel(new BorderLayout(0, 16)); body.setOpaque(false);
        JPanel filtro = Estilo.tarjeta(); filtro.setLayout(new BorderLayout(12, 12));
        JPanel campos = new JPanel(new GridLayout(1, 3, 14, 0)); campos.setOpaque(false); campos.add(campo("Buscar por nombre o apellido", texto)); campos.add(campo("Tipo de cliente", tipo)); campos.add(campo("Sexo", sexo)); filtro.add(campos, BorderLayout.CENTER);
        JButton limpiar = Estilo.boton("Limpiar filtros", false); limpiar.addActionListener(e -> { texto.setText(""); tipo.setSelectedIndex(0); sexo.setSelectedIndex(0); aplicarFiltro(); });
        JPanel accion = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); accion.setOpaque(false); accion.add(limpiar); filtro.add(accion, BorderLayout.SOUTH);
        javax.swing.event.DocumentListener listener = new javax.swing.event.DocumentListener() { public void insertUpdate(javax.swing.event.DocumentEvent e){ aplicarFiltro(); } public void removeUpdate(javax.swing.event.DocumentEvent e){ aplicarFiltro(); } public void changedUpdate(javax.swing.event.DocumentEvent e){ aplicarFiltro(); } };
        texto.getDocument().addDocumentListener(listener); tipo.addActionListener(e -> aplicarFiltro()); sexo.addActionListener(e -> aplicarFiltro()); body.add(filtro, BorderLayout.NORTH);
        JPanel resultados = Estilo.tarjeta(); resultados.setLayout(new BorderLayout(0, 12)); JPanel rh = new JPanel(new BorderLayout()); rh.setOpaque(false); rh.add(new JLabel("Resultados"), BorderLayout.WEST); rh.add(Estilo.subtitulo("El tipo de cliente se muestra con su nombre."), BorderLayout.EAST); resultados.add(rh, BorderLayout.NORTH);
        Estilo.tabla(tabla); tabla.setRowSorter(sorter); resultados.add(new JScrollPane(tabla), BorderLayout.CENTER); body.add(resultados, BorderLayout.CENTER); root.add(body, BorderLayout.CENTER); refrescar();
    }
    private JPanel campo(String titulo, JComponent c) { JPanel p=new JPanel(new BorderLayout(0,6)); p.setOpaque(false); p.add(Estilo.etiqueta(titulo),BorderLayout.NORTH); c.setPreferredSize(new Dimension(190,38)); p.add(c,BorderLayout.CENTER); return p; }
    private void refrescar() { modelo.setRowCount(0); for (DatosDemo.Cliente c : DatosDemo.CLIENTES) modelo.addRow(c.fila()); aplicarFiltro(); }
    private void aplicarFiltro() { String q=texto.getText().trim().toLowerCase(); String t=(String)tipo.getSelectedItem(), s=(String)sexo.getSelectedItem(); sorter.setRowFilter(new RowFilter<DefaultTableModel,Integer>() { public boolean include(Entry<? extends DefaultTableModel,? extends Integer> e) { String nombre=(e.getStringValue(1)+" "+e.getStringValue(2)).toLowerCase(); return (q.isEmpty()||nombre.contains(q)) && ("Todos los tipos".equals(t)||t.equals(e.getStringValue(5))) && ("Todos los sexos".equals(s)||s.equals(e.getStringValue(3))); } }); }
}
