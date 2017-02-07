package tr.com.muzo.component.test;

import java.math.BigDecimal;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tr.com.muzo.application.util.Arac;
import tr.com.muzo.component.inputNumber.InputNumber;

public class InputNumberTest extends Application {
	private InputNumber inputNumber = new InputNumber();
	private Label label = new Label("Sayý : ");
	private Button tamam = new Button("Tamam");
	private Button degistir = new Button("Deðiþtir");
	private Label sonucLabel = new Label("Sonuç");
	private Label sonuc = new Label();
	
	@Override
	public void init() throws Exception {
		Arac.setAwesomeFont();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 600, 500);
		GridPane gridPane = new GridPane();

		gridPane.add(label, 0, 0);
		gridPane.add(inputNumber, 1, 0);
		gridPane.add(degistir, 0, 1);
		gridPane.add(tamam, 1, 1);
		gridPane.add(sonucLabel, 0, 2);
		gridPane.add(sonuc, 1, 2);

		tamam.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String sonucFormatli = inputNumber.getText();
				String sonucGercek = null;
				if (inputNumber.getNumber() != null) {
					sonucGercek = String.valueOf(inputNumber.getNumber());
				}
				sonuc.setText(sonucGercek + "--->" + sonucFormatli);
			}
		});

		degistir.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				inputNumber.setValue(new BigDecimal("123123.12123123"));
			}
		});

		root.setCenter(gridPane);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(InputNumberTest.class);
	}

	@Override
	public void stop() throws Exception {

	}

}
