package nl.sourcelabs.sort;

public class SmartSort {

    public void sort(int[] ints) {
        boolean done = false;
        while (!done) { // Loop totdat done == true
            boolean swap = false; // variabele die aangeeft of er iets is geswapt
            for (int i = 0; i < ints.length - 1; i++) { // loop over de hele array
                if (ints[i] > ints[i + 1]) { // als de waarde op index i > index i + 1
                    int tmp = ints[i];
                    ints[i] = ints[i + 1];
                    ints[i + 1] = tmp;
                    swap = true; // Er is een wijziging geweest -> 
                }
            }
            if(!swap) {
                done = true;
            }
        }
    }
}
