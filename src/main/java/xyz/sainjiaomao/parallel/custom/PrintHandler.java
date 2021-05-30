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
    System.out.println(list.get(task.getCurrent().intValue()-1));
    return false;
  }
}
