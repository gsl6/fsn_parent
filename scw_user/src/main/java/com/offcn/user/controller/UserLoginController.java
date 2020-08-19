package com.offcn.user.controller;

import com.offcn.response.AppResponse;
import com.offcn.user.component.SmsTemplate;
import com.offcn.user.po.TMember;
import com.offcn.user.sevice.UserService;
import com.offcn.user.vo.req.UserRegistVo;
import com.offcn.user.vo.respon.UserRespVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang.StringUtils;
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

	@Autowired
	private UserService userService;





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

	}
	@ApiOperation("用户注册")
	@PostMapping("/regist")
	public AppResponse<Object> regist(UserRegistVo registVo){
		/*1、校验验证码*/
		String code= (String) redisTemplate.opsForValue().get(registVo.getLoginacct());
		if(!StringUtils.isEmpty(code)){
			/*redis中有验证码*/
			boolean b=code.equalsIgnoreCase(registVo.getCode());
			if(b){
				/*2、将vo转业务能用的数据对象*/
				TMember member=new TMember();
				BeanUtils.copyProperties(registVo, member);
				/*3、将用户信息注册到数据库*/
				try {
					userService.registerUser(member);
					log.debug("用户信息注册成功:{}",member.getLoginacct());
					/*4、注册成功后，删除验证码*/
					redisTemplate.delete(registVo.getLoginacct());
					return AppResponse.ok("注册成功");
				} catch (Exception e) {
					log.error("用户信息注册失败:{}",member.getLoginacct());
					return AppResponse.fail(e.getMessage());
				}
			}else {
				return AppResponse.fail("验证码错误");
			}
		}else {
			return AppResponse.fail("验证码过期，请重新获取");
		}

	}



	@ApiOperation("用户登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名", required = true),
			@ApiImplicitParam(name = "password", value = "密码", required = true)
	})//@ApiImplicitParams：描述所有参数；@ApiImplicitParam描述某个参数
	@PostMapping("/login")
	public AppResponse<UserRespVo> login(String username, String password){
	/*	1、尝试登录*/
		TMember member=userService.login(username,password);
		if(member==null){
			/*登录失败*/
			AppResponse<UserRespVo> fail=AppResponse.fail(null);
			fail.setMsg("用户名或密码错误");
			return fail;
		}
		/*2、登录成功;生成令牌*/
		String token = UUID.randomUUID().toString().replace("_", "");
		UserRespVo vo=new UserRespVo();
		BeanUtils.copyProperties(member, vo);
		vo.setAccessToken(token);
		/*3、经常根据令牌查询用户的id信息*/
		/*令牌和用户id对应*/
		redisTemplate.opsForValue().set(token, member.getId()+"");
		return AppResponse.ok(vo);



	}



}

/*

//        if (sendCode.equals("") || sendCode.equals("fail")) {
//            //短信失败
//            return fail(sendCode);
//        }
//        return ok(sendCode);
	}




}*/
