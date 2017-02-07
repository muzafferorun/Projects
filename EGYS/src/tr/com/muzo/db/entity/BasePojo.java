package tr.com.muzo.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import tr.com.muzo.component.autoComplete.AutoCompleteColumn;
import tr.com.muzo.component.dataTable.DataTableColumn;
import tr.com.muzo.component.dataTable.EntityProperty;

@MappedSuperclass
public class BasePojo implements Serializable, BasePojoInterface {

	private static final long serialVersionUID = -8240082257995731237L;

	private String ekleyenKullanici;
	private Date eklemeTarihi;
	private String guncelleyenKullanici;
	private Date guncellemeTarihi;
	private Boolean aktif;

	@Column(name = "EKLEYEN_KULLANICI")
	public String getEkleyenKullanici() {
		return ekleyenKullanici;
	}

	public void setEkleyenKullanici(String ekleyenKullanici) {
		this.ekleyenKullanici = ekleyenKullanici;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EKLEME_TARIHI")
	public Date getEklemeTarihi() {
		return eklemeTarihi;
	}

	public void setEklemeTarihi(Date eklemeTarihi) {
		this.eklemeTarihi = eklemeTarihi;
	}

	@Column(name = "GUNCELLEYEN_KULLANICI")
	public String getGuncelleyenKullanici() {
		return guncelleyenKullanici;
	}

	public void setGuncelleyenKullanici(String guncelleyenKullanici) {
		this.guncelleyenKullanici = guncelleyenKullanici;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GUNCELLEME_TARIHI")
	public Date getGuncellemeTarihi() {
		return guncellemeTarihi;
	}

	public void setGuncellemeTarihi(Date guncellemeTarihi) {
		this.guncellemeTarihi = guncellemeTarihi;
	}

	@Type(type = "numeric_boolean")
	@Column(name = "AKTIF")
	public Boolean getAktif() {
		return aktif;
	}

	public void setAktif(Boolean aktif) {
		this.aktif = aktif;
	}

	@Override
	@Transient
	public DataTableColumn[] getKolonlar() {
		return null;
	}

	@Override
	@Transient
	public String getListeAdi() {
		return null;
	}

	@Override
	@Transient
	public EntityProperty[] getEntityProperties() {
		return null;
	}

	@Override
	@Transient
	public AutoCompleteColumn getAutoCompleteColumn() {
		return null;
	}

	@Override
	public void setKolonlar(DataTableColumn[] kolonlar) {

	}

	@Override
	public void setListeAdi(String listeAdi) {

	}

	@Override
	public void setEntityProperties(EntityProperty[] entityProperties) {

	}

	@Override
	public void setAutoCompleteColumn(AutoCompleteColumn autoCompleteColumns) {

	}

}
