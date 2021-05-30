package xyz.sainjiaomao.parallel.task;

import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 李宇飞
 * create by 2021-05-30 02:14
 */
@Data
public class Task implements Serializable {

  protected String key;

  protected Long partition;

  protected Long current;

  protected Object body;

  public Task(String key, Long partition) {
    this.key = key+":partition";
    this.partition = partition;
    this.current = 0L;
  }

  public boolean isCompleted() {
    return partition <= current;
  }

}
