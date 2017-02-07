package tr.com.muzo.component.boolenButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import tr.com.muzo.component.base.ComponentBase;
import tr.com.muzo.component.util.AwesomeDude;
import tr.com.muzo.component.util.AwesomeIcons;

public class BooleanButton extends ComponentBase {

	private Button evetButton;
	private Button hayirButton;
	private Label baslik = new Label();
	private GridPane gridPane = new GridPane();

	public BooleanButton() {
		super();
		initialize();
	}

	public BooleanButton(String label) {
		super(label);
		initialize();
	}

	public BooleanButton(boolean required, String requiredMessage) {
		super(required, requiredMessage);
		initialize();
	}

	public BooleanButton(boolean required, String requiredMessage, String label) {
		super(required, requiredMessage, label);
		initialize();
	}

	public BooleanButton(String label, Object value) {
		super(label, value);
		initialize();
	}

	public BooleanButton(boolean required, String requiredMessage, Object value) {
		super(required, requiredMessage, value);
		initialize();
	}

	public BooleanButton(boolean required, String requiredMessage, String label, Object value) {
		super(required, requiredMessage, label, value);
		initialize();
	}

	public BooleanButton(Boolean value) {
		super();
		this.value = value;
		initialize();
	}

	public void initialize() {
		if (this.value == null) {
			this.value = Boolean.TRUE;
		}
		evetButton = AwesomeDude.createIconButton(AwesomeIcons.ICON_OK, "Evet");
		hayirButton = AwesomeDude.createIconButton(AwesomeIcons.ICON_CLOSE, "Hayýr");
		setMinWidth(300);
		baslik.setMinWidth(140);
		baslik.setMaxWidth(140);
		evetButton.setMinWidth(150);
		evetButton.setMaxWidth(150);
		hayirButton.setMinWidth(150);
		hayirButton.setMaxWidth(150);
		gridPane.setPadding(new Insets(0, 0, 0, 0));

		if (label != null) {
			baslik.setText(isRequired() ? "* " + getLabel() : getLabel());
			gridPane.add(baslik, 0, 0);
			if ((Boolean) value) {
				gridPane.add(evetButton, 1, 0);
			} else {
				gridPane.add(hayirButton, 1, 0);
			}
		} else {
			if ((Boolean) value) {
				gridPane.add(evetButton, 0, 0, 2, 1);
			} else {
				gridPane.add(hayirButton, 0, 0, 2, 1);
			}
		}

		evetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				gridPane.getChildren().removeAll(gridPane.getChildren());
				value = Boolean.FALSE;
				if (label != null) {
					baslik.setText(isRequired() ? "* " + getLabel() : getLabel());
					gridPane.add(baslik, 0, 0);
					if ((Boolean) value) {
						gridPane.add(evetButton, 1, 0);
					} else {
						gridPane.add(hayirButton, 1, 0);
					}
				} else {
					if ((Boolean) value) {
						gridPane.add(evetButton, 0, 0, 2, 1);
					} else {
						gridPane.add(hayirButton, 0, 0, 2, 1);
					}
				}
			}
		});

		hayirButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				gridPane.getChildren().removeAll(gridPane.getChildren());
				value = Boolean.TRUE;
				if (label != null) {
					baslik.setText(isRequired() ? "* " + getLabel() : getLabel());
					gridPane.add(baslik, 0, 0);
					if ((Boolean) value) {
						gridPane.add(evetButton, 1, 0);
					} else {
						gridPane.add(hayirButton, 1, 0);
					}
				} else {
					if ((Boolean) value) {
						gridPane.add(evetButton, 0, 0, 2, 1);
					} else {
						gridPane.add(hayirButton, 0, 0, 2, 1);
					}
				}
			}
		});
		getChildren().removeAll(getChildren());
		getChildren().add(gridPane);
	}

	public void setValue(Boolean value) {
		if (value != null) {
			if (value) {
				hayirButton.fire();
			} else {
				evetButton.fire();
			}
		}
	}

}
