package xyz.sainjiaomao.parallel.web;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.sainjiaomao.parallel.custom.CustomHandler;
import xyz.sainjiaomao.parallel.custom.Context;
import xyz.sainjiaomao.parallel.task.Constant;
import xyz.sainjiaomao.parallel.task.Task;
import xyz.sainjiaomao.parallel.task.configuration.Publisher;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 李宇飞
 * create by 2021-05-29 23:26
 */
@RestController
public class TaskAcceptController {

  @Resource
  private Publisher publisher;
  @Resource
  private RedisTemplate<String, Object> redisTemplate;


  @GetMapping("parallel/{key}/{partition}")
  public void parallel(@PathVariable("key")String key, @PathVariable("partition") Long partition) {
    Task task = new Task(key, partition);
    redisTemplate.opsForValue().setIfAbsent(key, Arrays.asList(9,8,7,6,5,4,3,2,1,0));
    Context context = new Context();
    context.setKey(key);
    task.setBody(context);
    publisher.sendMessage(Constant.excelTask, task);
  }

}
