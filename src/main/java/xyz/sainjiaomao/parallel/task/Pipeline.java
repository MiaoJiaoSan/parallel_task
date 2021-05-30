package xyz.sainjiaomao.parallel.task;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 李宇飞
 * create by 2021-05-30 02:25
 */
@Data
public class Pipeline {

  private final List<Handler> pipeline;
  private String topic;

  public Pipeline() {
    this.pipeline = new ArrayList<>();
  }

  public boolean registry(String topic) {
    this.topic = topic;
    return Executor.registry(topic, this);
  }

  public Pipeline addHandler(Handler handler) {
    pipeline.add(handler);
    return this;
  }
}
