import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.NumberFormat;
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
            JTextField textbox_limhigh,
            JTextField textbox_limlow,
            JTextField textbox_step,
            JTable table_result,
            JScrollPane jp,
            Container container) {
        textbox_limlow.setBounds(10, 10, 200, 20);
        textbox_limhigh.setBounds(10, 40, 200, 20);
        textbox_step.setBounds(10, 70, 200, 20);
        button_delete.setBounds(10, 200, 150, 50);
        button_insert.setBounds(170, 200, 150, 50);
        button_calculate.setBounds(330, 200, 150, 50);
        jp.setBounds(340, 10, 320, 127);
        table_result.setBounds(350, 30, 300, 97);

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
        container.add(jp);
        jp.setViewportView(table_result);
    }
}

public class SimpleGUI extends JFrame {

    public SimpleGUI() {

        super("Лабораторная работа №1");
        super.setBounds(0, 0, 700, 300);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane jp = new JScrollPane();
        Container container = super.getContentPane();
        container.setLayout(null);

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
                button_calculate = new JButton("Вычислить");
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
                textbox_limhigh,
                textbox_limlow,
                textbox_step,
                table_result,
                jp,
                container);

        IntegralActionListener Integral_al = new IntegralActionListener(table_result);
        InsertActionListener Add_al = new InsertActionListener(
                textbox_limlow,
                textbox_limhigh,
                textbox_step,
                table_result);
        DelActionListener Del_al = new DelActionListener(table_result);

        button_calculate.addActionListener(Integral_al);
        button_insert.addActionListener(Add_al);
        button_delete.addActionListener(Del_al);

    }

    public class IntegralActionListener implements ActionListener {
        private JTable table;
        private Integral integral;

        public IntegralActionListener(JTable table) {
            this.table = table;
            this.integral = new Integral();

        }

        public void actionPerformed(ActionEvent e) {
            int row_index = table.getSelectedRow();
            if (row_index == -1) {
                return;
            }

            double limlow = Double.parseDouble(table.getValueAt(row_index, 0).toString()),
                    limhigh = Double.parseDouble(table.getValueAt(row_index, 1).toString()),
                    step = Double.parseDouble(table.getValueAt(row_index, 2).toString()),
                    result = this.integral.calculate(limlow, limhigh, step);
            ((DefaultTableModel) table.getModel()).setValueAt(result, row_index, 3);
        }
    }

    public class InsertActionListener implements ActionListener {
        private JTextField textbox_limlow;
        private JTextField textbox_limhigh;
        private JTextField textbox_step;
        private JTable table;

        public InsertActionListener(JTextField low, JTextField high, JTextField step, JTable table) {

            this.textbox_limlow = low;
            this.textbox_limhigh = high;
            this.textbox_step = step;
            this.table = table;
        }

        public void actionPerformed(ActionEvent e) {
            ((DefaultTableModel) table.getModel()).addRow(
                    new Object[] {
                            this.textbox_limlow.getText(),
                            this.textbox_limhigh.getText(),
                            this.textbox_step.getText() });

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
        }
    }
}
