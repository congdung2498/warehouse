package com.viettel.vtnet360.vt02.vt020003.service;

import java.io.File;
import java.util.List;

import com.viettel.vtnet360.vt02.vt020003.entity.VT020003KitchenInfo;


public interface VT020003ExcelOutputService {
	public File createExcelOutputExcel(List<VT020003KitchenInfo> kitchenList) throws Exception;
}	
