package xyz.sainjiaomao.parallel.web;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.sainjiaomao.parallel.custom.ClearHandler;
import xyz.sainjiaomao.parallel.custom.Context;
import xyz.sainjiaomao.parallel.custom.PrintHandler;
import xyz.sainjiaomao.parallel.custom.ReadHandler;
import xyz.sainjiaomao.parallel.task.Constant;
import xyz.sainjiaomao.parallel.task.Handler;
import xyz.sainjiaomao.parallel.task.Task;
import xyz.sainjiaomao.parallel.task.configuration.Publisher;
import xyz.sainjiaomao.parallel.task.Pipeline;

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

  @Resource
  private ReadHandler readHandler;

  @Resource
  private PrintHandler printHandler;

  @Resource
  private ClearHandler clearHandler;


  @GetMapping("parallel/{key}/{partition}")
  public void parallel(@PathVariable("key")String key, @PathVariable("partition") Long partition) {
    Task task = new Task(key, partition, 0L);
    redisTemplate.opsForValue().setIfAbsent(key, Arrays.asList(9,8,7,6,5,4,3,2,1,0));
    Context context = new Context();
    context.setKey(key);
    task.setBody(context);
    publisher.sendMessage(Constant.excelTask, task);
  }

  @PostConstruct
  public void init() {
    Pipeline pipeline = new Pipeline();
    pipeline.registry(Constant.excelTask);
    pipeline.addHandler(readHandler)
        .addHandler(printHandler)
        .addHandler(clearHandler);
  }

}
