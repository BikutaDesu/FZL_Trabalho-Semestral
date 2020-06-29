package application.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.dao.TelefoneDAO;
import application.model.Telefone;
import application.model.Usuario;

public class TelefoneUsuarioControl {
	private TelefoneDAO telefoneDAO = new TelefoneDAO();
	
	public void adicionar(Telefone telefone) throws SQLException {
		telefoneDAO.insert(telefone);
	}
	
	public void remover(Telefone telefone) throws SQLException {
		telefoneDAO.delete(telefone);
	}
	
	public List<Telefone> getLista(Usuario usuario) throws SQLException {
		List<Telefone> telefones = new ArrayList<Telefone>();
		telefones.addAll(telefoneDAO.selectAll(usuario));
		return telefones;
	}	
}
