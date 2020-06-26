package application.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import application.dao.CategoriaDAO;
import application.model.Categoria;

public class CategoriaControl {
	private List<Categoria> categorias = new ArrayList<Categoria>();
	
	public List<Categoria> getLista() throws SQLException, IOException, ParseException {
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		categorias.addAll(categoriaDAO.selectAll());
		return categorias;
	}

}
