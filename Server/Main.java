package Server;

public class Main {

    public static void main(String[] args) {

        /* Подключение клиента ??? */

        SimpleGUI form = new SimpleGUI();
        form.setVisible(true);
        Pack pack = null;
        try {
            pack = new Pack();
        } catch (Exception ex) {

        }

    }
}
