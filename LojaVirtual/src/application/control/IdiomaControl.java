package application.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import application.dao.IdiomaDAO;
import application.model.Idioma;

public class IdiomaControl {
	private List<Idioma> idiomas = new ArrayList<Idioma>();
	
	public List<Idioma> getLista() throws IOException, ParseException, SQLException {
		IdiomaDAO idiomaDAO = new IdiomaDAO();
		idiomas.addAll(idiomaDAO.selectAll());
		return idiomas;
	}	
}
