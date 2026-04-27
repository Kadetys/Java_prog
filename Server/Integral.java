package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.spi.InetAddressResolver;

class Integral_Exception extends Exception {
    public Integral_Exception(String message) {
        super(message);
    }

    public void what() {
        System.out.print("Недопустимый диапазон введенных данных.\n\n" +
                "Максимальное допустимое число: 999999\n" +
                "Минимальное допустимое число: 0.000001\n");
    }

}

class SubIntegralThread extends Thread {
    private String name;
    Integral mainIntegral;
    double lim_high, lim_low, step;

    public SubIntegralThread(double lim_low, double lim_high, double step, Integral mainIntegral, String name) {
        super();
        this.name = name;
        this.mainIntegral = mainIntegral;
        this.lim_low = lim_low;
        this.lim_high = lim_high;
        this.step = step;
    }

    public void run() {
        double sum = Math.cos(lim_low);
        for (double i = lim_low + step; i < lim_high; i += step) {
            sum += Math.cos(i);
            if ((i + step) >= lim_high) {
                sum = sum * step;
                sum += (Math.cos(lim_high) * (lim_high - i));
                break;

            }
        }
        System.out.println(this.name + ": calculating finished.");
        this.mainIntegral.addResult(sum);
    }
}

class Pack {
    private ServerSocket serverSocket; // Серверный сокет
    private Socket clientSocket; // Пишущий сокет
    BufferedReader in; // Получение сообщений
    PrintWriter out; // Отправка сообщений

    Pack() throws IOException, InterruptedException, Integral_Exception {

        serverSocket = new ServerSocket(8080);
        System.out.println("Started: " + serverSocket);
        try {
            clientSocket = serverSocket.accept();
            System.out.println("client connected");
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);

        } catch (IOException ex) {
        }

    }

    /*
     * Отправка пакета с верхним пределом, нижним пределом и шагом. Затем получение
     * результата расчета.
     */
    double send(double lim_high, double lim_low, double step) {

        double result = 0;
        out.printf("%f|%f|%f\n", lim_high, lim_low, step);
        try {
            result = Double.parseDouble(in.readLine());
            return result;
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
            return result;
        }
    }

    void disconnect() throws IOException {

        clientSocket.close();
        serverSocket.close();

    }
}

public class Integral {
    private double result;

    public double calculate(double lim_low, double lim_high, double step) throws Integral_Exception {
        if ((lim_low < 0.000001 || lim_low > 1000000.0) ||
                (lim_high < 0.000001 || lim_high > 1000000.0) ||
                (step < 0.000001 || step > 1000000.0)) {
            throw new Integral_Exception("Недопустимый диапазон введенных данных.\n" +
                    "Максимальное допустимое число: 999999\n" +
                    "Минимальное допустимое число: 0.000001");
        }
        if (lim_low >= lim_high) {
            throw new Integral_Exception("Нижний предел должен быть меньше верхнего.");
        }
        // long start = System.nanoTime();
        // long stop = System.nanoTime();

        // System.out.printf("Время вычисления: %d мс.\n", (stop - start) / 1000_000);
        return result;
    }

    public void addResult(double value) {
        this.result += value;
    }
}