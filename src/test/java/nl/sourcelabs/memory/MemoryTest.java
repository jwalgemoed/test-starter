package nl.sourcelabs.memory;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MemoryTest {

    /**
     * Simple recursive (method that calls itself) function. Demonstrates a stackoverflow.
     */
    @Test
    public void stackOverFlow() {
        stackOverFlow();
    }

    /**
     * Test borrowed from https://crunchify.com/how-to-generate-out-of-memory-oom-in-java-programatically/
     * 
     * Main purpose is to demonstrate what happens when memory runs out. Method allocates (loads) of memory
     * and never releases it, making garbage collection impossible. Outofmemory is the result.
     * 
     * @throws Exception
     */
    @Test
    public void testMemoryConsumption() throws Exception {
        int iteratorValue = 20;
        System.out.println("\n=================> OOM test started..\n");
        for (int outerIterator = 1; outerIterator < 20; outerIterator++) {
            System.out.println("Iteration " + outerIterator + " Free Mem: " + Runtime.getRuntime().freeMemory());
            int loop1 = 2;
            int[] memoryFillIntVar = new int[iteratorValue];
            // fill memoryFillIntVar array in loop..
            do {
                memoryFillIntVar[loop1] = 0;
                loop1--;
            } while (loop1 > 0);
            iteratorValue = iteratorValue * 5;
            System.out.println("\nRequired Memory for next loop: " + iteratorValue);
            Thread.sleep(3000);
        }
    }

    /**
     * Shows allocation (and release) of memory due to dereferencing
     */
    @Test
    public void demonstrateGarbageCollects() throws Exception {
        fillAndRelease(1);
    }
    
    private void fillAndRelease(final int iteration) throws Exception {
        if(iteration == 5000) {
            return; // quit, we done
        }

        System.out.println("Round: " + iteration);
        Thread.sleep(2000);
        
        final List<String> strings = new ArrayList<>();
        for(int i = 0; i < 50000; i ++) {
            strings.add("MEH" + System.nanoTime());
        }
        
        fillAndRelease(iteration + 1);
    }
}
