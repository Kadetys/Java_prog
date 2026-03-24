import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class RowElement {

    public RowElement() {

    }
}

public class RecIntegral {

    private double limlow, limhigh, step, result;

    public RecIntegral(double limlow,
            double limhigh,
            double step,
            double result) {
        this.limlow = limlow;
        this.limhigh = limhigh;
        this.step = step;
        this.result = result;
    }

    public RecIntegral(double limlow,
            double limhigh,
            double step) {
        this.limlow = limlow;
        this.limhigh = limhigh;
        this.step = step;
        this.result = new Integral().calculate(limlow, limhigh, step);
    }

    Object[] getData() {
        Object[] dataset = { this.limlow, this.limhigh, this.step, this.result };
        return dataset;
    }

}
