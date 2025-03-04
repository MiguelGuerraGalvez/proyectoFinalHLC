package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServicioImplementar implements PedidoServicio {

	@Autowired
    private PedidoRepository repositorio;
	
	@Override
	public List<Pedido> listarPedido() {
		return repositorio.findAll();
	}

	@Override
	public Pedido guardarPedido(Pedido pedido) {
		return repositorio.save(pedido);
	}

	@Override
	public Pedido obtenerPedido(Integer id) {
		return repositorio.findById(id).get();
	}

	@Override
	public Pedido actualizarPedido(Pedido pedido) {
		return repositorio.save(pedido);
	}

	@Override
	public void borrarPedido(Integer id) {
		repositorio.deleteById(id);
	}
	
}
