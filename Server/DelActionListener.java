package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DelActionListener implements ActionListener {
    /**
     *
     */
    private final SimpleGUI simpleGUI;
    private JTable table;

    public DelActionListener(SimpleGUI simpleGUI, JTable table) {
        this.simpleGUI = simpleGUI;
        this.table = table;
    }

    public void actionPerformed(ActionEvent e) {
        int row_index = this.table.getSelectedRow();
        if (row_index == -1)
            return;

        ((DefaultTableModel) this.table.getModel()).removeRow(row_index);
        this.simpleGUI.tablist.remove(row_index);
    }
}