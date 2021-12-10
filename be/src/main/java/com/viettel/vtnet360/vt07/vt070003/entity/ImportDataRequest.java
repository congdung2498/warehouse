package com.viettel.vtnet360.vt07.vt070003.entity;
import java.util.List;
import com.viettel.vtnet360.vt00.common.BaseEntity;

public class ImportDataRequest extends BaseEntity{
	public List<ImportedDataRow> data;

	public List<ImportedDataRow> getData() {
		return data;
	}

	public void setDataRows(List<ImportedDataRow> data) {
		this.data = data;
	}
	
}
