// src/test/java/lab5/ClientHandlerTest.java
package lab5;

import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;

import static lab5.Message.Type.*;
import static org.junit.jupiter.api.Assertions.*;

class ClientHandlerTest {

    @BeforeEach
    void setup() throws Exception {
        ChatServer.messageHistory.clear();
        ClientHandler.clients.clear();
        // сброс activeClients, чтобы closeAll не вызывал System.exit
        Field f = ChatServer.class.getDeclaredField("activeClients");
        f.setAccessible(true);
        f.setInt(null, 2);
    }

    @Test
    void constructor_onLogin_addsClientAndSendsHistoryAndBroadcasts() throws Exception {
        ChatServer.messageHistory.add(new Message(MESSAGE, "u0", "m0"));

        // подготовка LOGIN
        ByteArrayOutputStream loginBuf = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(loginBuf)) {
            oos.writeObject(new Message(LOGIN, "john", "john"));
        }
        ByteArrayInputStream inStream = new ByteArrayInputStream(loginBuf.toByteArray());
        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();

        Socket stub = new Socket() {
            @Override public InputStream getInputStream() { return inStream; }
            @Override public OutputStream getOutputStream() { return outBuf; }
        };

        ClientHandler handler = new ClientHandler(stub);

        assertTrue(ClientHandler.clients.contains(handler));
        assertEquals(2, ChatServer.messageHistory.size());

        try (ObjectInputStream ois = new ObjectInputStream(
                 new ByteArrayInputStream(outBuf.toByteArray()))) {
            Message hist = (Message) ois.readObject();
            assertEquals("m0", hist.content());
            Message join = (Message) ois.readObject();
            assertEquals(SYSTEM, join.type());
            assertTrue(join.content().contains("john joined"));
        }
    }

    @Test
    void run_onList_sendsListResponse() throws Exception {
        // LOGIN + LIST
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(buf)) {
            oos.writeObject(new Message(LOGIN, "a", "a"));
            oos.writeObject(new Message(LIST, "a", ""));
        }
        ByteArrayInputStream inStream = new ByteArrayInputStream(buf.toByteArray());
        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();

        Socket stub = new Socket() {
            @Override public InputStream getInputStream() { return inStream; }
            @Override public OutputStream getOutputStream() { return outBuf; }
        };

        ClientHandler handler = new ClientHandler(stub);
        handler.run();

        try (ObjectInputStream ois = new ObjectInputStream(
                 new ByteArrayInputStream(outBuf.toByteArray()))) {
            // пропускаем join
            Message join = (Message) ois.readObject();
            assertEquals(SYSTEM, join.type());
            Message listResp = (Message) ois.readObject();
            assertEquals(LIST, listResp.type());
            assertTrue(listResp.content().contains("Active users"));
            assertTrue(listResp.content().contains("a"));
        }
    }
}
