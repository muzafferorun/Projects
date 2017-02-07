package tr.com.muzo.db.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import tr.com.muzo.component.autoComplete.AutoCompleteColumn;
import tr.com.muzo.component.dataTable.DataTableColumn;
import tr.com.muzo.component.dataTable.EntityProperty;
import tr.com.muzo.component.util.VeriData;
import tr.com.muzo.enums.AramaTip;
import tr.com.muzo.enums.VeriTip;

@Entity
@Table(name="KULLANICI" )
public class Kullanici extends BasePojo implements Serializable {

	private static final long serialVersionUID = -7066603469390149854L;


	private Long kullaniciId;
	private String kullaniciAdi;
	private String sifre;
	private Boolean girisYapti;

	@Id
	@Column(name="KULLANICI_ID")
	@GeneratedValue(generator="S_KULLANICI" , strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="S_KULLANICI"  , sequenceName="S_KULLANICI")
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

	@Type(type = "numeric_boolean")
	@Column(name = "GIRIS_YAPTI")
	public Boolean getGirisYapti() {
		return girisYapti;
	}

	public void setGirisYapti(Boolean girisYapti) {
		this.girisYapti = girisYapti;
	}

	@Override
	@Transient
	public DataTableColumn[] getKolonlar() {
		return new DataTableColumn[] { new DataTableColumn("kullaniciAdi", "Kullanýcý Adý"),
				new DataTableColumn("aktif", "Aktif") };
	}

	@Override
	@Transient
	public String getListeAdi() {
		return "Kullanýcý Listesi";
	}

	@Override
	@Transient
	public EntityProperty[] getEntityProperties() {
		return new EntityProperty[] {
				new EntityProperty("kullaniciAdi", true, true, true, true, "Kullanýcý Adý :", VeriTip.TEXT, null,
						"Kullanýcý Adý", AramaTip.HER_YERINDE),
				new EntityProperty("sifre", true, true, false, false, "Þifre :", VeriTip.PASSWORD, null, "Þifre" , null),
				new EntityProperty("girisYapti", false, true, true, true, "Giriþ Yaptý :", VeriTip.DROP_DOWN,
						new VeriData[] { new VeriData("Lütfen Seçiniz", null), new VeriData("Evet", Boolean.TRUE),
								new VeriData("Hayýr", Boolean.FALSE) },
						null, AramaTip.ESITLIK),
				new EntityProperty("aktif", false, true, true, true, "Aktif :",
						VeriTip.DROP_DOWN, new VeriData[] { new VeriData("Lütfen Seçiniz", null),
								new VeriData("Evet", Boolean.TRUE), new VeriData("Hayýr", Boolean.FALSE) },
						null, AramaTip.ESITLIK) };
	}

	@Override
	@Transient
	public AutoCompleteColumn getAutoCompleteColumn() {
		return new AutoCompleteColumn(new String[] { "kullaniciAdi" }, "kullaniciAdi");
	}

}
