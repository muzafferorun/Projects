package tr.com.muzo.component.test;

import java.util.Arrays;

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
import tr.com.muzo.component.dropDown.DropDown;
import tr.com.muzo.enums.AramaTip;

public class DropDownTest extends Application {
	private DropDown<AramaTip> dropDown = new DropDown<AramaTip>(false, null, "Arama Tip :",
			AramaTip.BASINDA, Arrays.asList(AramaTip.values()));
	private Button tamam = new Button("Tamam");
	private Button degistir = new Button("Degiþtir");
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

		gridPane.add(dropDown, 0, 0, 2, 1);
		gridPane.add(degistir, 0, 1);
		gridPane.add(tamam, 1, 1);
		gridPane.add(sonucLabel, 0, 2);
		gridPane.add(sonuc, 1, 2);

		tamam.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String sonucFormatli = dropDown.getValue().getTanim();
				sonuc.setText(sonucFormatli);
			}
		});

		degistir.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				dropDown.setValue(AramaTip.ESITLIK);
			}
		});

		root.setCenter(gridPane);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(DropDownTest.class);
	}

	@Override
	public void stop() throws Exception {

	}

}
