package actor;

import fpinjava.Result;

public interface Actor<T> {
    static <T> Result<Actor<T>> noSender() {
        return Result.empty();
    }

    Result<Actor<T>> self();

    ActorContext<T> getContext();

    default void tell(T message) {
        tell(message, self());
    }

    void tell(T message, Result<Actor<T>> sender);

    void shutdown();

    default void tell(T message, Actor<T> sender) {
        tell(message, Result.of(sender));
    }

    enum Type {SERIAL, PARALLEL}
}

/*
Wozu dient das Schlüsselwort synchronized?
--synchronized dient zur Sicherung der Datenkonsistenz, wenn mehrere Threads gleichzeitlig auf eine Methode zugreifen

Warum ist die tell-Methode synchronized?
Es ist synchronized
um sicherzustellen, dass Nachrichten
einzeln verarbeitet werden.
 */

/*
Was ist ein ExecutorService?
Die Java ExecutorService ist ein Java Interface, es stellt einen asynchronen Ausführungsmechanismus dar,
der Aufgaben gleichzeitig im Hintergrund ausführen kann.

Was ist ein Daemon-Thread?
In Java ist ein Daemon-Thread ein spezieller Thread, der Hintergrundthread für andere Threads unterstützt
Was ist ein User-Thread?
https://de.acervolima.com/unterschied-zwischen-daemon-threads-und-benutzer-threads-in-java/
Ein User Thread ist ein Thread was wartet das die Arbeit zuende es hört nicht auf bis alle Benutzer Threads fertig sind.
 */

/*
Haben die Aktoren des Actor-Frameworks eine Message-Queue?
Wenn ja: Wo ist sie denn?
Ja das Actor-Framework besitzt eine Message-Queue dies befindet sich in
 */

/*
Was ist ein Semaphor?
Warum verwendet das Ping-Pong-Beispiel ein Semaphor?
 */

/*
Führen Sie das Ping-Pong-Beispiel ohne Semaphor aus.
Was können Sie beobachten?
Warum ist das so?
 */
