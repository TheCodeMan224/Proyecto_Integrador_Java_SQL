package com.tecnm.envios.componentes;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelBotonesCRUD extends JPanel {

	//identificador serializable 
	private static final long serialVersionUID = 1L;
	
	//Botones de operaciones CRUD y limpiar
	private JButton btnNuevo, btnGuardar, btnEliminar, btnLimpiar, btnBuscar;
	
	//contructor
	public PanelBotonesCRUD() {
		//configura el panel y crea los botones 
		initComponents();
	}
	
	//inicializa el diseño del panel y agrega los botones 
	private void initComponents() {
		//acomoda los botones en fila centrada con separación
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 8));
		
		//boton nuevo
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setToolTipText("Preparar un registro nuevo");
		this.add(btnNuevo);
		
		//boton guardar
		btnGuardar = new JButton("Guardar");
		btnGuardar.setToolTipText("Guardar los datos del registro");
		this.add(btnGuardar);
		
		//boton eliminar
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setToolTipText("Eliminar el registro actual");
		this.add(btnEliminar);
		
		//boton limpiar
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setToolTipText("Limpiar el registro actual");
		this.add(btnLimpiar);
		
		//boton buscar
		btnBuscar = new JButton("Buscar");
		btnBuscar.setToolTipText("Buscar un registro existente");
		this.add(btnBuscar);
	}

	//regresa el boton nuevo
	public JButton getBtnNuevo() {
		return btnNuevo;
	}

	//regresa el boton guardar
	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	//regresa el boton eliminar
	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	//regresa el boton limpiar
	public JButton getBtnLimpiar() {
		return btnLimpiar;
	}

	//regresa el boton buscar
	public JButton getBtnBuscar() {
		return btnBuscar;
	}


	
}
