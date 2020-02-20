package Lesson6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Socket socket = null;

        try {
            socket = new Socket("localhost", 3444);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            Scanner sc = new Scanner(System.in);

            new Thread(() -> {
                while (true) {
                    String str = in.nextLine();
                    System.out.println("Server: " + str);
                }
            }).start();

            while (true) {
                System.out.println("Client, напиши свое сообщение");
                String str = sc.nextLine();
                out.println(str);
                System.out.println("Сообщение отправлено");

                if (str.equals("/end")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert socket != null;
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
