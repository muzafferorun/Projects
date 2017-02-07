package tr.com.muzo.component.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tr.com.muzo.application.util.ApplicationConfiguration;
import tr.com.muzo.application.util.Arac;
import tr.com.muzo.component.base.ButtonActionEvent;
import tr.com.muzo.component.commandButton.CommandButton;
import tr.com.muzo.component.inputText.InputText;
import tr.com.muzo.component.util.AwesomeIcons;

@SpringBootApplication(scanBasePackageClasses = ApplicationConfiguration.class)
public class CommandButtonTest extends Application {

	private ConfigurableApplicationContext applicationContext;

	private CommandButton button1 = new CommandButton("Test");
	private CommandButton button2 = new CommandButton("Test 2", AwesomeIcons.ICON_HOME);
	private InputText inputText = new InputText(true, null, "Zorunlu Alan");
	private InputText inputText2 = new InputText(false, null, "Zorunlu Alan 2");
	
	@Override
	public void init() throws Exception {
		applicationContext = SpringApplication.run(CommandButtonTest.class);
		Arac.setAwesomeFont();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 600, 500);
		GridPane gridPane = new GridPane();
		button1.setPreAction(new EventHandler<ButtonActionEvent>() {
			@Override
			public void handle(ButtonActionEvent event) {
				System.out.println("PreAction da");
			}
		});

		button1.setAction(new EventHandler<ButtonActionEvent>() {

			@Override
			public void handle(ButtonActionEvent event) {
				System.out.println("Action da");
			}
		});

		button2.setProcess("zorunluGrid");
		gridPane.setId("zorunluGrid");
		gridPane.add(button1, 0, 0);
		gridPane.add(button2, 0, 1);
		gridPane.add(inputText, 0, 2);
		gridPane.add(inputText2, 0, 3);
		root.setCenter(gridPane);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(CommandButtonTest.class);
	}

	@Override
	public void stop() throws Exception {
		if (applicationContext != null) {
			applicationContext.close();
		}
	}

}
