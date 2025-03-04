package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter // Poniendolo no haría falta poner ni los getters ni los setters
@Getter
@Entity
@Table(name = "pedidos")
public class Pedido {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@ManyToOne()
    @JoinColumn(name = "idUsuario")
    private Usuario idUsuario;

    @Column(name = "hamburguesa")
    private String hamburguesa;
    
    @Column(name = "patatas")
    private String patatas;
    
    @Column(name = "bebida")
    private String bebida;

    public Pedido() {
    	
    }
    
    public Pedido(String hamburguesa, String patatas, String bebida) {
		this.hamburguesa = hamburguesa;
		this.patatas = patatas;
		this.bebida = bebida;
	}
    
    public Pedido(Usuario idUsuario, String hamburguesa, String patatas, String bebida) {
		this.idUsuario = idUsuario;
		this.hamburguesa = hamburguesa;
		this.patatas = patatas;
		this.bebida = bebida;
	}
    
	public Pedido(Integer id, Usuario idUsuario, String hamburguesa, String patatas, String bebida) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.hamburguesa = hamburguesa;
		this.patatas = patatas;
		this.bebida = bebida;
	}

	/*public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getHamburguesa() {
		return hamburguesa;
	}

	public void setHamburguesa(String hamburguesa) {
		this.hamburguesa = hamburguesa;
	}

	public String getPatatas() {
		return patatas;
	}

	public void setPatatas(String patatas) {
		this.patatas = patatas;
	}

	public String getBebida() {
		return bebida;
	}

	public void setBebida(String bebida) {
		this.bebida = bebida;
	}*/
}
