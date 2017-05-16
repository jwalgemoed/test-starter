package main;

public class GenerateGreeting {

    public static void main(final String[] args) {
        System.out.println(getGreeting(false));
    }
    
    public static String getGreeting(boolean full) {
        final StringBuilder result = new StringBuilder();
        if (full) {
            result.append("HELLO");
        }
        result.append(" WORLD");
        return result.toString();
    }
}
