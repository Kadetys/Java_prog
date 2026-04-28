package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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