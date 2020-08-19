package com.offcn.user.controller;

import com.offcn.response.AppResponse;
import com.offcn.user.component.SmsTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Api(tags = "用户登录/注册模块（包括忘记密码等）")
@Slf4j
//extends BaseController
public class UserLoginController {

	@Autowired
	private SmsTemplate smsTemplate;

	@Autowired
	private RedisTemplate redisTemplate;




	@ApiOperation("获取注册的验证码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "phoneNo", value = "手机号", required = true)
	})

	@PostMapping("/sendCode")
	public AppResponse<Object> sendCode(String phoneNo) {
		//1、生成验证码保存到服务器，准备用户提交上来进行对比
		String code = UUID.randomUUID().toString().substring(0, 4);
		//2、保存验证码和手机号的对应关系,设置验证码过期时间
		redisTemplate.opsForValue().set(phoneNo, code, 10000, TimeUnit.MINUTES);
		//3、短信发送构造参数
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("mobile", phoneNo);
		querys.put("param", "code:" + code);
		//短信模板
		querys.put("tpl_id", "TP1711063");
		//4、发送短信
		String sendCode = smsTemplate.sendCode(querys);

		if (sendCode.equals("") || sendCode.equals("fail")) {
			return AppResponse.fail(sendCode);
		}
		return AppResponse.ok(sendCode);
//        if (sendCode.equals("") || sendCode.equals("fail")) {
//            //短信失败
//            return fail(sendCode);
//        }
//        return ok(sendCode);
	}




}