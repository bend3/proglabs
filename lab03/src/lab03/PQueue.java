package lab03;

import lab03.exceptions.EmptyQueueException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class PQueue<T extends Comparable> implements Iterable<T>{
    private ArrayList<T> list;
    private Comparator<T> comparator = new Comparator<T>() {
        @Override
        public int compare(T o1, T o2) {
            return o1.compareTo(o2);
        }
    };

    public PQueue() {
        this.list = new ArrayList<>();
    }
    public void push(T t){
        list.add(t);
        list.sort(comparator);
    }

    public T pop() throws EmptyQueueException {
        if (list.isEmpty()){
            throw  new EmptyQueueException();
        }
        T toRet = list.stream().max(comparator).get();
        list.remove(toRet);
        return toRet;
    }

    public T top() throws EmptyQueueException{
        if (list.isEmpty()){
            throw  new EmptyQueueException();
        }
        return list.stream().max(comparator).get();
    }

    public int size(){
        return list.size();
    }

    public void clear(){
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
