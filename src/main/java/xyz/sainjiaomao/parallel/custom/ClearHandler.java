package xyz.sainjiaomao.parallel.custom;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.sainjiaomao.parallel.task.Handler;
import xyz.sainjiaomao.parallel.task.Task;

import javax.annotation.Resource;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 李宇飞
 * create by 2021-05-30 13:37
 */
@Component
public class ClearHandler implements Handler {

  @Resource
  private RedisTemplate<String, Object> redisTemplate;


  @Override
  public boolean output(Task task) {
    if(!task.getPartition().equals(task.getCurrent())){
      return true;
    }
    Context context = (Context) task.getBody();
    System.out.println("=============del key:"+ context.getKey());
    redisTemplate.delete(context.getKey());
    return true;
  }
}
