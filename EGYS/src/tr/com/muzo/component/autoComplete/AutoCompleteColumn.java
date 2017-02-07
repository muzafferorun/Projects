package tr.com.muzo.component.autoComplete;

public class AutoCompleteColumn {

	private String[] kolonAdi;
	private String labelKolonAdi;

	public AutoCompleteColumn(String[] kolonAdi, String labelKolonAdi) {
		super();
		this.kolonAdi = kolonAdi;
		this.labelKolonAdi = labelKolonAdi;
	}
	public String[] getKolonAdi() {
		return kolonAdi;
	}
	public void setKolonAdi(String[] kolonAdi) {
		this.kolonAdi = kolonAdi;
	}
	public String getLabelKolonAdi() {
		return labelKolonAdi;
	}
	public void setLabelKolonAdi(String labelKolonAdi) {
		this.labelKolonAdi = labelKolonAdi;
	}

}
