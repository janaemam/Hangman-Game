package Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Character.toLowerCase;

public class Game {
    ArrayList<String> words = new ArrayList<>();
    ArrayList<Character>guesses=new ArrayList<>();
    public static ArrayList<String> codes = new ArrayList<>();
    Scanner reader = new Scanner(new File("words.txt"));
    User player;
    User player2;
    Team team= new Team();
    String name;
    PrintWriter out;
    PrintWriter out2;

    public Game(String name) throws FileNotFoundException {
        uploadWords();
        this.name=name;
    }
    public Game() throws FileNotFoundException {}
    public Game(PrintWriter out,User player) throws FileNotFoundException {
        this.out=out;
        this.player=player;
        uploadWords();
    }
    public Game(PrintWriter out1, PrintWriter out2,User player1, User player2) throws FileNotFoundException {
       this.out=out1;
       this.out2=out2;
       this.player=player1;
       this.player2=player2;
        uploadWords();
    }

    public String getWord() {
        Random random = new Random();
        String word= words.get(random.nextInt(words.size()));
        return word;
    }
    public void uploadWords() {
        while (reader.hasNext()) {
            words.add(reader.next());
        }
    }
    public String printStatus(String word ){
        String status="";
        int correctCount=0;
        for(int i=0;i<word.length();i++){
            if(guesses.contains(word.charAt(i))){
                status+= String.valueOf(word.charAt(i));
                correctCount++;
            }
            else {
                status+="_";
            }
        }

        printHangman(player.lives);
        if(correctCount==word.length()){
            //win(word);
            status="You Won";
        }
        if(player.lives==0){
            status="You Lost";
        }
        return status;
    }
    public String statusCheck(String word){
        String status="";
        int correctCount=0;
        for(int i=0;i<word.length();i++){
            
            if(guesses.contains(word.charAt(i))){
                correctCount++;
                
            }
        }
        if(correctCount==word.length()){
            //win(word);
            status= "win";

        }
        if(player.lives==0){
            status= "lose";
        }
        return status;
    }
    public void checkGuess(String word,String guess){
        char guessChar=toLowerCase(guess.charAt(0));
        if(word.contains(String.valueOf(guessChar))){
            guesses.add(guessChar);
        }
        else{
            player.lives--;
        }
    }
    public void addGuess(String guess){
        guesses.add(guess.charAt(0));
    }
    public void printHangman(int lives){
        out.println("You have "+player.lives+" lives left");
        int count=6-lives;
        if(count==1){
            out.println("|----------");
            out.println("|    O");
            out.println("|");
            out.println("|");
            out.println("|");
            out.println("|");
            out.println("|");
        } else if (count==2) {
            out.println("|----------");
            out.println("|    O");
            out.println("|    |");
            out.println("|");
            out.println("|");
            out.println("|");
            out.println("|");
        } else if (count==3) {
            out.println("|----------");
            out.println("|    O");
            out.println("|   -|");
            out.println("|");
            out.println("|");
            out.println("|");
            out.println("|");
        } else if (count==4) {
            out.println("|----------");
            out.println("|    O");
            out.println("|   -|-");
            out.println("|");
            out.println("|");
            out.println("|");
            out.println("|");
        } else if (count==5) {
            out.println("|----------");
            out.println("|    O");
            out.println("|   -|-");
            out.println("|   /");
            out.println("|");
            out.println("|");
            out.println("|");
        } else if (count==6) {
            out.println("|----------");
            out.println("|    O");
            out.println("|   -|-");
            out.println("|   / \\");
            out.println("|  ");
            out.println("|");
            out.println("|");
        }
    }
    public void win(String word) throws IOException {
        guesses.clear();
        player.wins++;
        player.writeHistory();
    }
    public void lose(String word) throws IOException {
        out.println("The word was "+word);
        guesses.clear();
        player.losses++;
        player.words.add(word);
        player.writeHistory();
        player.lives=6;
    }
    public boolean checkGame(String code){
        boolean flag=false;
        for(int i=0;i<codes.size();i++){
            if(codes.get(i).equals(code)){
                flag= true;
                break;
            }
        }
        return flag;
    }
    public void start(User player, Socket socket) throws IOException {
        Multiplayer multiplayer=new Multiplayer(player, socket);
    }
    public void addGame(String game){
        codes.add(name);
    }

}
