package com.tecnm.envios.componentes;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class JTextFieldValidado extends JTextField{

	//identificador, ya que implementa serializable 
	private static final long serialVersionUID = 1L;
	
	private int tamanio; //gaurda el maximo de caracteres permitidos en el campo
	private boolean mayusculas;//identifica si el texto debe convertirse automaticamente a mayuscula
	private boolean obligatorio;//para indicar si el campo no puede quedarse vacío
	public boolean isValidCampo;//guarda el resultado de la ultima validación
	private final Color defaultBorderColor;//guarda el color de original del campo antes de cambarlo a verde si es valdo o rojo si es invalido
	
	//primer contructor que usa un limite muy grande por defecto
	public JTextFieldValidado(){
		this(Integer.MAX_VALUE);
	}

   //segudo contructor que recibe cuatas letras maximo se permiten
	public JTextFieldValidado(int columns) {
		//creamos la caja de texto de JTextField y guarda el limite maximo de caracteres
		super();
		this.tamanio=columns;
		//revisamos si el tema trae color de borde, si no tiene le ponemos gris
		this.defaultBorderColor =UIManager.getColor("TextField.borderColor")!=null
				? UIManager.getColor("TextField.borderColor")
						:Color.GRAY;
		//le ponemos filtro al documento del campo para controlar mayusculas y limite de letras
		((AbstractDocument) getDocument()).setDocumentFilter(new TextoValidadoFilter());
		initCustomBehavior();
	}

	//metodos get y set de tamanio(devuelve y cambia el limite maximo de caracteres)
	public int getTamanio() {
		return tamanio;
	}
	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}

	//lo usamos para saber si esta activado pasar a mayusculas
	public boolean isMayusculas() {
		return mayusculas;
	}

	//activa o desactiva las mayusculas 
	public void setMayusculas(boolean mayusculas) {
		this.mayusculas = mayusculas;
	}

	//lo usamos para saber si el campo es obligatorio
	public boolean isObligatorio() {
		return obligatorio;
	}
	
	//lo usamos para cambiar si el campo es obligatorio o no
	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	//lo usamos para saber si el campo pasó la validación
	public boolean isValidCampo() {
		return isValidCampo;
	}
 
	public void initCustomBehavior() {
		//si no tiene definido un ancho de columnas, se le asigna 10 por defecto
		if (this.getColumns() == 0) {
			this.setColumns(10);
		}
		
		//detecta el focus dentro del cuadro de texto
		this.addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent e) {
				validarCampo();//revisa si el contenido cumple con las reglas 
			}
			
			public void focusGained(FocusEvent e) {
				//revisa que el cuadro de texto este vacio
				if(getText().trim().isEmpty()) {
					//si esta vacia, limpia el color y vuelve al borde normal
					resetVisualState();
				}
			}
		});
	}
	
	//evalua si el campo pasó la validación
	public boolean validarCampo() {
		//quita los espacios en blanco al inicio y al final
		String texto = getText().trim();
		
		//pregunta si es obligatorio y si es esta vacio
		if(obligatorio && texto.isEmpty()) {
			//si pasa eso, lo marca en rojo como invalido
			markAsInvalid();
			return false;
		}
		//si no, lo marca en verde como válido
		markAsValid();
		return true;
	}
	
	//si el campo esta en estado valido lo cambiamos a borde verde
	private void markAsValid() {
		this.isValidCampo = true;
		this.setBorder(BorderFactory.createLineBorder(new Color (40, 167, 69), 1));
	}
	
	//si el campo esta en estado invalido lo cambiamos a borde rojo
	private void markAsInvalid() {
		this.isValidCampo=false;
		this.setBorder(BorderFactory.createLineBorder(new Color(220, 53, 69), 1));
	}
	
	//regresa el borde a su color normal
	private void resetVisualState() {
		this.isValidCampo= false;
		this.setBorder(BorderFactory.createLineBorder(defaultBorderColor, 1));
	}

	//clase que extiende de DocumentFilter que revisa y filtra el texto que se escribe, borra o pega
	private class TextoValidadoFilter extends DocumentFilter{
		//se ejecuta cuando se intenta insertar texto nuevo
		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
				throws BadLocationException {
			//si no hay texto no hace nada y sale
			if(string==null) {
				return;
			}
			//obtiene el texto actual que hay en el documento
			String textoActual = fb.getDocument().getText(0, fb.getDocument().getLength());
			//arma como quedaria el texto metiendo string en la posiscion offset
			String nuevoTexto = textoActual.substring(0, offset)+ string + textoActual.substring(offset);
			//usa el metodo limpiarTexto al nuevoTexto
			String limpio =limpiarTexto(nuevoTexto);
			//escribe el texto limpio en el documento
			fb.replace(0, fb.getDocument().getLength(), limpio, attr);
		}
		
		//se ejecuta cuando se reeemplaza texto
		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
				throws BadLocationException {
			//lee el texto actual que hay en el documento
			String textoActual = fb.getDocument().getText(0, fb.getDocument().getLength());
			//con esto se arma el nuevo texto, antes del offset + el texto nuevo + despues de offset + length
			String nuevoTexto = textoActual.substring(0, offset)
					+ (text == null? "" : text)
					+ textoActual.substring(offset + length);
			//usa el metodo limpiarTexto al nuevoTexto
			String limpio = limpiarTexto(nuevoTexto);
			//escribe el texto limpio en el documento
			fb.replace(0, fb.getDocument().getLength(), limpio, attrs);
		}
		
		//se ejecuta cuando se borra texto
		@Override
		public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			//lee el texto actual que hay en el documento antes de borrar
			String textoActual = fb.getDocument().getText(0, fb.getDocument().getLength());
			//quita el pedazo que va desde offset hasta offset + length y junta lo demas 
			String nuevoTexto = textoActual.substring(0, offset)
					+ textoActual.substring(offset + length);
			//usa el metodo limpiarTexto al nuevoTexto despues de borrar
			String limpio = limpiarTexto(nuevoTexto);
			//escribe el texto limpio en el documento
			fb.replace(0, fb.getDocument().getLength(), limpio, null);
		}
		//ajusta el texto segun el formato de mayusculas y el tamaño maximo permitido
		private String limpiarTexto(String texto) {
			//si mayisculas esta activo, pasa el texto a mayusculas, si no lo deja igual
			String resultado = mayusculas? texto.toUpperCase() : texto;
			//revisar que la longitud del texto no supere el limite permitido
			if(resultado.length() > tamanio) {
				//si el texto se llega a pasar lo corta para dejarlo hasta el máximo permitido
				resultado = resultado.substring(0, tamanio);
			}
			//devuelve el resultado ya listo para mostrarse en el campo
			return resultado;
		}		
	}
	
	
}
