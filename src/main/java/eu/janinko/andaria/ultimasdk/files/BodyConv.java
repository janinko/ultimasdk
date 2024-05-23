package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.defs.BodyConvEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BodyConv {

    private final Map<Integer, BodyConvEntry> entries = new HashMap<>();

    private BodyConv(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        reader.lines().forEach(this::readLine);
    }

    private void readLine(String line) {
        line = line.replaceFirst("#.*", "");
        line = line.trim();
        String[] split = line.split("[\t ]+");

        if (split.length < 2) {
            return;
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
            return;
        }
        Anims.AnimFile animFile;
        switch (animFileNum) {
            case 1:
                animFile = Anims.AnimFile.ANIM2;
                break;
            case 2:
                animFile = Anims.AnimFile.ANIM3;
                break;
            case 3:
                animFile = Anims.AnimFile.ANIM4;
                break;
            case 4:
                animFile = Anims.AnimFile.ANIM5;
                break;
            default:
                return;
        }

        entries.put(animID, new BodyConvEntry(animID, animFile, indexInFile));
    }

    public static BodyConv load(Path file) throws IOException {
        try (InputStream input = Files.newInputStream(file)) {
            return new BodyConv(input);
        }
    }

    public BodyConvEntry get(int animID){
        return entries.get(animID);
    }
}
