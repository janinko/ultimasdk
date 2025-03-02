package eu.janinko.andaria.ultimasdk.utils;

import eu.janinko.andaria.ultimasdk.files.Anims;
import eu.janinko.andaria.ultimasdk.files.Body;
import eu.janinko.andaria.ultimasdk.files.BodyConv;

public class AnimResolver {

    public static AnimationInfo resolve(int id, Body body, BodyConv bodyConv) {
        int resolvedId = id;
        int color = 0;
        if (body.get(resolvedId) != null) {
            resolvedId = body.get(id).getReplacementAnimID();
            color = body.get(id).getColor();
        }
        Anims.AnimFile animFile = Anims.AnimFile.ANIM1;
        int fileIndex = resolvedId;
        if (bodyConv.get(resolvedId) != null) {
            animFile = bodyConv.get(resolvedId).getAnimFile();
            fileIndex = bodyConv.get(resolvedId).getIndexInFile();
        }

        return new AnimationInfo(id, animFile, fileIndex, color);
    }

    public record AnimationInfo(int originalId, Anims.AnimFile animFile, int idInFile, int color) {
    }
}
