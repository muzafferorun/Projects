package tr.com.muzo.component.util;

public class DialogGridData {
	private String kolonAdi;
	private String label;
	private String dataTip;
	private String placeHolder;
	private Boolean required;
	
	public DialogGridData(String kolonAdi, String label, String dataTip, String placeHolder, Boolean required) {
		super();
		this.kolonAdi = kolonAdi;
		this.label = label;
		this.dataTip = dataTip;
		this.placeHolder = placeHolder;
		this.required = required;
	}

	public String getKolonAdi() {
		return kolonAdi;
	}
	public void setKolonAdi(String kolonAdi) {
		this.kolonAdi = kolonAdi;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	public String getDataTip() {
		return dataTip;
	}
	public void setDataTip(String dataTip) {
		this.dataTip = dataTip;
	}
	public String getPlaceHolder() {
		return placeHolder;
	}
	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public Boolean getRequired() {
		return required;
	}
}