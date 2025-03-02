package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.defs.BodyConvEntry;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DefFilesTest {


    @Test
    public void testLoadAndSaveBodyConv() throws URISyntaxException, IOException {
        Path resouce = Paths.get(ClassLoader.getSystemResource("defs/Bodyconv.def").toURI());
        BodyConv bodyConv = BodyConv.load(resouce, StandardCharsets.UTF_8);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bodyConv.save(os, StandardCharsets.UTF_8);

        byte[] input = Files.readAllBytes(resouce);
        byte[] output = os.toByteArray();

        String inputString = new String(input, StandardCharsets.UTF_8);
        String outputString = new String(output, StandardCharsets.UTF_8);

        assertEquals(inputString, outputString);
        assertArrayEquals(input, output);
    }

    @Test
    public void testEditsBodyConv() throws URISyntaxException, IOException {
        Path resouce = Paths.get(ClassLoader.getSystemResource("defs/Bodyconv.def").toURI());
        BodyConv bodyConv = BodyConv.load(resouce, StandardCharsets.UTF_8);

        // 932	-1	932	-1	-1
        BodyConvEntry entry1 = bodyConv.get(932);
        entry1.setAnimFile(Anims.AnimFile.ANIM4);
        entry1.setIndexInFile(42);

        // 90	-1	90	-1	-1	# ocicko (Seeker) od Narji
        BodyConvEntry entry2 = new BodyConvEntry(90, Anims.AnimFile.ANIM2, 69);
        bodyConv.set(entry2);

        // new one
        BodyConvEntry entry3 = new BodyConvEntry(666, Anims.AnimFile.ANIM3, 123);
        bodyConv.set(entry3);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bodyConv.save(os, StandardCharsets.UTF_8);

        Path resouceModified = Paths.get(ClassLoader.getSystemResource("defs/Bodyconv-modified.def").toURI());
        byte[] input = Files.readAllBytes(resouceModified);
        byte[] output = os.toByteArray();

        String inputString = new String(input, StandardCharsets.UTF_8);
        String outputString = new String(output, StandardCharsets.UTF_8);

        assertEquals(inputString, outputString);
        assertArrayEquals(input, output);
    }



    @Test
    public void testLoadAndSaveBody() throws URISyntaxException, IOException {
        Path resouce = Paths.get(ClassLoader.getSystemResource("defs/Body.def").toURI());
        Body bodyConv = Body.load(resouce, StandardCharsets.UTF_8);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bodyConv.save(os, StandardCharsets.UTF_8);

        Path resouceCorrected = Paths.get(ClassLoader.getSystemResource("defs/Body-corrected.def").toURI());
        byte[] input = Files.readAllBytes(resouceCorrected);
        byte[] output = os.toByteArray();

        String inputString = new String(input, StandardCharsets.UTF_8);
        String outputString = new String(output, StandardCharsets.UTF_8);

        assertEquals(inputString, outputString);
        assertArrayEquals(input, output);
    }

}