package com.kaland.wechat.miniprogram;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 消息资源
 * @author 苏行利
 * @date 2019-10-09 09:46:29
 */
public class MessageResource {
	private static Properties prop = new Properties(); // 配置

	static {
		try {
			prop.load(new InputStreamReader(MessageResource.class.getResourceAsStream("/com/kaland/wechat/miniprogram/message_resource.properties"), "UTF-8")); // 加载消息资源
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取结果信息
	 * @author 苏行利
	 * @param code 结果码
	 * @return 结果信息
	 * @date 2019-10-09 09:46:36
	 */
	public static String getMsg(String code) {
		return prop.getProperty(code);
	}

	/**
	 * 获取结果信息
	 * @author 苏行利
	 * @param code 结果码
	 * @param default_msg 默认信息
	 * @return 结果信息
	 * @date 2019-10-09 09:46:49
	 */
	public static String getMsg(Integer code, String default_msg) {
		return getMsg(code.toString()) == null ? default_msg : getMsg(code.toString());
	}

	/**
	 * 获取结果信息
	 * @author 苏行利
	 * @param code 结果码
	 * @param default_msg 默认信息
	 * @return 结果信息
	 * @date 2019-10-09 09:47:06
	 */
	public static String getMsg(String code, String default_msg) {
		return getMsg(code) == null ? default_msg : getMsg(code);
	}

}
