package netcat.bidi.tcp;

import inout.Input;
import inout.InputOutput;
import inout.Output;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.IOException;

import static inout.ProcessReaderWriter.processReaderWriter;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetcatTest {


    @ParameterizedTest
    @CsvSource("'hallo', 'hi'")
    void netcatTest(String inputclient, String inputserver) throws IOException, InterruptedException {
        ProcessBuilder serv = new ProcessBuilder("java", "-jar", "/builds/vspraktikum/wise22_23/Blend.Salihu/inputoutpu/out/artifacts/bidicat/bidicat.jar", "-l", "9999");
        serv.directory(new File("/builds/vspraktikum/wise22_23/Blend.Salihu/inputoutpu/out/artifacts/bidicat"));
        ProcessBuilder client = new ProcessBuilder("java", "-jar", "/builds/vspraktikum/wise22_23/Blend.Salihu/inputoutpu/out/artifacts/bidicat/bidicat.jar", "localhost", "9999");
        client.directory(new File("/builds/vspraktikum/wise22_23/Blend.Salihu/inputoutpu/out/artifacts/bidicat"));
        Process serverstart = serv.start();
        Process clientstart = client.start();
        InputOutput serverinputout = processReaderWriter(serverstart);
        InputOutput clientinputoit = processReaderWriter(clientstart);
        clientinputoit.printLine(inputclient);
        serverinputout.printLine(inputserver);
        clientinputoit.printLine("\u0004");
        serverinputout.printLine("\u0004");
        clientinputoit.shutdownOutput();
        serverinputout.shutdownOutput();
        assertEquals(clientinputoit.readLines().head(), inputserver);
        assertEquals(serverinputout.readLines().head(), inputclient);
        clientstart.destroy();
        serverstart.destroy();

    }



//
/*
    @ParameterizedTest
    @CsvSource("'hallo', 'hi'")
    void netcatTest(String inputclient, String inputserver) throws IOException, InterruptedException {
        ProcessBuilder serv = new ProcessBuilder("java", "-jar", "C:\\Users\\49162\\IdeaProjects\\inputoutpu\\out\\artifacts\\bidicat\\bidicat.jar", "-l","3333");
        serv.directory(new File("C:\\Users\\49162\\IdeaProjects\\inputoutpu\\out\\artifacts\\bidicat"));
        ProcessBuilder client = new ProcessBuilder("java", "-jar", "C:\\Users\\49162\\IdeaProjects\\inputoutpu\\out\\artifacts\\bidicat\\bidicat.jar", "localhost", "3333");
        client.directory(new File("C:\\Users\\49162\\IdeaProjects\\inputoutpu\\out\\artifacts\\bidicat"));
        Process serverstart = serv.start();
        Process clientstart = client.start();
        //System.out.println(new String(serverstart.getErrorStream().readAllBytes()));
        InputOutput serverinputout = processReaderWriter(serverstart);
        InputOutput clientinputoit = processReaderWriter(clientstart);
        serverinputout.printLine(inputserver);
        clientinputoit.printLine(inputclient);
        serverinputout.printLine("\u0004");
        clientinputoit.printLine("\u0004");
        serverinputout.shutdownOutput();
        clientinputoit.shutdownOutput();
        assertEquals(clientinputoit.readLines().head(), inputserver);
        assertEquals(serverinputout.readLines().head(), inputclient);
        clientstart.destroy();
        serverstart.destroy();

    }

 */


}
