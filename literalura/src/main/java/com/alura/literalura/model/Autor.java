package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer añoNacimiento;
    private Integer añoMuerte;

    public Autor() {}

    public Autor(String nombre, Integer añoNacimiento, Integer añoMuerte) {
        this.nombre = nombre;
        this.añoNacimiento = añoNacimiento;
        this.añoMuerte = añoMuerte;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getAñoNacimiento() {
        return añoNacimiento;
    }

    public Integer getAñoMuerte() {
        return añoMuerte;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAñoNacimiento(Integer añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }

    public void setAñoMuerte(Integer añoMuerte) {
        this.añoMuerte = añoMuerte;
    }

	@Override
	public String toString() {
		return id+ " - "+ nombre + " - nacido en "+añoNacimiento+ ((añoMuerte != null) ? " - fallecido en "+añoMuerte : "" ); 
		
//		return "Autor [id=" + id + ", nombre=" + nombre + ", añoNacimiento=" + añoNacimiento + ", añoMuerte="
//				+ añoMuerte + "]";
	}
    
    
    
}