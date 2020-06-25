package application.view;

import application.model.Usuario;
import javafx.scene.layout.Pane;

public interface BoundaryContentLoggedUser {
	public Pane generateForm(Usuario usuario);
}