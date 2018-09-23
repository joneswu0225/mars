package com.jones.mars.model.constant;


import com.jones.mars.exception.MarsException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yue.su
 * @date 2018年3月28日
 */
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
