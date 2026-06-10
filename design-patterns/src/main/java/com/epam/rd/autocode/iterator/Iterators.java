package com.epam.rd.autocode.iterator;

import java.util.Iterator;
import java.util.*;

class Iterators {

    public static Iterator<Integer> intArrayTwoTimesIterator(int[] array){
        return new RepeatIterator(array, 2);
    }

    public static Iterator<Integer> intArrayThreeTimesIterator(int[] array) {
        return new RepeatIterator(array, 3);
    }

    public static Iterator<Integer> intArrayFiveTimesIterator(int[] array) {
        return new RepeatIterator(array, 5);
    }

    public static Iterable<String> table(String[] columns, int[] rows){
        return () -> new Iterator<>() {
            int colIndex = 0;
            int rowIndex = 0;

            @Override
            public boolean hasNext() {
                return colIndex<columns.length;
            }

            @Override
            public String next() {
                if (!hasNext()) throw new NoSuchElementException();
                String cell = columns[colIndex] +rows[rowIndex];
                rowIndex++;
                if (rowIndex>= rows.length) {
                    rowIndex = 0;
                    colIndex++;
                }
                return cell;
            }
        };
    }
    private static class RepeatIterator implements Iterator<Integer> {
        private final int[] array;
        private final int repeatCount;
        private int currentIndex = 0;
        private int currentRepeat = 0;

        RepeatIterator(int[] array, int repeatCount) {
            this.array = array;
            this.repeatCount = repeatCount;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < array.length;
        }

        @Override
        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            int value =array[currentIndex];
            currentRepeat++;
            if (currentRepeat == repeatCount) {
                currentRepeat = 0;
                currentIndex++;
            }
            return value;
        }
    }


}
