package tr.com.muzo.component.autoComplete;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import javafx.stage.Window;
import tr.com.muzo.application.db.util.QueryResult;
import tr.com.muzo.db.entity.BasePojo;

public class AutoCompletePopup<T> extends PopupControl {

	private AutoCompletePopupSkin<T> autoCompletePopupSkin;
	private AutoComplete<T> autoComplete;
	private AutoCompleteColumn autoCompleteColumn;

	public AutoCompletePopup(AutoComplete<T> autoComplete , Class<T> clazz) {
		this.autoComplete = autoComplete;
		try {
			T data = clazz.newInstance();
			autoCompleteColumn = ((BasePojo) data).getAutoCompleteColumn();
		} catch (InstantiationException | IllegalAccessException e) {
		}
		this.setAutoFix(true);
		this.setAutoHide(true);
		this.setHideOnEscape(true);
	}

	@Override
	protected Skin<AutoCompletePopup<T>> createDefaultSkin() {
		autoCompletePopupSkin = new AutoCompletePopupSkin<T>(this , autoCompleteColumn);
		return autoCompletePopupSkin;
	}

	public void showPopup(QueryResult<T> queryResult){
		if(autoCompletePopupSkin == null){
			createDefaultSkin();
		}
		autoCompletePopupSkin.getListe().getItems().removeAll(autoCompletePopupSkin.getListe().getItems());
		autoCompletePopupSkin.getListe().setItems(FXCollections.observableArrayList(queryResult.getListe()));
		autoCompletePopupSkin.getListe().prefWidth(40);
		autoCompletePopupSkin.getListe().maxWidth(40);
		autoCompletePopupSkin.getListe().minWidth(40);
		autoCompletePopupSkin.getListe().prefHeight(100);
		autoCompletePopupSkin.getListe().maxHeight(100);
		autoCompletePopupSkin.getListe().minHeight(100);
		autoCompletePopupSkin.getListe().prefHeightProperty().bind(Bindings.min(10, Bindings.size(autoCompletePopupSkin.getListe().getItems())).multiply(24).add(1));
		autoCompletePopupSkin.getListe().prefWidthProperty().bind(autoComplete.getTextField().widthProperty());
		if(queryResult.getListe().size() > 0){
			autoCompletePopupSkin.getListe().getFocusModel().focus(0);
			autoCompletePopupSkin.getListe().getSelectionModel().select(0);
		}
		Window window = autoComplete.getScene().getWindow();
		double x = window.getX() + autoComplete.localToScene(0, 0).getX() + autoComplete.getScene().getX();
		double y = window.getY() + autoComplete.localToScene(0, 0).getY() + autoComplete.getScene().getY() + 24;
		show(window, x, y);
	}

	public void secimYap(T t){
		if(t != null){
			String text = null;
			try {
				text = (String) PropertyUtils.getProperty(t, autoCompleteColumn.getLabelKolonAdi());
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			}
			autoComplete.getTextField().setText(text);
			autoComplete.setSeciliObje(t);
		}
		this.hide();
	}

	public AutoCompletePopupSkin<T> getAutoCompletePopupSkin() {
		return autoCompletePopupSkin;
	}

}