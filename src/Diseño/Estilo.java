package Diseño;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public final class Estilo {
    static final Color TINTA = new Color(28, 42, 62);
    static final Color SECUNDARIO = new Color(101, 116, 139);
    static final Color FONDO = new Color(245, 247, 251);
    static final Color AZUL = new Color(40, 94, 210);
    static final Color BORDE = new Color(222, 228, 237);

    public static void preparar() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) { }
        UIManager.put("Label.foreground", TINTA);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 13));
    }
    static JPanel pagina() { JPanel p = new JPanel(new BorderLayout(0, 22)); p.setBackground(FONDO); p.setBorder(new EmptyBorder(28, 34, 28, 34)); return p; }
    static JPanel tarjeta() { JPanel p = new JPanel(); p.setBackground(Color.WHITE); p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDE), new EmptyBorder(20, 22, 20, 22))); return p; }
    static JLabel titulo(String s) { JLabel l = new JLabel(s); l.setFont(new Font("Segoe UI", Font.BOLD, 25)); l.setForeground(TINTA); return l; }
    static JLabel subtitulo(String s) { JLabel l = new JLabel(s); l.setFont(new Font("Segoe UI", Font.PLAIN, 13)); l.setForeground(SECUNDARIO); return l; }
    static JLabel etiqueta(String s) { JLabel l = new JLabel(s); l.setFont(new Font("Segoe UI", Font.BOLD, 12)); l.setForeground(SECUNDARIO); return l; }
    static JTextField campo() { JTextField f = new JTextField(); f.setFont(new Font("Segoe UI", Font.PLAIN, 14)); f.setPreferredSize(new Dimension(220, 38)); f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDE), new EmptyBorder(5, 10, 5, 10))); return f; }
    static JPasswordField clave() { JPasswordField f = new JPasswordField(); f.setFont(new Font("Segoe UI", Font.PLAIN, 14)); f.setPreferredSize(new Dimension(220, 38)); f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDE), new EmptyBorder(5, 10, 5, 10))); return f; }
    static JButton boton(String texto, boolean primario) { JButton b = new JButton(texto); b.setFocusPainted(false); b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); b.setPreferredSize(new Dimension(primario ? 150 : 112, 38)); b.setBackground(primario ? new Color(225, 236, 255) : Color.WHITE); b.setForeground(Color.BLACK); b.setBorder(primario ? BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(196, 216, 249)), BorderFactory.createEmptyBorder(7, 15, 7, 15)) : BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDE), BorderFactory.createEmptyBorder(7, 14, 7, 14))); return b; }
    static void tabla(JTable t) { t.setRowHeight(34); t.setFont(new Font("Segoe UI", Font.PLAIN, 13)); t.setForeground(TINTA); t.setGridColor(new Color(238, 241, 246)); t.setSelectionBackground(new Color(226, 237, 255)); t.setSelectionForeground(TINTA); t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12)); t.getTableHeader().setBackground(new Color(248, 250, 253)); t.getTableHeader().setForeground(SECUNDARIO); t.setFillsViewportHeight(true); }
    static JPanel encabezado(String titulo, String descripcion, JButton volver) {
        JPanel p = new JPanel(new BorderLayout(12, 5)); p.setOpaque(false);
        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 4)); textos.setOpaque(false); textos.add(titulo(titulo)); textos.add(subtitulo(descripcion)); p.add(textos, BorderLayout.CENTER);
        if (volver != null) p.add(volver, BorderLayout.EAST);
        return p;
    }
    private Estilo() { }
}
