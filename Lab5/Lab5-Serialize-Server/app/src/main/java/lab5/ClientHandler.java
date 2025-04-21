package lab5;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import lab5.Message.Type;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clients = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String userName;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // 1 полученный объект - сообщение регистрации
            Message loginMsg = (Message) in.readObject();
            if (loginMsg.type() == Type.LOGIN) {
                this.userName = loginMsg.sender();
                clients.add(this);

                //  новому клиенту даю историю сообщений
                for (Message m : ChatServer.messageHistory) {
                    out.writeObject(m);
                }
                out.flush();

                Message msg = new Message(Type.SYSTEM, "SERVER", userName + " joined the chat.");
                ChatServer.addMessageToHistory(msg); // добавляем сообщение о регистрации в историю
                broadcastMessage(msg);
            }
        } catch (IOException | ClassNotFoundException e) {
            closeAll();
        }
    }

    @Override
    public void run() {
        try {
            Object obj;
            while ((obj = in.readObject()) != null) {
                if (obj instanceof Message) {
                    Message msg = (Message) obj;
                    if (msg.type() == Type.LOGOUT) {
                        Message logoutMsg = new Message(Type.SYSTEM, "SERVER", userName + " left the chat.");
                        ChatServer.addMessageToHistory(logoutMsg);
                        broadcastMessage(logoutMsg);
                        break;
                    } else if (msg.type() == Type.LIST) {
                        List<String> userNames = new ArrayList<>();
                        for (ClientHandler client : clients) {
                            userNames.add(client.userName);
                        }

                        String userList = "Active users:\n" + String.join("\n", userNames);
                        Message listResponse = new Message(Type.LIST, "SERVER", userList);
                        // отправляем ответ только запрашивающему клиенту!!!
                        out.writeObject(listResponse);
                        out.flush();
                    } else if (msg.type() == Type.MESSAGE) {
                        ChatServer.addMessageToHistory(msg);
                        broadcastMessage(msg);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error receiving messages from client: " + e.getMessage());
        } finally {
            closeAll();
        }
    }

    public void broadcastMessage(Message msg) {
        for (ClientHandler client : clients) {
            try {
                if (msg.type() == Type.MESSAGE && client.userName.equals(this.userName)) {
                    continue;
                }
                client.out.writeObject(msg);
                client.out.flush();
            } catch (IOException e) {
                closeAll();
            }
        }
    }

    public void closeAll() {
        clients.remove(this);
        ChatServer.clientDisconnected();
        try {
            if (socket != null) socket.close();
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (IOException e) {
            System.out.println("Error closing socket: " + e.getMessage());
        }
    }
}
