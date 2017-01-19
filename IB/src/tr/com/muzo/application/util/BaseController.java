package tr.com.muzo.application.util;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import tr.com.muzo.application.Main;
import tr.com.muzo.controller.view.kullanici.KullaniciController;

@Controller
public class BaseController implements Serializable{
	
	private static final long serialVersionUID = -249930691472255675L;
	private Main main;
	private double width;
	private double height;
	protected BorderPane root = new BorderPane();
	@Autowired
	private KullaniciController kullaniciController;
	
	@PostConstruct
	public void init(){
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		this.width = primaryScreenBounds.getWidth();
		this.height = primaryScreenBounds.getHeight();
	}
	
	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	public void initialize(){
		ekranDegistir();
	}
	
	public void fullScreen() {
		main.getStage().setX(0);
		main.getStage().setY(0);
		main.getStage().setWidth(width);
		main.getStage().setHeight(height);
		((BorderPane) main.getStage().getScene().getRoot()).setTop(getMenuBar());
		main.getStage().getScene().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
	}
	
	public void ekranDegistir(){
		root = new BorderPane();
		Scene scene = new Scene(root);
		root.getChildren().removeAll(root.getChildren());
		main.getStage().setScene(scene);
		fullScreen();
		if(!main.getStage().isShowing()){
			main.getStage().show();
		}
	}
	
	public MenuBar getMenuBar(){
		Menu yoneticiMenu = new Menu("Yönetim");
		MenuItem menu1 = new MenuItem("Kullanýcý Listesi");
		menu1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				kullaniciController.setMain(getMain());
				kullaniciController.initialize();
			}
		});
		
		yoneticiMenu.getItems().addAll(menu1);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(yoneticiMenu);
		menuBar.setUseSystemMenuBar(true);
		return menuBar;
	}
	
	public void addEventHandler(KeyCode keyCode , Button buton){
		main.getStage().getScene().getRoot().addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == keyCode) {
				buton.fire();
				ev.consume();
			}
		});
	}
	
	
	
}
