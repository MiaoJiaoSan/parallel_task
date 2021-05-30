package xyz.sainjiaomao.parallel.custom;

import org.springframework.stereotype.Component;
import xyz.sainjiaomao.parallel.task.Handler;
import xyz.sainjiaomao.parallel.task.Task;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 李宇飞
 * create by 2021-05-30 13:34
 */
@Component
public class PrintHandler implements Handler {

  @Override
  public boolean input(Task task) {
    Context context = (Context)task.getBody();
    List<Integer> list = context.getList();
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(list.get(task.getCurrent().intValue()-1));
    return false;
  }
}
