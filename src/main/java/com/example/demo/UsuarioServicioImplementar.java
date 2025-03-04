package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicioImplementar implements UsuarioServicio {

	@Autowired
    private UsuarioRepository repositorio;
	
	@Override
	public List<Usuario> listarUsuario() {
		return repositorio.findAll();
	}

	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		return repositorio.save(usuario);
	}

	@Override
	public Usuario obtenerUsuario(Integer id) {
		return repositorio.findById(id).get();
	}
	
	@Override
	public Usuario buscarUsuarioPorNombre(String nombre) {
		return repositorio.findByNombre(nombre);
	}

	@Override
	public Usuario actualizarUsuario(Usuario usuario) {
		return repositorio.save(usuario);
	}

	@Override
	public void borrarUsuario(Integer id) {
		repositorio.deleteById(id);
	}
	
}
