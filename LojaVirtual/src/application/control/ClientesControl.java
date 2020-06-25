package application.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.parser.ParseException;

import application.dao.UsuarioDAO;
import application.model.Telefone;
import application.model.Usuario;

public class ClientesControl {
	public String add(Usuario usuario) throws SQLException, IOException, ParseException {
		UsuarioDAO usuarioDao = new UsuarioDAO();
		boolean validaEmail = usuarioDao.validaEmail(usuario.getEmail());
		boolean validaNomeUsuario = usuarioDao.validaNomeUsuario(usuario.getNomeUsuario());
		boolean validaCpf = usuarioDao.validaCpf(usuario.getCPF().toString());
		
		if( validaEmail && validaNomeUsuario && validaCpf ) {
			try {
				usuarioDao.insert(usuario);
				
				List<Telefone> telefones = usuario.getTelefones();
				for(Telefone e : telefones) {
					//Adicionar telefones no bd
				}
				
				return "Usuario cadastrado com sucesso";
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(!validaEmail){
			return "E-Mail já utilizado";
		}
		if(!validaNomeUsuario){
			return "Nome de usuário já utilizado";
		}
		if(!validaCpf){
			return "CPF já cadastrado";
		}
		
		return "Erro ao guardar os dados";
	}
	
	
}
