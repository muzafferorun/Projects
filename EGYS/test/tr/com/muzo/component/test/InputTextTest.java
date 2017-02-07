package tr.com.muzo.component.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tr.com.muzo.application.util.ApplicationConfiguration;
import tr.com.muzo.application.util.Arac;
import tr.com.muzo.component.inputText.InputText;
import tr.com.muzo.component.util.ZorunluAlanKontrol;

@SpringBootApplication(scanBasePackageClasses = ApplicationConfiguration.class)
public class InputTextTest extends Application {

	private ConfigurableApplicationContext applicationContext;

	private GridPane gridPane = new GridPane();
	private Button button = new Button("Tamam");
	private InputText textFiled1 = new InputText();
	private InputText textFiled2 = new InputText("Baþlýklý");
	private InputText textFiled3 = new InputText(true, "Zorunlu Alan Bu Alan");
	private InputText textFiled4 = new InputText(true, "Zorunlu Alan Bu Alan", "Zorunlu Alan ");
	private InputText textFiled5 = new InputText(true, null, "Zorunlu Alan Mesajý Yok");
	private InputText textFiled6 = new InputText(true, null, null);

	private Label sonuc1 = new Label();
	private Label sonuc2 = new Label();
	private Label sonuc3 = new Label();
	private Label sonuc4 = new Label();
	private Label sonuc5 = new Label();
	private Label sonuc6 = new Label();

	@Override
	public void init() throws Exception {
		applicationContext = SpringApplication.run(InputTextTest.class);
		Arac.setAwesomeFont();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ZorunluAlanKontrol zorunluAlanKontrol = (ZorunluAlanKontrol) applicationContext
				.getBean(ZorunluAlanKontrol.class);
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 600, 500);

		gridPane.add(textFiled1, 0, 0);
		gridPane.add(textFiled2, 0, 1);
		gridPane.add(textFiled3, 0, 2);
		gridPane.add(textFiled4, 0, 3);
		gridPane.add(textFiled5, 0, 4);
		gridPane.add(textFiled6, 0, 5);
		gridPane.add(sonuc1, 1, 0);
		gridPane.add(sonuc2, 1, 1);
		gridPane.add(sonuc3, 1, 2);
		gridPane.add(sonuc4, 1, 3);
		gridPane.add(sonuc5, 1, 4);
		gridPane.add(sonuc6, 1, 5);
		gridPane.add(button, 1, 6);

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				boolean durum = zorunluAlanKontrol.kontrol(gridPane);
				if (durum) {
					sonuc1.setText(textFiled1.getText());
					sonuc2.setText(textFiled2.getText());
					sonuc3.setText(textFiled3.getText());
					sonuc4.setText(textFiled4.getText());
					sonuc5.setText(textFiled5.getText());
					sonuc6.setText(textFiled6.getText());
				}
			}
		});

		root.setCenter(gridPane);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		Application.launch(InputTextTest.class);
	}

	@Override
	public void stop() throws Exception {
		if (applicationContext != null) {
			applicationContext.close();
		}
	}

}
