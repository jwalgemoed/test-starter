package nl.sourcelabs.validator;

/**
 * Assignment: Test the validate method in this class.
 * 
 * Regex based password validator. A Regex is an expression that can be quickly parsed by a computer, allowing
 * for quickly matching patterns in text.
 */
public class PasswordValidator {

    /**
     * Password regular expression
     * - At least 1 number;
     * - At least 1 uppercase character;
     * - At least 1 lowercase character;
     * - At least 1 special character in this range: @, #, $, %, ^, &, +, =
     * - At least 8 characters long;
     * - Maximum of 20 characters
     */
    private static final String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])[^\\s]{8,20}$";

    /**
     * Execute the password validation
     *
     * @param password
     *         the password to check for validity
     * @return true if the password is valid, false if not
     */
    public static boolean isValid(final String password) {
        if(password == null) {
            return false;
        }
        return password.matches(passwordRegex);
    }
}