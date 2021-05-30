package xyz.sainjiaomao.parallel.task;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 李宇飞
 * create by 2021-05-30 02:11
 */
@Component
public class Executor {


  @Resource
  private Map<String, Handler> handlerMapping;

  @Resource
  private StringRedisTemplate stringRedisTemplate;


  public boolean execute(String topic, Task task) {

    Handler handler = handlerMapping.get(topic);
    if(Objects.isNull(handler)){
      return false;
    }
    while (task.current <= task.partition) {
      Long current = stringRedisTemplate.opsForValue().increment(task.getKey());
      Assert.notNull(current, "当前分片错误");
      if (current > task.getPartition()) {
        break;
      }
      task.setCurrent(current);
      try {
        handler.handler(task);
      }catch (Exception e){
        handler.exception(task ,e);
      } finally {
        stringRedisTemplate.expire(task.key, Objects.equals(task.getCurrent(), task.getPartition())?5L:30L, TimeUnit.MINUTES);
      }
    }
    return true;
  }

}
