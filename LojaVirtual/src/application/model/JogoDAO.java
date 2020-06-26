package application.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import application.db.connection.factory.SQLServerConnectionFactory;

public class JogoDAO implements IJogoDAO {

	private Connection con;
	private SQLServerConnectionFactory factory;
	
	public JogoDAO() throws SQLException, IOException, ParseException {
		factory = new SQLServerConnectionFactory();
		con = factory.getConnection();
	}
	
	@Override
	public void insert(Jogo jogo) throws SQLException {
		String sql = "INSERT INTO jogos VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, jogo.getNome());
		ps.setFloat(2, jogo.getPreco());
		ps.setInt(3, jogo.getQtdJogo());
		ps.setDate(4, (Date) jogo.getDataLancamento());
		ps.setString(5, jogo.getDesenvolvedora());
		ps.setString(6, jogo.getDistribuidora());
		ps.setString(7, jogo.getNomeImg());
		ps.setString(8, jogo.getDescricao());
		ps.setInt(9, jogo.getRequisito().getID());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void update(Jogo jogo) throws SQLException {
		String sql = "UPDATE jogo SET nome = ?, preco = ?, qtdJogo = ?, dataLancamento = ?, desenvolvedora = ?"
				+ "distribuidora = ?, imagem = ?, descricao = ?, requisitoCodigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);	
		ps.setString(1, jogo.getNome());
		ps.setFloat(2, jogo.getPreco());
		ps.setInt(3, jogo.getQtdJogo());
		ps.setDate(4, (Date) jogo.getDataLancamento());
		ps.setString(5, jogo.getDesenvolvedora());
		ps.setString(6, jogo.getDistribuidora());
		ps.setString(7, jogo.getNomeImg());
		ps.setString(8, jogo.getDescricao());
		ps.setInt(9, jogo.getRequisito().getID());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void delete(Jogo jogo) throws SQLException {
		String sql = "DELETE FROM jogos WHERE codigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());
		
		ps.execute();
		ps.close();
	}

	@Override
	public Jogo select(Jogo jogo) throws SQLException {
		String sql = "SELECT * FROM jogos j " 
				+ "INNER JOIN requisitos r " 
				+ "ON j.requisitoCodigo = r.codigo "
				+ "WHERE codigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		
		int i = 0;
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			jogo.setID(rs.getInt("codigo"));
			jogo.setNome(rs.getString("nome"));
			jogo.setPreco(rs.getFloat("preco"));
			jogo.setQtdJogo(rs.getInt("qtdJogo"));
			jogo.setDataLancamento(rs.getDate("dataLancamento"));
			jogo.setDesenvolvedora(rs.getString("desenvolvedora"));
			jogo.setDistribuidora(rs.getString("distribuidora"));
			jogo.setNomeImg(rs.getString("imagem"));
			jogo.setDescricao(rs.getString("descricao"));
			
			Requisito requisito = new Requisito();
			requisito.setID(rs.getInt("requisitoCodigo"));
			requisito.setSO(rs.getString("so"));
			requisito.setArmazenamento(rs.getString("armazenamento"));
			requisito.setProcessador(rs.getString("processador"));
			requisito.setMemoria(rs.getString("memoria"));
			requisito.setPlacaVideo(rs.getString("placaVideo"));
			requisito.setDirectX(rs.getString("directX"));
			
			jogo.setRequisito(requisito);
		}
		
		if (i == 0) {
			jogo = new Jogo();
		}
		
		return jogo;
	}

	@Override
	public List<Jogo> selectAll() throws SQLException {
		String sql = "SELECT * FROM jogos j " 
				+ "INNER JOIN requisitos r " 
				+ "ON j.requisitoCodigo = r.codigo "
				+ "WHERE codigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		List<Jogo> listaJogos = new ArrayList<Jogo>();
		
		while (rs.next()) {
			Jogo jogo = new Jogo();
			
			jogo.setID(rs.getInt("codigo"));
			jogo.setNome(rs.getString("nome"));
			jogo.setPreco(rs.getFloat("preco"));
			jogo.setQtdJogo(rs.getInt("qtdJogo"));
			jogo.setDataLancamento(rs.getDate("dataLancamento"));
			jogo.setDesenvolvedora(rs.getString("desenvolvedora"));
			jogo.setDistribuidora(rs.getString("distribuidora"));
			jogo.setNomeImg(rs.getString("imagem"));
			jogo.setDescricao(rs.getString("descricao"));
			
			Requisito requisito = new Requisito();
			requisito.setID(rs.getInt("requisitoCodigo"));
			requisito.setSO(rs.getString("so"));
			requisito.setArmazenamento(rs.getString("armazenamento"));
			requisito.setProcessador(rs.getString("processador"));
			requisito.setMemoria(rs.getString("memoria"));
			requisito.setPlacaVideo(rs.getString("placaVideo"));
			requisito.setDirectX(rs.getString("directX"));
			
			jogo.setRequisito(requisito);
			
			listaJogos.add(jogo);
		}
		return listaJogos;
	}

}
