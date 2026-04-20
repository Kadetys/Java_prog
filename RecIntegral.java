import java.io.OutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class RecIntegral implements Externalizable {

    private double limlow, limhigh, step, result;

    public RecIntegral() {
    }

    public RecIntegral(double limlow,
            double limhigh,
            double step,
            double result) throws Integral_Exception {
        this.limlow = limlow;
        this.limhigh = limhigh;
        this.step = step;
        this.result = result;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(this.limhigh);
        out.writeDouble(this.limlow);
        out.writeDouble(this.step);
        out.writeDouble(this.result);
    }

    public void readExternal(ObjectInput in) throws IOException {
        this.limhigh = in.readDouble();
        this.limlow = in.readDouble();
        this.step = in.readDouble();
        this.result = in.readDouble();
    }

    public RecIntegral(double limlow,
            double limhigh,
            double step) throws Integral_Exception {
        this.limlow = limlow;
        this.limhigh = limhigh;
        this.step = step;
        this.result = 0;

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
