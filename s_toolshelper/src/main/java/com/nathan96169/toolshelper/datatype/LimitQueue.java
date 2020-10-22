package com.nathan96169.toolshelper.datatype;

import java.util.LinkedList;

/**定长队列**/
public class LimitQueue<E>{
    private int limit; // 队列长度
    private LinkedList<E> queue = new LinkedList<E>();
    public LimitQueue(int limit){
        this.limit = limit;
    }
    /**
     * 入列：当队列大小已满时，把队头的元素poll掉
     */
    public void put(E e){
        if(queue.size() >= limit){
            queue.poll();
        }
        queue.offer(e);
    }
    public E get(int position) {
        return queue.get(position);
    }

    public E getLast() {
        return queue.getLast();
    }

    public E getFirst() {
        return queue.getFirst();
    }
    public LinkedList<E> getList() {
        return queue;
    }
    public void remove(int pos) {
        if(pos<0 || queue.size()-1<pos)return;
        queue.remove(pos);
    }

    public int getLimit() {
        return limit;
    }

    public int size() {
        return queue.size();
    }

    public double getAverange(){//取平均值
        double all = 0;
        int num = 0;
        for(int i = 0;i<queue.size();i++){
            E value = queue.get(i);
            if(value instanceof Double) {
                double vvv = (Double) value;
                if (vvv != 0) {
                    num++;
                    all += vvv;
                }
            }
        }
        if(num == 0)return 0;
        return all/num;
    }
    public int getMaxPos(){//取最大值坐标
        double max = 0;
        int pos = 0;
        for(int i = 0;i<queue.size();i++){
            E value = queue.get(i);
            if(value instanceof Double) {
                double vvv = (Double) value;
                if (vvv >max) {
                    max = vvv;
                    pos = i;
                }
            }
        }
        return pos;
    }
    public double getMax(){//取最大值坐标
        double max = 0;
        int pos = 0;
        for(int i = 0;i<queue.size();i++){
            E value = queue.get(i);
            if(value instanceof Double) {
                double vvv = (Double) value;
                if (vvv >max) {
                    max = vvv;
                    pos = i;
                }
            }
        }
        return max;
    }
    public boolean haveValue(E value){//是否存在值
        for(int i = 0;i<queue.size();i++){
            E vv = queue.get(i);
            if(vv == value)return true;
        }
        return false;
    }
}
