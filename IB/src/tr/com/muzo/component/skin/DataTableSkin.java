package tr.com.muzo.component.skin;

import javafx.scene.control.SkinBase;
import tr.com.muzo.component.DataTable;

@SuppressWarnings("rawtypes")
public class DataTableSkin<T> extends SkinBase{

	@SuppressWarnings("unchecked")
	public DataTableSkin(DataTable<T> control) {
		super(control);
	}
	
}
