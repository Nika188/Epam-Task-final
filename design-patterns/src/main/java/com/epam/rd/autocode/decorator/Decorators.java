package com.epam.rd.autocode.decorator;
import java.util.*;
import java.util.List;

public class Decorators {
    public static List<String> evenIndexElementsSubList(List<String> sourceList) {

        return new AbstractList<String>() {
            @Override
            public String get(int index) {
                if (index < 0 || index >= size()) throw new IndexOutOfBoundsException();
                return sourceList.get(index * 2);
            }

            @Override
            public int size() {
                return (sourceList.size() + 1) / 2;
            }

            @Override
            public Iterator<String> iterator() {
                return new Iterator<>() {
                    private int i = 0;

                    @Override
                    public boolean hasNext() {
                        return i < size();
                    }

                    @Override
                    public String next() {
                        if (!hasNext()) throw new NoSuchElementException();
                        return get(i++);
                    }
                };
            }
        };
    }


}
