package Server;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    public static ArrayList<User> users = new ArrayList<>();
    File file = new File("Accounts.txt");

    BufferedWriter bufferedWriter= new BufferedWriter(new FileWriter(file,true));

    Scanner reader = new Scanner(file);
    String username;
    String password;
    String name;
    Socket socket;
    int losses;
    int wins;
    ArrayList<String> words = new ArrayList<>();
    int lives=6;
    User () throws IOException {}
    User( String name,String username, String password) throws IOException {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String createUser(String name,String username,String password) throws IOException {

        User newUser = new User(name,username, password );
        users.add(newUser);
        bufferedWriter.write(name+":"+username+":"+password+"\n");
        bufferedWriter.flush();


        return "User added successfully";
    }
    public String signIn(String username,String password){
        
        String message="no";
        for(User u: users){
            if(u.username.equals(username) && u.password.equals(password)){
                message= "Signed in Successfully";
                break;
            }
            else if((u.username.equals(username)) && !(u.password.equals(password))){
                message= "401 Unauthorized Access";
            }
            else /*if(!u.username.equals(username)&& u.password.equals(password))*/{
                message= "404 User Not Found";
            }
        }
        return message;
    }
    public User getUser(String username,String password) throws IOException {
        User user = new User();
        for(User u: users){
            if(u.username.equals(username) && u.password.equals(password)){
                user = u;
            }
        }
        return user;
    }
    public boolean usernameExists(String uname){
        boolean flag = false;
        if(users.isEmpty()){
            return flag;
        }
        for(User u: users){
            if(u.username.equals(uname)){
                flag = true;
                break;
            }
        }
        return flag;
    }
    public void uploadUsers() throws IOException {
        while(reader.hasNext()){
            String line = reader.nextLine();
            String[] user = line.split(":");
            User newUser = new User(user[0], user[1], user[2]);
            users.add(newUser);
        }
    }
    public void writeHistory() throws IOException {
        String filePath= this.username+".txt";
        System.out.println(filePath);
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedWriter historyWriter= new BufferedWriter(new FileWriter(file));
        System.out.println(this.username+":"+this.wins+":"+this.losses);
        historyWriter.write(this.username+":"+this.wins+":"+this.losses);
        historyWriter.flush();
    }
    public void uploadScore() throws FileNotFoundException {
        Scanner reader = new Scanner(new File(this.username+".txt"));
        while(reader.hasNext()){
            String line = reader.nextLine();
            String[] user = line.split(":");
            this.wins=Integer.parseInt(user[1]);
            this.losses=Integer.parseInt(user[2]);

        }
    }

}
