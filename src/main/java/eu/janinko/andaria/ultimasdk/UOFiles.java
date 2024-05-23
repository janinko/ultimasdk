package eu.janinko.andaria.ultimasdk;

import java.io.IOException;
import java.nio.file.Path;

import eu.janinko.andaria.ultimasdk.files.Anims;
import eu.janinko.andaria.ultimasdk.files.Arts;
import eu.janinko.andaria.ultimasdk.files.CliLocs;
import eu.janinko.andaria.ultimasdk.files.Fonts;
import eu.janinko.andaria.ultimasdk.files.Gumps;
import eu.janinko.andaria.ultimasdk.files.Hues;
import eu.janinko.andaria.ultimasdk.files.Map;
import eu.janinko.andaria.ultimasdk.files.Radarcol;
import eu.janinko.andaria.ultimasdk.files.Sounds;
import eu.janinko.andaria.ultimasdk.files.Statics;
import eu.janinko.andaria.ultimasdk.files.TileData;
import eu.janinko.andaria.ultimasdk.files.UniFonts;
import eu.janinko.andaria.ultimasdk.files.index.IdxFile;
import java.nio.file.Files;
import java.util.HashMap;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class UOFiles implements AutoCloseable {
    public static final String ANIM1_IDX = "anim.idx";
    public static final String ANIM1_MUL = "anim.mul";
    public static final String ANIM2_IDX = "anim2.idx";
    public static final String ANIM2_MUL = "anim2.mul";
    public static final String ANIM3_IDX = "anim3.idx";
    public static final String ANIM3_MUL = "anim3.mul";
    public static final String ANIM4_IDX = "anim4.idx";
    public static final String ANIM4_MUL = "anim4.mul";
    public static final String ANIM5_IDX = "anim5.idx";
    public static final String ANIM5_MUL = "anim5.mul";
    public static final String ART_IDX = "artidx.mul";
    public static final String ART_MUL = "art.mul";
    public static final String CLILOC_ENU = "cliloc.enu";
    public static final String FONTS_MUL = "fonts.mul";
    public static final String GUMP_IDX = "gumpidx.mul";
    public static final String GUMP_MUL = "gumpart.mul";
    public static final String HUES_MUL = "hues.mul";
    public static final String MAP0_MUL = "map0.mul";
    public static final String RADARCOL_MUL = "radarcol.mul";
    public static final String SOUND_IDX = "soundidx.mul";
    public static final String SOUND_MUL = "sound.mul";
    public static final String STAICS0_IDX = "staidx0.mul";
    public static final String STAICS0_MUL = "statics0.mul";
    public static final String TILEDATA_MUL = "tiledata.mul";

    private final Path filePath;

    public UOFiles(Path filePath) {
        this.filePath = filePath;
    }

    private Anims anims;
    private Anims anims2;
    private Anims anims3;
    private Anims anims4;
    private Anims anims5;
    private Arts arts;
    private CliLocs cliLocs;
    private Fonts fonts;
    private Gumps gumps;
    private Hues hues;
    private Map map;
    private Radarcol radarcol;
    private Sounds sounds;
    private Statics statics;
    private TileData tiledata;
    private HashMap<Integer, UniFonts> uniFonts = new HashMap<>();

    public static void save(IdxFile<?> file, Path idx, Path mul) throws IOException {
        file.save(Files.newOutputStream(idx), Files.newOutputStream(mul));
    }

    public Anims getAnims() throws IOException {
        if (anims == null) {
            anims = loadAnimsFromDir(filePath);
        }
        return anims;
    }

    public static Anims loadAnims(Path idx, Path mul) throws IOException {
        return Anims.open(idx, mul, Anims.AnimFile.ANIM1);
    }

    public static Anims loadAnimsFromDir(Path dir) throws IOException {
        return loadAnims(dir.resolve(ANIM1_IDX), dir.resolve(ANIM1_MUL));
    }

    public static void saveToDir(Anims anims, Path dir) throws IOException {
        Files.createDirectories(dir);
        switch(anims.getAnimFile()){
            case ANIM1: save(anims, dir.resolve(ANIM1_IDX), dir.resolve(ANIM1_MUL)); return;
            case ANIM2: save(anims, dir.resolve(ANIM2_IDX), dir.resolve(ANIM2_MUL)); return;
            case ANIM3: save(anims, dir.resolve(ANIM3_IDX), dir.resolve(ANIM3_MUL)); return;
            case ANIM4: save(anims, dir.resolve(ANIM4_IDX), dir.resolve(ANIM4_MUL)); return;
            case ANIM5: save(anims, dir.resolve(ANIM5_IDX), dir.resolve(ANIM5_MUL)); return;
            default: throw new UnsupportedOperationException("Unknown anim type " + anims.getAnimFile());
        }
    }

    public Anims getAnims2() throws IOException {
        if (anims2 == null) {
            anims2 = loadAnims2FromDir(filePath);
        }
        return anims2;
    }

    public static Anims loadAnims2(Path idx, Path mul) throws IOException {
        return Anims.open(idx, mul, Anims.AnimFile.ANIM2);
    }

    public static Anims loadAnims2FromDir(Path dir) throws IOException {
        return loadAnims(dir.resolve(ANIM2_IDX), dir.resolve(ANIM2_MUL));
    }

    public Anims getAnims3() throws IOException {
        if (anims3 == null) {
            anims3 = loadAnims3FromDir(filePath);
        }
        return anims3;
    }

    public static Anims loadAnims3(Path idx, Path mul) throws IOException {
        return Anims.open(idx, mul, Anims.AnimFile.ANIM3);
    }

    public static Anims loadAnims3FromDir(Path dir) throws IOException {
        return loadAnims(dir.resolve(ANIM3_IDX), dir.resolve(ANIM3_MUL));
    }

    public Anims getAnims4() throws IOException {
        if (anims4 == null) {
            anims4 = loadAnims4FromDir(filePath);
        }
        return anims4;
    }

    public static Anims loadAnims4(Path idx, Path mul) throws IOException {
        return Anims.open(idx, mul, Anims.AnimFile.ANIM4);
    }

    public static Anims loadAnims4FromDir(Path dir) throws IOException {
        return loadAnims(dir.resolve(ANIM4_IDX), dir.resolve(ANIM4_MUL));
    }

    public Anims getAnims5() throws IOException {
        if (anims5 == null) {
            anims5 = loadAnims5FromDir(filePath);
        }
        return anims5;
    }

    public static Anims loadAnims5(Path idx, Path mul) throws IOException {
        return Anims.open(idx, mul, Anims.AnimFile.ANIM5);
    }

    public static Anims loadAnims5FromDir(Path dir) throws IOException {
        return loadAnims(dir.resolve(ANIM5_IDX), dir.resolve(ANIM5_MUL));
    }

    public Anims getAnims(int i) throws IOException {
        switch (i) {
            case 0:
            case 1:
                return getAnims();
            case 2:
                return getAnims2();
            case 3:
                return getAnims3();
            case 4:
                return getAnims4();
            case 5:
                return getAnims5();
            default:
                throw new IllegalArgumentException("Unknown anim file");
        }
    }

    public static Anims loadAnimsFromDir(int i, Path dir) throws IOException {
        switch (i) {
            case 0:
            case 1:
                return loadAnimsFromDir(dir);
            case 2:
                return loadAnims2FromDir(dir);
            case 3:
                return loadAnims3FromDir(dir);
            case 4:
                return loadAnims4FromDir(dir);
            case 5:
                return loadAnims5FromDir(dir);
            default:
                throw new IllegalArgumentException("Unknown anim file");
        }
    }

    public Arts getArts() throws IOException {
        if (arts == null) {
            arts = loadArtsFromDir(filePath);
        }
        return arts;
    }

    public static Arts loadArts(Path idx, Path mul) throws IOException {
        return Arts.open(idx, mul);
    }

    public static Arts loadArtsFromDir(Path dir) throws IOException {
        return loadArts(dir.resolve(ART_IDX), dir.resolve(ART_MUL));
    }

    public static void saveToDir(Arts arts, Path dir) throws IOException {
        Files.createDirectories(dir);
        save(arts, dir.resolve(ART_IDX), dir.resolve(ART_MUL));
    }

    public CliLocs getCliLocs() throws IOException {
        if (cliLocs == null) {
            cliLocs = loadCliLocsFromDir(filePath);
        }
        return cliLocs;
    }

    public static CliLocs loadCliLocs(Path mul) throws IOException {
        return CliLocs.load(mul);
    }

    public static CliLocs loadCliLocsFromDir(Path dir) throws IOException {
        return loadCliLocs(dir.resolve(CLILOC_ENU));
    }

    public static void save(CliLocs fonts, Path mul) throws IOException {
        fonts.save(Files.newOutputStream(mul));
    }

    public static void saveToDir(CliLocs fonts, Path dir) throws IOException {
        Files.createDirectories(dir);
        save(fonts, dir.resolve(FONTS_MUL));
    }

    public Fonts getFonts() throws IOException {
        if (fonts == null) {
            fonts = loadFontsFromDir(filePath);
        }
        return fonts;
    }

    public static Fonts loadFonts(Path mul) throws IOException {
        return Fonts.load(mul);
    }

    public static Fonts loadFontsFromDir(Path dir) throws IOException {
        return loadFonts(dir.resolve(FONTS_MUL));
    }

    public static void save(Fonts fonts, Path mul) throws IOException {
        fonts.save(Files.newOutputStream(mul));
    }

    public static void saveToDir(Fonts fonts, Path dir) throws IOException {
        Files.createDirectories(dir);
        save(fonts, dir.resolve(FONTS_MUL));
    }

    public Gumps getGumps() throws IOException {
        if (gumps == null) {
            gumps = loadGumpsFromDir(filePath);
        }
        return gumps;
    }

    public static Gumps loadGumps(Path idx, Path mul) throws IOException {
        return Gumps.open(idx, mul);
    }

    public static Gumps loadGumpsFromDir(Path dir) throws IOException {
        return loadGumps(dir.resolve(GUMP_IDX), dir.resolve(GUMP_MUL));
    }

    public static void saveToDir(Gumps gumps, Path dir) throws IOException {
        Files.createDirectories(dir);
        save(gumps, dir.resolve(GUMP_IDX), dir.resolve(GUMP_MUL));
    }

    public Hues getHues() throws IOException {
        if (hues == null) {
            hues = loadHuesFromDir(filePath);
        }
        return hues;
    }

    public static Hues loadHues(Path mul) throws IOException {
        return Hues.load(mul);
    }

    public static Hues loadHuesFromDir(Path dir) throws IOException {
        return loadHues(dir.resolve(HUES_MUL));
    }

    public static void save(Hues hues, Path mul) throws IOException {
        hues.save(Files.newOutputStream(mul));
    }

    public static void saveToDir(Hues hues, Path dir) throws IOException {
        Files.createDirectories(dir);
        save(hues, dir.resolve(HUES_MUL));
    }

    public Map getMap() throws IOException {
        if (map == null) {
            map = loadMapFromDir(filePath);
        }
        return map;
    }

    public static Map loadMap(Path mul) throws IOException {
        return Map.open(mul);
    }

    public static Map loadMapFromDir(Path dir) throws IOException {
        return loadMap(dir.resolve(MAP0_MUL));
    }

    public static void save(Map map, Path mul) throws IOException {
        map.save(Files.newOutputStream(mul));
    }

    public static void saveToDir(Map map, Path dir) throws IOException {
        Files.createDirectories(dir);
        save(map, dir.resolve(MAP0_MUL));
    }

    public Radarcol getRadarcol() throws IOException {
        if (radarcol == null) {
            radarcol = loadRadarcolFromDir(filePath);
        }
        return radarcol;
    }

    public static Radarcol loadRadarcol(Path mul) throws IOException {
        return Radarcol.load(mul);
    }

    public static Radarcol loadRadarcolFromDir(Path dir) throws IOException {
        return loadRadarcol(dir.resolve(RADARCOL_MUL));
    }

    public static void save(Radarcol radarcol, Path mul) throws IOException {
        radarcol.save(Files.newOutputStream(mul));
    }

    public static void saveToDir(Radarcol radarcol, Path dir) throws IOException {
        Files.createDirectories(dir);
        save(radarcol, dir.resolve(RADARCOL_MUL));
    }

    public Sounds getSounds() throws IOException {
        if (sounds == null) {
            sounds = loadSoundsFromDir(filePath);
        }
        return sounds;
    }

    public static Sounds loadSounds(Path idx, Path mul) throws IOException {
        return Sounds.open(idx, mul);
    }

    public static Sounds loadSoundsFromDir(Path dir) throws IOException {
        return loadSounds(dir.resolve(SOUND_IDX), dir.resolve(SOUND_MUL));
    }

    public static void saveToDir(Sounds sounds, Path dir) throws IOException {
        Files.createDirectories(dir);
        save(sounds, dir.resolve(SOUND_IDX), dir.resolve(SOUND_MUL));
    }

    public Statics getStatics() throws IOException {
        if (statics == null) {
            statics = loadStaticsFromDir(filePath);
        }
        return statics;
    }

    public static Statics loadStatics(Path idx, Path mul) throws IOException {
        return Statics.open(idx, mul);
    }

    public static Statics loadStaticsFromDir(Path dir) throws IOException {
        return loadStatics(dir.resolve(STAICS0_IDX), dir.resolve(STAICS0_MUL));
    }

    public static void saveToDir(Statics statics, Path dir) throws IOException {
        Files.createDirectories(dir);
        save(statics, dir.resolve(STAICS0_IDX), dir.resolve(STAICS0_MUL));
    }

    public TileData getTileData() throws IOException {
        if (tiledata == null) {
            tiledata = loadTileDataFromDir(filePath);
        }
        return tiledata;
    }

    public static TileData loadTileData(Path mul) throws IOException {
        return TileData.load(mul);
    }

    public static TileData loadTileDataFromDir(Path dir) throws IOException {
        return loadTileData(dir.resolve(TILEDATA_MUL));
    }

    public static void save(TileData tileData, Path mul) throws IOException {
        tileData.save(Files.newOutputStream(mul));
    }

    public static void saveToDir(TileData tileData, Path dir) throws IOException {
        Files.createDirectories(dir);
        save(tileData, dir.resolve(TILEDATA_MUL));
    }

    public UniFonts getUniFonts(int i) throws IOException {
        UniFonts uniFont = uniFonts.get(i);
        if (uniFont == null) {
            Path unifontMul = filePath.resolve("unifont" + i + ".mul");
            uniFont = UniFonts.load(unifontMul);
            uniFonts.put(i, uniFont);
        }
        return uniFont;
    }

    public void close() throws IOException {
        if (anims != null) {
            anims.close();
            anims = null;
        }
        if (anims2 != null) {
            anims2.close();
            anims2 = null;
        }
        if (anims3 != null) {
            anims3.close();
            anims3 = null;
        }
        if (anims4 != null) {
            anims4.close();
            anims4 = null;
        }
        if (anims5 != null) {
            anims5.close();
            anims5 = null;
        }
        if (arts != null) {
            arts.close();
            arts = null;
        }
        if (gumps != null) {
            gumps.close();
            gumps = null;
        }
        if (map != null) {
            map.close();
            map = null;
        }
        if (statics != null) {
            statics.close();
            statics = null;
        }
        cliLocs = null;
        fonts = null;
        hues = null;
        radarcol = null;
        tiledata = null;

        uniFonts.clear();
    }
}
