package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class IntegralActionListener implements ActionListener {
    /**
     *
     */
    private final SimpleGUI simpleGUI;
    private JTable table;
    private RecIntegral integral;

    public IntegralActionListener(SimpleGUI simpleGUI, JTable table) {
        this.simpleGUI = simpleGUI;
        this.table = table;

    }

    public void actionPerformed(ActionEvent e) {
        int row_index = table.getSelectedRow();
        if (row_index == -1) {
            return;
        }
        integral = this.simpleGUI.tablist.get(row_index);
        try {
            integral.setData(Double.parseDouble(table.getValueAt(row_index, 0).toString().replace(',', '.')),
                    Double.parseDouble(table.getValueAt(row_index, 1).toString().replace(',', '.')),
                    Double.parseDouble(table.getValueAt(row_index, 2).toString().replace(',', '.')));

        } catch (Integral_Exception ex) {
            JOptionPane.showMessageDialog(this.simpleGUI.getRootPane(), ex.getMessage());
        }
        this.simpleGUI.tablist.set(row_index, integral);
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) this.table.getModel()).removeRow(i);
        }
        for (var row : this.simpleGUI.tablist) {
            ((DefaultTableModel) this.table.getModel()).addRow(row.getData());
        }

    }
}