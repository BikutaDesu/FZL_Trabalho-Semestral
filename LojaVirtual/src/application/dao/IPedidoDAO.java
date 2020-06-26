package application.dao;

import java.sql.SQLException;
import java.util.List;

import application.model.Pedido;

public interface IPedidoDAO {
	public void insert(Pedido pedido) throws SQLException;
	public void delete(Pedido pedido) throws SQLException;
	public Pedido select(Pedido pedido) throws SQLException;
	public List<Pedido> selectAll() throws SQLException;
}
