package com.viettel.vtnet360.vt04.vt040010.service;

import java.io.File;

import com.viettel.vtnet360.vt04.vt040010.entity.VT040010Condition;

/**
 * interface export excel VTTB VT040010
 * 
 * @author ThangBT 20/10/2018
 *
 */
public interface VT040010ServiceExportExcel {

	public File createExcelVTTB(VT040010Condition condition) throws Exception;

}
