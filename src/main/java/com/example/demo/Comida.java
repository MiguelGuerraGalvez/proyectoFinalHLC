package com.example.demo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter // Poniendolo no haría falta poner ni los getters ni los setters
@Getter
@Entity
@Table(name = "comida")
public class Comida {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincrement
    private Integer id;
	
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "tipo")
    private String tipo;

    @Column(name = "precio")
    private double precio;
    
    public Comida() {
    	
    }
    
    public Comida(String nombre, String tipo, double precio) {
    	this.nombre = nombre;
    	this.tipo = tipo;
    	this.precio = precio;
    }
    
}
