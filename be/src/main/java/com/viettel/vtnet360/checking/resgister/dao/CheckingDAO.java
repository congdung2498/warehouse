package com.viettel.vtnet360.checking.resgister.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.viettel.vtnet360.checking.service.entity.Checking;
import com.viettel.vtnet360.checking.service.entity.SystemCode;
import com.viettel.vtnet360.vt01.vt010011.entity.VT010011ListCondition;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;
import com.viettel.vtnet360.vt03.vt030000.entity.Employee;


public interface CheckingDAO {
  
  public String getPathByUsername(String username);
  
  public List<String> getListRemindCheckings(int minute);
  
  public int countChecking(Map<String, Object> data);
  
  public List<Checking> findListInOut(Map<String, Object> data);
  
  public void createChecking(HashMap<String, Object> data);
  
  public void createWithChecking(HashMap<String, Object> data);
  
  public void createWithLateChecking(HashMap<String, Object> data);
  
  public void updateChecking(HashMap<String, Object> data);
  
  public void updateMoreTime(HashMap<String, Object> data);
  
  public List<Employee> employeeAutocomplete(String search);
  
  public List<Employee> managerEmployeeAutocomplete(String search); 
  
  public List<Employee> getWithEmployees(@Param("userNames") String[] userNames);
  
  public List<SystemCode> getSystemCode(String masterClass);
  
  public Checking getCheckingById(String checkingId);
  
  public int countCheckings(VT010011ListCondition condition);
  
  public List<Checking> getCheckings(VT010011ListCondition condition);
  
  public Employee getEmployeeByUsername(String username);
  
  public void toSmsForChecking(HashMap<String, Object> data);
  
  public void toUpdateSmsForChecking(HashMap<String, Object> data);
  
  public int getMessageByCheckingId(String checkingId);
  
  public int getConfigMinute();
  
  public List<VT020000Unit> getUnitByUnitId(String unitId);
  
  public Checking getCheckingByBarcode(String barcode);
}
