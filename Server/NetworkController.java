package Server;

import java.net.*;
import java.io.*;

class User extends Thread {
    private DatagramSocket socket;
    private InetAddress inet;

    User(DatagramSocket serverSocket) {
        super();
        try {
            inet = InetAddress.getByName("172.19.70.170");
            socket = new DatagramSocket();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /*
     * Отправка пакета с верхним пределом, нижним пределом и шагом. Затем получение
     * результата расчета.
     */
    public void run() {

        System.out.println(this.threadId() + ": ожидание подключения.");

        try {
            DatagramPacket pack = new DatagramPacket("12345".getBytes(), 5, this.inet, 8080);
            while (true) {
                this.socket.send(pack);
                Thread.sleep(1000);

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

class Sender extends Thread {
    BufferedReader in; // Получение сообщений
    PrintWriter out; // Отправка сообщений
    Socket clientSocket;

    Sender(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
        System.out.println(this.clientSocket + ": Клиент подключен");
    }

    public void run() {

    }
}

public class NetworkController {
    public NetworkController() {
        try {
            User[] users = new User[5];
            Server server = new Server();
            for (var i : users) {
                i = new User(server.getSocket());
                i.start();

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}