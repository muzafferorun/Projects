package tr.com.muzo.component.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import tr.com.muzo.component.base.ComponentBase;

public class Calendar extends ComponentBase {

	private String pattern;
	private String text;
	private DatePicker datePicker = new DatePicker();
	private Label baslik = new Label();
	private GridPane gridPane = new GridPane();

	public Calendar() {
		super();
		initialize();
	}

	public Calendar(String label) {
		super(label);
		initialize();
	}

	public Calendar(boolean required, String requiredMessage) {
		super(required, requiredMessage);
		initialize();
	}

	public Calendar(boolean required, String requiredMessage, String label) {
		super(required, requiredMessage, label);
		initialize();
	}

	public Calendar(String label, Object value) {
		super(label, value);
		initialize();
	}

	public Calendar(boolean required, String requiredMessage, Object value) {
		super(required, requiredMessage, value);
		initialize();
	}

	public Calendar(boolean required, String requiredMessage, String label, Object value) {
		super(required, requiredMessage, label, value);
		initialize();
	}

	public Calendar(Boolean value) {
		super();
		this.value = value;
		initialize();
	}

	public Calendar(String pattern, LocalDate localDate) {
		super();
		this.pattern = pattern;
		initialize();
	}

	public void initialize() {
		if (pattern == null) {
			pattern = "dd/MM/yyyy";
		}
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					text = dateFormatter.format(date);
				} else {
					text = "";
				}
				return text;
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};
		datePicker.setConverter(converter);
		datePicker.setShowWeekNumbers(true);

		setMinWidth(300);
		baslik.setMinWidth(140);
		baslik.setMaxWidth(140);
		datePicker.setMinWidth(150);
		datePicker.setMaxWidth(150);
		datePicker.setPromptText(label);
		gridPane.setPadding(new Insets(0, 0, 0, 0));
		if (label != null) {
			baslik.setText(isRequired() ? "* " + getLabel() : getLabel());
			gridPane.add(baslik, 0, 0);
			gridPane.add(datePicker, 1, 0);
		} else {
			gridPane.add(datePicker, 0, 0, 2, 1);
		}

		if (value != null) {
			datePicker.setValue((LocalDate) value);
		}
		getChildren().removeAll(getChildren());
		getChildren().add(gridPane);
	}

	public String getText() {
		return text;
	}

	public Date getDate() {
		if (text != null) {
			try {
				return new SimpleDateFormat(pattern).parse(text);
			} catch (ParseException e) {
			}
		}
		return null;
	}

	public void setValue(Object value) {
		datePicker.setValue((LocalDate) value);
	}

}
