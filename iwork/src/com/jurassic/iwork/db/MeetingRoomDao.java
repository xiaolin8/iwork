package com.jurassic.iwork.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jurassic.iwork.Constant;
import com.jurassic.iwork.domain.MeetingRoom;
import com.jurassic.iwork.domain.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MeetingRoomDao {

	public static final String TABLE_NAME = "MeetingRoom";
	public static final String COLUMN_ROOM_ID = "RoomId";
	public static final String COLUMN_ROOM_NAME = "RoomName";
	public static final String COLUMN_ROOM_ADDR = "RoomAddr";
	public static final String COLUMN_ROOM_CAPACITY = "RoomCapacity";
	public static final String COLUMN_ROOM_DESC = "RoomDesc";
	public static final String COLUMN_COMP_ID = "CompId";
	public static final String COLUMN_PHONE = "Phone";
	public static final String COLUMN_EQUIPMENTS = "Equipments";

	public DbOpenHelper dbHelper;

	public MeetingRoomDao(Context context) {
		dbHelper = DbOpenHelper.getInstance(context);
	}

	/**
	 * 获取会议室list
	 * 
	 * @return
	 */
	public List<MeetingRoom> getMeetingRoomList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<MeetingRoom> rooms = new ArrayList<MeetingRoom>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);

			while (cursor.moveToNext()) {
				Integer roomid = cursor.getInt(cursor
						.getColumnIndex(COLUMN_ROOM_ID));
				String roomname = cursor.getString(cursor
						.getColumnIndex(COLUMN_ROOM_NAME));
				String roomaddr = cursor.getString(cursor
						.getColumnIndex(COLUMN_ROOM_ADDR));
				Integer roomcapacity = cursor.getInt(cursor
						.getColumnIndex(COLUMN_ROOM_CAPACITY));
				String roomdesc = cursor.getString(cursor
						.getColumnIndex(COLUMN_ROOM_DESC));
				String compid = cursor.getString(cursor
						.getColumnIndex(COLUMN_COMP_ID));
				String phone = cursor.getString(cursor
						.getColumnIndex(COLUMN_PHONE));
				String equipments = cursor.getString(cursor
						.getColumnIndex(COLUMN_EQUIPMENTS));
				MeetingRoom room = new MeetingRoom();
				room.setRoomId(roomid);
				room.setRoomName(roomname);
				room.setRoomAddr(roomaddr);
				room.setRoomCapacity(roomcapacity);
				room.setRoomDesc(roomdesc);
				room.setCompId(compid);
				room.setPhone(phone);
				room.setEquipments(equipments);
				rooms.add(room);
			}
			cursor.close();
		}
		return rooms;
	}

	/**
	 * 保存会议室list
	 * 
	 * @param MeetingRoom
	 */
	public void saveMeetingRoomList(List<MeetingRoom> meetingRoomList) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(TABLE_NAME, null, null);
			for (MeetingRoom meetingRoom : meetingRoomList) {
				ContentValues values = new ContentValues();
				values.put(COLUMN_ROOM_ID, meetingRoom.getRoomId());
				if (meetingRoom.getRoomName() != null)
					values.put(COLUMN_ROOM_NAME, meetingRoom.getRoomName());
				if (meetingRoom.getRoomAddr() != null)
					values.put(COLUMN_ROOM_ADDR, meetingRoom.getRoomAddr());
				if (meetingRoom.getRoomCapacity() != -1)
					values.put(COLUMN_ROOM_CAPACITY,
							meetingRoom.getRoomCapacity());
				if (meetingRoom.getRoomDesc() != null)
					values.put(COLUMN_ROOM_DESC, meetingRoom.getRoomDesc());
				if (meetingRoom.getCompId() != null)
					values.put(COLUMN_COMP_ID, meetingRoom.getCompId());
				if (meetingRoom.getPhone() != null)
					values.put(COLUMN_PHONE, meetingRoom.getPhone());
				if (meetingRoom.getEquipments() != null)
					values.put(COLUMN_EQUIPMENTS, meetingRoom.getEquipments());
				db.replace(TABLE_NAME, null, values);
			}
		}
	}

	/**
	 * 凭借roomId获取一个会议室详细信息
	 * 
	 * @param roomId
	 */
	public MeetingRoom getMeetingRoomById(Integer roomId) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + TABLE_NAME
					+ " WHERE " + COLUMN_ROOM_ID + " = ?",
					new String[] { roomId.toString() });

			while (cursor.moveToNext()) {
				String RoomName = cursor.getString(cursor
						.getColumnIndex(COLUMN_ROOM_NAME));
				String RoomAddr = cursor.getString(cursor
						.getColumnIndex(COLUMN_ROOM_ADDR));
				Integer RoomCapacity = cursor.getInt(cursor
						.getColumnIndex(COLUMN_ROOM_CAPACITY));
				String RoomDesc = cursor.getString(cursor
						.getColumnIndex(COLUMN_ROOM_DESC));
				String CompId = cursor.getString(cursor
						.getColumnIndex(COLUMN_COMP_ID));
				String Phone = cursor.getString(cursor
						.getColumnIndex(COLUMN_PHONE));
				String Equipments = cursor.getString(cursor
						.getColumnIndex(COLUMN_EQUIPMENTS));
				MeetingRoom room = new MeetingRoom();
				room.setRoomId(roomId);
				room.setRoomName(RoomName);
				room.setRoomAddr(RoomAddr);
				room.setRoomCapacity(RoomCapacity);
				room.setRoomDesc(RoomDesc);
				room.setCompId(CompId);
				room.setPhone(Phone);
				room.setEquipments(Equipments);
				return room;
			}
			return null;
		}
		return null;
	}
}
