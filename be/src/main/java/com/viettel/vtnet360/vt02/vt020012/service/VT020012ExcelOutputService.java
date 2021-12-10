package com.viettel.vtnet360.vt02.vt020012.service;

import java.io.File;
import java.io.IOException;
import java.util.List;


import com.viettel.vtnet360.vt02.vt020012.entity.VT020012KitchenReport;

public interface VT020012ExcelOutputService {
	
	/**
	 * Create excel file
	 */
	public File createExcelOutputExcel(List<VT020012KitchenReport> kitchenList, String month) throws Exception;
}
