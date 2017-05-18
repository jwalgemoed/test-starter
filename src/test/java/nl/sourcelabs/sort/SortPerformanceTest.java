package nl.sourcelabs.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.fail;

public class SortPerformanceTest {
    
    @Test
    public void testSmallSort() throws Exception {
        compareSortPerformance(10, 10);
    }

    @Test
    public void testSlightlyBiggerSort() throws Exception {
        compareSortPerformance(100, 100);
    }

    @Test
    public void testBiggerSort() throws Exception {
        compareSortPerformance(1000, 1000);
    }

    @Test
    public void testHugeSort() throws Exception {
        compareSortPerformance(100000, 10000);
    }

    private int [] setUp(int size, int max) throws Exception {
        int [] numbers = new int[size];
        Random generator = new Random();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = generator.nextInt(max);
        }
        return numbers;
    }

    private void compareSortPerformance(final int size, final int max) throws Exception {
        int [] qs = setUp(size,max);
        int [] ss = Arrays.copyOf(qs, qs.length);

        System.out.println();
        System.out.println("Sorting " + qs.length + " items.");

        long time = System.currentTimeMillis();
        new Quicksort().sort(qs);
        System.out.println("Quicksort duration in millis: " + (System.currentTimeMillis() - time));
        
        sortedOk(qs);

        time = System.currentTimeMillis();
        new SmartSort().sort(ss);
        System.out.println("Smartsort duration in millis: " + (System.currentTimeMillis() - time));
        
        sortedOk(ss);
    }
    
    private void sortedOk(final int [] sorted) {
        for(int i = 0; i < sorted.length - 1; i ++) {
            if(sorted[i] > sorted[i+1]) {
                fail();
            }
        }
    }
}