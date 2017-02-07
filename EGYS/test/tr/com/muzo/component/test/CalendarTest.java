package tr.com.muzo.component.test;

import java.time.LocalDate;
import java.util.Date;

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
import tr.com.muzo.component.calendar.Calendar;

public class CalendarTest extends Application {
	private Label label = new Label("Tarih : ");
	private Calendar calendar = new Calendar(true, null, "Test Calendar", null);
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
		gridPane.add(calendar, 1, 0);
		gridPane.add(degistir, 0, 1);
		gridPane.add(tamam, 1, 1);
		gridPane.add(sonucLabel, 0, 2);
		gridPane.add(sonuc, 1, 2);

		tamam.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String sonucFormatli = calendar.getText();
				Date sonucGercek = calendar.getDate();
				sonuc.setText(sonucGercek + "--->" + sonucFormatli);
			}
		});

		degistir.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				calendar.setValue(LocalDate.now());
			}
		});

		root.setCenter(gridPane);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(CalendarTest.class);
	}

	@Override
	public void stop() throws Exception {

	}

}
