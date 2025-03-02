package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.defs.BodyConvEntry;
import eu.janinko.andaria.ultimasdk.files.defs.DefLine;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class BodyConv {

    private final Map<Integer, BodyConvEntry> entries = new HashMap<>();
    private final Map<Integer, DefLine<BodyConvEntry>> entryLines = new HashMap<>();
    private final ArrayList<DefLine<BodyConvEntry>> lines = new ArrayList<>();

    private BodyConv(InputStream is, Charset cs) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, cs));

        reader.lines().map(this::readLine)
                .peek(lines::add)
                .filter(DefLine::hasEntry)
                .forEach(l -> {
                    if(entries.containsKey(l.getEntry().getAnimID())){
                        System.err.println("Body.def contains duplicate entry, ignoring: " + l.getOriginalLine());
                        return;
                    }
                    entries.put(l.getEntry().getAnimID(), l.getEntry());
                    entryLines.put(l.getEntry().getAnimID(), l);
                });
    }

    public static BodyConv load(Path file, Charset cs) throws IOException {
        try (InputStream input = Files.newInputStream(file)) {
            return new BodyConv(input, cs);
        }
    }

    public void save(OutputStream os, Charset cs) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, cs))) {

            for (DefLine<BodyConvEntry> line : lines) {
                out.append(line.asString());
                out.append('\r');
                out.append('\n');
            }
        }
    }

    private DefLine<BodyConvEntry> readLine(String origLine) {
        String line = origLine.replaceFirst("#.*", "");
        line = line.trim();
        String[] split = line.split("[\t ]+");

        if (split.length < 2) {
            return new DefLine<>(origLine);
        }

        int animID = Integer.parseInt(split[0]);
        int animFileNum = 0;
        int indexInFile = -1;
        for (int i = 1; i < split.length; i++) {
            indexInFile = Integer.parseInt(split[i]);
            if (indexInFile >= 0) {
                animFileNum = i;
                break;
            }
        }
        if (indexInFile == -1) {
            return new DefLine<>(origLine);
        }
        Anims.AnimFile animFile;
        switch (animFileNum) {
            case 1 -> animFile = Anims.AnimFile.ANIM2;
            case 2 -> animFile = Anims.AnimFile.ANIM3;
            case 3 -> animFile = Anims.AnimFile.ANIM4;
            case 4 -> animFile = Anims.AnimFile.ANIM5;
            default -> {
                return new DefLine<>(origLine);
            }
        }

        BodyConvEntry entry = new BodyConvEntry(animID, animFile, indexInFile);
        String template = origLine.replaceFirst(Pattern.quote(line), DefLine.ENTRY_TEMPLATE);
        return new DefLine<>(origLine, entry, template);
    }

    public BodyConvEntry get(int animID) {
        return entries.get(animID);
    }

    public void set(BodyConvEntry entry) {
        if (entries.containsKey(entry.getAnimID())) {
            entries.put(entry.getAnimID(), entry);
            DefLine<BodyConvEntry> origLine = entryLines.get(entry.getAnimID());

            DefLine<BodyConvEntry> defLine = new DefLine<>(origLine.getOriginalLine(), entry, origLine.getTemplateLine());
            entryLines.put(entry.getAnimID(), defLine);
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i) == origLine) { // yes, object comparison
                    lines.set(i, defLine);
                    break;
                }
            }
        } else {
            entries.put(entry.getAnimID(), entry);
            DefLine<BodyConvEntry> defLine = new DefLine<>(entry);
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
