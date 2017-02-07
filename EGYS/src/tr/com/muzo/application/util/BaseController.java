package tr.com.muzo.application.util;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.persistence.MappedSuperclass;

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
import tr.com.muzo.application.kullanici.KullaniciController;
import tr.com.muzo.application.main.Main;

@MappedSuperclass
public class BaseController implements Serializable{

	private static final long serialVersionUID = -249930691472255675L;
	private Main main;
	private double width;
	private double height;
	
	@PostConstruct
	public void initBase(){
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
	
	public void initialize(Main main){
		this.main = main;
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
		getMain().setRoot(new BorderPane());
		getMain().setScene(new Scene(getMain().getRoot()));
		getMain().getRoot().getChildren().removeAll(getMain().getRoot().getChildren());
		main.getStage().setScene(getMain().getScene());
		fullScreen();
		if(!main.getStage().isShowing()){
			main.getStage().show();
		}
	}
	
	public MenuBar getMenuBar(){
		Menu yoneticiMenu = new Menu("Yönetim");
		MenuItem menuKullaniciListesi = new MenuItem("Kullanýcý Listesi");
		menuKullaniciListesi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				KullaniciController kullaniciController = (KullaniciController) Arac.getBean(KullaniciController.class);
				kullaniciController.initialize(getMain());
			}
		});
		yoneticiMenu.getItems().addAll(menuKullaniciListesi);
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
