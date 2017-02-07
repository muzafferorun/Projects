package tr.com.muzo.component.dropDown;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import tr.com.muzo.component.base.ComponentBase;

public class DropDown<T> extends ComponentBase {

	private ComboBox<T> comboBox = new ComboBox<T>();
	private Label baslik = new Label();
	private GridPane gridPane = new GridPane();
	private List<T> liste;

	public DropDown() {
		super();
		initialize();
	}

	public DropDown(String label, List<T> liste) {
		super(label);
		this.liste = liste;
		initialize();
	}

	public DropDown(boolean required, String requiredMessage, List<T> liste) {
		super(required, requiredMessage);
		this.liste = liste;
		initialize();
	}

	public DropDown(boolean required, String requiredMessage, String label, List<T> liste) {
		super(required, requiredMessage, label);
		this.liste = liste;
		initialize();
	}

	public DropDown(String label, Object value, List<T> liste) {
		super(label, value);
		this.liste = liste;
		initialize();
	}

	public DropDown(boolean required, String requiredMessage, Object value, List<T> liste) {
		super(required, requiredMessage, value);
		this.liste = liste;
		initialize();
	}

	public DropDown(boolean required, String requiredMessage, String label, Object value, List<T> liste) {
		super(required, requiredMessage, label, value);
		this.liste = liste;
		initialize();
	}

	@SuppressWarnings("unchecked")
	public void initialize() {
		setMinWidth(300);
		baslik.setMinWidth(140);
		baslik.setMaxWidth(140);
		comboBox.setMinWidth(150);
		comboBox.setMaxWidth(150);
		comboBox.setPromptText(label);
		gridPane.setPadding(new Insets(0, 0, 0, 0));
		if (label != null) {
			baslik.setText(isRequired() ? "* " + getLabel() : getLabel());
			gridPane.add(baslik, 0, 0);
			gridPane.add(comboBox, 1, 0);
		} else {
			gridPane.add(comboBox, 0, 0, 2, 1);
		}

		if (value != null) {
			comboBox.setValue((T) value);
		}

		if (liste != null) {
			comboBox.setItems(FXCollections.observableList(liste));
		}

		getChildren().removeAll(getChildren());
		getChildren().add(gridPane);
	}

	@SuppressWarnings("unchecked")
	public T getValue() {
		if (comboBox != null) {
			value = (T) comboBox.getValue();
			return (T) value;
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void setValue(Object value) {
		this.value = value;
		comboBox.setValue((T) value);
	}
	

	public void setListe(List<T> liste) {
		if (liste != null) {
			comboBox.setItems(FXCollections.observableList(liste));
		}
		this.liste = liste;
	}

}
