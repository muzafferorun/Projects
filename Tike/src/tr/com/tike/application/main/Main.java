package tr.com.tike.application.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Stage primaryStage;
	private BorderPane borderPane;
	private Scene scene;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		initialize();
	}
	
	public void initialize() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GirisController.class.getResource("Giris.fxml"));
        borderPane = (BorderPane) loader.load();
        scene = new Scene(borderPane);
        primaryStage.centerOnScreen();
        primaryStage.setMaxHeight(175);
        primaryStage.setMaxWidth(450);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("file:resources/images/House-icon.png"));
        
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public BorderPane getBorderPane() {
		return borderPane;
	}

	public void setBorderPane(BorderPane borderPane) {
		this.borderPane = borderPane;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
}
