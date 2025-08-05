import java.util.ArrayList;
import java.util.List;

public class Main {
    /**
     * Напишите код, с помощью которого можно разделить строку на части.
     * Код должен работать для строк любого размера и любого содержания.
     * Размер частей вы устанавливаете самостоятельно.
     */

    public static void main(String[] args) {
        String testText = "Проверочная строка с числом 5";
        int part = 3;
        List<String> result = splitString(testText, part);

        for (int i = 0; i < result.size(); i++) {
            System.out.println("Часть : " + (i + 1) + ": " + result.get(i));
        }

    }

    public static List<String> splitString(String input, int part) {
        List<String> parts = new ArrayList<>();

        if (input == null || input.isEmpty() || part <= 0) {
            return parts;
        }

        int length = input.length();

        for (int i = 0; i < length; i += part) {
            int end = Math.min(length, i + part);
            parts.add(input.substring(i, end));
        }
        return parts;
    }
}