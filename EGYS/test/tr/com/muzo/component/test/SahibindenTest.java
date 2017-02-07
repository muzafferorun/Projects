package tr.com.muzo.component.test;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tr.com.muzo.application.util.Arac;

public class SahibindenTest extends Application {

	private BorderPane root;
	private BrowserView browserView;
	private Browser browser;

	@Override
	public void init() throws Exception {
		System.setProperty("teamdev.license.info", "true");
		Arac.setAwesomeFont();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new BorderPane();
		Scene scene = new Scene(root, 1024, 768);
		browser = new Browser();
		browserView = new BrowserView(browser);
		root.setCenter(browserView);
		browser.loadURL("https://www.sahibinden.com/");
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(SahibindenTest.class);
	}

}
