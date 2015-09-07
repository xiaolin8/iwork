package com.jurassic.iwork.domain;

public class MeetingRoom {

	private Integer RoomId;
	private String RoomName;
	private String RoomAddr;
	private Integer RoomCapacity;
	private String RoomDesc;
	private String CompId;
	private String Phone;
	private String Equipments;

	public int getRoomId() {
		return RoomId;
	}

	public void setRoomId(int RoomId) {
		this.RoomId = RoomId;
	}

	public String getRoomName() {
		return RoomName;
	}

	public void setRoomName(String RoomName) {
		this.RoomName = RoomName;
	}

	public String getRoomAddr() {
		return RoomAddr;
	}

	public void setRoomAddr(String RoomAddr) {
		this.RoomAddr = RoomAddr;
	}

	public int getRoomCapacity() {
		return RoomCapacity;
	}

	public void setRoomCapacity(int RoomCapacity) {
		this.RoomCapacity = RoomCapacity;
	}

	public String getRoomDesc() {
		return RoomDesc;
	}

	public void setRoomDesc(String RoomDesc) {
		this.RoomDesc = RoomDesc;
	}

	public String getCompId() {
		return CompId;
	}

	public void setCompId(String CompId) {
		this.CompId = CompId;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String Phone) {
		this.Phone = Phone;
	}

	public String getEquipments() {
		return Equipments;
	}

	public void setEquipments(String Equipments) {
		this.Equipments = Equipments;
	}

}
