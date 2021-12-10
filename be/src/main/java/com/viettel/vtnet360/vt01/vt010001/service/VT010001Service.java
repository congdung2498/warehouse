package com.viettel.vtnet360.vt01.vt010001.service;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt01.vt010001.entity.VT010001EntityRpRegister;
import com.viettel.vtnet360.vt01.vt010001.entity.VT010001EntityRqRegister;
import com.viettel.vtnet360.vt01.vt010001.entity.VT010001SystemCode;
import com.viettel.vtnet360.vt03.vt030000.entity.Employee;

import java.security.Principal;

/**
 * Class interface VT010001Service
 * 
 * @author KienHT 09/08/2018
 * 
 */
public interface VT010001Service {

	/**
	 * register in_out_record
	 * 
	 * @param VT010001EntityRpRegister
	 * @param Principal
	 * @return VT010001EntityRpRegister
	 */
	public VT010001EntityRpRegister inOutregister(VT010001EntityRqRegister requestParam, Principal principal) throws Exception;
	/**
	 * get employee
	 * @param employee
	 * @return
	 * @throws Exception
	 */
	public ResponseEntityBase findEmployee(Employee employee) throws Exception;

	/**
	 * get employee by userName
	 * @param employee
	 * @return
	 * @throws Exception
	 */
	public ResponseEntityBase findEmployeeByUserName(Employee employee) throws Exception;

	public ResponseEntityBase getSystemCodeByParams(VT010001SystemCode entity) throws Exception;
}
