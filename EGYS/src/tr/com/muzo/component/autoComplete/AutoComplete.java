package tr.com.muzo.component.autoComplete;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import tr.com.muzo.application.db.util.QueryResult;
import tr.com.muzo.application.db.util.SimpleService;
import tr.com.muzo.application.util.Arac;
import tr.com.muzo.component.base.ComponentBase;
import tr.com.muzo.component.util.AwesomeDude;
import tr.com.muzo.component.util.AwesomeIcons;
import tr.com.muzo.db.entity.BasePojo;

public class AutoComplete<T> extends ComponentBase {

	private TextField textField = new TextField();
	private Label baslik = new Label();
	private GridPane gridPane = new GridPane();
	private Button button = AwesomeDude.createIconButton(AwesomeIcons.ICON_CARET_DOWN);
	private Class<T> clazz;
	private AutoCompletePopup<T> popupControl ;
	private T seciliObje;
	private SimpleService<T> simpleService ;

	@SuppressWarnings("unchecked")
	public AutoComplete(Class<T> clazz) {
		super();
		this.clazz = clazz;
		simpleService = (SimpleService<T>) Arac.getBean(Arac.getServiceClassName(this.clazz));
		initialize();
	}

	public void initialize() {
		popupControl = new AutoCompletePopup<T>(this , clazz);
		setMinWidth(300);
		baslik.setMinWidth(140);
		baslik.setMaxWidth(140);
		textField.setMinWidth(150);
		textField.setMaxWidth(150);
		HBox hBox = new HBox(0);
		hBox.getChildren().add(textField);
		hBox.getChildren().add(button);
		hBox.setPadding(new Insets(0, 0, 0, 0));
		if (label == null) {
			gridPane.add(hBox, 0, 0, 1, 1);
		} else {
			baslik.setText(label);
			textField.setPromptText(label);
			gridPane.add(baslik, 0, 0);
			gridPane.add(hBox, 1, 0);
		}
		getChildren().removeAll(getChildren());
		getChildren().add(gridPane);

		textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(!event.getCode().equals(KeyCode.DOWN) && !event.getCode().equals(KeyCode.UP) &&
						!event.getCode().equals(KeyCode.RIGHT) && !event.getCode().equals(KeyCode.LEFT) &&
						!event.getCode().equals(KeyCode.ENTER)){
					if(textField.getText() != null && textField.getText().length() > 3){
						try {
							T data = clazz.newInstance();
							AutoCompleteColumn autoCompleteKolon = ((BasePojo) data).getAutoCompleteColumn();
							String deger = textField.getText();
							if (deger == null) {
								deger = "";
							}
							for (String kolonAdi : autoCompleteKolon.getKolonAdi()) {
								PropertyUtils.setProperty(data, kolonAdi, deger);
							}
							QueryResult<T> queryResult = simpleService.getListe(data, 1, 10);
							if(queryResult.getToplamKayitSayisi() == 1){
								seciliObje = queryResult.getListe().get(0);
								textField.setText((String)PropertyUtils.getProperty(seciliObje, autoCompleteKolon.getLabelKolonAdi()));
							}else{
								popupControl.showPopup(queryResult);
							}
						} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						}
					}
				}else if(event.getCode().equals(KeyCode.ENTER)){
					popupControl.secimYap(popupControl.getAutoCompletePopupSkin().getListe().getSelectionModel().getSelectedItem());
				}else{
					event.consume();
				}
			}
		});

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					T data = clazz.newInstance();
					AutoCompleteColumn autoCompleteKolon = ((BasePojo) data).getAutoCompleteColumn();
					if (autoCompleteKolon != null) {
						String deger = textField.getText();
						if (deger == null) {
							deger = "";
						}
						for (String kolonAdi : autoCompleteKolon.getKolonAdi()) {
							PropertyUtils.setProperty(data, kolonAdi, deger);
						}
						QueryResult<T> queryResult = simpleService.getListe(data, 1, 10);
						if(queryResult.getToplamKayitSayisi() == 1){
							seciliObje = queryResult.getListe().get(0);
							textField.setText((String)PropertyUtils.getProperty(seciliObje, autoCompleteKolon.getLabelKolonAdi()));
						}else{
							popupControl.showPopup(queryResult);
						}
					}
				} catch (InstantiationException | IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
				}
			}
		});
	}

	public TextField getTextField() {
		return textField;
	}

	public T getSeciliObje() {
		return seciliObje;
	}

	public void setSeciliObje(T seciliObje) {
		this.seciliObje = seciliObje;
	}

}
