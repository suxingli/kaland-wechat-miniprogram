package com.kaland.wechat.miniprogram.factory;

import jxl.httpclient.HttpExplorer;

/**
 * Http浏览器工厂类
 * @author 苏行利
 * @date 2019-10-09 09:48:57
 */
public class HttpExplorerFactory {
	private static HttpExplorer explorer; // Http浏览器

	/**
	 * 获取Http浏览器
	 * @author 苏行利
	 * @return Http浏览器
	 * @date 2019-10-09 09:49:01
	 */
	public static HttpExplorer getHttpExplorer() {
		if (explorer == null) {
			synchronized (HttpExplorerFactory.class) {
				if (explorer == null) {
					return explorer = new HttpExplorer();
				}
			}
		}
		return explorer;
	}
}
