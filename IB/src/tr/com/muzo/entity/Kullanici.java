package tr.com.muzo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="KULLANICI" , schema="MUZO")
public class Kullanici implements Serializable{

	private static final long serialVersionUID = -7066603469390149854L;
	
	private Long kullaniciId;
	private String kullaniciAdi;
	private String sifre;
	private Boolean aktif;
		
	@Id
	@Column(name="KULLANICI_ID")
	@GeneratedValue(generator="S_KULLANICI" , strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="S_KULLANICI" , schema="MUZO" , sequenceName="S_KULLANICI")
	public Long getKullaniciId() {
		return kullaniciId;
	}
	public void setKullaniciId(Long kullaniciId) {
		this.kullaniciId = kullaniciId;
	}
	
	@Column(name="KULLANICI_ADI")
	public String getKullaniciAdi() {
		return kullaniciAdi;
	}
	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}

	@Column(name="SIFRE")
	public String getSifre() {
		return sifre;
	}
	public void setSifre(String sifre) {
		this.sifre = sifre;
	}
	
	@Type(type="numeric_boolean")
	@Column(name="AKTIF")
	public Boolean getAktif() {
		return aktif;
	}
	public void setAktif(Boolean aktif) {
		this.aktif = aktif;
	}
	
}
