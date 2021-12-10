package com.viettel.vtnet360.vt05.vt050001.service;

import java.util.List;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050001.entity.CalculationUnit;
import com.viettel.vtnet360.vt05.vt050001.entity.LimitSpend;
import com.viettel.vtnet360.vt05.vt050001.entity.Stationery;

public interface VT050001Service {
	public ResponseEntityBase getAllStationery();

	public ResponseEntityBase getCalulationUnit();

	public ResponseEntityBase findCalulationUnit(CalculationUnit calUnit);

	public ResponseEntityBase searchStationery(Stationery stationery);

	public ResponseEntityBase insertStationery(Stationery stationery, byte[] bytes);

	public ResponseEntityBase updateStationery(Stationery stationery, byte[] bytes);

	public ResponseEntityBase deleteStationery(Stationery stationery);
	
	public ResponseEntityBase deleteSelectStationery(List<Stationery> stationery);

	public ResponseEntityBase getLimit();

	public ResponseEntityBase updateLimit(LimitSpend limit);
	
	public ResponseEntityBase searchCountStationery(Stationery stationery);
}
