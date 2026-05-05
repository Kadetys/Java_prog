package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class InsertActionListener implements ActionListener {
    /**
     *
     */
    private final SimpleGUI simpleGUI;
    private JTextField textbox_limlow;
    private JTextField textbox_limhigh;
    private JTextField textbox_step;
    private JTable table;
    private RecIntegral recIntegral;

    public InsertActionListener(SimpleGUI simpleGUI, JTextField low, JTextField high, JTextField step, JTable table) {

        this.simpleGUI = simpleGUI;
        this.textbox_limlow = low;
        this.textbox_limhigh = high;
        this.textbox_step = step;
        this.table = table;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            recIntegral = new RecIntegral(
                    Double.parseDouble(textbox_limlow.getText()),
                    Double.parseDouble(textbox_limhigh.getText()),
                    Double.parseDouble(textbox_step.getText()));
            this.simpleGUI.tablist.add(recIntegral);
            for (int i = table.getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) this.table.getModel()).removeRow(i);
            }
            for (var iterable_element : this.simpleGUI.tablist) {
                ((DefaultTableModel) this.table.getModel()).addRow(iterable_element.getData());
            }

        } catch (Integral_Exception ex) {
            JOptionPane.showMessageDialog(this.simpleGUI.getRootPane(),
                    ex.getMessage());
        }
    }

}