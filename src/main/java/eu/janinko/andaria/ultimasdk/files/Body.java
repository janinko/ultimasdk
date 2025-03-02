package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.defs.BodyEntry;
import eu.janinko.andaria.ultimasdk.files.defs.DefLine;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Body {

    private static final Pattern linePattern = Pattern.compile("([0-9]+)[ \t]+\\{([0-9, \t]+)}[ \t]+([0-9]+)");
    private final Matcher matcher = linePattern.matcher("");

    private final Map<Integer, BodyEntry> entries = new HashMap<>();
    private final Map<Integer, DefLine<BodyEntry>> entryLines = new HashMap<>();
    private final ArrayList<DefLine<BodyEntry>> lines = new ArrayList<>();

    private Body(InputStream is, Charset cs) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, cs));

        reader.lines().map(this::readLine)
                .peek(lines::add)
                .filter(DefLine::hasEntry)
                .forEach(l -> {
                    entries.put(l.getEntry().getAnimID(), l.getEntry());
                    entryLines.put(l.getEntry().getAnimID(), l);
                });
    }

    public static Body load(Path file, Charset cs) throws IOException {
        try (InputStream input = Files.newInputStream(file)) {
            return new Body(input, cs);
        }
    }

    public void save(OutputStream os, Charset cs) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, cs))) {

            for (DefLine<BodyEntry> line : lines) {
                out.append(line.asString());
                out.append('\r');
                out.append('\n');
            }
        }
    }

    private DefLine<BodyEntry> readLine(String origLine) {
        String line = origLine.replaceFirst("#.*", "");
        line = line.trim();

        if (matcher.reset(line).matches()) {
            int animID = Integer.parseInt(matcher.group(1));
            String[] replacementAnimIDs = matcher.group(2).split(",");
            int replacementAnimID;
            if (replacementAnimIDs.length >= 3) {
                replacementAnimID = Integer.parseInt(replacementAnimIDs[2].trim());
            } else {
                replacementAnimID = Integer.parseInt(replacementAnimIDs[0].trim());
            }
            if(replacementAnimIDs.length > 1){
                System.err.println("Body.def has extra replacement elements in entry, using only " + replacementAnimID + ". Original line: " + origLine);
            }
            int color = Integer.parseInt(matcher.group(3));
            BodyEntry entry = new BodyEntry(animID, replacementAnimID, color);
            String template = origLine.replaceFirst(Pattern.quote(line), DefLine.ENTRY_TEMPLATE);
            return new DefLine<>(origLine, entry, template);
        }
        return new DefLine<>(origLine);
    }

    public BodyEntry get(int animID) {
        return entries.get(animID);
    }

    public void set(BodyEntry entry) {
        if (entries.containsKey(entry.getAnimID())) {
            entries.put(entry.getAnimID(), entry);
            DefLine<BodyEntry> origLine = entryLines.get(entry.getAnimID());

            DefLine<BodyEntry> defLine = new DefLine<>(origLine.getOriginalLine(), entry, origLine.getTemplateLine());
            entryLines.put(entry.getAnimID(), defLine);
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i) == origLine) { // yes, object comparison
                    lines.set(i, defLine);
                    break;
                }
            }
        } else {
            entries.put(entry.getAnimID(), entry);
            DefLine<BodyEntry> defLine = new DefLine<>(entry);
            entryLines.put(entry.getAnimID(), defLine);
            lines.add(defLine);
        }
    }

    public void clear(int animID) {
        entries.remove(animID);
    }

    public boolean isEntry(int animId){
        return entries.containsKey(animId);
    }
}
