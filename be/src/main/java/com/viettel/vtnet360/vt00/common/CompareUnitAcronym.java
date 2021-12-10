package com.viettel.vtnet360.vt00.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DuyNK
 *
 */
public class CompareUnitAcronym {

	public String getUnitAcronym(String unitName) {
		
		List<UnitAcronym> listUnit = new ArrayList<>();
		listUnit.add(new UnitAcronym("Trung tâm Kỹ thuật khu vực 1 - TCT VTNET"						, "KV1"));
		listUnit.add(new UnitAcronym("Trung tâm Kỹ thuật khu vực 2 - TCT VTNET"						, "KV2"));
		listUnit.add(new UnitAcronym("Trung tâm Kỹ thuật khu vực 3 - TCT VTNET"						, "KV3"));
		listUnit.add(new UnitAcronym("Trung tâm Quản lý dịch vụ - TCT VTNet"						, "TTQLDV"));
		listUnit.add(new UnitAcronym("Trung tâm Kỹ thuật toàn cầu - TCT VTNet"						, "TTKTTC"));
		listUnit.add(new UnitAcronym("Trung tâm Vận hành khai thác toàn cầu - TCT VTNet"			, "TTVHKTTC"));
		listUnit.add(new UnitAcronym("Trung Tâm Phát triển Phần mềm - TCT VTNet"					, "TTPTPM"));
		listUnit.add(new UnitAcronym("Trung tâm Kiểm soát Vận hành Khai thác - TCT VTNET"			, "TTKSVHKT"));
		listUnit.add(new UnitAcronym("Trung tâm Chiến lược Mạng lưới và Đổi mới Công nghệ - TCT VTNET", "TTCLML"));
		listUnit.add(new UnitAcronym("Trung Tâm Đo lường Chất lượng - TCT VTNET"					, "TTĐLCL"));
		listUnit.add(new UnitAcronym("Ban Quản lý Dự án Hạ tầng Viễn thông - TCT VTNET"				, "BQLDA"));
		listUnit.add(new UnitAcronym("Phòng Tổ chức lao động"										, "P.TCLĐ"));
		listUnit.add(new UnitAcronym("Phòng Chính trị"												, "P.Ctr"));
		listUnit.add(new UnitAcronym("Phòng Tài chính"												, "P.TC"));
		listUnit.add(new UnitAcronym("Phòng Kỹ thuật"												, "P.KT"));
		listUnit.add(new UnitAcronym("Phòng Kiểm soát nội bộ"										, "P.KSNB"));
		listUnit.add(new UnitAcronym("Văn phòng"													, "VP"));
		listUnit.add(new UnitAcronym("Phòng Công nghệ thông tin"									, "P.CNTT"));
		listUnit.add(new UnitAcronym("Phòng Đầu tư"													, "P.ĐT"));
		
		for (int i = 0; i < listUnit.size(); i++) {
			unitName = unitName.replaceAll(listUnit.get(i).getUnitName(), listUnit.get(i).getAcronym());
		}
		
		return unitName;
	}
}
