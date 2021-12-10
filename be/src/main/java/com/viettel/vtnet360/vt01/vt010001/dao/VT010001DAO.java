package com.viettel.vtnet360.vt01.vt010001.dao;

import com.viettel.vtnet360.vt01.vt010001.entity.VT010001EntityRqRegister;
import com.viettel.vtnet360.vt01.vt010001.entity.VT010001SystemCode;
import com.viettel.vtnet360.vt03.vt030000.entity.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class interface VT010001DAO
 * 
 * @author KienHT 09/08/2018
 * 
 */
@Transactional
public interface VT010001DAO {

	/**
	 * register in_out_record
	 * 
	 * @param HashMap<String,Object>
	 */
	public void insertInOutRegister(HashMap<String, Object> data);



	/**
	 * find for valid issues Register IN OUT
	 * 
	 * @param Map<String, Object>
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> isValid(Map<String, Object> data);
	/**
	 * Get all employee in qldv.employee
	 * @param employee
	 * @return
	 */
	public List<Employee> findEmployee(Employee employee);

	/**
	 * Get employee by user name in qldv.employee
	 * @param employee
	 * @return
	 */
	public Employee findEmployeeByUserName(Employee employee);

	/**
	 * Get system code by master code, code name
	 * @param des
	 * @return
	 */
	public List<VT010001SystemCode> getSystemCodeByParams(VT010001SystemCode des);

	/**
	 * total record of in out register
	 *
	 * @param condition
	 * @return number
	 */
	public int countTotalRecord(VT010001EntityRqRegister condition);
}
