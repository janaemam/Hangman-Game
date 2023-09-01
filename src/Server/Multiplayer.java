package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Multiplayer {
    PrintWriter out1 = null;
    PrintWriter out2 = null;
    BufferedReader in1 = null;
    BufferedReader in2 = null;
    User player1;
    User player2;
    Socket socket1;
    Socket socket2;
    Game game ;
    Multiplayer(User player, Socket clientSocket) throws IOException {
        if(player1==null){
            player1=player;
            socket1=clientSocket;

        }
        else if(player2==null){
            player2=player;
            socket2=clientSocket;
            start(player1,player2,socket1,socket2);

        }

    }
    public void start(User player1,User player2,Socket socket1,Socket socket2) throws IOException {
        out1 = new PrintWriter(socket1.getOutputStream(), true);
        in1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
        out2 = new PrintWriter(socket2.getOutputStream(), true);
        in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));

        game=new Game(out1,out2,player1,player2);
        while(true){
            String word=game.getWord();
            out1.println("New Game");
            out1.println("---------------");
            out1.println(word);
            out2.println("New Game");
            out2.println("---------------");
            out2.println(word);

            while(true){

            }



        }



    }
}
