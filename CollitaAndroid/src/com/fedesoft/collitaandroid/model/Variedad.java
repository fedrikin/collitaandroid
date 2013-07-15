package com.fedesoft.collitaandroid.model;

public class Variedad {
	private Integer id;
	private String nombre;
	private Double precioKilo;
	private Double precioMedioCompra=0.20;
	private Integer kilosPorCajon=18;
	
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
	public Double getPrecioKilo() {
		return precioKilo;
	}
	public void setPrecioKilo(Double precioKilo) {
		this.precioKilo = precioKilo;
	}
	public String toString(){
		return nombre;
	}
	public Double getPrecioMedioCompra() {
		return precioMedioCompra;
	}
	public void setPrecioMedioCompra(Double precioMedioCompra) {
		this.precioMedioCompra = precioMedioCompra;
	}
	public Integer getKilosPorCajon() {
		return kilosPorCajon;
	}
	public void setKilosPorCajon(Integer kilosPorCajon) {
		this.kilosPorCajon = kilosPorCajon;
	}
}
