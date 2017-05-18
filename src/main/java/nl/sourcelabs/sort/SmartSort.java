package nl.sourcelabs.sort;

public class SmartSort {

    public void sort(int[] ints) {
        boolean done = false;
        while (!done) {
            boolean swap = false;
            for (int i = 0; i < ints.length - 1; i++) {
                if (ints[i] > ints[i + 1]) {
                    int tmp = ints[i];
                    ints[i] = ints[i + 1];
                    ints[i + 1] = tmp;
                    swap = true;
                }
            }
            if(!swap) {
                done = true;
            }
        }
    }
}
