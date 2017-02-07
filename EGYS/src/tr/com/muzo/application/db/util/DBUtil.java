package tr.com.muzo.application.db.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tr.com.muzo.component.dataTable.EntityProperty;
import tr.com.muzo.db.entity.BasePojo;
import tr.com.muzo.enums.AramaTip;

@Repository
public class DBUtil<T> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Transactional
	public void kaydet(Object obje){
		getCurrentSession().saveOrUpdate(obje);
	}

	@Transactional
	public void sil(Object obje) {
		getCurrentSession().delete(obje);
	}
	
	@Transactional
	@SuppressWarnings({ "deprecation", "unchecked" })
	public QueryResult<T> getQueryResult(T obje, int sayfaNumarasi,
			int sayfadaGosterilecekKayitSayisi) {
		sayfaNumarasi = sayfaNumarasi - 1;
		QueryResult<T> result = new QueryResult<T>();
		Criteria criteria = getCurrentSession().createCriteria(obje.getClass());
		criteria.setProjection(Projections.rowCount());
		BasePojo basePojo = (BasePojo) obje;
		for (EntityProperty entityProperty : basePojo.getEntityProperties()) {
			if (entityProperty.isParametreyeEklenebilir()) {
				try {
					Object o = PropertyUtils.getProperty(obje, entityProperty.getKolonAdi());
					if (o != null) {
						if (AramaTip.BASINDA.equals(entityProperty.getAramaTip())) {
							criteria.add(
									Restrictions.like(entityProperty.getKolonAdi(), "%" + ((String) o)).ignoreCase());
						} else if (AramaTip.BUYUK_ESIT.equals(entityProperty.getAramaTip())) {
							criteria.add(Restrictions.ge(entityProperty.getKolonAdi(), o));
						} else if (AramaTip.BUYUKTUR.equals(entityProperty.getAramaTip())) {
							criteria.add(Restrictions.gt(entityProperty.getKolonAdi(), o));
						} else if (AramaTip.ESITLIK.equals(entityProperty.getAramaTip())) {
							if (o instanceof String) {
								criteria.add(Restrictions.eq(entityProperty.getKolonAdi(), o).ignoreCase());
							} else {
								criteria.add(Restrictions.eq(entityProperty.getKolonAdi(), o));
							}
						} else if (AramaTip.HER_YERINDE.equals(entityProperty.getAramaTip())) {
							criteria.add(Restrictions.like(entityProperty.getKolonAdi(), "%" + ((String) o) + "%")
									.ignoreCase());
						} else if (AramaTip.KUCUK_ESIT.equals(entityProperty.getAramaTip())) {
							criteria.add(Restrictions.le(entityProperty.getKolonAdi(), o));
						} else if (AramaTip.KUCUKTUR.equals(entityProperty.getAramaTip())) {
							criteria.add(Restrictions.lt(entityProperty.getKolonAdi(), o));
						} else if (AramaTip.SONUNDA.equals(entityProperty.getAramaTip())) {
							criteria.add(
									Restrictions.like(entityProperty.getKolonAdi(), ((String) o) + "%").ignoreCase());
						}
					}
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				}
			}
		}

		Long count = (Long) criteria.uniqueResult();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		result.setToplamKayitSayisi(count.intValue());
		criteria.setProjection(null);

		criteria.setFirstResult(sayfaNumarasi * sayfadaGosterilecekKayitSayisi);
		criteria.setMaxResults(sayfadaGosterilecekKayitSayisi);

		List<T> liste = (List<T>) criteria.list();
		result.setListe(liste);
		int artikSayi = 0;
		if (count % sayfadaGosterilecekKayitSayisi != 0) {
			artikSayi = 1;
		}
		int toplamSayfaSayisi = new Integer("" + (count / sayfadaGosterilecekKayitSayisi));
		if (toplamSayfaSayisi == 0) {
			toplamSayfaSayisi = 1;
		} else {
			toplamSayfaSayisi = toplamSayfaSayisi + artikSayi;
		}
		result.setToplamSayfaSayisi(toplamSayfaSayisi);
		return result;
	}

}
