package application.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainBoundary extends Application{

	private LoginBoundary login = new LoginBoundary();
	
	@Override
	public void start(Stage stage) throws Exception {
		Scene scn = new Scene(login.generateForm(), 1200, 900);
		
		stage.setScene(scn);
		stage.setTitle("Raizer Games");
		stage.show();
	}
	
	public static void main(String[] args) {
		MainBoundary.launch(args);
	}

}
