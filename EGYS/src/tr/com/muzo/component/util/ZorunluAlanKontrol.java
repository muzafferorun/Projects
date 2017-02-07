package tr.com.muzo.component.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import tr.com.muzo.application.util.Arac;
import tr.com.muzo.component.base.ComponentBase;
import tr.com.muzo.component.inputText.InputText;

@Service
public class ZorunluAlanKontrol {

	@Autowired
	private MessageUtil messageUtil;

	private final String BOS_GECILEMEZ = " boþ geçilemeyen bir alandýr \n";

	@SuppressWarnings("rawtypes")
	public boolean kontrol(Object... fields) {
		StringBuffer stringBuffer = new StringBuffer();
		for (Object obje : fields) {
			if (obje instanceof TextField) {
				String val = ((TextField) obje).getText();
				if (StringUtils.isEmpty(val)) {
					stringBuffer.append(((TextField) obje).getAccessibleText());
					stringBuffer.append(BOS_GECILEMEZ);
				}
			} else if (obje instanceof ComboBox) {
				Object val = ((ComboBox) obje).getSelectionModel().getSelectedItem();
				if (val == null) {
					stringBuffer.append(((TextField) obje).getAccessibleText());
					stringBuffer.append(BOS_GECILEMEZ);
				}
			}
		}
		if (StringUtils.isEmpty(stringBuffer.toString())) {
			return false;
		} else {
			messageUtil.uyariGoster("UYARI", stringBuffer.toString());
			return true;
		}
	}

	public boolean kontrol(Pane panel) {
		StringBuffer stringBuffer = new StringBuffer();
		if(panel == null){
			return true;
		}
		for (Node node : panel.getChildren()) {
			if (node instanceof ComponentBase) {
				ComponentBase baseObje = ((ComponentBase) node);
				if (baseObje.isRequired()) {
					String data = baseObje.getText();
					if (StringUtils.isEmpty(data)) {
						if (baseObje.getRequiredMessage() == null) {
							stringBuffer.append(baseObje.getLabel() == null ? "" : baseObje.getLabel());
							stringBuffer.append(BOS_GECILEMEZ);
						} else {
							stringBuffer.append(baseObje.getRequiredMessage() + "\n");
						}
						if (baseObje instanceof InputText) {
							((InputText) baseObje).getTextField().setBorder(Arac.zorunluAlanBorder());
							((InputText) baseObje).getBaslik().setStyle("-fx-text-fill: red");
						}
					} else {
						if (baseObje instanceof InputText) {
							((InputText) baseObje).getTextField().setBorder(null);
							((InputText) baseObje).getBaslik().setStyle("-fx-text-fill: black");
						}
					}
				}
			}
		}
		
		if (StringUtils.isEmpty(stringBuffer.toString())) {
			return false;
		} else {
			messageUtil.uyariGoster("UYARI", stringBuffer.toString());
			return true;
		}
	}

	public boolean kontrol(ObservableList<ComponentBase> components) {
		StringBuffer stringBuffer = new StringBuffer();
		for (ComponentBase baseObje : components) {
			if (baseObje.isRequired()) {
				Object data = baseObje.getValue();
				if (data == null) {
					if (baseObje.getRequiredMessage() == null) {
						stringBuffer.append(baseObje.getLabel() == null ? "" : baseObje.getLabel());
						stringBuffer.append(BOS_GECILEMEZ);
					} else {
						stringBuffer.append(baseObje.getRequiredMessage() + "\n");
					}
					if (baseObje instanceof InputText) {
						((InputText) baseObje).getTextField().setBorder(Arac.zorunluAlanBorder());
						((InputText) baseObje).getBaslik().setStyle("-fx-text-fill: red");
					}
				} else {
					if (baseObje instanceof InputText) {
						((InputText) baseObje).getTextField().setBorder(null);
						((InputText) baseObje).getBaslik().setStyle("-fx-text-fill: black");
					}
				}
			}
		}
		if (StringUtils.isEmpty(stringBuffer.toString())) {
			return true;
		} else {
			messageUtil.uyariGoster("UYARI", stringBuffer.toString());
			return false;
		}
	}

}
