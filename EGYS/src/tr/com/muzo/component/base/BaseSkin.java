package tr.com.muzo.component.base;

import javafx.scene.control.SkinBase;

@SuppressWarnings("rawtypes")
public class BaseSkin extends SkinBase {

	@SuppressWarnings("unchecked")
	protected BaseSkin(ComponentBase control) {
		super(control);
	}

	@SuppressWarnings("unchecked")
	protected BaseSkin(ButtonBase control) {
		super(control);
	}

}
