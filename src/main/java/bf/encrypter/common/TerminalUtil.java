package bf.encrypter.common;

public class TerminalUtil {

    public static void print() {
        print("", "\n");
    }

    public static void print(String message) {
        print(message, "\n");
    }

    public static void print(String message, String end) {
        System.out.print(message + end);
    }
}
