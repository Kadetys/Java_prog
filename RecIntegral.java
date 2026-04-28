public class RecIntegral {

    private double limlow, limhigh, step, result;

    public RecIntegral(double lim_low,
            double lim_high,
            double step,
            double result) throws Integral_Exception {
        this.limlow = lim_low;
        this.limhigh = lim_high;
        this.step = step;
        this.result = result;
    }

    public RecIntegral(double lim_low,
            double lim_high,
            double step) throws Integral_Exception {
        if ((lim_low < 0.000001 || lim_low > 1000000.0)) {
            throw new Integral_Exception(
                    lim_low + " имеет неверный формат. "
                            + "Нижний предел имеет недопустимый диапазон.\nДопустимый диапазон: от 0.000001 до 1000000");
        }
        if (lim_high < 0.000001 || lim_high > 1000000.0) {
            throw new Integral_Exception(
                    lim_high + " имеет неверный формат. "
                            + "Верхний предел имеет недопустимый диапазон.\nДопустимый диапазон: от 0.000001 до 1000000");
        }
        if (step < 0.000001 || step > 1000000.0) {
            throw new Integral_Exception(
                    step + " имеет неверный формат. "
                            + "Шаг имеет недопустимый диапазон.\nДопустимый диапазон: от 0.000001 до 1000000");
        }
        if (lim_low >= lim_high) {
            throw new Integral_Exception("Нижний предел должен быть меньше верхнего.");
        }
        if (step >= (lim_high - lim_low)) {
            throw new Integral_Exception(
                    ("Шаг: " + step + ">=" + (lim_high - lim_low) + "\n")
                            + "Шаг должен быть меньше области интегрирования.");
        }
        this.limlow = lim_low;
        this.limhigh = lim_high;
        this.step = step;
        this.result = 0;

    }

    public Object[] getData() {
        Object[] dataset = { this.limlow, this.limhigh, this.step, this.result };
        return dataset;
    }

    public void setData(double limlow,
            double limhigh,
            double step, double result) throws Integral_Exception {
        this.limlow = limlow;
        this.limhigh = limhigh;
        this.step = step;
        this.result = result;
    }

    public void setData(double limlow,
            double limhigh,
            double step) throws Integral_Exception {
        this.limlow = limlow;
        this.limhigh = limhigh;
        this.step = step;
        this.result = new Integral().calculate(limlow, limhigh, step);
    }

}
