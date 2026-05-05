package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LoadActionListener implements ActionListener {
    /**
     *
     */
    private final SimpleGUI simpleGUI;
    private JTable table;
    private JFileChooser fileChooser;

    public LoadActionListener(SimpleGUI simpleGUI, JTable table) {
        this.simpleGUI = simpleGUI;
        this.table = table;
        this.fileChooser = new JFileChooser();
    }

    public void actionPerformed(ActionEvent e) {
        this.simpleGUI.tablist.removeAll(this.simpleGUI.tablist);
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) this.table.getModel()).removeRow(i);
        }
        this.fileChooser.showOpenDialog(this.simpleGUI.getRootPane());
        File selectedFile = this.fileChooser.getSelectedFile();
        if (selectedFile == null)
            return;
        FileInputStream loading_file = null;
        String fileData = null;

        try {
            loading_file = new FileInputStream(selectedFile);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        if (selectedFile.getName().contains(".bin")) {
            try {
                ObjectInputStream ois = new ObjectInputStream(loading_file);
                this.simpleGUI.tablist = (ArrayList<RecIntegral>) ois.readObject();
                if (this.table.getRowCount() > 0)
                    for (int i = this.table.getRowCount() - 1; i > 0; i--)
                        ((DefaultTableModel) this.table.getModel()).removeRow(i);
                for (var i : this.simpleGUI.tablist)
                    ((DefaultTableModel) this.table.getModel()).addRow(i.getData());

                return;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this.simpleGUI.getRootPane(), ex.getMessage());
                System.out.print(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this.simpleGUI.getRootPane(), ex.getMessage());
                System.out.print(ex.getMessage());
            }

        }

        try {
            fileData = new String(loading_file.readAllBytes());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this.simpleGUI.getRootPane(),
                    ex.getMessage());
        }

        String[] rows = fileData.split("\n");
        for (int i = 0; i < rows.length; i++) {
            String[] cols = rows[i].replaceFirst("^\\s*\\|\\s*", "").split("\\s*\\|\\s*");
            try {

                this.simpleGUI.tablist.add(new RecIntegral(
                        Double.parseDouble(cols[0].replace(',', '.')),
                        Double.parseDouble(cols[1].replace(',', '.')),
                        Double.parseDouble(cols[2].replace(',', '.'))));
                ((DefaultTableModel) this.table.getModel()).addRow(this.simpleGUI.tablist.getLast().getData());
            } catch (Integral_Exception ex) {
                JOptionPane.showMessageDialog(this.simpleGUI.getRootPane(),
                        ex.getMessage());
            }

        }
    }
}