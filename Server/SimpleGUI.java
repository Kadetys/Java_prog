package Server;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Container;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.net.*;
import java.io.*;

class Server {
    private ServerSocket serverSocket; // Серверный сокет

    Server() throws IOException, InterruptedException, Integral_Exception {
        serverSocket = new ServerSocket(8080);
    }

    ServerSocket getSocket() {
        return this.serverSocket;
    }
}

class User extends Thread {
    private Socket clientSocket;
    private ServerSocket serverSocket;
    BufferedReader in; // Получение сообщений
    PrintWriter out; // Отправка сообщений

    User(ServerSocket serverSocket) {
        super();
        this.serverSocket = serverSocket;
    }

    /*
     * Отправка пакета с верхним пределом, нижним пределом и шагом. Затем получение
     * результата расчета.
     */
    public void run() {
        System.out.println(this.threadId() + ": ожидание подключения.");
        try {
            clientSocket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

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
            JButton button_savefile,
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
        button_savefile.setBounds(530, 200, 120, 50);

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
        container.add(button_cleartab);
        container.add(button_savefile);
        container.add(button_loadtab);
        container.add(jp);
        jp.setViewportView(table_result);
    }
}

public class SimpleGUI extends JFrame {
    ArrayList<RecIntegral> tablist;

    public SimpleGUI(int max_users) {

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
                button_loadtab = new JButton("<html><center>Загрузить<br>таблицу</center></html>"),
                button_savefile = new JButton("<html><center>Сохранить<br>файл</center></html>");
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

        var setGUI = new Set_GUI_Elements(
                label_limhigh,
                label_limlow,
                label_result,
                label_step,
                button_calculate,
                button_delete,
                button_insert,
                button_cleartab,
                button_loadtab,
                button_savefile,
                textbox_limhigh,
                textbox_limlow,
                textbox_step,
                table_result,
                jp,
                container);

        var Integral_al = new IntegralActionListener(this, table_result);
        var Add_al = new InsertActionListener(
                this, textbox_limlow,
                textbox_limhigh,
                textbox_step,
                table_result);
        var Del_al = new DelActionListener(this, table_result);
        var ClearTab_al = new ClearTabActionListener(table_result);
        var Load_al = new LoadActionListener(this, table_result);
        var SaveFile_al = new SaveFileActionListener(this);
        button_calculate.addActionListener(Integral_al);
        button_insert.addActionListener(Add_al);
        button_delete.addActionListener(Del_al);
        button_cleartab.addActionListener(ClearTab_al);
        button_loadtab.addActionListener(Load_al);
        button_savefile.addActionListener(SaveFile_al);

    }

}