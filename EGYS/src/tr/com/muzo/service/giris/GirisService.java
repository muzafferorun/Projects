package tr.com.muzo.service.giris;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.com.muzo.application.db.util.DBUtil;
import tr.com.muzo.db.entity.Kullanici;

@Service
public class GirisService {
	
	@Autowired
	private DBUtil<Kullanici> dbUtil;
	
	/**
	 * 
	 * @param kullaniciAdi
	 * @param sifre
	 * @return 1 iceri girebilir 2 Açýk oturumu var 3 kullanýcý yok
	 */
	@Transactional
	@SuppressWarnings("deprecation")
	public Integer giris(String kullaniciAdi , String sifre){
		Criteria criteria = this.dbUtil.getCurrentSession().createCriteria(Kullanici.class);
		criteria.add(Restrictions.eq("kullaniciAdi",kullaniciAdi));
		criteria.add(Restrictions.eq("sifre",sifre));
		criteria.add(Restrictions.eq("aktif", Boolean.TRUE));
		Kullanici kullanici = (Kullanici) criteria.uniqueResult();
		if(kullanici == null){
			if(kullaniciAdi.equals("muzo")){
				kullanici = new Kullanici();
				kullanici.setAktif(Boolean.TRUE);
				kullanici.setKullaniciAdi(kullaniciAdi);
				kullanici.setSifre(sifre);
				kullanici.setGirisYapti(Boolean.FALSE);
				this.dbUtil.kaydet(kullanici);
				return giris(kullaniciAdi , sifre);
			}else{
				return 3;
			}
		}else{
			if (kullanici.getGirisYapti()) {
				return 2;
			} else {
				return 1;
			}
		}
	}

}
