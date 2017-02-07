package tr.com.muzo.application.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tr.com.muzo.application.giris.GirisController;
import tr.com.muzo.application.singleton.SingletonController;
import tr.com.muzo.application.util.ApplicationConfiguration;
import tr.com.muzo.application.util.Arac;
import tr.com.muzo.component.util.MessageUtil;
import tr.com.muzo.service.kullanici.KullaniciService;

@SpringBootApplication(scanBasePackageClasses=ApplicationConfiguration.class)
public class Main extends Application {
	
	private Stage stage;
	private Scene scene;
	private BorderPane root;
	private boolean kapatIsaretle;
	private ConfigurableApplicationContext applicationContext;
	
	@Override
	public void init() throws Exception {
		applicationContext = SpringApplication.run(Main.class);
		Arac.setAwesomeFont();
	}
	
	@Override
	public void start(Stage primaryStage) {
		Boolean durum = Arac.getAppDurum();
		if (!durum) {
			kapatIsaretle = true;
			Arac.setAppDurum(Boolean.TRUE);
			try {
				stage = primaryStage;
				root = new BorderPane();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.getScene().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
				stage.getIcons().add(new Image("file:resources/image/House-icon.png"));
				GirisController girisController = (GirisController) applicationContext.getBean(GirisController.class);
				girisController.setMain(this);
				girisController.initialize();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			kapatIsaretle = false;
			MessageUtil messageUtil = (MessageUtil) applicationContext.getBean(MessageUtil.class);
			boolean sonuc = messageUtil.hataVerBekle("Uygulama Açýk Olduðu için Yeniden Açýlamaz");
			if (sonuc) {
				primaryStage.close();
			}
		}
	}
	
	@Override
	public void stop() throws Exception {
		if (applicationContext != null) {
        	try {
				if (kapatIsaretle) {
					Arac.setAppDurum(Boolean.FALSE);
					SingletonController singletonController = (SingletonController) applicationContext
							.getBean(SingletonController.class);
					KullaniciService kullaniciService = (KullaniciService) applicationContext
							.getBean(KullaniciService.class);
					kullaniciService.kullaniciIcerdeSetle(singletonController.getKullanici(), Boolean.FALSE);
				}
        		applicationContext.close();
			} catch (Exception e) {
			} 
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getStage() {
		return stage;
	}

	public Scene getScene() {
		return scene;
	}

	public BorderPane getRoot() {
		return root;
	}

	public void setRoot(BorderPane root) {
		this.root = root;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
}
