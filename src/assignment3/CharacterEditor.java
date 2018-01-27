package assignment3;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Launching class for CSSE2002 Assignment 3
 * @author leggy (Lachlan Healey)
 */
public class CharacterEditor extends Application{
	
	/**
	 * DO NOT MODIFY THIS CLASS
	 */
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		URL location = getClass().getResource("view.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);

		Parent root = fxmlLoader.load(location.openStream());
		Scene scene = new Scene(root, 1000, 700);

		primaryStage.setTitle("CSSE2002 Assignment 3: Character Editor");
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(1000);
		primaryStage.setMinHeight(700);
		primaryStage.show();	
	}

}
