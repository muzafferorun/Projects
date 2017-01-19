package tr.com.muzo.parameter;

import java.io.Serializable;
import java.util.Map;

import tr.com.muzo.db.util.MapBuilder;

public class KullaniciAramaParametre implements SimpleAramaKriter , Serializable{
	
	private static final long serialVersionUID = -935183705869817534L;
	
	private String kullaniciAdi;
	private Boolean aktif;
	
	public String getKullaniciAdi() {
		return kullaniciAdi;
	}
	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}
	public Boolean getAktif() {
		return aktif;
	}
	public void setAktif(Boolean aktif) {
		this.aktif = aktif;
	}
	
	@Override
	public Map<String, Map<String, String>> aramaKriter() {
		return MapBuilder.aramaMapOlustur("kullaniciAdi" , "like","anyWhere","ASC", "String" , "Kullanýcý Adý",
				"aktif" , "=" , "" , "ASC" , "Boolean" , "Aktif");
	}
}
