import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StopWords {

    private static final Set<String> words = new HashSet<>(Arrays.asList(
            "a", "o", "e", "de", "do", "da",
            "dos", "das", "em", "para", "por",
            "com", "um", "uma", "os", "as",
            "na", "no", "nas", "nos", "que",
            "se", "ao", "aos"
    ));

    public static boolean isStopWord(String s) {
        return words.contains(s);
    }
}