package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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
        mainIntegral.addResult(sum);
    }
}

class Integral {
    private double result;

    public double calculate(double lim_low, double lim_high, double step) {
        result = 0;
        /* Создание потоков для вычисления подинтегралов */
        var th1 = new SubIntegralThread(lim_low,
                lim_low + ((lim_high - lim_low) / 3),
                step,
                this,
                "th1");
        var th2 = new SubIntegralThread(lim_low + ((lim_high - lim_low) / 3),
                lim_low + 2 * ((lim_high - lim_low) / 3),
                step,
                this,
                "th2");
        var th3 = new SubIntegralThread(lim_low + 2 * ((lim_high - lim_low) / 3),
                lim_high,
                step,
                this,
                "th3");
        long start = System.nanoTime();
        th1.start();
        th2.start();
        th3.start();

        try {
            th1.join();
            th2.join();
            th3.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        long stop = System.nanoTime();
        System.out.printf("Время вычисления: %d мс.\n", (stop - start) / 1000_000);
        return result;
    }

    void addResult(double value) {
        this.result += value;
    }

    double getResult() {
        return this.result;
    }

}

class Pack {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    Pack() throws UnknownHostException, IOException {

        socket = new Socket("localhost", 8080);
        System.out.println(socket);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        try {
            while (socket.isConnected()) {
                String[] received = (in.readLine().split("|"));
                System.out.printf(
                        "Получены данные с сервера...\n Верхний предел: %s\n Нижний предел: %s\n Шаг: %s",
                        received[0],
                        received[1],
                        received[2]);
                Integral integral = new Integral();
                integral.calculate(
                        Double.parseDouble(received[0]),
                        Double.parseDouble(received[1]),
                        Double.parseDouble(received[2]));
                out.println(integral.getResult());
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
