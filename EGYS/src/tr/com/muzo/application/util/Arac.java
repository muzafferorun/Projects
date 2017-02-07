package tr.com.muzo.application.util;

import java.util.Arrays;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tr.com.muzo.application.main.Main;
import tr.com.muzo.component.base.ButtonActionEvent;
import tr.com.muzo.component.dataTable.EntityProperty;
import tr.com.muzo.component.util.VeriData;
import tr.com.muzo.enums.IslemTip;
import tr.com.muzo.enums.VeriTip;

public class Arac {

	public static Boolean getAppDurum() {
		Preferences prefs = Preferences.userRoot().node(Sabit.KAYIT_DEFTERI_PATH);
		return prefs.getBoolean(Sabit.KAYIT_DEFTERI_KEY, false);
	}

	public static void setAppDurum(Boolean durum) {
		Preferences prefs = Preferences.userRoot().node(Sabit.KAYIT_DEFTERI_PATH);
		prefs.putBoolean(Sabit.KAYIT_DEFTERI_KEY, durum);
	}

	public static Node getNode(EntityProperty entityProperty, Object val) {
		if (entityProperty.getVeriTip().equals(VeriTip.TEXT)) {
			TextField textField = new TextField();
			textField.setPromptText(entityProperty.getPromptText());
			textField.setId(entityProperty.getKolonAdi());
			if (val != null) {
				textField.setText((String) val);
			}
			return textField;
		} else if (entityProperty.getVeriTip().equals(VeriTip.PASSWORD)) {
			PasswordField passwordField = new PasswordField();
			passwordField.setPromptText(entityProperty.getPromptText());
			passwordField.setId(entityProperty.getKolonAdi());
			if (val != null) {
				passwordField.setText((String) val);
			}
			return passwordField;
		} else if (entityProperty.getVeriTip().equals(VeriTip.DROP_DOWN)) {
			ComboBox<VeriData> comboBox = new ComboBox<VeriData>(
					FXCollections.observableArrayList(Arrays.asList(entityProperty.getVeriData())));
			comboBox.setId(entityProperty.getKolonAdi());
			for (VeriData veriData : entityProperty.getVeriData()) {
				if (val != null) {
					if (veriData.getVeriData() instanceof Boolean) {
						if ((Boolean) veriData.getVeriData() && (Boolean) val) {
							comboBox.setValue(veriData);
							break;
						} else if (!(Boolean) veriData.getVeriData() && !(Boolean) val) {
							comboBox.setValue(veriData);
							break;
						}
					}
				} else {
					if (veriData.getVeriData() == null) {
						comboBox.setValue(veriData);
						break;
					}
				}
			}
			return comboBox;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Object getNodeValue(Node node, EntityProperty[] entityProperties) {
		for (EntityProperty entityProperty : entityProperties) {
			if (entityProperty.getKolonAdi().equals(node.getId())) {
				if (entityProperty.getVeriTip().equals(VeriTip.TEXT)) {
					TextField textField = (TextField) node;
					return textField.getText();
				} else if (entityProperty.getVeriTip().equals(VeriTip.PASSWORD)) {
					PasswordField passwordField = (PasswordField) node;
					return passwordField.getText();
				} else if (entityProperty.getVeriTip().equals(VeriTip.DROP_DOWN)) {
					ComboBox<VeriData> comboBox = (ComboBox<VeriData>) node;
					VeriData veriData = comboBox.getValue();
					if (veriData != null) {
						return veriData.getVeriData();
					}
				}
			}
		}
		return null;
	}

	public static boolean isIslemTip(IslemTip islemTip, EntityProperty entityProperty) {
		if (islemTip.equals(IslemTip.YENI) && entityProperty.isEklenebilir()) {
			return true;
		} else if (islemTip.equals(IslemTip.DETAY) && entityProperty.isDetayGoruntulenebilir()) {
			return true;
		} else if (islemTip.equals(IslemTip.GUNCELLE) && entityProperty.isGuncellenebilir()) {
			return true;
		}
		return false;
	}

	public static String getServiceClassName(Class<?> clazz) {
		String className = clazz.getSimpleName() + "Service";
		className = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
		return className;
	}

	public static void setAwesomeFont() {
		Font.loadFont(Main.class.getResource("/fonts/fontawesome-webfont.ttf").toExternalForm(), 12);
	}

	public static Border zorunluAlanBorder() {
		return new Border(
				new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.6)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getBean(Class bean) {
		return ApplicationContextProvider.getContext().getBean(bean);
	}

	public static Object getBean(String beanName) {
		return ApplicationContextProvider.getContext().getBean(beanName);
	}

	private static EventType<ButtonActionEvent> PRE_ACTION;
	private static EventType<ButtonActionEvent> LAST_ACTION;

	public static EventType<ButtonActionEvent> getPreAction() {
		if (PRE_ACTION == null) {
			PRE_ACTION = new EventType<ButtonActionEvent>("PRE_ACTION");
		}
		return PRE_ACTION;
	}

	public static EventType<ButtonActionEvent> getLastAction() {
		if (LAST_ACTION == null) {
			LAST_ACTION = new EventType<ButtonActionEvent>("LAST_ACTION");
		}
		return LAST_ACTION;
	}

}
