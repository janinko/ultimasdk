package eu.janinko.andaria.ultimasdk;

import eu.janinko.andaria.ultimasdk.files.*;
import eu.janinko.andaria.ultimasdk.files.index.IdxFile;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class UOFileSizes {
    public static final String ANIM1_IDX = "anim.idx";
    public static final String ANIM2_IDX = "anim2.idx";
    public static final String ANIM3_IDX = "anim3.idx";
    public static final String ANIM4_IDX = "anim4.idx";
    public static final String ANIM5_IDX = "anim5.idx";
    public static final String ART_IDX = "artidx.mul";
    public static final String CLILOC_ENU = "cliloc.enu";
    public static final String FONTS_MUL = "fonts.mul";
    public static final String GUMP_IDX = "gumpidx.mul";
    public static final String HUES_MUL = "hues.mul";
    public static final String MAP0_MUL = "map0.mul";
    public static final String RADARCOL_MUL = "radarcol.mul";
    public static final String SOUND_IDX = "soundidx.mul";
    public static final String STAICS0_IDX = "staidx0.mul";
    public static final String TILEDATA_MUL = "tiledata.mul";
    public static final String BODY_DEF = "Body.def";
    public static final String BODYCONV_DEF = "Bodyconv.def";

    @Getter
    private final Path filePath;

    public UOFileSizes(Path filePath) {
        this.filePath = filePath;
    }

    public long loadSizeOfAnims() throws IOException {
        return loadSizeOfAnims(filePath);
    }

    public static long loadSizeOfAnims(Path dir) throws IOException {
        return Files.size(dir.resolve(ANIM1_IDX));
    }

    public long loadSizeOfAnims2() throws IOException {
        return loadSizeOfAnims2(filePath);
    }
    
    public static long loadSizeOfAnims2(Path dir) throws IOException {
        return Files.size(dir.resolve(ANIM2_IDX));
    }

    public long loadSizeOfAnims3() throws IOException {
        return loadSizeOfAnims3(filePath);
    }

    public static long loadSizeOfAnims3(Path dir) throws IOException {
        return Files.size(dir.resolve(ANIM3_IDX));
    }

    public long loadSizeOfAnims4() throws IOException {
        return loadSizeOfAnims4(filePath);
    }

    public static long loadSizeOfAnims4(Path dir) throws IOException {
        return Files.size(dir.resolve(ANIM4_IDX));
    }

    public long loadSizeOfAnims5() throws IOException {
        return loadSizeOfAnims5(filePath);
    }

    public static long loadSizeOfAnims5(Path dir) throws IOException {
        return Files.size(dir.resolve(ANIM5_IDX));
    }

    public long loadSizeOfAnims(Anims.AnimFile i) throws IOException {
        switch (i) {
            case ANIM1:
                return loadSizeOfAnims();
            case ANIM2:
                return loadSizeOfAnims2();
            case ANIM3:
                return loadSizeOfAnims3();
            case ANIM4:
                return loadSizeOfAnims4();
            case ANIM5:
                return loadSizeOfAnims5();
            default:
                throw new IllegalArgumentException("Unknown anim file");
        }
    }

    public long loadSizeOfAnims(int i) throws IOException {
        switch (i) {
            case 0:
            case 1:
                return loadSizeOfAnims();
            case 2:
                return loadSizeOfAnims2();
            case 3:
                return loadSizeOfAnims3();
            case 4:
                return loadSizeOfAnims4();
            case 5:
                return loadSizeOfAnims5();
            default:
                throw new IllegalArgumentException("Unknown anim file");
        }
    }

    public static long loadSizeOfAnims(int i, Path dir) throws IOException {
        switch (i) {
            case 0:
            case 1:
                return loadSizeOfAnims(dir);
            case 2:
                return loadSizeOfAnims2(dir);
            case 3:
                return loadSizeOfAnims3(dir);
            case 4:
                return loadSizeOfAnims4(dir);
            case 5:
                return loadSizeOfAnims5(dir);
            default:
                throw new IllegalArgumentException("Unknown anim file");
        }
    }

    public long loadSizeOfArts() throws IOException {
        return loadSizeOfArts(filePath);
    }

    public static long loadSizeOfArts(Path dir) throws IOException {
        return Files.size(dir.resolve(ART_IDX));
    }
    
    public long loadSizeOfCliLocs() throws IOException {
        return loadSizeOfCliLocs(filePath);
    }

    public static long loadSizeOfCliLocs(Path dir) throws IOException {
        return Files.size(dir.resolve(CLILOC_ENU));
    }

    public long loadSizeOfFonts() throws IOException {
        return loadSizeOfFonts(filePath);
    }
    
    public static long loadSizeOfFonts(Path dir) throws IOException {
        return Files.size(dir.resolve(FONTS_MUL));
    }

    public long loadSizeOfGumps() throws IOException {
        return loadSizeOfGumps(filePath);
    }

    public static long loadSizeOfGumps(Path dir) throws IOException {
        return Files.size(dir.resolve(GUMP_IDX));
    }

    public long loadSizeOfHues() throws IOException {
        return loadSizeOfHues(filePath);
    }
    
    public static long loadSizeOfHues(Path dir) throws IOException {
        return Files.size(dir.resolve(HUES_MUL));
    }
    
    public long loadSizeOfMap() throws IOException {
        return loadSizeOfMap(filePath);
    }
    
    public static long loadSizeOfMap(Path dir) throws IOException {
        return Files.size(dir.resolve(MAP0_MUL));
    }

    public long loadSizeOfRadarcol() throws IOException {
        return loadSizeOfRadarcol(filePath);
    }

    public static long loadSizeOfRadarcol(Path dir) throws IOException {
        return Files.size(dir.resolve(RADARCOL_MUL));
    }
    
    public long loadSizeOfSounds() throws IOException {
        return loadSizeOfSounds(filePath);
    }

    public static long loadSizeOfSounds(Path dir) throws IOException {
        return Files.size(dir.resolve(SOUND_IDX));
    }

    public long loadSizeOfStatics() throws IOException {
        return loadSizeOfStatics(filePath);
    }
    
    public static long loadSizeOfStatics(Path dir) throws IOException {
        return Files.size(dir.resolve(STAICS0_IDX));
    }

    public long loadSizeOfTileData() throws IOException {
        return loadSizeOfTileData(filePath);
    }

    public static long loadSizeOfTileData(Path dir) throws IOException {
        return Files.size(dir.resolve(TILEDATA_MUL));
    }
    
    public long loadSizeOfBody() throws IOException {
        return loadSizeOfBody(filePath);
    }
    
    public static long loadSizeOfBody(Path dir) throws IOException {
        return Files.size(dir.resolve(BODY_DEF));
    }
    
    public long loadSizeOfBodyConv() throws IOException {
        return loadSizeOfBodyConv(filePath);
    }

    public static long loadSizeOfBodyConv(Path dir) throws IOException {
        return Files.size(dir.resolve(BODYCONV_DEF));
    }

    public long loadSizeOfUniFonts(int i) throws IOException {
        Path unifontMul = filePath.resolve("unifont" + i + ".mul");
        return Files.size(unifontMul);
    }
}
