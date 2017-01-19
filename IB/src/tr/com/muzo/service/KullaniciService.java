package tr.com.muzo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.muzo.db.util.DBUtil;
import tr.com.muzo.entity.Kullanici;

@Service
public class KullaniciService implements SimpleService{
	
	@Autowired
	private DBUtil dbUtil;


	@Override
	@SuppressWarnings("unchecked")
	public List<Kullanici> listele(Object kullaniciAramaParametre) {
		return dbUtil.queryOlustur(" Kullanici ", kullaniciAramaParametre);
	}


	@Override
	public void kaydet(Object obje) {
		this.dbUtil.kaydet(obje);
	}

	
}
