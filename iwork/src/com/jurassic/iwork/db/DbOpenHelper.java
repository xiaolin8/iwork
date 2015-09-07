package com.jurassic.iwork.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jurassic.applib.controller.HXSDKHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static DbOpenHelper instance;
	private SQLiteDatabase db;

	private static final String USERNAME_TABLE_CREATE = "CREATE TABLE "
			+ UserDao.TABLE_NAME + " (" + UserDao.COLUMN_USER_NAME + " TEXT, "
			+ UserDao.COLUMN_ENGLISH_NAME + " TEXT, " + UserDao.COLUMN_DEP_ID
			+ " TEXT, " + UserDao.COLUMN_GENDER + " INTEGER, "
			+ UserDao.COLUMN_SIGNATURE + " TEXT, " + UserDao.COLUMN_HEAD_PIC
			+ " INTEGER, " + UserDao.COLUMN_CELL_PHONE + " TEXT, "
			+ UserDao.COLUMN_OFFICE_PHONE + " TEXT, " + UserDao.COLUMN_EMAIL
			+ " TEXT, " + UserDao.COLUMN_POSITION + " TEXT, "
			+ UserDao.COLUMN_DISPLAY_INDEX + " INTEGER, "
			+ UserDao.COLUMN_STATUS + " INTEGER, " + UserDao.COLUMN_USER_ID
			+ " TEXT PRIMARY KEY);";

	private static final String INIVTE_MESSAGE_TABLE_CREATE = "CREATE TABLE "
			+ InviteMessgeDao.TABLE_NAME + " ("
			+ InviteMessgeDao.COLUMN_NAME_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ InviteMessgeDao.COLUMN_NAME_FROM + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_GROUP_ID + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_GROUP_Name + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_REASON + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_STATUS + " INTEGER, "
			+ InviteMessgeDao.COLUMN_NAME_ISINVITEFROMME + " INTEGER, "
			+ InviteMessgeDao.COLUMN_NAME_TIME + " TEXT); ";

	private static final String MEETING_ROOM_TABLE_CREATE = "CREATE TABLE "
			+ MeetingRoomDao.TABLE_NAME + " (" + MeetingRoomDao.COLUMN_ROOM_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MeetingRoomDao.COLUMN_ROOM_NAME + " TEXT, "
			+ MeetingRoomDao.COLUMN_ROOM_ADDR + " TEXT, "
			+ MeetingRoomDao.COLUMN_ROOM_CAPACITY + " INTEGER, "
			+ MeetingRoomDao.COLUMN_ROOM_DESC + " TEXT, "
			+ MeetingRoomDao.COLUMN_COMP_ID + " TEXT, "
			+ MeetingRoomDao.COLUMN_PHONE + " TEXT, "
			+ MeetingRoomDao.COLUMN_EQUIPMENTS + " TEXT); ";

	private DbOpenHelper(Context context) {
		super(context, getUserDatabaseName(), null, DATABASE_VERSION);
	}

	public static DbOpenHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DbOpenHelper(context.getApplicationContext());
		}
		return instance;
	}

	private static String getUserDatabaseName() {
		return "iwork.db";
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(USERNAME_TABLE_CREATE);
		db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);
		db.execSQL(MEETING_ROOM_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void closeDB() {
		if (instance != null) {
			try {
				SQLiteDatabase db = instance.getWritableDatabase();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			instance = null;
		}
	}

	/**
	 * 检测表是否存在
	 * 
	 * @param tableName
	 * @param openDb
	 * @return
	 */
	public boolean isTableExists(String tableName) {
		if (instance != null) {
			if (db == null || !db.isOpen()) {
				db = instance.getWritableDatabase();
			}
			if (!db.isReadOnly()) {
				db.close();
				db = getReadableDatabase();
			}
		}
		Cursor cursor = db.rawQuery(
				"select DISTINCT tbl_name from sqlite_master where tbl_name = '"
						+ tableName + "'", null);
		if (cursor != null) {
			if (cursor.getCount() > 0) {
				cursor.close();
				return true;
			}
			cursor.close();
		}
		return false;
	}
}
