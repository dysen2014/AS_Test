package com.pactera.financialmanager.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.app.MyApplication;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.model.User;
import com.pactera.financialmanager.ui.notice.NoticeService;
import com.pactera.financialmanager.util.Tool;

/**
 * @Name：LogoActivity
 * @Description：logo动画
 */

public class LogoActivity extends ParentActivity {
	public static User user;
	public static boolean updateFlag;
	public static boolean noticeFlagShow;
	public static String noticeAllText = "";
	private String certcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Tool.creatRootDir();
		updateFlag = true;
		noticeFlagShow = false;
		try {
			user = new User(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		certcode = LogoActivity.user.getCERTCODE();

		MyApplication.initTimeStamp();
		// 启动通知推送服务
//		startAdvertising();
		prepareErrorCodeInfo();
		startActivity(new Intent(LogoActivity.this, NewLoginActivity.class));
//		startActivity(new Intent(LogoActivity.this, PersonArchiving.class));
		this.finish();
	}

	private void startAdvertising() {
		Intent mService = new Intent(LogoActivity.this, NoticeService.class);
		mService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(mService);
	}
	
	//创建一个错误码说明的表，应用中对系统返回的错误码不必再分别判断了，直接取值显示即可。
	private void prepareErrorCodeInfo() {
		SharedPreferences sp = getSharedPreferences("errorCode", MODE_PRIVATE);
		Editor editor = sp.edit();
		//验证通过
		editor.putString("0000", "验证通过");
		//用户级错误
		editor.putString("1001", "验证参数为空");
		editor.putString("1002", "用户号为空");
		editor.putString("1003", "密码为空");
		editor.putString("1004", "交易号为空");
		editor.putString("1005", "渠道号为空");
		editor.putString("1006", "设备号为空");
		editor.putString("1007", "机构号为空");
		editor.putString("1008", "客户号为空");
		editor.putString("1009", "时间为空");
		editor.putString("1010", "开始时间为空");
		editor.putString("1011", "结束时间为空");
		editor.putString("1012", "时间格式错误");
		editor.putString("1013", "开始时间不能小于结束时间");
		editor.putString("1014", "开始时间不能大于结束时间");
		editor.putString("1015", "格式错误");
		editor.putString("1016", "OCRM用户不存在");
		editor.putString("1017", "密码错误");
		editor.putString("1018", "非法设备");
		editor.putString("1019", "用户名或密码错");
		editor.putString("1020", "本设备未登记，请联系您所在农商行的系统管理员进行设备登记");
		editor.putString("1021", "本设备未领用，请联系您所在农商行的系统管理员进行设备领用");
		editor.putString("1022", "设备维修中");
		editor.putString("1023", "设备报废");
		editor.putString("1024", "设备挂失");
		editor.putString("1025", "用户失效");
		editor.putString("1026", "用户未启用");
		editor.putString("1027", "用户离职");
		editor.putString("1028", "密码失效");
		editor.putString("1029", "密码错误达到限制");
		editor.putString("1030", "密码错误达到累积限制");
		editor.putString("1031", "库存数量小于兑换数量");
		editor.putString("1032", "客户积分不足");
		editor.putString("1033", "登录用户所在机构和设备领用用户机构不同");
		editor.putString("1034", "无该功能权限");		
		//系统级错误
		editor.putString("2000", "非法网络（非3G网络）");
		editor.putString("2001", "返回数据为空");
		editor.putString("2002", "客户端链接超时");
		editor.putString("2003", "BMC链接超时");
		editor.putString("2004", "OCRM链接超时");
		editor.putString("2005", "外围系统链接超时");
		editor.putString("2006", "客户端系统错误");
		editor.putString("2007", "BMC系统错误");
		editor.putString("2008", "OCRM系统错误");
		editor.putString("2009", "外围系统错误");
		editor.putString("2010", "分页错误");
		editor.putString("2011", "XML报文转换错误");
		editor.putString("2012", "字符串转换为XML报文错误");
		//数据库级错误
		editor.putString("3001", "客户端数据库访问失败");
		editor.putString("3002", "BMC数据库访问失败");
		editor.putString("3003", "OCRM数据库访问失败");
		editor.putString("3004", "外围数据库访问失败");
		editor.commit();
	}

}
