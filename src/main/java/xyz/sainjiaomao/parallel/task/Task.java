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

  public Task(String key, Long partition, Long current) {
    this.key = key+":partition";
    this.partition = partition;
    this.current = current;
  }

  public boolean isCompleted() {
    return partition <= current;
  }

}
