package tr.com.muzo.util;

import org.springframework.stereotype.Service;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
		alert.show();;
	}
	
	
}
