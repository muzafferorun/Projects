package tr.com.muzo.application.singleton;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tr.com.muzo.db.entity.Kullanici;

@Controller
@Scope("singleton")
public class SingletonController implements Serializable{

	private static final long serialVersionUID = -2674312405512916645L;

	private Kullanici kullanici;

	public Kullanici getKullanici() {
		return kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}


}
