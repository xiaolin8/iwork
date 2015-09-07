package com.jurassic.iwork.domain;

import com.easemob.chat.EMContact;

public class User extends EMContact {
	private int unreadMsgCount;
	private String header;

	private String UserId;
	private String UserName;
	private String EnglishName;
	private String DeptId;
	private Integer HeadPic;
	private Integer Gender;
	private String Signature;
	private String OfficePhone;
	private String Email;
	private String CellPhone;
	private String Position;
	private Integer DisplayIndex;
	private String Comment;
	private Integer Status;

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public int getUnreadMsgCount() {
		return unreadMsgCount;
	}

	public void setUnreadMsgCount(int unreadMsgCount) {
		this.unreadMsgCount = unreadMsgCount;
	}

	@Override
	public int hashCode() {
		return 17 * getUsername().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof User)) {
			return false;
		}
		return getUsername().equals(((User) o).getUsername());
	}

	@Override
	public String toString() {
		return nick == null ? username : nick;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String UserId) {
		this.UserId = UserId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String UserName) {
		this.UserName = UserName;
	}

	public String getEnglishName() {
		return EnglishName;
	}

	public void setEnglishName(String EnglishName) {
		this.EnglishName = EnglishName;
	}

	public String getSignature() {
		return Signature;
	}

	public void setSignature(String Signature) {
		this.Signature = Signature;
	}

	public Integer getHeadPic() {
		return HeadPic;
	}

	public void setHeadPic(Integer HeadPic) {
		this.HeadPic = HeadPic;
	}

	public Integer getGender() {
		return Gender;
	}

	public void setGender(Boolean Gender) {
		this.Gender = (Gender == true ? 1 : 0);
	}

	public String getDeptId() {
		return DeptId;
	}

	public void setDeptId(String DeptId) {
		this.DeptId = DeptId;
	}

	public String getOfficePhone() {
		return OfficePhone;
	}

	public void setOfficePhone(String OfficePhone) {
		this.OfficePhone = OfficePhone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String Email) {
		this.Email = Email;
	}

	public String getCellPhone() {
		return CellPhone;
	}

	public void setCellPhone(String CellPhone) {
		this.CellPhone = CellPhone;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String Position) {
		this.Position = Position;
	}

	public Integer getDisplayIndex() {
		return DisplayIndex;
	}

	public void setDisplayIndex(Integer DisplayIndex) {
		this.DisplayIndex = DisplayIndex;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String Comment) {
		this.Comment = Comment;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer Status) {
		this.Status = Status;
	}
}