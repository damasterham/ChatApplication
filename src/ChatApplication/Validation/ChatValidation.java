package ChatApplication.Validation;

public class ChatValidation {
    public static final String VALID_CHARS = "'-','_','a-z','A-Z'";
    public static final int MAX_NAME_LENGTH = 12;

    // Checks whether char is within ASCII capital letter range (65 to 90) - returns false if it is
    private static boolean notLowerChar(int ch)
    {
        return !(ch >= 65 && ch <= 90);
    }

    // Checks whether char is within ASCII small letter range (97 to 90) - returns false if it is
    private static boolean notCapChar(int ch)
    {
        return !(ch >= 97 && ch <= 122);
    }

    // Checks if name is using valid characters
    public static boolean usesValidChar(String name)
    {
        for (int i = 0; i < name.length(); i++)
        {
            int ch = name.charAt(i);
            boolean dash = ch != (int) '-';  // true if char is different from '-' (ASCII no. 45)
            boolean under = ch != (int) '_'; // true if char is different from '_' (ASCII no. 95)
            boolean l = notLowerChar(ch);    // true if char is not within ASCII no. 65 to 90 (Capital letters)
            boolean c = notCapChar(ch);      // true if char is not within ASCII no. 97 to 122 (small letters)

            if (dash && under && l && c)
            {
                return false;
            }

        }

        return true;
    }

    // Checks if name is empty and less than or equal to 12 chars
    public static boolean isNameLengthValid(String name) {
        return name != "" && (name.length() <= MAX_NAME_LENGTH);
    }
}

