package tr.com.muzo.application;
	
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.stage.Stage;
import tr.com.muzo.giris.GirisController;

@SpringBootApplication
public class Main extends Application {
	
	private Stage stage;
	private ConfigurableApplicationContext applicationContext;
	
	@Override
	public void init() throws Exception {
		applicationContext = SpringApplication.run(Main.class);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			GirisController girisController = (GirisController) applicationContext.getBean("girisController");
			girisController.setMain(this);
			girisController.initialize();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		if (applicationContext != null) {
        	applicationContext.close();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public Stage getStage() {
		return stage;
	}
	
}