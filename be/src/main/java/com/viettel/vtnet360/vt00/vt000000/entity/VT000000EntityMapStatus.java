package com.viettel.vtnet360.vt00.vt000000.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VT000000EntityMapStatus {
	
	/** STATUS OF IN OUT REGISTRY */
	public static final Map<Integer, String> STATUS_IN_OUT_MANAGEMENT = initMapInOutRegistry();

	private static Map<Integer, String> initMapInOutRegistry() {
		Map<Integer, String> map = new HashMap<>();
		map.put(0, "Chờ phê duyệt");
		map.put(1, "Đã duyệt");
		map.put(2, "Từ chối duyệt");
		map.put(3, "Đã ra");
		map.put(4, "Đã vào");
		map.put(5, "Chờ gia hạn");
		map.put(6, "Từ chối ra");
		map.put(7, "Từ chối vào");
		map.put(8, "Vào quá hạn");
		map.put(9, "Không vào");
		return Collections.unmodifiableMap(map);
	}

	/** STATUS OF CAR BOOKING */
	public static final Map<Integer, String> STATUS_CAR_BOOKING = initMapCarBooking();

	private static Map<Integer, String> initMapCarBooking() {
		Map<Integer, String> map = new HashMap<>();
		map.put(0, "Chờ phê duyệt");
		map.put(1, "Phê duyệt");
		map.put(2, "Từ chối");
		map.put(3, "Hủy đặt xe");
		map.put(4, "Đã xếp xe");
		map.put(5, "Bắt đầu đi");
		map.put(6, "Đến nơi");
		map.put(7, "Hoàn thành");
		map.put(8, "Quá hạn");
		map.put(11, "Đã ghép xe");
		return Collections.unmodifiableMap(map);
	}

	/** STATUS OF ISSUE SERVICE */
	public static final Map<Integer, String> STATUS_ISSUE_SERVICE = initMapIssueService();

	private static Map<Integer, String> initMapIssueService() {
		Map<Integer, String> map = new HashMap<>();
		map.put(0, "Chờ phê duyệt");
		map.put(1, "Phê duyệt");
		map.put(2, "Từ chối duyệt");
		map.put(3, "Đang thực hiện");
		map.put(4, "Hoãn thực hiện");
		map.put(5, "Không thực hiện được");
		map.put(6, "Hoàn thành");
		map.put(7, "Hủy đơn");
		map.put(8, "Tiếp nhận bàn giao");
		map.put(9, "Từ chối bàn giao");
		return Collections.unmodifiableMap(map);
	}
}
