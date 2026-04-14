import java.time.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.JOptionPane;

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

class SubIntegralThread implements Callable<Double> {
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

    public Double call() {
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
        return sum;
    }
}

public class Integral {
    private double result;

    public double calculate(
            double lim_low,
            double lim_high,
            double step) throws Integral_Exception, InterruptedException {
        if ((lim_low < 0.000001 || lim_low > 1000000.0) ||
                (lim_high < 0.000001 || lim_high > 1000000.0) ||
                (step < 0.000001 || step > 1000000.0)) {
            throw new Integral_Exception(
                    "Недопустимый диапазон введенных данных.\n" +
                            "Максимальное допустимое число: 999999\n" +
                            "Минимальное допустимое число: 0.000001");
        }
        if (lim_low >= lim_high) {
            throw new Integral_Exception("Нижний предел должен быть меньше верхнего.");
        }

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
        var futureTask_1 = new FutureTask<Double>(th1);
        var futureTask_2 = new FutureTask<Double>(th2);
        var futureTask_3 = new FutureTask<Double>(th3);
        var t1 = new Thread(futureTask_1);
        var t2 = new Thread(futureTask_2);
        var t3 = new Thread(futureTask_3);
        t1.start();
        t2.start();
        t3.start();
        try {
            result = futureTask_1.get() + futureTask_2.get() + futureTask_3.get();

        } catch (ExecutionException ex) {
            System.out.println(ex.getMessage());
        }

        long stop = System.nanoTime();
        System.out.printf("Время вычисления: %d мс.\n", (stop - start) / 1000_000);
        return result;
    }

    public void addResult(double value) {
        this.result += value;
    }
}