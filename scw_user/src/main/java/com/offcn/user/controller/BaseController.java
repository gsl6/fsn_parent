package com.offcn.user.controller;

import com.offcn.enums.ResponseCodeEnume;
import com.offcn.response.AppResponse;

public class BaseController implements NetWorkCallback<Object> {
	@Override
	public AppResponse<Object> ok(Object data) {
		AppResponse<Object> resp=new AppResponse<Object>();
		resp.setCode(ResponseCodeEnume.SUCCESS.getCode());
		resp.setMsg(ResponseCodeEnume.SUCCESS.getMsg());
		resp.setData(data);
		return resp;
	}

	@Override
	public AppResponse<Object> fail(Object data) {
		AppResponse<Object> resp=new AppResponse<>();
		resp.setCode(ResponseCodeEnume.FAIL.getCode());
		resp.setMsg(ResponseCodeEnume.FAIL.getMsg());
		resp.setData(data);
		return resp;
	}
}
