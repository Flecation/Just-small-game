
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class number_game extends game_main{
    private int number;
    private  String gid = "g04"; 

    public int guess;
    public int count =0;

    //to use the util class of function
    private Random ra = new Random();
    public Scanner sc = new Scanner(System.in);

    //default constructor
    public number_game(){}

    public number_game(int g){
        this.guess = g;
    }

    //getter & setter method
    public int getNumber() {
        return number;
    }
    public void setNumber() {
        int num = ra.nextInt(100)+1;
        this.number = num;
    }
    public int getGuess() {
        return guess;
    }
    public void setGuess(int guess) {
        this.guess = guess;
    }
     public int getCount() {
        return count;
    }
    public void setCount() {
        this.count++;
    }

    //number game function
    public void guestingNumber() throws SQLException{
        System.out.println("--Welcome from the guesting number--");
        System.out.println("Ok let's play the game!!");
        System.out.println("The number is between 1 to 100 (if you want to exit enter '0')");
        String choice = "yes";
       
        while(choice.equalsIgnoreCase("yes")){
            this.setNumber();
            boolean flag = true;
            while(getGuess()!=getNumber() && flag){
                System.out.print("Guess number : ");
                this.setGuess(Integer.parseInt(sc.nextLine()));
                
                if(getGuess()==0){
                    System.out.println("You lose the game ");
                    Connection con = super.connect();
                    CallableStatement ca = this.con.prepareCall("{CALL surrender_score(?,?,?)}");
                        ca.setString(1,super.id);
                        ca.setString(2, this.gid);
                        ca.setInt(3, getCount());
                        ca.execute();
                        ca.close();
                        this.con.close();
                        flag = false;

                }else if(getGuess()>100 && getGuess()<1){
                    System.out.println("The nummber is between 1 and 100 ");
                    System.out.println("Your guested number is invalid");
                    System.out.println("Try again !!");
                }else if(getGuess()==getNumber()){
                    System.out.println("You answer is correct!!");
                    System.out.println("You win the game !!");
                    Connection con = super.connect();
                    CallableStatement ca = this.con.prepareCall("{CALL insert_score(?,?,?)}");
                        ca.setString(1,game_main.id);
                        ca.setString(2, this.gid);
                        ca.setInt(3, getCount());
                        ca.execute();
                        ca.close();
                        this.con.close();
                        count =0;
                }else{
                    if(getGuess()>getNumber()){
                        System.out.println("Your guesting number is greater than hiden number");
                        System.out.println("try one more again");
                        setCount();
                    }else{
                        System.out.println("Your guesting numberis less than hiden number");
                        System.out.println("try one more again");
                        setCount();
                        
                    }
                }
            }
            if(flag){
                System.out.println("Do you want to guess another time?? (yes/no)");
                choice = sc.nextLine();
            }else{
                choice ="no";
                
            }
        }
    }
    
}
