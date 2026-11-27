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



public class JTextFieldNumerico extends JTextField{

	//parametro serializable
	private static final long serialVersionUID = 1L;
	
	private int tamanio;//limite maximo de caracteres 
	private boolean isValidNumero;//indica si el valor actual es valido
	private final Color defaultBorderColor;//Color original del borde del campo
	
	//primer contructor que usa un limite muy grande por defecto
	public JTextFieldNumerico(){
		this(Integer.MAX_VALUE);
	}

    //segundo contructor que recibe cuantos digitos maximos se permiten
	public JTextFieldNumerico(int columns) {
		//creamos la caja de texto de JTextField y guarda el limite maximo de caracteres
		super();
		this.tamanio=columns;
		//revisamos si el tema trae color de borde, si no tiene le ponemos gris
		this.defaultBorderColor =UIManager.getColor("TextField.borderColor")!=null
				? UIManager.getColor("TextField.borderColor")
						:Color.GRAY;
		//conecta con el filtro para permitir solo digitos numericos
		((AbstractDocument) getDocument()).setDocumentFilter(new NumericoFilter());
		initCustomBehavior();
	}
    
	//devuelve el limite máximo de caracteres
	public int getTamanio() {
		return tamanio;
	}

	//cambia el limite maximo de caracteres
	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}
	
	public void initCustomBehavior() {
		//si no tiene definido un ancho de columnas, se le asigna 10 por defecto
		if (this.getColumns() == 0) {
			this.setColumns(10);
		}
		
		//detecta el focus dentro del cuadro de texto
		this.addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent e) {
				//valida el contenido al salir del campo
				validateCurrentNumero();
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
	
	public Integer getValor() {
		//quitamos los espacios en blanco al inicio y al final
		String texto = getText().trim();
		//si esta vacio regresa null
		if(texto.isEmpty()) {
			return null;
		}
		try {
			//convierte el texto a numero entero
			return Integer.valueOf(texto);
		}catch(NumberFormatException e) {
			//si no se puede convertir regresa null
			return null;
		}
	}
	
	public void setValor(Integer valor) {
		//si el valor  no tiene datos, deja vacio el cuadro de texto desde el codigo
		if(valor==null) {
			setText("");
			//regresa el borde a su color normal
			resetVisualState();
		}else {
			//convierte el numero a texto y lo muestra en el campo
			setText(String.valueOf(valor));
			//marca el campo como valido
			markAsValid();
		}
	}
	
	public boolean validateCurrentNumero() {
		//obtiene el texto sin espacios al inicio y al fin
		String texto = getText().trim();
		
		//si esta vacio restaura el borde normal
		if(texto.isEmpty()) {
			resetVisualState();
			return true;
		}
		try {
			//comprueba que el texto sea un entero valido
			Integer.parseInt(texto);
			//lo marca como valido
			markAsValid();
			return true;
		}catch(NumberFormatException e){
			//si falla la conversión lo marca como invalido
			markAsInvalid();
			return false;
		}
	}
	
	//devuelve si el campo pasó la ultima validación
	public boolean isValidNumero() {
		return isValidNumero;
	}
	
	//si el campo esta en estado valido lo cambiamos a borde verde
	private void markAsValid() {
		this.isValidNumero=true;
		this.setBorder(BorderFactory.createLineBorder(new Color(40,167,69), 1));
	}
	
	//si el campo esta en estado invalido lo cambiamos a borde rojo
	private void markAsInvalid() {
		this.isValidNumero=false;
		this.setBorder(BorderFactory.createLineBorder(new Color(220, 53, 69), 1));
	}
	
	//regresa el borde a su color normal y quita la marca de valido
	private void resetVisualState() {
		this.isValidNumero=false;
		this.setBorder(BorderFactory.createLineBorder(defaultBorderColor, 1));
	}
	
	//clase que extiende de DocumentFilter que revisa y filtra el texto numerico en tiempo real
	private class NumericoFilter extends DocumentFilter{
		//se ejecuta al insertar texto nuevo en el documento
		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
				throws BadLocationException {
			//si no hay texto nuevo no hace nada
			if(string == null) {
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
		
		//se ejecuta al reemplazar texto o escribir sobre una selección
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
		
		//se eejcuta al borrar texto del documento
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
		
		//conserva solo digitos númericos y corta si supera el tamaño
		private String limpiarTexto(String texto) {
		    //usamos el StringBuilder para ir creando el texto limpio
			StringBuilder sb = new StringBuilder();
			//recorre todo el texto
			for(int i=0; i<texto.length(); i++) {
				char c = texto.charAt(i);
				//revisa que el caracter sea un digito, si lo es, lo agrega al StringBuilder(sb)
				if(Character.isDigit(c)) {
					sb.append(c);
				}
			}
			//si lo que guardamos supera el limite, lo recorta al tamanio, si no, lo deja como esta
			if(sb.length()>tamanio) {
				sb.setLength(tamanio);
			}
			//convierte el StringBuilder a string y lo devuelve
			return sb.toString();
		}
	}

}
