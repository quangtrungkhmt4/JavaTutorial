package sample.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static Set<String> NAMES = new HashSet<>();
    public static Set<PrintWriter> WRITERS = new HashSet<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Server is running....");
        ExecutorService service = Executors.newFixedThreadPool(100);
        try (ServerSocket socket = new ServerSocket(1997)){
            while (true){
                service.execute(new Handler(socket.accept()));
            }
        }
    }

}
