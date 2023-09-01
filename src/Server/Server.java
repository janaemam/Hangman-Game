package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public ServerSocket serverSocket;
    public int counter=0;

    public void startServer(){

        try {

            serverSocket = new ServerSocket(4999);
            while(!serverSocket.isClosed()){
                counter++;
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client "+counter+" connected"+clientSocket.getInetAddress().getHostAddress());
                MultiThreadHandler handler = new MultiThreadHandler(clientSocket);
                new Thread(handler).start();
            }


        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
