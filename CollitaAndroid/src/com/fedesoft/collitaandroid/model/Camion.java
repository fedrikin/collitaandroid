package com.fedesoft.collitaandroid.model;

public class Camion {
	private Integer id;
	private String nombre;
	private String conductor;
	private String telefono;
	private Integer cajonesMaximo;
	
	// modificacióg
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getConductor() {
		return conductor;
	}
	public void setConductor(String conductor) {
		this.conductor = conductor;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Integer getCajonesMaximo() {
		return cajonesMaximo;
	}
	public void setCajonesMaximo(Integer cajonesMaximo) {
		this.cajonesMaximo = cajonesMaximo;
	}
	

}
