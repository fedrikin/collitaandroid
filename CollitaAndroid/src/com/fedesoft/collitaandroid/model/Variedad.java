package com.fedesoft.collitaandroid.model;

public class Variedad {
	private Integer id;
	private String nombre;
	private Double precioKiloCollita;
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
	public Double getPrecioKiloCollita() {
		return precioKiloCollita;
	}
	public void setPrecioKiloCollita(Double precioKilo) {
		this.precioKiloCollita = precioKilo;
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
