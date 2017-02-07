package tr.com.muzo.component.dataTable;

import tr.com.muzo.component.util.VeriData;
import tr.com.muzo.enums.AramaTip;
import tr.com.muzo.enums.VeriTip;

public class EntityProperty {

	private String kolonAdi;
	private boolean eklenebilir;
	private boolean guncellenebilir;
	private boolean detayGoruntulenebilir;
	private boolean parametreyeEklenebilir;
	private String label;
	private VeriTip veriTip;
	private VeriData[] veriData;
	private String promptText;
	private AramaTip aramaTip;

	public EntityProperty(String kolonAdi, boolean eklenebilir, boolean guncellenebilir, boolean detayGoruntulenebilir,
			boolean parametreyeEklenebilir, String label, VeriTip veriTip, VeriData[] veriData, String promptText,
			AramaTip aramaTip) {
		super();
		this.kolonAdi = kolonAdi;
		this.eklenebilir = eklenebilir;
		this.guncellenebilir = guncellenebilir;
		this.detayGoruntulenebilir = detayGoruntulenebilir;
		this.parametreyeEklenebilir = parametreyeEklenebilir;
		this.label = label;
		this.veriTip = veriTip;
		this.veriData = veriData;
		this.promptText = promptText;
		this.aramaTip = aramaTip;
	}

	public String getKolonAdi() {
		return kolonAdi;
	}

	public void setKolonAdi(String kolonAdi) {
		this.kolonAdi = kolonAdi;
	}

	public boolean isEklenebilir() {
		return eklenebilir;
	}

	public void setEklenebilir(boolean eklenebilir) {
		this.eklenebilir = eklenebilir;
	}

	public boolean isGuncellenebilir() {
		return guncellenebilir;
	}

	public void setGuncellenebilir(boolean guncellenebilir) {
		this.guncellenebilir = guncellenebilir;
	}

	public boolean isDetayGoruntulenebilir() {
		return detayGoruntulenebilir;
	}

	public void setDetayGoruntulenebilir(boolean detayGoruntulenebilir) {
		this.detayGoruntulenebilir = detayGoruntulenebilir;
	}

	public boolean isParametreyeEklenebilir() {
		return parametreyeEklenebilir;
	}

	public void setParametreyeEklenebilir(boolean parametreyeEklenebilir) {
		this.parametreyeEklenebilir = parametreyeEklenebilir;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public VeriTip getVeriTip() {
		return veriTip;
	}

	public void setVeriTip(VeriTip veriTip) {
		this.veriTip = veriTip;
	}

	public VeriData[] getVeriData() {
		return veriData;
	}

	public void setVeriData(VeriData[] veriData) {
		this.veriData = veriData;
	}

	public String getPromptText() {
		return promptText;
	}

	public void setPromptText(String promptText) {
		this.promptText = promptText;
	}

	public AramaTip getAramaTip() {
		return aramaTip;
	}

	public void setAramaTip(AramaTip aramaTip) {
		this.aramaTip = aramaTip;
	}

}
