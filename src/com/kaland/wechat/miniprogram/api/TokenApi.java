package com.kaland.wechat.miniprogram.api;

import com.kaland.wechat.miniprogram.MessageResource;
import com.kaland.wechat.miniprogram.bean.AccessToken;
import com.kaland.wechat.miniprogram.factory.HttpExplorerFactory;

import jxl.httpclient.HttpExplorer;
import jxl.httpclient.exception.HttpException;
import jxl.httpclient.request.HttpGetRequest;
import jxl.httpclient.response.HttpResponse;
import net.sf.json.JSONObject;

/**
 * 凭证接口
 * @author 苏行利
 * @date 2019-10-09 09:56:40
 */
public class TokenApi {
	private static final HttpExplorer explorer = HttpExplorerFactory.getHttpExplorer();
	private static final String get_access_token = "https://api.weixin.qq.com/cgi-bin/token";

	/**
	 * 获取接口调用凭证
	 * @author 苏行利
	 * @param appid 小程序唯一凭证
	 * @param secret 小程序唯一凭证密钥
	 * @return 接口调用凭证
	 * @throws HttpException
	 * @date 2019-10-09 09:56:45
	 */
	public static AccessToken getAccessToken(String appid, String secret) throws HttpException {
		HttpGetRequest request = new HttpGetRequest(get_access_token);
		request.addLinkParam("appid", appid);
		request.addLinkParam("secret", secret);
		request.addLinkParam("grant_type", "client_credential");
		HttpResponse response = explorer.doGet(request);
		if (response.isOKStatus()) {
			JSONObject result = JSONObject.fromObject(response.getContent());
			if (result.has("errcode") && result.getInt("errcode") != 0) {
				throw new HttpException(result.getInt("errcode"), MessageResource.getMsg(result.getInt("errcode"), result.getString("errmsg")));
			}
			AccessToken o = new AccessToken();
			o.setAccess_token(result.getString("access_token"));
			o.setExpires_in(result.getInt("expires_in"));
			return o;
		}
		throw new HttpException(response.getStatusCode(), response.getReasonPhrase());
	}

}
