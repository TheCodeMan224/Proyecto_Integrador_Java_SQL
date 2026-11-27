package Diseño;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/** Pantalla de conexión. El botón de vista previa evita simular una conexión real. */
public class FrmLogin extends JFrame {
    private final JTextField servidor = Estilo.campo(), base = Estilo.campo(), usuario = Estilo.campo();
    private final JPasswordField contrasena = Estilo.clave();

    public FrmLogin() {
        super("Envíos | Clientes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 620));
        setLocationRelativeTo(null);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Color.WHITE);
        setContentPane(raiz);
        JPanel marca = crearPanelMarca();
        marca.setPreferredSize(new Dimension(340, 720));
        raiz.add(marca, BorderLayout.WEST);
        raiz.add(crearPanelAcceso(), BorderLayout.CENTER);
    }

    private JPanel crearPanelMarca() {
        JPanel marca = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, new Color(23, 43, 74), getWidth(), getHeight(), new Color(37, 77, 133)));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(255, 255, 255, 17));
                g2.fill(new Ellipse2D.Double(-130, getHeight() - 210, 330, 330));
                g2.fill(new Ellipse2D.Double(getWidth() - 160, -120, 300, 300));
                // Ruta decorativa que evoca el seguimiento de envíos.
                g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{5f, 8f}, 0));
                g2.setColor(new Color(180, 205, 245, 115));
                g2.drawLine(75, getHeight() - 105, 145, getHeight() - 150);
                g2.drawLine(145, getHeight() - 150, 220, getHeight() - 115);
                g2.drawLine(220, getHeight() - 115, 285, getHeight() - 178);
                g2.setStroke(new BasicStroke(1f));
                for (int[] p : new int[][]{{75,getHeight()-105},{145,getHeight()-150},{220,getHeight()-115},{285,getHeight()-178}}) {
                    g2.setColor(new Color(255,255,255,215)); g2.fill(new Ellipse2D.Double(p[0]-5,p[1]-5,10,10));
                }
                g2.dispose();
            }
        };
        marca.setOpaque(false);
        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setBorder(new EmptyBorder(64, 48, 64, 36));
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

        JPanel logoRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0)); logoRow.setOpaque(false); logoRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel icono = new JLabel("E", SwingConstants.CENTER); icono.setOpaque(true); icono.setBackground(new Color(255,255,255,38)); icono.setForeground(Color.WHITE); icono.setFont(new Font("Segoe UI", Font.BOLD, 23)); icono.setPreferredSize(new Dimension(48,48));
        JLabel marcaNombre = new JLabel("ENVÍOS"); marcaNombre.setForeground(Color.WHITE); marcaNombre.setFont(new Font("Segoe UI", Font.BOLD, 15));
        logoRow.add(icono); logoRow.add(marcaNombre);

        JLabel eyebrow = textoMarca("GESTIÓN DE CLIENTES", 11, true, new Color(181,205,241));
        JLabel titular1 = textoMarca("Todo tu equipo,", 30, true, Color.WHITE);
        JLabel titular2 = textoMarca("en un solo lugar.", 30, true, Color.WHITE);
        JLabel detalle1 = textoMarca("Organiza la información de tus clientes", 14, false, new Color(211,222,240));
        JLabel detalle2 = textoMarca("con una experiencia simple y organizada.", 14, false, new Color(211,222,240));
        JLabel insignia = textoMarca("●   Gestión de clientes", 12, true, Color.WHITE);
        contenido.add(logoRow); contenido.add(Box.createVerticalStrut(82)); contenido.add(eyebrow); contenido.add(Box.createVerticalStrut(16));
        contenido.add(titular1); contenido.add(Box.createVerticalStrut(2)); contenido.add(titular2); contenido.add(Box.createVerticalStrut(18));
        contenido.add(detalle1); contenido.add(Box.createVerticalStrut(5)); contenido.add(detalle2); contenido.add(Box.createVerticalGlue());
        insignia.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255,255,255,70)), new EmptyBorder(10,13,10,13)));
        contenido.add(insignia);
        marca.add(contenido, BorderLayout.CENTER);
        return marca;
    }

    private JPanel crearPanelAcceso() {
        JPanel zona = new JPanel(new BorderLayout());
        zona.setBackground(new Color(247,249,253));
        zona.setBorder(new EmptyBorder(28, 38, 24, 38));

        JPanel top = new JPanel(new BorderLayout()); top.setOpaque(false);
        JLabel breadcrumb = new JLabel("ESPACIO DE TRABAJO  /  ACCESO"); breadcrumb.setFont(new Font("Segoe UI", Font.BOLD, 10)); breadcrumb.setForeground(Estilo.SECUNDARIO);
        top.add(breadcrumb, BorderLayout.WEST);
        zona.add(top, BorderLayout.NORTH);

        JPanel tarjeta = new JPanel(new BorderLayout(0, 22));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(231,235,242)), new EmptyBorder(38, 42, 34, 42)));
        tarjeta.setPreferredSize(new Dimension(520, 560));

        JPanel encabezado = new JPanel(); encabezado.setOpaque(false); encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        JLabel etiqueta = new JLabel("BIENVENIDO"); etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 10)); etiqueta.setForeground(Estilo.AZUL); etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel titulo = new JLabel("Iniciar sesión"); titulo.setFont(new Font("Segoe UI", Font.BOLD, 31)); titulo.setForeground(Estilo.TINTA); titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel descripcion = new JLabel("Conecta con tu base de datos para continuar."); descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14)); descripcion.setForeground(Estilo.SECUNDARIO); descripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        encabezado.add(etiqueta); encabezado.add(Box.createVerticalStrut(8)); encabezado.add(titulo); encabezado.add(Box.createVerticalStrut(7)); encabezado.add(descripcion);
        tarjeta.add(encabezado, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(4, 1, 0, 15)); campos.setOpaque(false);
        campos.add(campo("Servidor", servidor, "Ej. localhost o 192.168.1.10"));
        campos.add(campo("Base de datos", base, "Nombre de la base de datos"));
        campos.add(campo("Usuario", usuario, "Usuario de la base de datos"));
        campos.add(campo("Contraseña", contrasena, "Contraseña"));
        tarjeta.add(campos, BorderLayout.CENTER);

        JPanel acciones = new JPanel(); acciones.setOpaque(false); acciones.setLayout(new BoxLayout(acciones, BoxLayout.Y_AXIS));
        JButton conectar = Estilo.boton("Conectar   →", true); conectar.setFont(new Font("Segoe UI", Font.BOLD, 14)); conectar.setAlignmentX(Component.LEFT_ALIGNMENT); conectar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48)); conectar.addActionListener(e -> conectar());
        JButton vista = new JButton("Explorar vista previa"); vista.setFont(new Font("Segoe UI", Font.BOLD, 13)); vista.setForeground(Color.BLACK); vista.setBorderPainted(false); vista.setContentAreaFilled(false); vista.setFocusPainted(false); vista.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); vista.setAlignmentX(Component.CENTER_ALIGNMENT); vista.addActionListener(e -> { new FrmClientes().setVisible(true); dispose(); });
        JLabel nota = new JLabel("La conexión se configura con tus credenciales de BD."); nota.setFont(new Font("Segoe UI", Font.PLAIN, 11)); nota.setForeground(Estilo.SECUNDARIO); nota.setAlignmentX(Component.CENTER_ALIGNMENT);
        acciones.add(conectar); acciones.add(Box.createVerticalStrut(8)); acciones.add(vista); acciones.add(Box.createVerticalStrut(7)); acciones.add(nota);
        tarjeta.add(acciones, BorderLayout.SOUTH);

        JPanel centro = new JPanel(new GridBagLayout()); centro.setOpaque(false); centro.add(tarjeta);
        zona.add(centro, BorderLayout.CENTER);
        JLabel pie = new JLabel("PROYECTO INTEGRADOR  ·  CLIENTES"); pie.setFont(new Font("Segoe UI", Font.PLAIN, 9)); pie.setForeground(new Color(144,155,173)); pie.setHorizontalAlignment(SwingConstants.CENTER); zona.add(pie, BorderLayout.SOUTH);
        return zona;
    }

    private JLabel textoMarca(String texto, int tam, boolean negrita, Color color) {
        JLabel l = new JLabel(texto); l.setFont(new Font("Segoe UI", negrita ? Font.BOLD : Font.PLAIN, tam)); l.setForeground(color); l.setAlignmentX(Component.LEFT_ALIGNMENT); return l;
    }

    private JPanel campo(String etiqueta, JTextField campo, String ayuda) {
        JPanel p = new JPanel(new BorderLayout(0, 5)); p.setOpaque(false);
        JLabel label = Estilo.etiqueta(etiqueta); label.setFont(new Font("Segoe UI", Font.BOLD, 13)); p.add(label, BorderLayout.NORTH);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 15)); campo.setToolTipText(ayuda); campo.setPreferredSize(new Dimension(340, 46)); p.add(campo, BorderLayout.CENTER);
        return p;
    }

    private void conectar() {
        if (servidor.getText().isBlank() || base.getText().isBlank() || usuario.getText().isBlank() || contrasena.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Completa servidor, base de datos, usuario y contraseña.", "Datos requeridos", JOptionPane.WARNING_MESSAGE); return;
        }
        JOptionPane.showMessageDialog(this, "La pantalla está lista. La conexión se integrará con ConexionBD en la siguiente etapa.", "Integración pendiente", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        Estilo.preparar();
        SwingUtilities.invokeLater(() -> new FrmLogin().setVisible(true));
    }
}
