package tr.com.muzo.component.util;

import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

public class Anchor {
	
	private String anchorId;
	private String link;
	private WebEngine webEngine;

	public Anchor(String anchorId, String link, WebEngine webEngine) {
		super();
		this.anchorId = anchorId;
		this.link = link;
		this.webEngine = webEngine;
		initialize();
	}

	public void initialize() {
		JSObject jsobj = (JSObject) webEngine.executeScript("window");
		jsobj.setMember("muzo", this);
		webEngine.executeScript("$('#" + anchorId + "').attr('onclick' , 'muzo.clickLink()')");
	}

	public void run() {
		webEngine.executeScript("$('#" + anchorId + "').click()");
	}


	public void clickLink() {
		webEngine.load(link.replace("javascript:", ""));
	}
	
}
