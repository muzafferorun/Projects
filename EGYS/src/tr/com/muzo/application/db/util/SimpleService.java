package tr.com.muzo.application.db.util;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface SimpleService<T> {
	
	public QueryResult<T> getListe(T obje, int sayfaNumarasi, int sayfadaGosterilecekKayitSayisi);

	public void kaydet(T obje);

	public void sil(T obje);

}
