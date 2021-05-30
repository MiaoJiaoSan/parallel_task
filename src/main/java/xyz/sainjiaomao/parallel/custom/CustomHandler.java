package xyz.sainjiaomao.parallel.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.sainjiaomao.parallel.task.Constant;
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
 * create by 2021-05-30 13:37
 */
@Slf4j
@Component(Constant.excelTask)
public class CustomHandler implements Handler {

  @Resource
  private RedisTemplate<String, Object> redisTemplate;


  @Override
  public boolean handler(Task task) {
    Context body = (Context) task.getBody();
    Object o = redisTemplate.opsForValue().get(body.getKey());
    List<Integer> list = ((List<Integer>) o);
    System.out.println(task.getCurrent());
    if(task.getCurrent() % 2 == 0){
      throw new RuntimeException(task.getCurrent() + "");
    }
    return true;
  }

  @Override
  public boolean exception(Task task, Exception e) {
    log.error("",e);
    Context body = (Context) task.getBody();
    if(task.isCompleted()) {
      redisTemplate.delete(body.getKey());
    }
    return false;
  }
}
