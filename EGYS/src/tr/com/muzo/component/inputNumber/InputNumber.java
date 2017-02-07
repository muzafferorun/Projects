package tr.com.muzo.component.inputNumber;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import tr.com.muzo.component.base.ComponentBase;

public class InputNumber extends ComponentBase {

	private String[] sayilar = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	private TextField textField = new TextField();
	private Label baslik = new Label();
	private GridPane gridPane = new GridPane();
	private BigDecimal number = BigDecimal.ZERO;
	private int degerKusurat;
	private BigInteger degerTam;
	private NumberFormat numberFormat = DecimalFormat.getInstance(new Locale("tr", "TR"));
	private boolean integerOnly;
	private int caretPosition;

	public InputNumber() {
		super();
		initialize();
	}

	public InputNumber(String label) {
		super(label);
		initialize();
	}

	public InputNumber(boolean required, String requiredMessage) {
		super(required, requiredMessage);
		initialize();
	}

	public InputNumber(boolean required, String requiredMessage, String label) {
		super(required, requiredMessage, label);
		initialize();
	}

	public InputNumber(boolean required, String requiredMessage, Object value) {
		super(required, requiredMessage, value);
		initialize();
	}

	public InputNumber(boolean required, String requiredMessage, String label, Object value) {
		super(required, requiredMessage, label, value);
		initialize();
	}

