package com.test.springboot.core;

public enum StatusCode {

	/** 返回正确 **/
	SUCCESS("0", "success"),

	/** 返回异常 **/
	ERROR("-1", "服务器内部错误"),

	/** 请求相关错误 **/
	ERROR_403("403", "禁止访问"),
	ERROR_404("404", "资源不存在"),
	ERROR_405("405", "请求方法不支持"),
	ERROR_406("406", "您无权进行该操作"),
	ERROR_407("407", "未登录，无法授权信息"),
	ERROR_408("408", "登录过期，无法授权信息"),
	ERROR_409("409", "超级管理员不能进行APP操作"),
	ERROR_410("410", "您无权进行APP登录"),
	ERROR_411("411","未认证用户无法访问"),

	/** 程序相关错误 **/
	ERROR_500("500", "程序错误"),

	/** 请求头相关错误 **/
	ERROR_TOKEN_EMPTY("600", "缺少token"),
	ERROR_LACK_PARAM("601", "缺少参数"),
	ERROR_PARAM_DECIPHERING("602", "参数解密失败"),
	ERROR_PARAM("603", "参数错误"),
	ERROR_COOKIE("607", "cookie不存在"),

	/** 未找到相应记录相关错误 **/
	ERROR_NO_RECORD("604","未找到相应记录"),
	ERROR_ICNO("605","身份证号码位数错误"),
	ERROR_APPLY_STATUS("606","修改申请单状态错误"),
	ERROR_APPLY_NO_PICTURE("607","存在必填照片未采集"),

	/** 验证码相关错误 **/
	ERROR_PIC_CODE("700", "图片验证码错误"),
	ERROR_MOBILE_CODE("701", "短信验证码错误"),
	ERROR_MOBILE_CODE_TYPE("702", "短信验证码类型错误"),
	ERROR_MOBILE_CODE_EXPIRED("703", "短信验证码已失效，请重新发送"),
	ERROR_MOBILE_CODE_SEND_LIMIT("704", "短信验证码发送次数过多，请稍后发送"),
	ERROR_MOBILE_CODE_EMPTY("705", "短信验证码为空"),
	ERROR_PIC_EMPTY("706", "图形验证码为空"),
	ERROR_OCR_RECOGNITION_SEND_LIMIT("707", "营业执照ocr识别次数过多，请稍后再试"),

	/** 账户相关错误 **/
	ERROR_USER_OTHER_DEVICE_IS_LOGIN("800", "用户另外一台设备登录，请重新登录"),
	ERROR_USER_TOKEN_EXPIRED("801", "用户信息过期，请重新登录"),
	ERROR_USER_TOKEN_DISABLED("802", "用户信息失效，请重新登录"),
	ERROR_MOBILE_NOT_EXISTS("803", "手机号未注册"),
	ERROR_MOBILE_FORMAT("804", "请输入正确的手机号"),
	ERROR_ACCOUNT_LOCK_OR_LEAVE("805", "账户锁定或者已经离职，请联系管理员"),
	ERROR_OLD_PWD_EMPTY("806", "请输入旧密码"),
	ERROR_NEW_PWD_EMPTY("807", "请输入新密码"),
	ERROR_CONFIRM_PWD_EMPTY("808", "请输入确认新密码"),
	ERROR_PWD_NOT_EQUALS("809", "2次密码输入不一致"),
	ERROR_ACCOUNT_NOT_EXIST("810", "账户不存在"),
	ERROR_PWD_VALIDATE("811", "密码校验不通过"),
	ERROR_MOBILE_EMPTY("812", "手机号为空"),
	ERROR_ACCOUNT_EXIST("813", "账户已存在"),
	ERROR_ACCOUNT_CAN_NOT_LOCK("814", "账户不可锁定状态"),
	ERROR_ACCOUNT_CAN_NOT_UNLOCK("815", "账户不可解除锁定状态"),
	ERROR_ACCOUNT_CAN_NOT_LEAVE("816", "账户不可离职状态"),
	ERROR_USER_NAME_EXIST("817", "工号已存在"),
	ERROR_MOBILE_PHONE_EXIST("818", "手机号已注册"),
	ERROR_ID_CARD_NAME_EXIST("819", "身份证已存在"),
	ERROR_USER_NAME_EMPTY("820", "工号为空"),
	ERROR_PWD_EMPTY("821", "密码为空"),
	ERROR_PWD_SAMPLE("822", "密码强度不够"),
	ERROR_BASE_ROLES_CAN_NOT_MODIFY("823", "基础角色不允许修改"),
	ERROR_STORE_NAME_EXISTS("824", "门店名称已存在"),
	ERROR_CAN_NOT_DEL("825", "系统初始化角色不能删除"),
	ERROR_USER_NOT_EXIST("826","用户信息不存在"),
	ERROR_USER_WAIT_VERIFY("827","等待提交公司资料认证"),
	ERROR_USER_IS_AUDIT("828","账户审核中,请等待通知"),
	ERROR_USER_AUDIT_FAIL("829","审核失败"),
    ERROR_SUBMIT_OVERMUCH("830","提交次数过多"),
	ERROR_ACCOUNT_LOCK("831","企业账户已锁定"),
    ERROR_PWD_FORMAT("832","密码格式不正确"),
	ERROR_PWD_LOGIN("833","密码错误次数大于5次,请在15分钟后重试"),

	/** 消息通知相关错误 **/
	ERROR_NOTIC_NID_EMPTY("900", "NID类型为空"),
	ERROR_NOTIC_TYPE_EMPTY("901", "类型为空"),
	ERROR_NOTIC_RECEIVE_USER_EMPTY("902", "接收者为空"),
	ERROR_NOTIC_RECEIVE_ADDRESS_EMPTY("903", "接收地址为空"),
	ERROR_NOTIC_TYPE_NOT_CONFIG("904", "类型未配置"),

	/** 配置相关错误 **/
	ERROR_NID_EXIST("1000", "NID类型已存在"),
	ERROR_TYPE_NOT_EXIST("1001", "资源类型不存在"),
	ERROR_VIDOE_TYPE_NOT_EXIST("1002", "视频类型不存在"),
	ERROR_SECOND_VERIFY_CLOSE("1003", "复审已关闭，终审不能设置开启"),
	ERROR_SECOND_VERIFY_MORE_LAST_VERIFY("1004", "复审金额不能大于终审金额"),

	/** 申请单相关错误 **/
	ERROR_REDAY_REPAY_SUCCESS("2000", "该申请单已还清"),

	/** 又拍云相关错误 **/
	ERROR_UP_YUN_POLICY("3000", "又拍云policy获取失败"),

	/**支付接口专用**/
	ERROR_PAY("4000","缺少金额"),
	ERROR_PAY_TYPE("4011","缺少充值类型"),
	ERROR_POST_FORM_EMPTY("4001","远程调用支付宝接口错误"),

	/***/
	ERROR_NOT_ENOUGH_MONEY("4002","账户余额不足"),
	ERROR_OCR("4003","OCR识别失败"),
	ERROR_ZHIMAVERIFY("4004","芝麻信用查询出错"),
	;

	private String code;
	private String msg;

	StatusCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
