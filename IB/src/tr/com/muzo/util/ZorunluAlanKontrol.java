package tr.com.muzo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

@Service
public class ZorunluAlanKontrol {
	
	@Autowired
	private MessageUtil messageUtil;

	private final String BOS_GECILEMEZ = " boþ geçilemeyen bir alandýr \n"; 
	
	@SuppressWarnings("rawtypes")
	public boolean kontrol(Object... fields){ StringBuffer stringBuffer = new StringBuffer();
		for(Object obje : fields){
			if(obje instanceof TextField){
				String val = ((TextField) obje).getText();
				if(StringUtils.isEmpty(val)){
					stringBuffer.append(((TextField) obje).getAccessibleText());
					stringBuffer.append(BOS_GECILEMEZ);
				}
			}else if(obje instanceof ComboBox){
				Object val = ((ComboBox) obje).getSelectionModel().getSelectedItem();
				if(val == null){
					stringBuffer.append(((TextField) obje).getAccessibleText());
					stringBuffer.append(BOS_GECILEMEZ);
				}
			}
		}
		if(StringUtils.isEmpty(stringBuffer.toString())){
			return false;
		}else{
			messageUtil.uyariGoster("UYARI", stringBuffer.toString());
			return true;
		}
	}
	
}
