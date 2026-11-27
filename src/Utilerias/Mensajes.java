package Utilerias;

import java.awt.Component;
import javax.swing.JOptionPane;

public class Mensajes {
	// Component padre: Es la ventana que se está usando, es decir, la ventana a la
	// que se le mostrará arriba la otra ventana de Info, Advertencia o Error.
	// ---------------------------------------------------------------------------------------
	// Forma de usarlos: Llamar a la ventana según el caso y usar el "this"
	// Mensajes.mostrarInfo(this, " x texto que se vaya a mostrar");

	public static void mostrarInfo(Component padre, String mensaje) {
		JOptionPane.showMessageDialog(padre, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void mostrarAdvertencia(Component padre, String mensaje) {
		JOptionPane.showMessageDialog(padre, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
	}

	public static void mostrarError(Component padre, String mensaje) {
		JOptionPane.showMessageDialog(padre, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static boolean confirmar(Component padre, String mensaje) {
		int respuesta = JOptionPane.showConfirmDialog(padre, mensaje, "Confirmar acción", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		return respuesta == JOptionPane.YES_OPTION;
	}
}