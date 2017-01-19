package tr.com.muzo.service;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.com.muzo.db.util.DBUtil;
import tr.com.muzo.entity.Kullanici;

@Service
public class GirisService {
	
	@Autowired
	private DBUtil dbUtil;
	
	@Transactional
	@SuppressWarnings("deprecation")
	public boolean giris(String kullaniciAdi , String sifre){
		
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
				this.dbUtil.kaydet(kullanici);
				return giris(kullaniciAdi , sifre);
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	
	
	
}
