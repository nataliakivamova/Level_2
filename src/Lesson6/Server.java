package Lesson6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(3444);
            System.out.println("Сервер запустился");
            socket = server.accept();
            System.out.println("Новый клиент подключился");

            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);

            new Thread(() -> {
                while (true) {
                    System.out.println("Server, напиши сообщение");
                    String str = sc.nextLine();
                    out.println(str);
                    System.out.println("Сообщение отправлено");
                }
            }) .start();

            while (true) {
                String str = in.nextLine();
                if(str.equals("/end")){
                    System.out.println("Клиент отключился");
                    break;
                }
                System.out.println("Клиент: " + str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert socket != null;
                socket.close();
                server.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
