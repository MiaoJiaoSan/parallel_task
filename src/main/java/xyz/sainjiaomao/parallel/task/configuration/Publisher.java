package xyz.sainjiaomao.parallel.task.configuration;

import cn.hutool.core.lang.UUID;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import xyz.sainjiaomao.parallel.task.Task;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 李宇飞
 * create by 2021-05-29 23:38
 */
@Component
public class Publisher {

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Resource
  private StringRedisTemplate stringRedisTemplate;


  public void sendMessage(String topic, Task task) {
    Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent(task.getKey(), String.valueOf(task.getCurrent()));
    if (Objects.nonNull(aBoolean) && aBoolean) {
      redisTemplate.convertAndSend(topic, task);
    }
  }

}
