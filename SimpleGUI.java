import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.awt.*;

class Set_GUI_Elements {
    /*
     * Расстановка элементов интерфейса
     * на главном окне.
     */
    public Set_GUI_Elements(
            JLabel label_limhigh,
            JLabel label_limlow,
            JLabel label_result,
            JLabel label_step,
            JButton button_calculate,
            JButton button_delete,
            JButton button_insert,
            JButton button_cleartab,
            JButton button_loadtab,
            JButton button_save_textfile,
            JButton button_save_binfile,
            JTextField textbox_limhigh,
            JTextField textbox_limlow,
            JTextField textbox_step,
            JTable table_result,
            JScrollPane jp,
            Container container) {
        textbox_limlow.setBounds(10, 10, 200, 20);
        textbox_limhigh.setBounds(10, 40, 200, 20);
        textbox_step.setBounds(10, 70, 200, 20);

        button_insert.setBounds(10, 200, 120, 20);
        button_delete.setBounds(10, 230, 120, 20);
        button_calculate.setBounds(140, 200, 120, 50);

        button_cleartab.setBounds(270, 200, 120, 50);
        button_loadtab.setBounds(400, 200, 120, 50);
        button_save_textfile.setBounds(530, 200, 120, 50);
        button_save_binfile.setBounds(660, 200, 120, 50);

        jp.setBounds(400, 10, 360, 80);
        label_limlow.setBounds(220, 10, 120, 20);
        label_limhigh.setBounds(220, 40, 120, 20);
        label_step.setBounds(220, 70, 120, 20);

        container.add(label_limlow);
        container.add(label_limhigh);
        container.add(label_step);
        container.add(label_result);
        container.add(textbox_limlow);
        container.add(textbox_limhigh);
        container.add(textbox_step);
        container.add(button_delete);
        container.add(button_insert);
        container.add(button_calculate);
        container.add(button_cleartab);
        container.add(button_save_textfile);
        container.add(button_save_binfile);
        container.add(button_loadtab);
        container.add(jp);
        jp.setViewportView(table_result);
    }
}

public class SimpleGUI extends JFrame {
    private ArrayList<RecIntegral> tablist;

    public SimpleGUI() {

        super("Лабораторная работа №1");
        super.setBounds(0, 0, 800, 300);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane jp = new JScrollPane();
        Container container = super.getContentPane();
        container.setLayout(null);
        tablist = new ArrayList<>();
        JLabel label_limlow = new JLabel("Нижняя граница"),
                label_limhigh = new JLabel("Верхняя граница"),
                label_step = new JLabel("Шаг"),
                label_result = new JLabel("Результат");
        /*
         * Экземпляры полей ввода для считывания нижнего,
         * верхнего пределов и шага.
         */
        NumberFormat floatFormat = NumberFormat.getNumberInstance(Locale.US);
        floatFormat.setMaximumFractionDigits(5);
        floatFormat.setRoundingMode(RoundingMode.HALF_UP);
        floatFormat.setGroupingUsed(false);
        JFormattedTextField textbox_limlow = new JFormattedTextField(floatFormat),
                textbox_limhigh = new JFormattedTextField(floatFormat),
                textbox_step = new JFormattedTextField(floatFormat);
        textbox_limlow.setValue(0.0);
        textbox_limhigh.setValue(0.0);
        textbox_step.setValue(0.0);

        textbox_limhigh.setValue(0.0);
        textbox_step.setValue(0.0);

        /*
         * Экземпляры кнопок
         * для взаимодействия.
         */
        JButton button_delete = new JButton("Удалить"),
                button_insert = new JButton("Добавить"),
                button_calculate = new JButton("Вычислить"),
                button_cleartab = new JButton("<html><center>Очистить<br>таблицу</center></html>"),
                button_loadtab = new JButton("<html><center>Загрузить<br>таблицу</center></html>"),
                button_save_textfile = new JButton("<html><center>Сохранить<br>текстовый файл</center></html>"),
                button_save_binfile = new JButton("<html><center>Сохранить<br>двоичный файл</center></html>");
        /*
         * Экземпляр таблицы для хранения
         * результатов вычислений.
         */
        JTable table_result = new JTable((new DefaultTableModel(

                new Object[][] {

                },
                new String[] {
                        "Нижний", "Верхний", "Шаг", "Результат"
                }) {

        })) {

            @Override
            /*
             * Установка последней колонки
             * таблицы в режим "только для чтения".
             */
            public boolean isCellEditable(int row, int column) {
                if (column == 3)
                    return false;
                return true;
            }
        };

        Set_GUI_Elements setGUI = new Set_GUI_Elements(
                label_limhigh,
                label_limlow,
                label_result,
                label_step,
                button_calculate,
                button_delete,
                button_insert,
                button_cleartab,
                button_loadtab,
                button_save_textfile,
                button_save_binfile,
                textbox_limhigh,
                textbox_limlow,
                textbox_step,
                table_result,
                jp,
                container);

        var Integral_al = new IntegralActionListener(table_result);
        var Add_al = new InsertActionListener(
                textbox_limlow,
                textbox_limhigh,
                textbox_step,
                table_result);
        var Del_al = new DelActionListener(table_result);
        var ClearTab_al = new ClearTabActionListener(table_result);
        var Load_al = new LoadActionListener(table_result);
        var SaveTextFile_al = new SaveTextActionListener();
        var SaveBinFile_al = new SaveBinActionListener();
        button_calculate.addActionListener(
                Integral_al);
        button_insert.addActionListener(Add_al);
        button_delete.addActionListener(Del_al);
        button_cleartab.addActionListener(ClearTab_al);
        button_loadtab.addActionListener(Load_al);
        button_save_textfile.addActionListener(SaveTextFile_al);
        button_save_binfile.addActionListener(SaveBinFile_al);

    }

