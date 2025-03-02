package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.defs.DefLine;
import eu.janinko.andaria.ultimasdk.files.defs.EquipConvEntry;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EquipConv {

    private final Map<EquipConvEntry.EquipConvKey, EquipConvEntry> entries = new HashMap<>();
    private final Map<EquipConvEntry.EquipConvKey, DefLine<EquipConvEntry>> entryLines = new HashMap<>();
    private final ArrayList<DefLine<EquipConvEntry>> lines = new ArrayList<>();

    private EquipConv(InputStream is, Charset cs) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, cs));

        reader.lines().map(this::readLine)
                .peek(lines::add)
                .filter(DefLine::hasEntry)
                .forEach(l -> {
                    if(entries.containsKey(l.getEntry().getKey())){
                        System.err.println("equipconv.def contains duplicate entry, ignoring: " + l.getOriginalLine());
                        return;
                    }
                    entries.put(l.getEntry().getKey(), l.getEntry());
                    entryLines.put(l.getEntry().getKey(), l);
                });
    }

    public static EquipConv load(Path file, Charset cs) throws IOException {
        try (InputStream input = Files.newInputStream(file)) {
            return new EquipConv(input, cs);
        }
    }

    public void save(OutputStream os, Charset cs) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, cs))) {

            for (DefLine<EquipConvEntry> line : lines) {
                out.append(line.asString());
                out.append('\r');
                out.append('\n');
            }
        }
    }

    private DefLine<EquipConvEntry> readLine(String origLine) {
        String line = origLine.replaceFirst("#.*", "");
        line = line.trim();
        String[] split = line.split("[\t ]+");

        if (split.length < 5) {
            return new DefLine<>(origLine);
        }

        int bodyID = Integer.parseInt(split[0]);
        int originalID = Integer.parseInt(split[1]);
        int replacementID = Integer.parseInt(split[2]);
        int gump = Integer.parseInt(split[3]);
        int color = Integer.parseInt(split[4]);

        EquipConvEntry entry = new EquipConvEntry(bodyID, originalID, replacementID, gump, color);
        String template = origLine.replaceFirst(Pattern.quote(line), DefLine.ENTRY_TEMPLATE);
        return new DefLine<>(origLine, entry, template);
    }

    public EquipConvEntry get(int body, int animID) {
        return entries.get(new EquipConvEntry.EquipConvKey(body, animID));
    }

    public Set<EquipConvEntry> getByOriginalID(int animID) {
        return entries.values().stream().filter(e -> e.getOriginalID() == animID).collect(Collectors.toSet());
    }

    public void set(EquipConvEntry entry) {
        if (entries.containsKey(entry.getKey())) {
            entries.put(entry.getKey(), entry);
            DefLine<EquipConvEntry> origLine = entryLines.get(entry.getKey());

            DefLine<EquipConvEntry> defLine = new DefLine<>(origLine.getOriginalLine(), entry, origLine.getTemplateLine());
            entryLines.put(entry.getKey(), defLine);
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i) == origLine) { // yes, object comparison
                    lines.set(i, defLine);
                    break;
                }
            }
        } else {
            entries.put(entry.getKey(), entry);
            DefLine<EquipConvEntry> defLine = new DefLine<>(entry);
            entryLines.put(entry.getKey(), defLine);
            lines.add(defLine);
        }
    }

    public void clear(int body, int animID) {
        entries.remove(new EquipConvEntry.EquipConvKey(body, animID));
    }

    public boolean isEntry(int body, int animID){
        return entries.containsKey(new EquipConvEntry.EquipConvKey(body, animID));
    }

}
