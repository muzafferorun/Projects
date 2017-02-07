package tr.com.muzo.application.db.util;

import java.util.List;

public class QueryResult<T> {

	private List<T> liste;
	private int toplamKayitSayisi;
	private int toplamSayfaSayisi;

	public List<T> getListe() {
		return liste;
	}

	public void setListe(List<T> liste) {
		this.liste = liste;
	}

	public int getToplamKayitSayisi() {
		return toplamKayitSayisi;
	}

	public void setToplamKayitSayisi(int toplamKayitSayisi) {
		this.toplamKayitSayisi = toplamKayitSayisi;
	}

	public int getToplamSayfaSayisi() {
		return toplamSayfaSayisi;
	}

	public void setToplamSayfaSayisi(int toplamSayfaSayisi) {
		this.toplamSayfaSayisi = toplamSayfaSayisi;
	}

}
