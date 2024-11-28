package actor;

import fpinjava.Result;


import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class PingPong {

    private static final Semaphore semaphore = new Semaphore(1);

    public static void main(String... args) throws InterruptedException {
        Actor<Integer> referee =
                new AbstractActor<Integer>("Referee", Actor.Type.SERIAL) {
                    @Override
                    public void onReceive(Integer message, Result<Actor<Integer>> sender) {
                        System.out.println("Game ended after " + message + " shots");
                        //semaphore.release();
                    }
                };
        Actor<Integer> player1 = new Player("Player1", "Ping", referee);
        Actor<Integer> player2 = new Player("Player2", "Pong", referee);
        //semaphore.acquire();
        player1.tell(1, Result.success(player2));
        semaphore.acquire();
        // Aufgabe Sleep
        sleep(5000);
    }
}
/*
Was ist ein Semaphor?
Ein Semaphor ist eine Art Kontrolle zur Nutzung von Ressourcen.
Semaphor managed die Erlaubniszugriffe an Ressourcen

Warum verwendet das Ping-Pong-Beispiel ein Semaphor?
Es wird verwendet damot das Spiel nicht sofort beendet wird nach einem Zug.
 */

/*
Was können Sie beobachten?, Warum ist das so?
Es wird kein Output ausgegeben da die Threads da die Threads nicht wer jetzt Zugriff hat oder nicht
 */

/*
Warum terminiert das Programm nicht?
https://de.acervolima.com/unterschied-zwischen-daemon-threads-und-benutzer-threads-in-java/
 */

