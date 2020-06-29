package application.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import application.db.connection.factory.SQLServerConnectionFactory;
import application.model.Jogo;
import application.model.Requisito;

public class RequisitoDAO implements IRequisitoDAO {

	private SQLServerConnectionFactory factory;
	private Connection con;

	public RequisitoDAO(){
		try {
			factory = new SQLServerConnectionFactory();
			con = factory.getConnection();
		} catch (SQLException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(Jogo jogo) throws SQLException {
		Requisito requisito = jogo.getRequisito();
		String sql = "INSERT INTO requisitos VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());
		ps.setString(2, requisito.getSO());
		ps.setString(3, requisito.getArmazenamento());
		ps.setString(4, requisito.getProcessador());
		ps.setString(5, requisito.getMemoria());
		ps.setString(6, requisito.getPlacaVideo());
		ps.setString(7, requisito.getDirectX());
		ps.execute();
		ps.close();
	}

	@Override
	public void update(Jogo jogo) throws SQLException {
		Requisito requisito = jogo.getRequisito();
		String sql = "UPDATE requisitos SET so=?, armazenamento=?, processador=?, memoria=?, placaVideo=?, directX=? "
				+ "WHERE codigo=?";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, requisito.getSO());
		ps.setString(2, requisito.getArmazenamento());
		ps.setString(3, requisito.getProcessador());
		ps.setString(4, requisito.getMemoria());
		ps.setString(5, requisito.getPlacaVideo());
		ps.setString(6, requisito.getDirectX());
		ps.setInt(7, jogo.getID());

		ps.execute();
		ps.close();
	}

	@Override
	public void delete(Jogo jogo) throws SQLException {
		String sql = "DELETE requisitos WHERE codigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());

		ps.execute();
		ps.close();

	}

	@Override
	public Requisito select(Jogo jogo) throws SQLException {
		Requisito requisito = new Requisito();
		String sql = "SELECT * FROM requisitos WHERE codigo=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			requisito.setID(rs.getInt("codigo"));
			requisito.setSO(rs.getString("so"));
			requisito.setArmazenamento(rs.getString("armazenamento"));
			requisito.setProcessador(rs.getString("processador"));
			requisito.setMemoria(rs.getString("memoria"));
			requisito.setPlacaVideo(rs.getString("placaVideo"));
			requisito.setDirectX(rs.getString("directX"));
			
			rs.close();
			ps.close();
			return requisito;
		}else {
			rs.close();
			ps.close();
			return requisito;
		}
	}

	@Override
	public List<Requisito> selectAll() throws SQLException {
		String sql = "SELECT * FROM requisitos";
		PreparedStatement ps = con.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();
		
		List<Requisito> listaRequisitos = new ArrayList<Requisito>();
		
		while (rs.next()) {
			Requisito requisito = new Requisito();
			requisito.setID(rs.getInt("codigo"));
			requisito.setSO(rs.getString("so"));
			requisito.setArmazenamento(rs.getString("armazenamento"));
			requisito.setProcessador(rs.getString("processador"));
			requisito.setMemoria(rs.getString("memoria"));
			requisito.setPlacaVideo(rs.getString("placaVideo"));
			requisito.setDirectX(rs.getString("directX"));
			
			listaRequisitos.add(requisito);
		}
		rs.close();
		ps.close();
		
		return listaRequisitos;
	}

}
