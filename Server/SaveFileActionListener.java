package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SaveFileActionListener implements ActionListener {

    /**
     *
     */
    private final SimpleGUI simpleGUI;

    /**
     * @param simpleGUI
     */
    SaveFileActionListener(SimpleGUI simpleGUI) {
        this.simpleGUI = simpleGUI;
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser_text = new JFileChooser(),
                fileChooser_bin = new JFileChooser();
        fileChooser_text.showSaveDialog(this.simpleGUI.getRootPane());
        fileChooser_bin.showSaveDialog(this.simpleGUI.getRootPane());
        File selectedFile_text = fileChooser_text.getSelectedFile(),
                selectedFile_bin = fileChooser_bin.getSelectedFile();
        if (selectedFile_text == null || selectedFile_bin == null)
            return;
        FileOutputStream saved_file_text = null, saved_file_bin = null;
        try {
            saved_file_text = new FileOutputStream(selectedFile_text);
            saved_file_bin = new FileOutputStream(selectedFile_bin);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this.simpleGUI.getRootPane(),
                    ex.getMessage());
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(saved_file_bin);
            oos.writeObject(this.simpleGUI.tablist);
            for (var iterable_element : this.simpleGUI.tablist) {
                saved_file_text.write(iterable_element.getDataStr().getBytes());
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this.simpleGUI.getRootPane(),
                    ex.getMessage());
        }
    }

}