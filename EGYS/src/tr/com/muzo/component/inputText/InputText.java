package tr.com.muzo.component.inputText;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import tr.com.muzo.component.base.ComponentBase;

/**
 * @author Muzaffer
 *
 */
public class InputText extends ComponentBase {

	private TextField textField = new TextField();
	private Label baslik = new Label();
	private GridPane gridPane = new GridPane();

	public InputText() {
		super();
		initialize();
	}

	public InputText(String label) {
		super(label);
		initialize();
	}

	public InputText(boolean required, String requiredMessage) {
		super(required, requiredMessage);
		initialize();
	}

	public InputText(boolean required, String requiredMessage, String label) {
		super(required, requiredMessage, label);
		initialize();
	}

	public InputText(String label, Object value) {
		super(label, value);
		initialize();
	}

	public InputText(boolean required, String requiredMessage, Object value) {
		super(required, requiredMessage, value);
		initialize();
	}

	public InputText(boolean required, String requiredMessage, String label, Object value) {
		super(required, requiredMessage, label, value);
		initialize();
	}

	public void initialize() {
		setMinWidth(300);
		baslik.setMinWidth(140);
		baslik.setMaxWidth(140);
		textField.setMinWidth(150);
		textField.setMaxWidth(150);
		textField.setPromptText(label);
		gridPane.setPadding(new Insets(0, 0, 0, 0));
		if (label != null) {
			baslik.setText(isRequired() ? "* " + getLabel() : getLabel());
			gridPane.add(baslik, 0, 0);
			gridPane.add(textField, 1, 0);
		} else {
			gridPane.add(textField, 0, 0, 2, 1);
		}

		if (value != null) {
			textField.setText((String) value);
		}
		getChildren().removeAll(getChildren());
		getChildren().add(gridPane);
	}

	@Override
	public String getText() {
		if (textField != null) {
			text = textField.getText();
		}
		return text;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
		this.textField.setText((String) value);
	}

	public TextField getTextField() {
		return textField;
	}

	public Label getBaslik() {
		return baslik;
	}

}
