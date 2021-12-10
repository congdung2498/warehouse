package com.viettel.vtnet360.vt05.vt050001.dao;

import java.util.List;

import com.viettel.vtnet360.vt05.vt050001.entity.CalculationUnit;
import com.viettel.vtnet360.vt05.vt050001.entity.LimitSpend;
import com.viettel.vtnet360.vt05.vt050001.entity.Stationery;

public interface VT050001DAO {
  public List<Stationery> getAllStationery();

  public List<CalculationUnit> getCalulationUnit();

  public List<CalculationUnit> findCalulationUnit(CalculationUnit calUnit);

  public List<Stationery> searchStationery(Stationery stationery);

  public int insertStationery(Stationery stationery);

  public int updateStationery(Stationery stationery);

  public int deleteStationery(Stationery stationery);

  public int checkActive(Stationery stationery);

  public LimitSpend getLimit(String code);

  public int updateLimit(LimitSpend limit);

  public int checkDeleted(Stationery stationery);
  
  public List<Stationery> searchCountStationery(Stationery stationery);
  
}
