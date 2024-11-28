package netcat.uni.tcp;


import inout.Input;
import inout.Output;
import inout.ProcessReader;
import inout.ProcessWriter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.*;

import static inout.ProcessReader.processReader;
import static inout.ProcessWriter.processWriter;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetcatTest {

    @ParameterizedTest
    @CsvSource("'hallo'")
    void netcatTest(String inputtest) throws IOException, InterruptedException {
        ProcessBuilder serv = new ProcessBuilder("java", "-jar", "/builds/vspraktikum/wise22_23/Blend.Salihu/inputoutpu/out/artifacts/unicat/unicat.jar", "-l","4444");
        serv.directory(new File("/builds/vspraktikum/wise22_23/Blend.Salihu/inputoutpu/out/artifacts/unicat"));
        ProcessBuilder client = new ProcessBuilder("java", "-jar", "/builds/vspraktikum/wise22_23/Blend.Salihu/inputoutpu/out/artifacts/unicat/unicat.jar", "localhost", "4444");
        client.directory(new File("/builds/vspraktikum/wise22_23/Blend.Salihu/inputoutpu/out/artifacts/unicat"));
        Process readstart = serv.start();
        Process writestart = client.start();
        Input read = processReader(readstart);
        Output write = processWriter(writestart);
        write.printLine(inputtest);
        write.printLine("\u0004");
        write.shutdownOutput();
        sleep(2000);
        assertEquals(read.readLines().head(), inputtest);
        writestart.destroy();
        readstart.destroy();


    }
/*

    @ParameterizedTest
    @CsvSource("'hallo'")
    void netcatTest(String inputtest) throws IOException, InterruptedException {
        ProcessBuilder serv = new ProcessBuilder("java", "-jar", "C:\\Users\\49162\\IdeaProjects\\inputoutpu\\out\\artifacts\\unicat\\unicat.jar", "-l","4444");
        serv.directory(new File("C:\\Users\\49162\\IdeaProjects\\inputoutpu\\out\\artifacts\\unicat"));
        ProcessBuilder client = new ProcessBuilder("java", "-jar", "C:\\Users\\49162\\IdeaProjects\\inputoutpu\\out\\artifacts\\unicat\\unicat.jar", "localhost", "4444");
        client.directory(new File("C:\\Users\\49162\\IdeaProjects\\inputoutpu\\out\\artifacts\\unicat\\unicat.jar"));
        Process readstart = serv.start();
        Process writestart = client.start();
        Input read = processReader(readstart);
        Output write = processWriter(writestart);
        write.printLine(inputtest);
        write.printLine("\u0004");
        write.shutdownOutput();
        sleep(2000);
        assertEquals(read.readLines().head(), inputtest);
        writestart.destroy();
        readstart.destroy();


    }

 */
}

