package tr.com.muzo.component.autoComplete;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class AutoCompletePopupSkin<T> implements Skin<AutoCompletePopup<T>> {

	private AutoCompletePopup<T> autoCompletePopup;
	private BorderPane borderPane;
	private ListView<T> liste = new ListView<T>()  ;

	public AutoCompletePopupSkin(AutoCompletePopup<T> autoCompletePopup , AutoCompleteColumn autoCompleteColumn) {
		this.autoCompletePopup = autoCompletePopup;
		liste.setPlaceholder(new Label("Gösterilecek Kayýt Bulunamadý"));
		liste.getStyleClass().add("auto-complete-popup");
		liste.getStylesheets().add(getClass().getResource("/css/autocompletion.css").toExternalForm());
		liste.setCellFactory(new Callback<ListView<T>, ListCell<T>>(){
            @Override
            public ListCell<T> call(ListView<T> p) {
                ListCell<T> cell = new ListCell<T>(){
                    @Override
                    protected void updateItem(T t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                        	String sonuc = null;
							try {
								sonuc = (String) PropertyUtils.getProperty(t, autoCompleteColumn.getLabelKolonAdi());
							} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
							}
                            setText(sonuc);
                        }
                    }
                };
                return cell;
            }
        });
		liste.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				autoCompletePopup.secimYap(liste.getSelectionModel().getSelectedItem());
			}
		});
		liste.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.ENTER)){
					autoCompletePopup.secimYap(liste.getSelectionModel().getSelectedItem());
				}
			}
		});
		borderPane = new BorderPane();
		borderPane.setCenter(null);
		borderPane.setCenter(liste);
	}

	@Override
	public AutoCompletePopup<T> getSkinnable() {
		return autoCompletePopup;
	}

	@Override
	public Node getNode() {
		return borderPane;
	}

	@Override
	public void dispose() {

	}

	public ListView<T> getListe() {
		return liste;
	}

}