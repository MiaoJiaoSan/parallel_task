package xyz.sainjiaomao.parallel.task.configuration;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import xyz.sainjiaomao.parallel.task.Executor;
import xyz.sainjiaomao.parallel.task.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 李宇飞
 * create by 2021-05-29 23:33
 */
@Component
public class Consumer implements MessageListener {

  private static final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
  //  @Resource
  private StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
  //  @Resource
  private JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

  @Override
  public void onMessage(Message message, byte[] pattern) {
    byte[] body = message.getBody();
    byte[] channel = message.getChannel();
    String topic = stringRedisSerializer.deserialize(channel);
    Task task = (Task) jdkSerializationRedisSerializer.deserialize(body);
    Assert.notNull(task, "");
    pool.execute(() -> Executor.execute(topic, task));

  }
}
