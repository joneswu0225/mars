package com.jones.mars.model.constant;


public enum MessageStatus {

	UNREAD(0, "未读"),
	READ(1, "已读"),
	DELETE(2, "删除")
	;

	public final Integer key;

	public final String description;

	MessageStatus(Integer key, String description) {
		this.key = key;
		this.description = description;
	}

}
