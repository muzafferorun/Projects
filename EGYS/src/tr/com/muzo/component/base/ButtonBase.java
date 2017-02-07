package tr.com.muzo.component.base;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import tr.com.muzo.application.util.Arac;

public class ButtonBase extends Control {

	protected Button button;
	protected EventHandler<ButtonActionEvent> preAction;
	protected EventHandler<ButtonActionEvent> action;
	protected String label;
	protected String icon;
	protected String process;

	public ButtonBase() {
		super();
	}

	public ButtonBase(String label) {
		super();
		this.label = label;
	}

	public ButtonBase(String label, String icon) {
		super();
		this.label = label;
		this.icon = icon;
	}

	public ButtonBase(String label, String icon, String process) {
		super();
		this.label = label;
		this.icon = icon;
		this.process = process;
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new BaseSkin(this);
	}

	public EventHandler<ButtonActionEvent> getPreAction() {
		return preAction;
	}

	public void setPreAction(EventHandler<ButtonActionEvent> preAction) {
		button.removeEventHandler(Arac.getPreAction(), event -> this.preAction.handle(event));
		this.preAction = preAction;
		button.addEventHandler(Arac.getPreAction(), event -> this.preAction.handle(event));
	}

	public EventHandler<ButtonActionEvent> getAction() {
		return action;
	}

	public void setAction(EventHandler<ButtonActionEvent> action) {
		button.removeEventHandler(Arac.getLastAction(), event -> this.action.handle(event));
		this.action = action;
		button.addEventHandler(Arac.getLastAction(), event -> this.action.handle(event));
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getProcess() {
		return process;
	}

	/**
	 * 
	 * @param process
	 *            parametresi @this sadece kendisi @secene butun sayfa digerleri
	 *            panel id ile çalýþýr
	 */

	public void setProcess(String process) {
		this.process = process;
	}

}