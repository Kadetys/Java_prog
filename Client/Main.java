package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

class Pack {
    private Socket socket;

    Pack() throws UnknownHostException, IOException {

        socket = new Socket("localhost", 8080);
        System.out.println(socket);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                    true);
            for (int i = 0; i < 10; i++) {
                out.println(i + 1);
            }

        } finally {
            socket.close();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Pack p;
        try {
            p = new Pack();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
