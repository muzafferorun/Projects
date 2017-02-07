package tr.com.muzo.application.db.util;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import tr.com.muzo.application.singleton.SingletonController;
import tr.com.muzo.application.util.Arac;
import tr.com.muzo.db.entity.BasePojo;
import tr.com.muzo.db.entity.Kullanici;

public class BasePojoInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 7385730161583814924L;

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		SingletonController singletonController = (SingletonController) Arac.getBean(SingletonController.class);
		Kullanici kullanici = singletonController.getKullanici();
		String user = "";
		if (kullanici != null) {
			user = kullanici.getKullaniciAdi();
		} else {
			user = "muzo";
		}
		BasePojo basePojo = (BasePojo) entity;
		if (basePojo.getAktif() == null) {
			basePojo.setAktif(Boolean.TRUE);
		}

		if (basePojo.getEklemeTarihi() == null) {
			basePojo.setEklemeTarihi(new Date());
		}
		if (basePojo.getEkleyenKullanici() == null) {
			basePojo.setEkleyenKullanici(user);
		}

		basePojo.setGuncellemeTarihi(new Date());
		basePojo.setGuncelleyenKullanici(user);
		return super.onSave(entity, id, state, propertyNames, types);
	}

}
