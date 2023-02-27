package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.skills.Skill;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import eu.janinko.andaria.ultimasdk.files.index.FileIndex;
import eu.janinko.andaria.ultimasdk.files.index.IdxFile;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Skills extends IdxFile<Skill>{

    private Skills(InputStream skillidx, File skillmul) throws IOException {
        super(skillidx, skillmul, 100);
    }

    public static Skills open(Path idxFile, Path mulFile) throws IOException {
        try (InputStream idxStream = Files.newInputStream(idxFile)) {
            return new Skills(idxStream, mulFile.toFile());
        }
    }

    @Override
    public Skill get(int index) throws IOException{
        FileIndex.DataPack data =  fileIndex.getData(index);
        if(data == null) return null;

        return new Skill(data.getNewStream(), data.getData().length);
    }

    public void setSkill(int i, Skill skill, OutputStream skillidx) throws IOException {
        byte[] data = skill.write();
        int extra = 0;
        FileIndex.DataPack dp = new FileIndex.DataPack(data, extra);
        fileIndex.set(i, dp);
    }
}