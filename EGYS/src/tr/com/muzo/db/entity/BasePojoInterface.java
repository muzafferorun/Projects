package tr.com.muzo.db.entity;

import javax.persistence.Transient;

import tr.com.muzo.component.autoComplete.AutoCompleteColumn;
import tr.com.muzo.component.dataTable.DataTableColumn;
import tr.com.muzo.component.dataTable.EntityProperty;

public interface BasePojoInterface {

	@Transient
	public DataTableColumn[] getKolonlar();

	@Transient
	public String getListeAdi();

	@Transient
	public EntityProperty[] getEntityProperties();

	@Transient
	public AutoCompleteColumn getAutoCompleteColumn();

	public void setKolonlar(DataTableColumn[] kolonlar);

	public void setListeAdi(String listeAdi);

	public void setEntityProperties(EntityProperty[] entityProperties);

	public void setAutoCompleteColumn(AutoCompleteColumn autoCompleteColumns);

}
