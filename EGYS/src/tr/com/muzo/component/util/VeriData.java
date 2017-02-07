package tr.com.muzo.component.util;

public class VeriData {
	public VeriData(String veriLabel, Object veriData) {
		super();
		this.veriLabel = veriLabel;
		this.veriData = veriData;
	}
	private String veriLabel;
	private Object veriData;

	public String getVeriLabel() {
		return veriLabel;
	}

	public void setVeriLabel(String veriLabel) {
		this.veriLabel = veriLabel;
	}

	public Object getVeriData() {
		return veriData;
	}

	public void setVeriData(Object veriData) {
		this.veriData = veriData;
	}

	@Override
	public String toString() {
		return veriLabel;
	}

}
