package xyz.sainjiaomao.parallel.task;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

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
public class Executor implements ApplicationContextAware {


  private final static Map<String, Pipeline> PIPELINE_MAPPING = new ConcurrentHashMap<>();

  private static RedisTemplate<String, String> redisTemplate;

  public static boolean registry(String topic, Pipeline pipeline) {
    return Objects.isNull(PIPELINE_MAPPING.putIfAbsent(topic, pipeline));
  }


  public static boolean execute(String topic, Task task) {
    Pipeline pipeline = PIPELINE_MAPPING.get(topic);

    while (!task.isCompleted()) {
      Long current = redisTemplate.opsForValue().increment(task.getKey());
      Assert.notNull(current, "当前分片错误");
      if (current > task.getPartition()) {
        break;
      }
      task.setCurrent(current);
      for (Handler handler : pipeline.getPipeline()) {
        try {
          if (!handler.input(task)) {
            break;
          }
        } catch (Exception e) {
          handler.exception(task, e);
          return false;
        }
      }
    }
    output(task, pipeline);

    return true;
  }

  private static void output(Task task, Pipeline pipeline) {
    try {
      for (Handler handler : pipeline.getPipeline()) {
        try {
          if (!handler.output(task)) {
            break;
          }
        } catch (Exception e) {
          handler.exception(task, e);
          break;
        }
      }
    } finally {
      long timeout = 30L;
      if (Objects.equals(task.partition, task.current)) {
        timeout = 3L;
      }
      redisTemplate.expire(task.getKey(), timeout, TimeUnit.MINUTES);
    }
  }


  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    RedisTemplate<String, String> stringRedisTemplate = applicationContext.getBean("stringRedisTemplate", RedisTemplate.class);
    Assert.notNull(stringRedisTemplate, "");
    redisTemplate = stringRedisTemplate;
  }
}
