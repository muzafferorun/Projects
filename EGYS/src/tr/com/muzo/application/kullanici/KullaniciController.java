package tr.com.muzo.application.kullanici;

import java.io.Serializable;

import org.springframework.stereotype.Controller;

import tr.com.muzo.application.main.Main;
import tr.com.muzo.application.util.BaseController;

@Controller
public class KullaniciController extends BaseController implements Serializable {

	private static final long serialVersionUID = -8542296931459847147L;
		
	@Override
	public void initialize(Main main) {
		super.initialize(main);
	}
	
}
