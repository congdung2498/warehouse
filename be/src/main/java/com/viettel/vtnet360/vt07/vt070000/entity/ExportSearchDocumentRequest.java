package com.viettel.vtnet360.vt07.vt070000.entity;
import java.util.List;
import com.viettel.vtnet360.vt00.common.BaseEntity;
import com.viettel.vtnet360.vt07.vt070000.entity.search.DocumentDetailSearch;

public class ExportSearchDocumentRequest extends BaseEntity{
	public List<DocumentDetailSearch> data;

	public List<DocumentDetailSearch> getData() {
		return data;
	}

	public void setData(List<DocumentDetailSearch> data) {
		this.data = data;
	}

}