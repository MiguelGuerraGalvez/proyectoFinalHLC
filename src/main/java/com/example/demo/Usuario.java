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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Setter // Poniendolo no haría falta poner ni los getters ni los setters
@Getter
@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincrement
    private Integer id;
	
    @Column(name = "usuario")
    private String nombre;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.!-_:;¿?¡])(?=\\S+$).{8,}$",
        message = "La contraseña debe tener al menos una mayúscula, una minúscula, un número y un caracter especial"
    )
    @Column(name = "password")
    private String password;
    
    private String rol;
    
    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;
    
    public Usuario() {
    	
    }
    
    public Usuario(String nombre, String password) {
		this.nombre = nombre;
		this.password = password;
	}
    
    public Usuario(String nombre, String password, String rol) {
		this.nombre = nombre;
		this.password = password;
		this.rol = rol;
	}
    
    public Usuario(String nombre, String password, List<Pedido> pedidos, String rol) {
		this.nombre = nombre;
		this.password = password;
		this.pedidos = pedidos;
		this.rol = rol;
	}

	public Usuario(Integer id, String nombre, String password, List<Pedido> pedidos, String rol) {
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.pedidos = pedidos;
		this.rol = rol;
	}

	/*public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}*/
}
