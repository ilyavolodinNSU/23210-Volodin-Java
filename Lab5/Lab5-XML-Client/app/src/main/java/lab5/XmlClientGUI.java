package lab5;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlClientGUI {
    private final XmlChatClient client;
    private final JTextArea chatArea;
    private final JTextField messageField;

    public XmlClientGUI(String userName, int port, String serverHost) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setDarkTheme();
        } catch (Exception e) {
            e.printStackTrace();
        }

        client = new XmlChatClient(userName, port, serverHost, this);

        JFrame frame = new JFrame("XML Chat - " + userName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setMinimumSize(new Dimension(400, 500));
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(45, 45, 45));

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chatArea.setForeground(Color.WHITE);
        chatArea.setBackground(new Color(60, 60, 60));
        chatArea.setCaretColor(Color.WHITE);

        JScrollPane chatScroll = new JScrollPane(chatArea);
        chatScroll.setBorder(BorderFactory.createEmptyBorder());
        chatScroll.getViewport().setBackground(new Color(60, 60, 60));
        mainPanel.add(chatScroll, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        inputPanel.setBackground(new Color(45, 45, 45));

        messageField = new JTextField();
        messageField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageField.setForeground(Color.WHITE);
        messageField.setBackground(new Color(80, 80, 80));
        messageField.setCaretColor(Color.WHITE);
        messageField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(new Color(70, 130, 180));
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);

        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());

        frame.setVisible(true);
        client.listenForMessages();
    }

    private void setDarkTheme() {
        UIManager.put("control", new Color(70, 70, 70));
        UIManager.put("info", new Color(60, 60, 60));
        UIManager.put("nimbusBase", new Color(30, 30, 30));
        UIManager.put("nimbusAlertYellow", new Color(210, 170, 0));
        UIManager.put("nimbusDisabledText", new Color(150, 150, 150));
        UIManager.put("nimbusFocus", new Color(70, 130, 180));
        UIManager.put("nimbusGreen", new Color(50, 150, 50));
        UIManager.put("nimbusInfoBlue", new Color(50, 120, 200));
        UIManager.put("nimbusLightBackground", new Color(60, 60, 60));
        UIManager.put("nimbusOrange", new Color(200, 100, 0));
        UIManager.put("nimbusRed", new Color(180, 50, 50));
        UIManager.put("nimbusSelectedText", Color.WHITE);
        UIManager.put("nimbusSelectionBackground", new Color(70, 130, 180));
        UIManager.put("text", Color.WHITE);
    }

    public void sendMessage() {
        String message = messageField.getText().trim();
        if (message.isEmpty()) return;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element commandElem = doc.createElement("command");

            if (message.equalsIgnoreCase("/list")) {
                commandElem.setAttribute("name", "list");
                Element sessionElem = doc.createElement("session");
                sessionElem.setTextContent(client.getSessionId());
                commandElem.appendChild(sessionElem);
            } else if (message.equalsIgnoreCase("/exit")) {
                commandElem.setAttribute("name", "logout");
                Element sessionElem = doc.createElement("session");
                sessionElem.setTextContent(client.getSessionId());
                commandElem.appendChild(sessionElem);
            } else if (message.toLowerCase().startsWith("/whisper")) {
                String[] parts = message.split("\\s+", 3);
                if (parts.length < 3) return;
                
                commandElem.setAttribute("name", "whisper");
                Element sessionElem = doc.createElement("session");
                sessionElem.setTextContent(client.getSessionId());
                commandElem.appendChild(sessionElem);

                Element targetElem = doc.createElement("target");
                targetElem.setTextContent(parts[1]);
                commandElem.appendChild(targetElem);

                Element messageElem = doc.createElement("message");
                messageElem.setTextContent(parts[2]);
                commandElem.appendChild(messageElem);

                appendMessage("[whisper]: " + parts[2] + " to " + parts[1]);
            } else {
                commandElem.setAttribute("name", "message");
                Element messageElem = doc.createElement("message");
                messageElem.setTextContent(message);
                commandElem.appendChild(messageElem);

                Element sessionElem = doc.createElement("session");
                sessionElem.setTextContent(client.getSessionId());
                commandElem.appendChild(sessionElem);

                appendMessage("You: " + message);
            }

            doc.appendChild(commandElem);
            client.sendXMLCommand(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        messageField.setText("");
    }

    public void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }

    public static int loadPortFromConfig() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            return Integer.parseInt(properties.getProperty("server.port", "1234"));
        } catch (IOException e) {
            return 1234;
        }
    }

    public static String loadHostFromConfig() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            return properties.getProperty("server.host", "localhost");
        } catch (IOException e) {
            return "localhost";
        }
    }

    public static void main(String[] args) {
        String userName = JOptionPane.showInputDialog("Enter your username for the XML chat:");
        if (userName != null) {
            int port = loadPortFromConfig();
            String serverHost = loadHostFromConfig();
            SwingUtilities.invokeLater(() -> new XmlClientGUI(userName, port, serverHost));
        } else {
            System.exit(0);
        }
    }
}