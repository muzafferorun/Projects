package tr.com.muzo.giris;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import tr.com.muzo.application.util.BaseController;
import tr.com.muzo.index.IndexController;
import tr.com.muzo.service.GirisService;
import tr.com.muzo.util.MessageUtil;
import tr.com.muzo.util.ZorunluAlanKontrol;


@Controller("girisController")
public class GirisController extends BaseController{
	
	private static final long serialVersionUID = 2358377746268447004L;

	@Autowired
	private ZorunluAlanKontrol zorunluAlanKontrol;

	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private GirisService girisService;
	
	@Autowired
	private IndexController indexController;
	
	private Label kullaniciAdiLabel = new Label("Kullanýcý Adý : ");
	private TextField kullaniciAdiInput = new TextField();
	private Label sifreLabel = new Label("Þifre :");
	private PasswordField sifreInput = new PasswordField();
	private Button girisButon = new Button("Giriþ");

	public void initialize(){
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root , 300 , 120);
		scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		getMain().getStage().setScene(scene);
		GridPane gridPane = new GridPane();
		gridPane.setHgap(50);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(5));
		kullaniciAdiInput.setAccessibleText("Kullanýcý Adý");
		sifreInput.setAccessibleText("Þifre");
		gridPane.add(kullaniciAdiLabel , 0 ,0);
		gridPane.add(kullaniciAdiInput , 1 ,0);
		gridPane.add(sifreLabel , 0 ,1);
		gridPane.add(sifreInput , 1 ,1);
		girisButon.setMinWidth(150);
		gridPane.add(girisButon , 1 ,2);
		root.setCenter(gridPane);
		getMain().getStage().centerOnScreen();
		getMain().getStage().show();
		addEventHandler(KeyCode.ENTER, girisButon);
		girisButon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean hataVar = zorunluAlanKontrol.kontrol(new Object[]{kullaniciAdiInput , sifreInput});
				if(!hataVar){
					hataVar = girisService.giris(kullaniciAdiInput.getText(), sifreInput.getText());
					if(hataVar){
						messageUtil.hataVer("Kullanýcý Adý ya da Þifre hatalý");
					}else{
						getMain().getStage().close();
						indexController.setMain(getMain());
						indexController.initialize();
					}
				}
			}
		});
	}
	
	
}
