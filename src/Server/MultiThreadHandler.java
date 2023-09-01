package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiThreadHandler implements Runnable{
    ArrayList<MultiThreadHandler>clients = new ArrayList<>();
    public User user= new User();

    private Socket clientSocket;
    PrintWriter out = null;
    BufferedReader in = null;
    MultiThreadHandler(Socket socket) throws IOException {
        clients.add(this);
        this.clientSocket = socket;
    }

    @Override
    public void run(){
        try {
            //user.uploadUsers();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //out.println("over");
            while(true) {
                user.uploadUsers();
                out.println("Welcome to Hangman");
                out.println("1.Register\n2.Sign In");
                out.println("needreply");
                String choice = in.readLine();

                if (choice.equals("1")) {
                    while (true) {
                        out.println("Enter a username");
                        out.println("needreply");
                        String username = in.readLine();


                        if (!user.usernameExists(username)) {
                            out.println("Enter your name");
                            out.println("needreply");
                            String name = in.readLine();
                            System.out.println(name);
                            out.println("Enter your password");
                            out.println("needreply");
                            String password = in.readLine();
                            out.println(user.createUser(name,username, password ));
                            out.println("Sign in to start playing");
                            break;
                        } else {
                            out.println("Username already exists");

                        }
                    }
                } else if (choice.equals("2")) {
                    out.println("Enter your username");
                    out.println("needreply");
                    String username = in.readLine();
                    out.println("Enter your password");
                    out.println("needreply");
                    String password = in.readLine();
                    out.println(user.signIn(username,password));
                    if(user.signIn(username,password).equals("Signed in Successfully")){
                        user=user.getUser(username,password);
                        Hangman hangman = new Hangman(clientSocket,user);
                    }
                }
                else {
                    out.println("Invalid choice");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
