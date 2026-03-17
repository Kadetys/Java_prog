import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;

public class SimpleGUI extends JFrame {

    public SimpleGUI() {

        super("Лабораторная работа №1");
        super.setBounds(0, 0, 700, 300);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = super.getContentPane();
        container.setLayout(null);

        JLabel inf1 = new JLabel("Нижняя граница");
        JLabel inf2 = new JLabel("Верхняя граница");
        JLabel inf3 = new JLabel("Шаг");
        JLabel inf4 = new JLabel("Результат");
        JTextField low = new JTextField("");
        JTextField high = new JTextField("");
        JTextField step = new JTextField("");
        JScrollPane jp = new JScrollPane();
        JTable res = new JTable(new DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Нижний", "Верхний", "Шаг", "Результат"
                }));

        JButton del = new JButton("Удалить");
        JButton add = new JButton("Добавить");
        JButton calc = new JButton("Вычислить");

        low.setBounds(10, 10, 200, 20);
        high.setBounds(10, 40, 200, 20);
        step.setBounds(10, 70, 200, 20);
        del.setBounds(10, 200, 150, 50);
        add.setBounds(170, 200, 150, 50);
        calc.setBounds(330, 200, 150, 50);
        jp.setBounds(340, 10, 320, 127);
        res.setBounds(350, 30, 300, 97);

        inf1.setBounds(220, 10, 120, 20);
        inf2.setBounds(220, 40, 120, 20);
        inf3.setBounds(220, 70, 120, 20);

        container.add(inf1);
        container.add(inf2);
        container.add(inf3);
        container.add(inf4);
        container.add(low);
        container.add(high);
        container.add(step);
        container.add(del);
        container.add(add);
        container.add(calc);
        container.add(jp);
        jp.setViewportView(res);

        JTextField nonEditTableField = new JTextField();
        nonEditTableField.setEditable(false);
        res.getColumnModel().getColumn(3).setCellEditor(
                new DefaultCellEditor(nonEditTableField));
        IntegralActionListener ial = new IntegralActionListener(
                low,
                high,
                step,
                res);
        calc.addActionListener(ial);

        Listener Add = new Listener(low, high, step, res);
        add.addActionListener(Add);

        DelActionListener Del = new DelActionListener(res);
        del.addActionListener(Del);

    }

    public class IntegralActionListener implements ActionListener {
        private JTextField lowField;
        private JTextField highField;
        private JTextField stepField;
        private JTable table;

        public IntegralActionListener(JTextField low, JTextField high, JTextField step, JTable table) {
            this.lowField = low;
            this.highField = high;
            this.stepField = step;
            this.table = table;

        }

        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            String lowText = table.getValueAt(row, 0).toString();
            String highText = table.getValueAt(row, 1).toString();
            String stepText = table.getValueAt(row, 2).toString();
            double low = Double.parseDouble(lowText);
            double high = Double.parseDouble(highText);
            double step = Double.parseDouble(stepText);

            Integral i1 = new Integral();
            double result = i1.calculate(low, high, step);

            ((DefaultTableModel) table.getModel()).setValueAt(result, row, 3);
        }
    }

    public class Listener implements ActionListener {
        private JTextField lowField;
        private JTextField highField;
        private JTextField stepField;
        private JTable table;

        public Listener(JTextField low, JTextField high, JTextField step, JTable table) {
            this.lowField = low;
            this.highField = high;
            this.stepField = step;
            this.table = table;
        }

        public void actionPerformed(ActionEvent e) {
            String lowText = lowField.getText().trim();
            String highText = highField.getText().trim();
            String stepText = stepField.getText().trim();
            DefaultTableModel tm = (DefaultTableModel) table.getModel();
            tm.addRow(new Object[] { lowText, highText, stepText });
        }

    }

    public class DelActionListener implements ActionListener {
        private JTable table;

        public DelActionListener(JTable table) {
            this.table = table;
        }

        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(selectedRow);
        }
    }
}
