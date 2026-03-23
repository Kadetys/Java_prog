import java.util.ArrayList;
import javax.swing.JTable;

class RowElement {
    public double limlow, limhigh, step, result;

    public RowElement() {

    }
}

public class RecIntegral {
    private ArrayList tabList;

    public RecIntegral() {
        tabList = new ArrayList<RowElement>(4);

    }

    public void Add(JTable table) {
        tabList.clear();
        for (int i = 0; i < table.getRowCount(); i++) {
            var row = new RowElement();
            row.limlow = Double.parseDouble(table.getValueAt(i, 0).toString());
            row.limhigh = Double.parseDouble(table.getValueAt(i, 1).toString());
            row.step = Double.parseDouble(table.getValueAt(i, 2).toString());
            row.result = Double.parseDouble(table.getValueAt(i, 3).toString());
            tabList.add(row);
        }
    }

    public void print() {
        for (var object : this.tabList) {
            System.out.println(object);
        }
    }
}
