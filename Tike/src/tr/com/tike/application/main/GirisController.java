package tr.com.tike.application.main;

import java.io.Serializable;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class GirisController implements Serializable{

	private static final long serialVersionUID = -982489144575760540L;
	
	public GirisController() {
		super();
	}

	@FXML
	private TextField kullaniciAdi;
	@FXML
	private PasswordField sifre;
	@FXML
	private Label kullaniciAdiLabel;
	@FXML
	private Label sifreLabel;
	@FXML
	private Button girisButton;
	
	public void giris(){
		System.out.println("Kullanýcý Adý :" +kullaniciAdi.getText());
		System.out.println("Þifre :" +sifre.getText());
	}
}