    public class IntegralActionListener implements ActionListener {
        private JTable table;
        private RecIntegral integral;

        public IntegralActionListener(JTable table) {
            this.table = table;

        }

        public void actionPerformed(ActionEvent e) {
            int row_index = table.getSelectedRow();
            if (row_index == -1) {
                return;
            }
            integral = tablist.get(row_index);
            try {
                integral.setData(Double.parseDouble(table.getValueAt(row_index, 0).toString().replace(',', '.')),
                        Double.parseDouble(table.getValueAt(row_index, 1).toString().replace(',', '.')),
                        Double.parseDouble(table.getValueAt(row_index, 2).toString().replace(',', '.')));

            } catch (Integral_Exception ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
            tablist.set(row_index, integral);
            for (int i = table.getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) this.table.getModel()).removeRow(i);
            }
            for (var row : tablist) {
                ((DefaultTableModel) this.table.getModel()).addRow(row.getData());
            }

        }
    }

    public class InsertActionListener implements ActionListener {
        private JTextField textbox_limlow;
        private JTextField textbox_limhigh;
        private JTextField textbox_step;
        private JTable table;
        private RecIntegral recIntegral;

        public InsertActionListener(JTextField low, JTextField high, JTextField step, JTable table) {

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
                tablist.add(recIntegral);
                for (int i = table.getRowCount() - 1; i >= 0; i--) {
                    ((DefaultTableModel) this.table.getModel()).removeRow(i);
                }
                for (var iterable_element : tablist) {
                    ((DefaultTableModel) this.table.getModel()).addRow(iterable_element.getData());
                }

            } catch (Integral_Exception ex) {
                JOptionPane.showMessageDialog(rootPane,
                        ex.getMessage());
            }
        }

    }

    public class DelActionListener implements ActionListener {
        private JTable table;

        public DelActionListener(JTable table) {
            this.table = table;
        }

        public void actionPerformed(ActionEvent e) {
            int row_index = this.table.getSelectedRow();
            if (row_index == -1)
                return;

            ((DefaultTableModel) this.table.getModel()).removeRow(row_index);
            tablist.remove(row_index);
        }
    }

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

    public class LoadActionListener implements ActionListener {
        private JTable table;
        private JFileChooser fileChooser;

        public LoadActionListener(JTable table) {
            this.table = table;
            this.fileChooser = new JFileChooser();
        }

        public void actionPerformed(ActionEvent e) {
            tablist.removeAll(tablist);
            for (int i = table.getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) this.table.getModel()).removeRow(i);
            }
            var filter = new FileNameExtensionFilter("Допустимые файлы: *.txt, *.object, *.bin",
                    "txt",
                    "object",
                    "bin");
            this.fileChooser.setFileFilter(filter);
            this.fileChooser.showOpenDialog(rootPane);

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

            if (selectedFile.getName().contains(".bin") || selectedFile.getName().contains(".object")) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(loading_file);
                    tablist = (ArrayList<RecIntegral>) ois.readObject();
                    if (this.table.getRowCount() > 0)
                        for (int i = this.table.getRowCount() - 1; i > 0; i--)
                            ((DefaultTableModel) this.table.getModel()).removeRow(i);
                    for (var i : tablist)
                        ((DefaultTableModel) this.table.getModel()).addRow(i.getData());

                    return;
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                    System.out.print(ex.getMessage());
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                    System.out.print(ex.getMessage());
                }

            }

            try {
                fileData = new String(loading_file.readAllBytes());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane,
                        ex.getMessage());
            }

            String[] rows = fileData.split("\n");
            for (int i = 0; i < rows.length; i++) {
                String[] cols = rows[i].replaceFirst("^\\s*\\|\\s*", "").split("\\s*\\|\\s*");
                try {

                    tablist.add(new RecIntegral(
                            Double.parseDouble(cols[0].replace(',', '.')),
                            Double.parseDouble(cols[1].replace(',', '.')),
                            Double.parseDouble(cols[2].replace(',', '.'))));
                    ((DefaultTableModel) this.table.getModel()).addRow(tablist.getLast().getData());
                } catch (Integral_Exception ex) {
                    JOptionPane.showMessageDialog(rootPane,
                            ex.getMessage());
                }

            }
        }
    }

    public class SaveTextActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser_text = new JFileChooser();
            var filter = new FileNameExtensionFilter("Текстовый файл (*.txt)", "txt");
            fileChooser_text.setFileFilter(filter);
            fileChooser_text.showSaveDialog(rootPane);

            File selectedFile_text = fileChooser_text.getSelectedFile();
            if (selectedFile_text == null)
                return;
            FileOutputStream saved_file_text = null;
            try {
                saved_file_text = new FileOutputStream(selectedFile_text);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(rootPane,
                        ex.getMessage());
            }
            try {
                for (var iterable_element : tablist) {
                    saved_file_text.write(iterable_element.getDataStr().getBytes());
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane,
                        ex.getMessage());
            }
        }

    }

    public class SaveBinActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser_bin = new JFileChooser();
            fileChooser_bin
                    .setFileFilter(new FileNameExtensionFilter("Двоичный файл (*.bin, *.object)", "bin", "object"));
            fileChooser_bin.showSaveDialog(rootPane);

            File selectedFile_bin = fileChooser_bin.getSelectedFile();
            if (selectedFile_bin == null)
                return;
            FileOutputStream saved_file_bin = null;
            try {
                saved_file_bin = new FileOutputStream(selectedFile_bin);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(rootPane,
                        ex.getMessage());
            }
            try {
                ObjectOutputStream oos = new ObjectOutputStream(saved_file_bin);
                oos.writeObject(tablist);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane,
                        ex.getMessage());
            }
        }
    }
}