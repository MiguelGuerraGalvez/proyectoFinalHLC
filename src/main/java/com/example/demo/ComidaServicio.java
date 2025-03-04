package com.example.demo;

import java.util.List;

public interface ComidaServicio {
	public List<Comida> listarComida();

    public Comida guardarComida(Comida comida);

    public Comida obtenerComida(Integer id);
    
    public Comida buscarComidaPorNombre(String nombre);

    public Comida actualizarComida(Comida comida);

    public void borrarComida(Integer id);
}
