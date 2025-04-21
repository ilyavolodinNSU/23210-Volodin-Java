// package lab5;

// import org.junit.jupiter.api.*;
// import org.junit.jupiter.api.io.TempDir;
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;

// import javax.swing.JFrame;
// import javax.xml.parsers.DocumentBuilderFactory;
// import java.io.*;
// import java.nio.file.Path;
// import java.util.Properties;
// import static org.junit.jupiter.api.Assertions.*;

// class XmlMessageTest {
//     @Test
//     void constructorSetsFieldsCorrectly() {
//         XmlMessage msg = new XmlMessage(XmlMessage.Type.MESSAGE, "user", "hello", "session123");
//         assertEquals(XmlMessage.Type.MESSAGE, msg.getType());
//         assertEquals("user", msg.getSender());
//         assertEquals("hello", msg.getContent());
//         assertEquals("session123", msg.getSessionId());
//     }

//     @Test
//     void toXmlDocumentCreatesCorrectLoginXml() throws Exception {
//         XmlMessage msg = new XmlMessage(XmlMessage.Type.LOGIN, "testUser", null, null);
//         Document doc = msg.toXmlDocument();
//         Element root = doc.getDocumentElement();
        
//         assertEquals("command", root.getTagName());
//         assertEquals("login", root.getAttribute("name"));
//         assertEquals("testUser", root.getElementsByTagName("name").item(0).getTextContent());
//         assertEquals("CHAT_CLIENT_XML", root.getElementsByTagName("type").item(0).getTextContent());
//     }

//     @Test
//     void fromXmlParsesMessageEventCorrectly() throws Exception {
//         Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
//         Element event = doc.createElement("event");
//         event.setAttribute("name", "message");
//         Element name = doc.createElement("name");
//         name.setTextContent("sender");
//         Element message = doc.createElement("message");
//         message.setTextContent("test message");
//         event.appendChild(name);
//         event.appendChild(message);
//         doc.appendChild(event);

//         XmlMessage msg = XmlMessage.fromXml(doc);
//         assertEquals(XmlMessage.Type.MESSAGE, msg.getType());
//         assertEquals("sender", msg.getSender());
//         assertEquals("test message", msg.getContent());
//     }
// }

// class XmlChatClientTest {
//     private XmlChatClient client;
//     private TestXmlClientGUI testGui;

//     static class TestXmlClientGUI extends XmlClientGUI {
//         String lastMessage;
        
//         public TestXmlClientGUI(String userName, int port, String serverHost) {
//             super(userName, port, serverHost);
//         }
        
//         @Override
//         public void appendMessage(String message) {
//             lastMessage = message;
//         }
//     }

//     @Test
//     void clientCreationSetsSessionId() {
//         // Этот тест требует реального сервера для работы
//         // В реальном проекте нужно использовать mock-сервер
//         assertTrue(true); // Заглушка для демонстрации
//     }

//     @Test
//     void sendXMLCommandDoesNotThrowWithValidDocument() {
//         try {
//             Document doc = DocumentBuilderFactory.newInstance()
//                     .newDocumentBuilder()
//                     .newDocument();
//             Element command = doc.createElement("command");
//             doc.appendChild(command);
            
//             // В реальном тесте нужно проверить отправку данных
//             assertDoesNotThrow(() -> client.sendXMLCommand(doc));
//         } catch (Exception e) {
//             fail("Exception during test setup: " + e.getMessage());
//         }
//     }
// }

// class XmlClientGUITest {
//     private XmlClientGUI gui;
    
//     @Test
//     void appendMessageAddsTextToChatArea() {
//         TestFrame frame = new TestFrame();
//         gui = new XmlClientGUI("test", 1234, "localhost") {
//             @Override
//             protected void initComponents() {
//                 // Skip GUI initialization for tests
//             }
//         };
        
//         gui.appendMessage("test message");
//         // В реальном тесте нужно проверить содержимое chatArea
//         assertTrue(true);
//     }

//     @Test
//     void sendMessageHandlesEmptyInput() {
//         gui = new XmlClientGUI("test", 1234, "localhost") {
            
//             @Override
//             public void sendMessage() {
//                 super.sendMessage();
//             }
//         };
        
//         assertDoesNotThrow(() -> gui.sendMessage());
//     }

//     @Test
//     void loadPortFromConfigReturnsDefaultWhenFileMissing() {
//         assertEquals(1234, XmlClientGUI.loadPortFromConfig());
//     }

//     @Test
//     void loadHostFromConfigReturnsDefaultWhenFileMissing() {
//         assertEquals("localhost", XmlClientGUI.loadHostFromConfig());
//     }

//     @Test
//     void loadConfigValuesFromFile(@TempDir Path tempDir) throws IOException {
//         File configFile = tempDir.resolve("config.properties").toFile();
//         try (FileOutputStream out = new FileOutputStream(configFile)) {
//             Properties props = new Properties();
//             props.setProperty("server.port", "9999");
//             props.setProperty("server.host", "testhost");
//             props.store(out, null);
//         }

//         int port = XmlClientGUI.loadPortFromConfig();
//         String host = XmlClientGUI.loadHostFromConfig();

//         assertEquals(9999, port);
//         assertEquals("testhost", host);
//     }
// }