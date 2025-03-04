package com.example.demo;

import java.util.List;

public interface UsuarioServicio {
	public List<Usuario> listarUsuario();

    public Usuario guardarUsuario(Usuario usuario);

    public Usuario obtenerUsuario(Integer id);
    
    public Usuario buscarUsuarioPorNombre(String nombre);

    public Usuario actualizarUsuario(Usuario usuario);

    public void borrarUsuario(Integer id);
}
