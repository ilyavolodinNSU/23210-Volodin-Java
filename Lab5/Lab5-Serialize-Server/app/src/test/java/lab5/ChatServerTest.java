// src/test/java/lab5/ChatServerTest.java
package lab5;

import org.junit.jupiter.api.*;
import java.io.*;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatServerTest {

    private static final String CONFIG_PATH =
        "Lab5-Serialize-Server\\app\\src\\main\\resources\\config.properties";

    @BeforeEach
    void setup() throws Exception {
        // очищаем историю и сбрасываем activeClients через рефлексию
        ChatServer.messageHistory.clear();
        setActiveClients(0);
    }

    // хелперы для работы с private static int activeClients
    private void setActiveClients(int value) throws Exception {
        Field field = ChatServer.class.getDeclaredField("activeClients");
        field.setAccessible(true);
        field.setInt(null, value);
    }

    private int getActiveClients() throws Exception {
        Field field = ChatServer.class.getDeclaredField("activeClients");
        field.setAccessible(true);
        return field.getInt(null);
    }

    @Test
    void testLoadPortFromConfigSuccess() throws IOException {
        File file = new File(CONFIG_PATH);
        file.getParentFile().mkdirs();
        try (FileWriter w = new FileWriter(file)) {
            w.write("server.port=5678\n");
        }
        assertEquals(5678, ChatServer.loadPortFromConfig());
        file.delete();
    }

    @Test
    void testLoadPortFromConfigFailure() {
        new File(CONFIG_PATH).delete();
        assertEquals(1234, ChatServer.loadPortFromConfig());
    }

    @Test
    void testLoadLoggingFromConfigSuccess() throws IOException {
        File file = new File(CONFIG_PATH);
        file.getParentFile().mkdirs();
        try (FileWriter w = new FileWriter(file)) {
            w.write("server.log=true\n");
        }
        assertTrue(ChatServer.loadLoggingFromConfig());
        file.delete();
    }

    @Test
    void testLoadLoggingFromConfigFailure() {
        new File(CONFIG_PATH).delete();
        assertFalse(ChatServer.loadLoggingFromConfig());
    }

    @Test
    void testAddMessageToHistoryBounds() {
        ChatServer.messageHistory.clear();
        for (int i = 0; i < 12; i++) {
            ChatServer.addMessageToHistory(new Message(
                Message.Type.MESSAGE, "u", "m" + i));
        }
        assertEquals(10, ChatServer.messageHistory.size());
        assertEquals("m2", ChatServer.messageHistory.get(0).content());
    }

    @Test
    void testClientDisconnectedDecrements() throws Exception {
        setActiveClients(3);
        ChatServer.clientDisconnected();
        assertEquals(2, getActiveClients());
    }
    
}
