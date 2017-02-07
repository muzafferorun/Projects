package tr.com.muzo.component.base;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class ComponentBase extends Control {

	protected boolean required;
	protected String requiredMessage;
	protected String label;
	protected String text;
	protected Object value;

	public ComponentBase() {
		super();
	}

	public ComponentBase(String label) {
		super();
		this.label = label;
	}

	public ComponentBase(boolean required, String requiredMessage) {
		super();
		this.required = required;
		this.requiredMessage = requiredMessage;
	}

	public ComponentBase(boolean required, String requiredMessage, String label) {
		super();
		this.required = required;
		this.requiredMessage = requiredMessage;
		this.label = label;
	}

	public ComponentBase(String label, Object value) {
		super();
		this.label = label;
		this.value = value;
	}

	public ComponentBase(boolean required, String requiredMessage, Object value) {
		super();
		this.required = required;
		this.requiredMessage = requiredMessage;
		this.value = value;
	}

	public ComponentBase(boolean required, String requiredMessage, String label, Object value) {
		super();
		this.required = required;
		this.requiredMessage = requiredMessage;
		this.label = label;
		this.value = value;
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new BaseSkin(this);
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getRequiredMessage() {
		return requiredMessage;
	}

	public void setRequiredMessage(String requiredMessage) {
		this.requiredMessage = requiredMessage;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
