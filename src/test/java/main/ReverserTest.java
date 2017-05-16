package main;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReverserTest {

    @Test
    public void testReverserPalindroom() {
        assertEquals("regelnevenleger", Reverser.reverse("regelnevenleger"));
    }
    
    @Test
    public void testReverserRandomWord() {
        assertEquals("ollah", Reverser.reverse("hallo"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testReverserNull(){
        Reverser.reverse(null);
    }
}