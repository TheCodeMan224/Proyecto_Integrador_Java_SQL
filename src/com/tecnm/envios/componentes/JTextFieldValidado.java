package com.tecnm.envios.componentes;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
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
				? UIManager.getColor("TextFied.borderColor")
						:Color.GRAY;
		//le ponemos filtro al di¿ocumento del campo para controlar mayusculas y limite de letras
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
		this.setColumns(10);//cuantas columnas de ancho debe mostrar
		
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

	private class TextoValidadoFilter extends DocumentFilter{
		
	}
	
	
}
