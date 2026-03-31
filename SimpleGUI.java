import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
            JButton button_savetab,
            JButton button_loadtab,
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

        button_savetab.setBounds(270, 200, 120, 50);
        button_loadtab.setBounds(400, 200, 120, 50);

        jp.setBounds(340, 10, 320, 80);
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
        container.add(button_savetab);
        container.add(button_loadtab);
        container.add(jp);
        jp.setViewportView(table_result);
    }
}

public class SimpleGUI extends JFrame {
    private ArrayList<RecIntegral> tablist;

    public SimpleGUI() {

        super("Лабораторная работа №1");
        super.setBounds(0, 0, 700, 300);
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
                button_loadtab = new JButton("<html><center>Загрузить<br>таблицу</center></html>");
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
        DelActionListener Del_al = new DelActionListener(table_result);
        ClearTabActionListener ClearTab_al = new ClearTabActionListener(table_result);
        LoadActionListener Load_al = new LoadActionListener(table_result);

        button_calculate.addActionListener(Integral_al);
        button_insert.addActionListener(Add_al);
        button_delete.addActionListener(Del_al);
        button_cleartab.addActionListener(ClearTab_al);
        button_loadtab.addActionListener(Load_al);

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
                integral.setData(Double.parseDouble(table.getValueAt(row_index, 0).toString()),
                        Double.parseDouble(table.getValueAt(row_index, 1).toString()),
                        Double.parseDouble(table.getValueAt(row_index, 2).toString()));

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
            for (int i = table.getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) this.table.getModel()).removeRow(i);
            }
            this.fileChooser.showOpenDialog(rootPane);
            File selected_file = this.fileChooser.getSelectedFile();
            FileInputStream loading_file = null;
            String fileData = null;
            try {
                loading_file = new FileInputStream(selected_file);
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                fileData = new String(loading_file.readAllBytes());
                System.out.println(fileData);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            String[] rows = fileData.split("\n");
            for (int i = 0; i < rows.length; i++) {
                String[] cols = rows[i].split("\\d,\\d*");
                ((DefaultTableModel) this.table.getModel()).addRow(cols);
            }
        }
    }
}