package sample.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Handler implements Runnable {
    private String name;
    private Socket socket;
    private Scanner input;
    private PrintWriter output;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);

            while (true){
                output.println("ENTER YOUR NAME");
                name = input.nextLine();
                if (name == null){
                    return;
                }

                synchronized (Server.NAMES){
                    if (!name.isEmpty() && !Server.NAMES.contains(name)){
                        Server.NAMES.add(name);
                        break;
                    }
                }
            }

            output.println("NAME ACCEPTED");
            for (PrintWriter writer : Server.WRITERS){
                writer.println(name + " has joined");
            }
            Server.WRITERS.add(output);

            while (true){
                String message = input.nextLine();
                if (message.toLowerCase().startsWith("/QUIT")){
                    return;
                }

                for (PrintWriter oldWriter : Server.WRITERS){
                    oldWriter.println("Message_" + name + "_" + message);
                }
            }
        }catch (Exception ex){
            System.out.println(ex);
        }finally {
            if (output != null){
                Server.WRITERS.remove(output);
            }

            if (name != null){
                System.out.println(name + " is leaving");
                Server.NAMES.remove(name);
                for (PrintWriter oldWriter : Server.WRITERS){
                    oldWriter.println("Message_" + name + "_has left");
                }
            }
            try {
                socket.close();
            }catch (IOException e){

            }
        }
    }
}
