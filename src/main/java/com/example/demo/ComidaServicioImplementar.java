package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComidaServicioImplementar implements ComidaServicio {

	@Autowired
    private ComidaRepository repositorio;
	
	@Override
	public List<Comida> listarComida() {
		return repositorio.findAll();
	}

	@Override
	public Comida guardarComida(Comida comida) {
		return repositorio.save(comida);
	}

	@Override
	public Comida obtenerComida(Integer id) {
		return repositorio.findById(id).get();
	}
	
	@Override
	public Comida buscarComidaPorNombre(String nombre) {
		return repositorio.findByNombre(nombre);
	}

	@Override
	public Comida actualizarComida(Comida comida) {
		return repositorio.save(comida);
	}

	@Override
	public void borrarComida(Integer id) {
		repositorio.deleteById(id);
	}
	
}
