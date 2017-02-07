package tr.com.muzo.component.test;

import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLAnchorElement;
import org.w3c.dom.html.HTMLIFrameElement;
import org.w3c.dom.html.HTMLInputElement;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import tr.com.muzo.application.util.Arac;
import tr.com.muzo.component.util.Anchor;

//@SpringBootApplication(scanBasePackageClasses = ApplicationConfiguration.class)
public class HurriyetTest extends Application {

	// private ConfigurableApplicationContext applicationContext;

	private BorderPane root;
	private GridPane gridPane;
	private WebView browser;
	private WebEngine webEngine;

	private int adim = 0;

	@Override
	public void init() throws Exception {
		// applicationContext = SpringApplication.run(AutoCompleteTest.class);
		Arac.setAwesomeFont();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new BorderPane();
		Scene scene = new Scene(root, 1024, 768);
		gridPane = new GridPane();
		browser = new WebView();
		webEngine = browser.getEngine();
		webEngine.load("http://www.hurriyetemlak.com/uye-giris");

		webEngine.createPopupHandlerProperty().addListener(new ChangeListener<Callback<PopupFeatures, WebEngine>>() {

			@Override
			public void changed(ObservableValue<? extends Callback<PopupFeatures, WebEngine>> observable,
					Callback<PopupFeatures, WebEngine> oldValue, Callback<PopupFeatures, WebEngine> newValue) {
				System.out.println("pop up açýldý");
			}
		});

		webEngine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {
			@Override
			public void changed(ObservableValue<? extends Throwable> observable, Throwable oldValue,
					Throwable newValue) {
				System.out.println("exception");
			}
		});

		webEngine.getLoadWorker().titleProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("titleProperty");
				sayfaIsle();
			}
		});

		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				if (newValue.equals(State.SUCCEEDED)) {
					sayfaIsle();
				}
			}
		});
		gridPane.add(browser, 0, 0);
		root.setCenter(gridPane);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(HurriyetTest.class);
	}

	@Override
	public void stop() throws Exception {
		// if (applicationContext != null) {
		// applicationContext.close();
		// }
	}

	public void sayfaIsle() {
		switch (adim) {
		case 0:
			HTMLInputElement username = (HTMLInputElement) webEngine.getDocument().getElementById("login-email");
			HTMLInputElement password = (HTMLInputElement) webEngine.getDocument().getElementById("login-password");
			username.setValue("cihangirkurtipek06@hotmail.com");
			password.setValue("1958.58");
			webEngine.executeScript("loginSys.LoginPortal()");
			adim++;
			break;
		case 1:
			HTMLAnchorElement anchorElement = (HTMLAnchorElement) webEngine.getDocument()
					.getElementById("ctl00_ctlHeader1_lnkMlsPortfolio");
			Anchor anchor = new Anchor(anchorElement.getId(), anchorElement.getHref(), webEngine);
			anchor.clickLink();
			adim++;
			break;
		case 2:
			anchorElement = (HTMLAnchorElement) webEngine.getDocument()
					.getElementById("ctl00_ctlHeader1_lnkRealtyAdvertise");
			anchor = new Anchor(anchorElement.getId(), anchorElement.getHref(), webEngine);
			anchor.clickLink();
			adim++;
			break;
		case 3:
			webEngine.executeScript(
					"WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions(\"ctl00$ContentPlaceHolder1$btnContinue\", \"\", true, \"vgpAddRealty\", \"\", false, true))");
			// HTMLInputElement htmlInputElement = (HTMLInputElement)
			// webEngine.getDocument()
			// .getElementById("ctl00_ctlFooter1_ctlPrivacyPolicy1_btnHidden");
			// htmlInputElement.click();
			webEngine.executeScript(
					"setTimeout(function(){ $('#ctl00_ctlFooter1_ctlPrivacyPolicy1_btnClose').click();}, 10000);");
			adim++;
			break;
		case 4:
			HTMLIFrameElement iframeUpload = (HTMLIFrameElement) webEngine.getDocument().getElementById("frameUpload");
			NodeList nodeList = iframeUpload.getContentDocument().getElementsByTagName("input");
			for (int i = 0; nodeList.getLength() > i; i++) {
				HTMLInputElement htmlInputElement = (HTMLInputElement) nodeList.item(i);
				System.out.println(htmlInputElement.getType());
				System.out.println(htmlInputElement.getClassName());
				if ("file".equals(htmlInputElement.getType())
						&& "upload-input".equals(htmlInputElement.getClassName())) {
					htmlInputElement.click();
				}
			}

			// HTMLInputElement ilanBasligi = (HTMLInputElement)
			// webEngine.getDocument()
			// .getElementById("ctl00_ContentPlaceHolder1_ctlAddRealtyGeneral1_txtRealtyTittleValue");
			// ilanBasligi.setValue("Örnek Emlak Ýlaný");
			// adim++;
			break;
		default:
			break;
		}

	}

}
