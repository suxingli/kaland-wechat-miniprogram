package com.kaland.wechat.miniprogram.api;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.kaland.wechat.miniprogram.MessageResource;
import com.kaland.wechat.miniprogram.bean.UserSession;
import com.kaland.wechat.miniprogram.factory.HttpExplorerFactory;

import jxl.httpclient.HttpExplorer;
import jxl.httpclient.exception.HttpException;
import jxl.httpclient.request.HttpGetRequest;
import jxl.httpclient.response.HttpResponse;
import net.sf.json.JSONObject;

/**
 * 用户接口
 * @author 苏行利
 * @date 2019-10-09 09:57:34
 */
public class UserApi {
	private static final HttpExplorer explorer = HttpExplorerFactory.getHttpExplorer();
	private static final String sns_jscode2session = "https://api.weixin.qq.com/sns/jscode2session";
	private static final String get_paidunionid = "https://api.weixin.qq.com/wxa/getpaidunionid";

	/**
	 * 授权
	 * @author 苏行利
	 * @param appid 小程序唯一凭证
	 * @param secret 小程序唯一凭证密钥
	 * @param js_code 登录时获取的code
	 * @return 用户会话
	 * @throws HttpException
	 * @date 2019-10-09 09:57:58
	 */
	public static UserSession authorize(String appid, String secret, String js_code) throws HttpException {
		HttpGetRequest request = new HttpGetRequest(sns_jscode2session);
		request.addLinkParam("appid", appid);
		request.addLinkParam("secret", secret);
		request.addLinkParam("js_code", js_code);
		request.addLinkParam("grant_type", "authorization_code");
		HttpResponse response = explorer.doGet(request);
		if (response.isOKStatus()) {
			JSONObject result = JSONObject.fromObject(response.getContent());
			if (result.has("errcode") && result.getInt("errcode") != 0) {
				throw new HttpException(result.getInt("errcode"), MessageResource.getMsg(result.getInt("errcode"), result.getString("errmsg")));
			}
			UserSession o = new UserSession();
			o.setOpenid(result.getString("openid"));
			o.setSession_key(result.getString("session_key"));
			o.setUnionid(result.has("unionid") ? result.getString("unionid") : null);
			return o;
		}
		throw new HttpException(response.getStatusCode(), response.getReasonPhrase());
	}

	/**
	 * 获取用户的UnionId(无需用户授权，调用前需要用户完成支付，且在支付后的五分钟内有效)
	 * @author 苏行利
	 * @param access_token 接口调用凭证
	 * @param openid 支付用户唯一标识
	 * @param transaction_id 微信支付订单号
	 * @return 用户的UnionId
	 * @throws HttpException
	 * @date 2019-10-09 09:58:37
	 */
	public static String getPaidUnionId(String access_token, String openid, String transaction_id) throws HttpException {
		HttpGetRequest request = new HttpGetRequest(get_paidunionid);
		request.addLinkParam("access_token", access_token);
		request.addLinkParam("openid", openid);
		request.addLinkParam("transaction_id", transaction_id);
		HttpResponse response = explorer.doGet(request);
		if (response.isOKStatus()) {
			JSONObject result = JSONObject.fromObject(response.getContent());
			if (result.has("errcode") && result.getInt("errcode") != 0) {
				throw new HttpException(result.getInt("errcode"), MessageResource.getMsg(result.getInt("errcode"), result.getString("errmsg")));
			}
			return result.getString("unionid");
		}
		throw new HttpException(response.getStatusCode(), response.getReasonPhrase());
	}

	/**
	 * 获取用户的UnionId(无需用户授权，调用前需要用户完成支付，且在支付后的五分钟内有效)
	 * @author 苏行利
	 * @param access_token 接口调用凭证
	 * @param openid 支付用户唯一标识
	 * @param mch_id 微信支付分配的商户号
	 * @param out_trade_no 微信支付商户订单号
	 * @return 用户的UnionId
	 * @throws HttpException
	 * @date 2019-10-09 09:59:01
	 */
	public static String getPaidUnionId(String access_token, String openid, String mch_id, String out_trade_no) throws HttpException {
		HttpGetRequest request = new HttpGetRequest(get_paidunionid);
		request.addLinkParam("access_token", access_token);
		request.addLinkParam("openid", openid);
		request.addLinkParam("mch_id", mch_id);
		request.addLinkParam("out_trade_no", out_trade_no);
		HttpResponse response = explorer.doGet(request);
		if (response.isOKStatus()) {
			JSONObject result = JSONObject.fromObject(response.getContent());
			if (result.has("errcode") && result.getInt("errcode") != 0) {
				throw new HttpException(result.getInt("errcode"), MessageResource.getMsg(result.getInt("errcode"), result.getString("errmsg")));
			}
			return result.getString("unionid");
		}
		throw new HttpException(response.getStatusCode(), response.getReasonPhrase());
	}

	/**
	 * 解密用户手机号码
	 * @author 苏行利
	 * @param encrypted 加密数据
	 * @param session_key 加密秘钥
	 * @param iv 偏移量
	 * @return 解密后的手机号码
	 * @throws HttpException
	 * @date 2019-10-09 09:59:27
	 */
	public static String decryptPhoneNumber(String encrypted, String session_key, String iv) throws HttpException {
		byte[] dbytes = Base64.decodeBase64(encrypted); // 加密数据
		byte[] kbttes = Base64.decodeBase64(session_key); // 加密秘钥
		byte[] ivByte = Base64.decodeBase64(iv); // 偏移量
		int n = 16;
		if (kbttes.length % n != 0) {
			byte[] bytes = new byte[(kbttes.length / n + (kbttes.length % n != 0 ? 1 : 0)) * n];
			Arrays.fill(bytes, (byte) 0);
			System.arraycopy(kbttes, 0, bytes, 0, kbttes.length);
			kbttes = bytes;
		}
		try {
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec spec = new SecretKeySpec(kbttes, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] rbytes = cipher.doFinal(dbytes);
			if (rbytes == null || rbytes.length <= 0) {
				return null;
			}
			return JSONObject.fromObject(new String(rbytes, "UTF-8")).getString("purePhoneNumber");
		} catch (Exception e) {
			throw new HttpException(-1, "解密手机号码失败");
		}
	}

}
