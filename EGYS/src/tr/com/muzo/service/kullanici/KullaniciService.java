package tr.com.muzo.service.kullanici;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.com.muzo.application.db.util.DBUtil;
import tr.com.muzo.application.db.util.QueryResult;
import tr.com.muzo.application.db.util.SimpleService;
import tr.com.muzo.db.entity.Kullanici;

@Service
public class KullaniciService implements SimpleService<Kullanici> {

	@Autowired
	private DBUtil<Kullanici> dbUtil;

	@Transactional
	public Kullanici getKullanici(Long id) {
		return dbUtil.getCurrentSession().find(Kullanici.class, id);
	}

	@Transactional
	@SuppressWarnings("deprecation")
	public Kullanici getKullanici(String kullaniciAdi, String sifre) {
		Criteria criteria = this.dbUtil.getCurrentSession().createCriteria(Kullanici.class);
		criteria.add(Restrictions.eq("kullaniciAdi", kullaniciAdi));
		criteria.add(Restrictions.eq("sifre", sifre));
		return (Kullanici) criteria.uniqueResult();
	}

	@Transactional
	public void kullaniciIcerdeSetle(Kullanici kullanici, Boolean durum) {
		kullanici.setGirisYapti(durum);
		this.dbUtil.kaydet(kullanici);
	}

	@Override
	public QueryResult<Kullanici> getListe(Kullanici obje, int sayfaNumarasi, int sayfadaGosterilecekKayitSayisi) {
		QueryResult<Kullanici> result = new QueryResult<Kullanici>();
		result = this.dbUtil.getQueryResult(obje, sayfaNumarasi, sayfadaGosterilecekKayitSayisi);
		return result;
	}

	@Override
	public void kaydet(Kullanici obje) {
		obje.setGirisYapti(Boolean.FALSE);
		this.dbUtil.kaydet(obje);
	}

	@Override
	public void sil(Kullanici obje) {
		this.dbUtil.sil(obje);
	}

}
