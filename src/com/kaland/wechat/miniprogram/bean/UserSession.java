
package com.kaland.wechat.miniprogram.bean;

/**
 * 用户会话
 * @author 苏行利
 * @date 2019-10-09 09:54:56
 */
public class UserSession {
	private String openid; // 用户唯一标识
	private String session_key; // 会话密钥
	private String unionid; // 用户在开放平台的唯一标识符(在满足 UnionID下发条件的情况下会返回)

	/**
	 * 获取用户唯一标识
	 * @author 苏行利
	 * @return 用户唯一标识
	 * @date 2019-10-09 09:55:00
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * 设置用户唯一标识
	 * @author 苏行利
	 * @param openid 用户唯一标识
	 * @date 2019-10-09 09:55:09
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * 获取会话密钥
	 * @author 苏行利
	 * @return 会话密钥
	 * @date 2019-10-09 09:55:19
	 */
	public String getSession_key() {
		return session_key;
	}

	/**
	 * 设置会话密钥
	 * @author 苏行利
	 * @param session_key 会话密钥
	 * @date 2019-10-09 09:55:30
	 */
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	/**
	 * 获取用户在开放平台的唯一标识符
	 * @author 苏行利
	 * @return 用户在开放平台的唯一标识符(在满足 UnionID下发条件的情况下会返回)
	 * @date 2019-10-09 09:55:40
	 */
	public String getUnionid() {
		return unionid;
	}

	/**
	 * 设置用户在开放平台的唯一标识符
	 * @author 苏行利
	 * @param unionid 用户在开放平台的唯一标识符
	 * @date 2019-10-09 09:55:52
	 */
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
}
