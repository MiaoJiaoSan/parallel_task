package xyz.sainjiaomao.parallel.task;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 李宇飞
 * create by 2021-05-30 02:13
 */
public interface Handler {


  default boolean input(Task task) {
    return true;
  }


  default boolean output(Task task) {
    return true;
  }

  default void exception(Exception e){
  }
}
