package tr.com.muzo.component.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tr.com.muzo.application.util.ApplicationConfiguration;
import tr.com.muzo.application.util.Arac;
import tr.com.muzo.component.dataTable.DataTable;
import tr.com.muzo.db.entity.Kullanici;
import tr.com.muzo.service.kullanici.KullaniciService;

@SpringBootApplication(scanBasePackageClasses = ApplicationConfiguration.class)
public class DataTableTest extends Application {

	private ConfigurableApplicationContext applicationContext;

	@Override
	public void init() throws Exception {
		applicationContext = SpringApplication.run(DataTableTest.class);
		Arac.setAwesomeFont();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		KullaniciService kullaniciService = (KullaniciService) applicationContext.getBean(KullaniciService.class);
		for (int i = 0; 105 > i; i++) {
			Kullanici kullanici = new Kullanici();
			kullanici.setKullaniciAdi("Muzaffer -> " + i);
			kullaniciService.kaydet(kullanici);
		}

		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 600, 500);
		DataTable<Kullanici> dataTable = new DataTable<Kullanici>(scene, Kullanici.class);
		root.setCenter(dataTable);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(DataTableTest.class);
	}

	@Override
	public void stop() throws Exception {
		if (applicationContext != null) {
			applicationContext.close();
		}
	}

}