	public void initialize() {
		integerOnly = false;
		degerTam = BigInteger.ZERO;
		degerKusurat = 0;
		numberFormat.setGroupingUsed(true);
		numberFormat.setParseIntegerOnly(false);
		numberFormat.setMaximumIntegerDigits(16);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);
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
			textField.setText(numberFormat.format((BigDecimal) value));
			number = (BigDecimal) value;
		}
		
		textField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
	         public void handle( KeyEvent t ) {
				caretPosition = textField.getCaretPosition();
				int virgulPozisyon = textField.getText().indexOf(",");
				if (!Arrays.asList(sayilar).contains(t.getCharacter())) {
					if (t.getCharacter().equals("\b") && caretPosition != 0) {
						if (virgulPozisyon == caretPosition) {
							if (("" + degerTam).length() > 3) {
								String sol = ("" + degerTam).substring(0,
										caretPosition - getNoktaSayi(("" + degerTam).length()));
								String sag = ("" + degerTam).substring(
										caretPosition + 1 - getNoktaSayi(("" + degerTam).length()),
										("" + degerTam).length());
								degerTam = new BigInteger(sol + sag);
								number = new BigDecimal(degerTam + "." + degerKusurat);
								textField.setText(numberFormat.format(number));
								textField.positionCaret(
										("" + degerTam).length() + getNoktaSayi(("" + degerTam).length()));
							} else {
								String sol = ("" + degerTam).substring(0, caretPosition);
								String sag = ("" + degerTam).substring(caretPosition + 1, ("" + degerTam).length());
								if (sag.equals("") && sol.equals("")) {
									sol = "0";
									caretPosition++;
								}
								degerTam = new BigInteger(sol + sag);
								number = new BigDecimal(degerTam + "." + degerKusurat);
								textField.setText(numberFormat.format(number));
								textField.positionCaret(caretPosition);
							}
						} else if (virgulPozisyon > caretPosition) {
							String sol = ("" + degerTam).substring(0,
									caretPosition - 1);
							String sag = ("" + degerTam).substring(
									caretPosition,
									("" + degerTam).length());
							degerTam = new BigInteger(sol + sag);
							number = new BigDecimal(degerTam + "." + degerKusurat);
							textField.setText(numberFormat.format(number));
							textField.positionCaret(caretPosition);
						} else if (virgulPozisyon < caretPosition) {
							int kusuratCaret = caretPosition - virgulPozisyon;
							if (kusuratCaret == 2) {
								degerKusurat = new Integer(("" + degerKusurat).substring(0, 1));
								number = new BigDecimal(degerTam + "." + degerKusurat);
								textField.setText(numberFormat.format(number));
								textField.positionCaret(caretPosition);
							} else if (kusuratCaret == 1) {
								if (("" + degerKusurat).length() == 1) {
									degerKusurat = 0;
								} else if (("" + degerKusurat).length() == 2) {
									degerKusurat = new Integer(("" + degerKusurat).substring(1, 2));
									number = new BigDecimal(degerTam + "." + degerKusurat);
									textField.setText(numberFormat.format(number));
									textField.positionCaret(caretPosition);
								}
							}
						}
						t.consume();
					} else if (t.getCharacter().equals(",")) {
						if (virgulPozisyon == -1) {
							number = BigDecimal.ZERO;
							degerTam = BigInteger.ZERO;
							degerKusurat = 0;
							number = new BigDecimal(degerTam + "." + degerKusurat);
							textField.setText(numberFormat.format(number));
							textField.positionCaret(1);
						} else {
							textField.positionCaret(virgulPozisyon + 1);
						}
						t.consume();
					} else {
						t.consume();
					}
				} else {
					if (virgulPozisyon == -1) {
						degerTam = new BigInteger(t.getCharacter());
						number = new BigDecimal(degerTam + "." + degerKusurat);
						textField.setText(numberFormat.format(number));
						textField.positionCaret(caretPosition + 1);
					} else if (virgulPozisyon != -1 && number.compareTo(BigDecimal.ZERO) == 0) {
						degerTam = new BigInteger(t.getCharacter());
						number = new BigDecimal(degerTam + "." + degerKusurat);
						textField.setText(numberFormat.format(number));
						textField.positionCaret(caretPosition);
					} else {
						if (("" + degerTam).length() != 16) {
							if (virgulPozisyon == caretPosition) {
								degerTam = new BigInteger(degerTam + t.getCharacter());
								number = new BigDecimal(degerTam + "." + degerKusurat);
								textField.setText(numberFormat.format(number));
								if (("" + degerTam).length() > 3) {
									textField.positionCaret(
											("" + degerTam).length() + getNoktaSayi(("" + degerTam).length()));
								} else {
									textField.positionCaret(caretPosition + 1);
								}
							} else if (caretPosition == 0) {
								degerTam = new BigInteger(t.getCharacter() + degerTam);
								number = new BigDecimal(degerTam + "." + degerKusurat);
								textField.setText(numberFormat.format(number));
								if (("" + degerTam).length() > 3) {
									textField.positionCaret(
											("" + degerTam).length() + getNoktaSayi(("" + degerTam).length()));
								} else {
									textField.positionCaret(caretPosition + 1);
								}
							} else if (virgulPozisyon > caretPosition) {
								String sol = ("" + degerTam).substring(0, caretPosition - 1);
								String sag = ("" + degerTam).substring(caretPosition - 1, ("" + degerTam).length());
								degerTam = new BigInteger(sol + t.getCharacter() + sag);
								number = new BigDecimal(degerTam + "." + degerKusurat);
								textField.setText(numberFormat.format(number));
								textField.positionCaret(caretPosition + 1);
							} else if (virgulPozisyon < caretPosition) {
								if (degerKusurat == 0) {
									degerKusurat = new Integer(t.getCharacter());
									number = new BigDecimal(degerTam + "." + degerKusurat);
									textField.setText(numberFormat.format(number));
									textField.positionCaret(virgulPozisyon + 2);
								} else if (("" + degerKusurat).length() == 1) {
									degerKusurat = new Integer(degerKusurat + t.getCharacter());
									number = new BigDecimal(degerTam + "." + degerKusurat);
									textField.setText(numberFormat.format(number));
									textField.positionCaret(virgulPozisyon + 3);
								}

							}
						} else if (("" + degerTam).length() == 16 && degerKusurat == 0) {
							degerKusurat = new Integer(t.getCharacter() + "0");
							number = new BigDecimal(degerTam + "." + degerKusurat);
							textField.setText(numberFormat.format(number));
							textField.positionCaret(virgulPozisyon + 2);
						}
					}
					t.consume();
				}
	          }
	       });
		
		getChildren().removeAll(getChildren());
		getChildren().add(gridPane);
	}

	public BigDecimal getNumber() {
		return number;
	}

	@Override
	public String getText() {
		if (textField != null) {
			text = textField.getText();
		}
		return text;
	}

	public void setIntegerOnly(boolean integerOnly) {
		if (integerOnly) {
			numberFormat.setGroupingUsed(false);
			numberFormat.setParseIntegerOnly(true);
			numberFormat.setMaximumIntegerDigits(16);
			numberFormat.setMinimumFractionDigits(0);
			numberFormat.setMaximumFractionDigits(0);
		} else {
			numberFormat.setGroupingUsed(true);
			numberFormat.setParseIntegerOnly(false);
			numberFormat.setMaximumIntegerDigits(16);
			numberFormat.setMinimumFractionDigits(2);
			numberFormat.setMaximumFractionDigits(2);
		}
		if (number != null) {
			textField.setText(numberFormat.format(number));
		}
		this.integerOnly = integerOnly;
	}

	public boolean isIntegerOnly() {
		return integerOnly;
	}

	public int getNoktaSayi(int length) {
		if (length > 0 && length < 4) {
			return 0;
		} else if (length > 3 && length < 7) {
			return 1;
		} else if (length > 6 && length < 10) {
			return 2;
		} else if (length > 9 && length < 13) {
			return 3;
		} else if (length > 12 && length < 16) {
			return 4;
		} else if (length > 15 && length < 19) {
			return 5;
		}
		return 0;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
		if (this.value != null) {
			textField.setText(numberFormat.format((BigDecimal) value));
			number = (BigDecimal) value;
			degerTam = number.toBigInteger();
			degerKusurat = number.remainder(BigDecimal.ONE).multiply(new BigDecimal(100)).intValue();
		} else {
			textField.setText(null);
		}
	}

}
