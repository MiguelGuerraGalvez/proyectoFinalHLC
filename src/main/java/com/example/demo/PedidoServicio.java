package com.example.demo;

import java.util.List;

public interface PedidoServicio {
	public List<Pedido> listarPedido();

    public Pedido guardarPedido(Pedido pedido);

    public Pedido obtenerPedido(Integer id);

    public Pedido actualizarPedido(Pedido pedido);

    public void borrarPedido(Integer id);
}
