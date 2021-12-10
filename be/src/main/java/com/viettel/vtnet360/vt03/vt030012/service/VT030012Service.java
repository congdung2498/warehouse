package com.viettel.vtnet360.vt03.vt030012.service;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030012.entity.Ratting;

public interface VT030012Service {
	public ResponseEntityBase updateRatting(Ratting rate);
	public ResponseEntityBase updateRattingUser(Ratting rate);
}
