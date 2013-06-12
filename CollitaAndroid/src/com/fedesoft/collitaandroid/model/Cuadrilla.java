package com.fedesoft.collitaandroid.model;

public class Cuadrilla {
	private Integer id;
	private String nombre;
	private Integer numeroCollidors;
	private String telefono;
	
	public void setId(Integer id){
		this.id=id;
	}
	
	public Integer getId(){
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getNumeroCollidors() {
		return numeroCollidors;
	}

	public void setNumeroCollidors(Integer numeroCollidors) {
		this.numeroCollidors = numeroCollidors;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String toString(){
		return nombre;
	}
	
	

}
