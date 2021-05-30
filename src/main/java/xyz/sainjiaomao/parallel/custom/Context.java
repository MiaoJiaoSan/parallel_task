package xyz.sainjiaomao.parallel.custom;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 李宇飞
 * create by 2021-05-30 13:46
 */
@Data
public class Context implements Serializable {

  private String key;

  private List<Integer> list;


}
