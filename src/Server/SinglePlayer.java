package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SinglePlayer {
    Socket clientSocket;
    PrintWriter out = null;
    BufferedReader in = null;
    User player;
    Game game ;
    SinglePlayer(Socket clientSocket,User player) throws IOException {
        this.clientSocket=clientSocket;
        this.player=player;

    }
    public void start() throws IOException {
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        game=new Game(out,player);
        while(true){


            String word=game.getWord();
            out.println("New Game");
            out.println("---------------");
            //out.println(word);
            out.println("Enter a letter");

            while(true){
                out.println("needreply");
                String guess=  in.readLine();
                game.addGuess(guess);
                game.checkGuess(word,guess);

                out.println(game.printStatus(word));
                if(game.statusCheck(word).equals("win")){

                    game.win(word);
                    break;
                }
                else if(game.statusCheck(word).equals("lose")){

                    game.lose(word);
                    break;
                }




            }
            out.println("Game Over");
            out.println("Enter y to play again");
            out.println("Enter n to go to main menu");
            out.println("needreply");
            String choice= in.readLine();
            if(choice.equals("y")){
                continue;
            }
            else if(choice.equals("n")){
                break;
            }



        }
    }
}
