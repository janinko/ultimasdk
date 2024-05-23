package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.defs.BodyEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Body {

    private static final Pattern linePattern = Pattern.compile("([0-9]+)[ \t]+\\{([0-9]+)}[ \t]+([0-9]+)");
    private final Matcher matcher = linePattern.matcher("");

    Map<Integer, BodyEntry> entries = new HashMap<>();

    private Body(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        reader.lines().forEach(this::readLine);
    }

    private void readLine(String line) {
        line = line.replaceFirst("#.*", "");
        line = line.trim();

        if(matcher.reset(line).matches()){
            int animID = Integer.parseInt(matcher.group(1));
            int replacementAnimID = Integer.parseInt(matcher.group(2));
            int color = Integer.parseInt(matcher.group(3));
            entries.put(animID, new BodyEntry(animID, replacementAnimID, color));
        }
    }

    public static Body load(Path file) throws IOException {
        try (InputStream input = Files.newInputStream(file)) {
            return new Body(input);
        }
    }

    public BodyEntry get(int animID){
        return entries.get(animID);
    }
}
