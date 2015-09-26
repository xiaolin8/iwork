package com.jurassic.iwork.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.jurassic.iwork.Constant;
import com.jurassic.iwork.domain.User;
import com.easemob.util.HanziToPinyin;

public class UserDao {

	public static final String TABLE_NAME = "users";
	public static final String COLUMN_USER_ID = "UserId";
	public static final String COLUMN_USER_NAME = "UserName";
	public static final String COLUMN_ENGLISH_NAME = "EnglishName";
	public static final String COLUMN_DEP_ID = "DeptId";
	public static final String COLUMN_HEAD_PIC = "HeadPic";
	public static final String COLUMN_GENDER = "Gender";
	public static final String COLUMN_SIGNATURE = "Signature";// 个性签名
	public static final String COLUMN_CELL_PHONE = "CellPhone";
	public static final String COLUMN_OFFICE_PHONE = "OfficePhone";
	public static final String COLUMN_EMAIL = "Email";
	public static final String COLUMN_POSITION = "Position";
	public static final String COLUMN_DISPLAY_INDEX = "DisplayIndex";
	public static final String COLUMN_STATUS = "Status";
	public static final String COLUMN_CMMMENT = "Comment";

	private DbOpenHelper dbHelper;

	public UserDao(Context context) {
		dbHelper = DbOpenHelper.getInstance(context);
	}

