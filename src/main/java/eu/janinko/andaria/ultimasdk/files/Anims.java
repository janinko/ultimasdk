package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.index.IdxFile;
import eu.janinko.andaria.ultimasdk.files.index.FileIndex.DataPack;
import eu.janinko.andaria.ultimasdk.files.anims.Anim;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiConsumer;

import eu.janinko.andaria.ultimasdk.files.anims.Body;
import eu.janinko.andaria.ultimasdk.files.anims.Body.Action;
import eu.janinko.andaria.ultimasdk.files.anims.Body.Direction;
import eu.janinko.andaria.ultimasdk.files.anims.BodyChar;
import eu.janinko.andaria.ultimasdk.files.anims.BodyHigh;
import eu.janinko.andaria.ultimasdk.files.anims.BodyLow;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Anims extends IdxFile<Anim>{
    public static enum AnimFile {
        ANIM1(200, 200, 0x40000),
        ANIM2(200, 200, 0x10000),
        ANIM3(300, 100, 0x20000),
        ANIM4(200, 200, 0x20000),
        ANIM5(200, 200, 0x20000);

        private final int bodyHighCount;
        private final int bodyLowCount;
        private final int animsCount;

        private AnimFile(int bodyHighCount, int bodyLowCount, int animsCount) {
            this.bodyHighCount = bodyHighCount;
            this.bodyLowCount = bodyLowCount;
            this.animsCount = animsCount;
        }

        private int getHighBound(){
            return bodyHighCount;
        }

        private int getLowBound(){
            return bodyHighCount + bodyLowCount;
        }
    }

    public static final int ANIMS_COUNT=0x40000;
    public static final int BODY_COUNT=1697;

    public static final int WALK=0;
    public static final int RUN=1;
    public static final int STAY=2;
    public static final int IDLE=3;
    public static final int ATTACK=5;

    public static final int DOWN = 0;
    public static final int SOUTH = 1;
    public static final int LEFT = 2;
    public static final int WEST = 3;
    public static final int UP = 4;

    private final AnimFile animFile;

    private Anims(InputStream animidx, File animmul, AnimFile animFile) throws IOException{
        super(animidx, animmul, animFile.animsCount);
        this.animFile = animFile;
    }
    
    public static Anims open(Path idxFile, Path mulFile, AnimFile animFile) throws IOException{
        try (InputStream idxStream = Files.newInputStream(idxFile)) {
            return new Anims(idxStream, mulFile.toFile(), animFile);
        }
    }

    @Override
    public Anim get(int index) throws IOException {
        DataPack data =  fileIndex.getData(index);
        if(data == null) return null;

        Anim anim = new Anim(data.getNewStream());

        return anim;
    }

    public boolean isBody(int body) {
        return fileIndex.isData(bodyToIndex(body));
    }

    @Override
    public int count(){
        int hight = animFile.bodyHighCount * 110;
        int low = animFile.bodyLowCount * 65;
        int count = fileIndex.size();
        if(hight > count){
            return count / 110;
        }
        count -= hight;
        if(low > count){
            return animFile.bodyHighCount + count / 65;
        }
        count -= low;
        return animFile.bodyHighCount + animFile.bodyLowCount + count / 175;
    }

    public Anim getAnim(int body, int action, Body.Direction dir) throws IOException {
        int index = bodyToIndex(body);
        index += action * 5;
        index += dir.getOffset();
        return get(index);
    }

    private int bodyToIndex(int body) {
        int index;
        if (body < animFile.getHighBound()) {
            index = body * 110;
        } else if (body < animFile.getLowBound()) {
            index = animFile.bodyHighCount * 110 + ((body - animFile.bodyHighCount) * 65);
        } else {
            index = animFile.bodyHighCount * 110 + animFile.bodyLowCount * 65 + ((body - animFile.getLowBound()) * 175);
        }
        return index;
    }

    public Body getBody(int body) throws IOException{
        Body out;
        boolean partial = false;

        if (body < animFile.getHighBound()) {
            BodyHigh b = new BodyHigh();
            for (BodyHigh.ActionHigh action : BodyHigh.ActionHigh.values()) {
                partial |= loadAction(body, action, (a,d) -> b.setAnim(a, action, d));
            }
            out = b;
        } else if (body < animFile.getLowBound()) {
            BodyLow b = new BodyLow();
            for (BodyLow.ActionLow action : BodyLow.ActionLow.values()) {
                partial |= loadAction(body, action, (a,d) -> b.setAnim(a, action, d));
            }
            out = b;
        } else {
            BodyChar b = new BodyChar();
            for (BodyChar.ActionChar action : BodyChar.ActionChar.values()) {
                partial |= loadAction(body, action, (a, d) -> b.setAnim(a, action, d));
            }
            out = b;
        }

        if(!partial){
            return null;
        }
        return out;
    }

    private boolean loadAction(int body, Action action, BiConsumer<Anim, Direction> setter) throws IOException{
        boolean complete = true;
        boolean partial = false;
        for (BodyHigh.Direction direction : BodyHigh.Direction.values()) {
            Anim anim = getAnim(body, action.getOffset(), direction);
            if (anim == null) {
                complete = false;
            } else {
                partial = true;
                setter.accept(anim, direction);
            }
        }
        if(!complete && partial){
            System.err.printf("Partial body (%d) animation (%s)\n", body, action);
        }
        return partial;
    }

    public int numberOfBodies(){
        return (fileIndex.size() -35000+1)/175+400-1;
    }

    public AnimFile getAnimFile() {
        return animFile;
    }

}
