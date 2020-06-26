package application.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import application.dao.PlataformaDAO;
import application.model.Plataforma;

public class PlataformaControl {
	private List<Plataforma> plataformas = new ArrayList<Plataforma>();
	
	public List<Plataforma> getLista() throws SQLException, IOException, ParseException {
		PlataformaDAO plataformaDAO = new PlataformaDAO();
		plataformas.addAll(plataformaDAO.selectAll());
		return plataformas;
	}
}
