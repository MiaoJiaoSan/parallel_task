package xyz.sainjiaomao.parallel.task.configuration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import xyz.sainjiaomao.parallel.task.Constant;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 李宇飞
 * create by 2021-05-29 23:48
 */
@SpringBootConfiguration
public class RedisConfiguration {

//  @Bean
//  public JdkSerializationRedisSerializer jdkSerializationRedisSerializer(){
//    return new JdkSerializationRedisSerializer();
//  }
//
//  @Bean
//  public StringRedisSerializer stringRedisSerializer(){
//    return new StringRedisSerializer();
//  }

//  @Bean
//  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory,
//                                                     StringRedisSerializer stringRedisSerializer,
//                                                     JdkSerializationRedisSerializer jdkSerializationRedisSerializer){
//    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//    redisTemplate.setConnectionFactory(redisConnectionFactory);
//    redisTemplate.setKeySerializer(stringRedisSerializer);
//    redisTemplate.setValueSerializer(stringRedisSerializer);
//    return redisTemplate;
//  }


  @Bean
  public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                 MessageListenerAdapter adapter) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    //可以添加多个 messageListener
    container.addMessageListener(adapter, new PatternTopic(Constant.excelTask));
    return container;
  }


  /**
   * @return 适配器
   */
  @Bean
  public MessageListenerAdapter adapter(Consumer consumer) {
    return new MessageListenerAdapter(consumer, "onMessage");
  }



}
