package com.jurassic.iwork.domain;

import com.easemob.chat.EMContact;

public class User extends EMContact {
	private int unreadMsgCount;
	private String header;

	private String UserId;
	private String Account;
	private String RealName;
	private String Alias;
	private String DepartmentId;
	private Integer HeadPic;
	private Integer Gender;
	private String Signature;
	private String OfficePhone;
	private String Email;
	private String Mobile;
	private Integer SortCode;
	private String Description;
	private Integer Enabled;

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

	public String getAccount() {
		return Account;
	}

	public void setAccount(String Account) {
		this.Account = Account;
	}

	public String getRealName() {
		return RealName;
	}

	public void setRealName(String RealName) {
		this.RealName = RealName;
	}

	public String getAlias() {
		return Alias;
	}

	public void setAlias(String Alias) {
		this.Alias = Alias;
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

	public String getDepartmentId() {
		return DepartmentId;
	}

	public void setDepartmentId(String DepartmentId) {
		this.DepartmentId = DepartmentId;
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

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String Mobile) {
		this.Mobile = Mobile;
	}

	public Integer getSortCode() {
		return SortCode;
	}

	public void setSortCode(Integer SortCode) {
		this.SortCode = SortCode;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}

	public Integer getEnabled() {
		return Enabled;
	}

	public void setEnabled(Integer Enabled) {
		this.Enabled = Enabled;
	}
}