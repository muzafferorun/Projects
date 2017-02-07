package tr.com.muzo.component.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tr.com.muzo.application.util.ApplicationConfiguration;
import tr.com.muzo.application.util.Arac;
import tr.com.muzo.component.autoComplete.AutoComplete;
import tr.com.muzo.db.entity.Kullanici;
import tr.com.muzo.service.kullanici.KullaniciService;

@SpringBootApplication(scanBasePackageClasses = ApplicationConfiguration.class)
public class AutoCompleteTest extends Application {

	private ConfigurableApplicationContext applicationContext;
	private AutoComplete<Kullanici> autoComplete;

	@Override
	public void init() throws Exception {
		applicationContext = SpringApplication.run(AutoCompleteTest.class);
		Arac.setAwesomeFont();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		autoComplete = new AutoComplete<Kullanici>(Kullanici.class);
		KullaniciService kullaniciService = applicationContext.getBean(KullaniciService.class);
		for (int i = 0; 5 > i; i++) {
			Kullanici kullanici = new Kullanici();
			kullanici.setKullaniciAdi("Muzaffer " + i);
			kullanici.setSifre("123");
			kullaniciService.kaydet(kullanici);
		}
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 600, 500);
		GridPane gridPane = new GridPane();
		gridPane.add(autoComplete, 0, 0);
		root.setCenter(gridPane);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(AutoCompleteTest.class);
	}

	@Override
	public void stop() throws Exception {
		if (applicationContext != null) {
			applicationContext.close();
		}
	}

}
