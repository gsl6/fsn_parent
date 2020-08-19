package com.offcn;

import com.offcn.user.UserStartApplication;
import lombok.ToString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserStartApplication.class})
public class ScwUserApplicationTest {
	/*引入日志文件*/
	Logger logger= LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisTemplate<Object,Object> redisTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void contextLoads(){
		stringRedisTemplate.opsForValue().set("msg", "23333");
		logger.debug("success");
	}

}
