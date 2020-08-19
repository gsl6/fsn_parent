package com.offcn.user.controller;

import com.offcn.enums.ResponseCodeEnume;
import com.offcn.response.AppResponse;

public interface NetWorkCallback<T> {
	AppResponse<T> ok(T data);
	AppResponse<T> fail(T data);

	/*public static<T> AppResponse<T> ok(T data){
		AppResponse<T> resp=new AppResponse<T>();
		resp.setCode(ResponseCodeEnume.SUCCESS.getCode());
		resp.setMsg(ResponseCodeEnume.SUCCESS.getMsg());
		resp.setData(data);
		return resp;
	}

	public static<T> AppResponse<T> fail(T data){
		AppResponse<T> resp=new AppResponse<>();
		resp.setCode(ResponseCodeEnume.FAIL.getCode());
		resp.setMsg(ResponseCodeEnume.FAIL.getMsg());
		resp.setData(data);
		return resp;
	}*/
}
