package xyz.sainjiaomao.parallel.custom;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.sainjiaomao.parallel.task.Handler;
import xyz.sainjiaomao.parallel.task.Task;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 李宇飞
 * create by 2021-05-30 12:36
 */
@Component
public class ReadHandler implements Handler {

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Override
  public boolean input(Task task) {

    Context context = (Context)task.getBody();
    Object o = redisTemplate.opsForValue().get(context.getKey());
    context.setList((List<Integer>) o);
    return true;
  }
}
