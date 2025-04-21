package lab5;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import org.w3c.dom.Document;

public class XmlChatServer {
    private static final Logger logger = LogManager.getLogger(XmlChatServer.class.getName());
    private static boolean loggingEnabled;

    private ServerSocket serverSocket;
    private static int activeClients = 0;

    public static ArrayList<Document> messageHistory = new ArrayList<>();

    public XmlChatServer(int port, boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
        try {
            serverSocket = new ServerSocket(port);
            log("XML Server started on port: " + port);
        } catch (IOException e) {
            log("Error starting XML server: " + e.getMessage());
            System.exit(0);
        }
    }

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                ++activeClients;
                log("A new client has connected! Active clients: " + activeClients);
                XmlClientHandler handler = new XmlClientHandler(clientSocket);
                Thread thread = new Thread(handler);
                thread.start(); // invoke the run() method in the new thread (в объекте ClientHandler запустилось считывание клиентских сообщений)
            }
        } catch (IOException e) {
            log("Error starting server: " + e.getMessage());
        }
    }

    public static void clientDisconnected() {
        --activeClients;
        log("A client has disconnected. Active clients: " + activeClients);
        if (activeClients == 0) {
            log("No clients connected. Server shutting down...");
            System.exit(0);
        }
    }

    public static void log(String message) {
        if (loggingEnabled) {
            logger.info(message);
        }
        System.out.println(message);
    }

    public static synchronized void addMessageToHistory(Document messageDoc) {
        messageHistory.add(messageDoc);
        if (messageHistory.size() > 10) {
            messageHistory.removeFirst();
        }
    }

    public static int loadPortFromConfig() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("app\\src\\main\\resources\\config.properties")) {
            properties.load(input);
            return Integer.parseInt(properties.getProperty("server.port", "1234"));
        } catch (IOException e) {
//            System.out.println("Error loading config, using default port 1234");
            //log("Error loading config, using default port 1234");
            return 1234;
        }
    }

    public static boolean loadLoggingFromConfig() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("app\\src\\main\\resources\\config.properties")) {
            properties.load(input);
            return Boolean.parseBoolean(properties.getProperty("server.log", "false"));
        } catch (IOException e) {
            log("Error loading logging config, using default (false)");
            return false;
        }
    }

    public static void main(String[] args) {
        int port = loadPortFromConfig(); // порт на котором сервер слушает подключения
        boolean logEnabled = loadLoggingFromConfig();
        XmlChatServer server = new XmlChatServer(port, logEnabled);
        server.start();
    }
}