	/**
	 * 保存好友list
	 * 
	 * @param contactList
	 */
	public void saveContactList(List<User> contactList) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(TABLE_NAME, null, null);
			for (User user : contactList) {
				ContentValues values = new ContentValues();
				values.put(COLUMN_USER_ID, user.getUserId());
				if (user.getAccount() != null)
					values.put(COLUMN_USER_NAME, user.getAccount());
				if (user.getAlias() != null)
					values.put(COLUMN_ENGLISH_NAME, user.getAlias());
				if (user.getDepartmentId() != null)
					values.put(COLUMN_DEP_ID, user.getDepartmentId());
				if (user.getGender() != null)
					values.put(COLUMN_GENDER, user.getGender());
				if (user.getSignature() != null)
					values.put(COLUMN_SIGNATURE, user.getSignature());
				if (user.getHeadPic() != null)
					values.put(COLUMN_HEAD_PIC, user.getHeadPic());
				if (user.getMobile() != null)
					values.put(COLUMN_CELL_PHONE, user.getMobile());
				if (user.getOfficePhone() != null)
					values.put(COLUMN_OFFICE_PHONE, user.getOfficePhone());
				if (user.getEmail() != null)
					values.put(COLUMN_EMAIL, user.getEmail());
				if (user.getSortCode() != null)
					values.put(COLUMN_POSITION, user.getSortCode());
				if (user.getEnabled() != null)
					values.put(COLUMN_STATUS, user.getEnabled());
				db.replace(TABLE_NAME, null, values);
			}
		}
	}

	/**
	 * 获取好友list
	 * 
	 * @return
	 */
	public Map<String, User> getContactList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Map<String, User> users = new HashMap<String, User>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);

			while (cursor.moveToNext()) {
				String userid = cursor.getString(cursor
						.getColumnIndex(COLUMN_USER_ID));
				String username = cursor.getString(cursor
						.getColumnIndex(COLUMN_USER_NAME));
				String EnglishName = cursor.getString(cursor
						.getColumnIndex(COLUMN_ENGLISH_NAME));
				String DeptId = cursor.getString(cursor
						.getColumnIndex(COLUMN_DEP_ID));
				Integer HeadPic = cursor.getInt(cursor
						.getColumnIndex(COLUMN_HEAD_PIC));
				Integer Gender = cursor.getInt(cursor
						.getColumnIndex(COLUMN_GENDER));
				String Signature = cursor.getString(cursor
						.getColumnIndex(COLUMN_SIGNATURE));
				String OfficePhone = cursor.getString(cursor
						.getColumnIndex(COLUMN_OFFICE_PHONE));
				String Email = cursor.getString(cursor
						.getColumnIndex(COLUMN_EMAIL));
				String CellPhone = cursor.getString(cursor
						.getColumnIndex(COLUMN_CELL_PHONE));
				String Position = cursor.getString(cursor
						.getColumnIndex(COLUMN_POSITION));
				Integer DisplayIndex = cursor.getInt(cursor
						.getColumnIndex(COLUMN_DISPLAY_INDEX));
				Integer Status = cursor.getInt(cursor
						.getColumnIndex(COLUMN_STATUS));
				User user = new User();
				user.setUsername(userid);
				user.setUserId(userid);
				user.setRealName(username);
				user.setNick(username);
				user.setAlias(EnglishName);
				user.setDepartmentId(DeptId);
				user.setHeadPic(HeadPic);
				user.setGender((Gender == 1));
				user.setSignature(Signature);
				user.setMobile(CellPhone);
				user.setOfficePhone(OfficePhone);
				String headerName = user.getUsername();
				if (userid.equals(Constant.NEW_FRIENDS_USERNAME)
						|| userid.equals(Constant.GROUP_USERNAME)) {
					user.setHeader("");
				} else if (Character.isDigit(headerName.charAt(0))) {
					user.setHeader("#");
				} else {
					user.setHeader(userid.substring(0, 1).toUpperCase());
				}
				users.put(userid, user);
			}
			cursor.close();
		}
		return users;
	}

	/**
	 * 删除一个联系人
	 * 
	 * @param userid
	 */
	public void deleteContact(String userid) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(TABLE_NAME, COLUMN_USER_ID + " = ?",
					new String[] { userid });
		}
	}

	/**
	 * 保存一个联系人
	 * 
	 * @param user
	 */
	public void saveContact(User user) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_USER_ID, user.getUserId());
		if (user.getRealName() != null)
			values.put(COLUMN_USER_NAME, user.getRealName());
		if (db.isOpen()) {
			db.replace(TABLE_NAME, null, values);
		}
	}

	/**
	 * 凭借userId获取一个联系人
	 * 
	 * @param userid
	 */
	public User getContactById(String userid) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + TABLE_NAME +" WHERE UserId = ?",
					new String[] { userid });

			while (cursor.moveToNext()) {
				String username = cursor.getString(cursor
						.getColumnIndex(COLUMN_USER_NAME));
				String EnglishName = cursor.getString(cursor
						.getColumnIndex(COLUMN_ENGLISH_NAME));
				String DeptId = cursor.getString(cursor
						.getColumnIndex(COLUMN_DEP_ID));
				Integer HeadPic = cursor.getInt(cursor
						.getColumnIndex(COLUMN_HEAD_PIC));
				Integer Gender = cursor.getInt(cursor
						.getColumnIndex(COLUMN_GENDER));
				String Signature = cursor.getString(cursor
						.getColumnIndex(COLUMN_SIGNATURE));
				String OfficePhone = cursor.getString(cursor
						.getColumnIndex(COLUMN_OFFICE_PHONE));
				String Email = cursor.getString(cursor
						.getColumnIndex(COLUMN_EMAIL));
				String CellPhone = cursor.getString(cursor
						.getColumnIndex(COLUMN_CELL_PHONE));
				String Position = cursor.getString(cursor
						.getColumnIndex(COLUMN_POSITION));
				Integer DisplayIndex = cursor.getInt(cursor
						.getColumnIndex(COLUMN_DISPLAY_INDEX));
				Integer Status = cursor.getInt(cursor
						.getColumnIndex(COLUMN_STATUS));
				User user = new User();
				user.setUsername(userid);
				user.setUserId(userid);
				user.setRealName(username);
				user.setNick(username);
				user.setAlias(EnglishName);
				user.setDepartmentId(DeptId);
				user.setHeadPic(HeadPic);
				user.setGender((Gender == 1));
				user.setSignature(Signature);
				user.setMobile(CellPhone);
				user.setOfficePhone(OfficePhone);
				String headerName = user.getUsername();
				if (userid.equals(Constant.NEW_FRIENDS_USERNAME)
						|| userid.equals(Constant.GROUP_USERNAME)) {
					user.setHeader("");
				} else if (Character.isDigit(headerName.charAt(0))) {
					user.setHeader("#");
				} else {
					user.setHeader(userid.substring(0, 1).toUpperCase());
				}
				return user;
			}
			return null;
		}
		return null;
	}
}
