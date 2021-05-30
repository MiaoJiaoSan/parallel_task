import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.sainjiaomao.parallel.Application;
import xyz.sainjiaomao.parallel.web.TaskAcceptController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 李宇飞
 * create by 2021-05-29 23:44
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class MessageTest {

  @Resource
  TaskAcceptController controller;

  @Test
  public void test() throws IOException {
    controller.parallel("sum",10L);
    System.in.read();
  }


}
