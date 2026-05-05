package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClearTabActionListener implements ActionListener {
    private JTable table;

    public ClearTabActionListener(JTable table) {
        this.table = table;

    }

    public void actionPerformed(ActionEvent e) {
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) this.table.getModel()).removeRow(i);
        }
    }
}