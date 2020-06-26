package application.dao;

import java.sql.SQLException;
import java.util.List;

import application.model.Funcionario;

public interface IFuncionarioDAO {

	public void insert(Funcionario funcionario) throws SQLException;
	public void update(Funcionario funcionario) throws SQLException;
	public void delete(Funcionario funcionario) throws SQLException;
	public Funcionario select(Funcionario funcionario) throws SQLException;
	public List<Funcionario> selectAll() throws SQLException;
	
}
