package Diseño;

import javax.swing.SwingUtilities;
import Diseño.Estilo;
import Diseño.FrmLogin;

public class Main {
    public static void main(String[] args) {
        Estilo.preparar();
        SwingUtilities.invokeLater(() -> new FrmLogin().setVisible(true));
    }
}
