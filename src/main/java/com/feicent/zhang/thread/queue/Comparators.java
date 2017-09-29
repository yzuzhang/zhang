package com.feicent.zhang.thread.queue;

import java.util.Comparator;

/**
 * 队列中元素用 Comparator进行排序
 * 要自己实现Comparator 接口类
 * 添加进优先队列中的数据，Queue会调用Comparator进行比较，小的数据会放在Queue的前部，反之，放在后部。最小的数据会放在Queue的头部
 * 放在Queue中的数据只有添加时才进行比较，加入后的位置是不会改变的——所以元素比较用的值最好是固定的，否则取出的数据有可能不是最优先的。
 * @author yzuzhang
 * @date 2017年1月10日
 */
public class Comparators implements Comparator<Executor> {
    @Override
    public int compare(Executor paramT1, Executor paramT2) {
        return (int)(paramT1.getDeadline() - paramT2.getDeadline());
    }
}
