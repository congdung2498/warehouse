package com.viettel.vtnet360.vt02.vt020013.service;

import java.io.File;
import java.io.IOException;
import java.util.List;


import com.viettel.vtnet360.vt02.vt020013.entity.VT020013KitchenReport;

public interface VT020013ExcelOutputService {
	
	/**
	 * Create excel file
	 */
	public File createExcelOutputExcel(List<VT020013KitchenReport> kitchenList, String month) throws Exception;
}
