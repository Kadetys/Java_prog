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

public class Integral {
    private double result;

    public double calculate(
            double lim_low,
            double lim_high,
            double step) throws Integral_Exception {
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

        return result;
    }

    public void addResult(double value) {
        this.result += value;
    }
}