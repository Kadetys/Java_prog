package Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        SimpleGUI form = new SimpleGUI();
        form.setVisible(true);
        Pack p;
        try {
            p = new Pack();
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
