package main;

import org.junit.Test;

import static org.junit.Assert.*;

public class GenerateGreetingTest {

    @Test
    public void testGenerateGreetingTrue() {
        assertEquals("HELLO WORLD", GenerateGreeting.getGreeting(true));
    }

    @Test
    public void testGenerateGreetingFalse() {
        assertEquals("WORLD", GenerateGreeting.getGreeting(false));
    }
}