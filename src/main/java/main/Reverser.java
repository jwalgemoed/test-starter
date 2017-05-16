package main;

public class Reverser {

    public static void main(final String[] args) {
        System.out.println(reverse("regelnevenleger"));
    }
    
    public static String reverse(final String input) {
//        if(null == input) {
//            throw new IllegalArgumentException("Input cannot be null");
//        }
        
        final StringBuilder result = new StringBuilder();
        for (int i = input.length() - 1; i > 0; i--) {
            result.append(input.charAt(i));
        }
        return result.toString();
    }
}
