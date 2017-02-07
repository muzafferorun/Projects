package tr.com.muzo.component.util;

import java.util.Optional;

import org.springframework.stereotype.Service;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

@Service
public class MessageUtil {
	
	public void uyariGoster(String baslik , String icerik ){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(baslik);
		alert.setHeaderText(baslik);
		alert.setContentText(icerik);
		alert.show();
	}
	
	public void hataVer(String hata){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Hata");
		alert.setHeaderText("Hata");
		alert.setContentText(hata);
		alert.show();
	}
	
	public boolean hataVerBekle(String hata) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Hata");
		alert.setHeaderText("Hata");
		alert.setContentText(hata);
		Optional<ButtonType> buttonType = alert.showAndWait();
		if (buttonType.get() == ButtonType.OK){
			return true;
		}else{
			return true;
		}
	}
	
	public boolean confirmDialog(String title, String header, String uyari) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		if (title != null) {
			alert.setTitle(title);
		}
		if (header != null) {
			alert.setHeaderText(header);
		}
		if (uyari != null) {
			alert.setContentText(uyari);
		}
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}

	}

}
