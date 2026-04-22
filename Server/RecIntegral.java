package Server;

import java.io.OutputStream;
import java.io.Serializable;

public class RecIntegral implements Serializable {

    private double limlow, limhigh, step, result;

    public RecIntegral(double limlow,
            double limhigh,
            double step,
            double result) throws Integral_Exception {
        this.limlow = limlow;
        this.limhigh = limhigh;
        this.step = step;
        this.result = result;
    }

    public RecIntegral(double limlow,
            double limhigh,
            double step) throws Integral_Exception {
        this.limlow = limlow;
        this.limhigh = limhigh;
        this.step = step;
        this.result = new Integral().calculate(limlow, limhigh, step);

    }

    public String getDataStr() {
        String dataset = String.format("|\t%f\t|\t%f\t|\t%f\t|\t%f\t|\n",
                this.limlow, this.limhigh, this.step, this.result);
        return dataset;
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
