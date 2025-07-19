
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class animal_game {
    
    protected String gid = "g01";//the game of id

    public DBConnection db = new DBConnection();//to connect with database
    public Connection con = db.connect();
    


    public game_main gmain = new game_main();//to get user id

    private  String[]  words = {"antelope","ape","badger","bear","beaver","bison","crocodile","elephant",
                            "elk","ferret","goat","goose","kangaroo","llama","lion","monkey","moose",
                            "orangutan","shark","snake","tiger","whale","wombat"};
    private Random ra = new Random();
    private String answer;

    public String guess;
    public int count =0 ;
    public animal_game(){};
    public animal_game(String guess,int count){
        this.guess = guess;
        this.count = count;
    }
    public String getGuess() {
        return guess;
    }
    public void setGuess(String guess) {
        this.guess = guess;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer() {
        this.answer = words[ra.nextInt(words.length)];
    }


    public HashSet<String> store = new HashSet<>();
    
    public void answer() throws SQLException{
        Scanner sc = new Scanner(System.in);
        
        

        String choice = "yes";
        while(choice.equalsIgnoreCase("yes")){
            boolean flag = false;
            setAnswer();
            System.out.println("Welcome From guesting game");
            System.out.println("This is the animal guesting game");
            System.out.println("Let's start (If you want exit enter 0)");
            System.out.println("==========================================");
            System.out.println("The animal of the name start with "+answer.charAt(0) +"and have "+answer.length()+" numbers!!");
            
            String show =""+answer.charAt(0);
            for (int i =0;i<answer.length()-1;i++) {
                show+="*";
            }
            System.out.println("[ "+show+" ]");

            while(!flag){

                while(!flag){
                    flag = true;

                    System.out.print("(guess it)=>");
                    setGuess(sc.nextLine());
                    if(guess.length()==answer.length()){
                        break;
                    }else if (guess.equals("0")) {
                        break;
                    }else if(guess.length()>answer.length()){
                        System.out.println("Your answer is greater than the the answer");
                        System.out.println("Try again");
                        flag = false;
                    }else if(guess.length()<answer.length()){
                        System.out.println("Your answer is less than the the answer");
                        System.out.println("Try again");
                        flag = false;
                    }else{
                        System.out.println("Try Again!!");
                        flag = false;
                    }
                }

                if(guess.equals("0")){
                    System.out.println("You fail the mission!!");
                    CallableStatement ca = con.prepareCall("{CALL surrender_score(?,?,?)}");
                    ca.setString(1,gmain.id);
                    ca.setString(2, gid);
                    ca.setInt(3, count);
                    ca.execute();
                    ca.close();
                    con.close();
                    break;
                }else if(guess.equalsIgnoreCase(answer)){
                    System.out.println("Your answer is corret");
                    System.out.println("Complete the mission!!");
                    System.out.println("[ "+getGuess()+" ]");
                    
                    CallableStatement ca = con.prepareCall("{CALL insert_score(?,?,?)}");
                    ca.setString(1,gmain.id);
                    ca.setString(2, gid);
                    ca.setInt(3, count);
                    ca.execute();
                    ca.close();
                    con.close();
                    count =0;
                    break;
                }else{
                    count+=1;
                    store.add(getGuess());
                    String result =checking(getGuess(), answer); 
                    System.out.println("[ "+ result +" ]");
                    System.out.println("You uesd "+count+" chance(s)");
                    System.out.println("Try again guess");
                    flag = false;
                }


            }
            System.out.println("Do you want to play next time (yes/no)?");
            choice = sc.nextLine();
        }
        
    }

    public String checking(String guess,String ans){
        String result ="";
        System.out.println("Your correct words are");
        
        for(int i =0;i<guess.length();i++){
            if(guess.charAt(i)==answer.charAt(i)){
                result+=guess.charAt(i);
            }else{
                result+="*";
            }
        }

        return result;
    }

    
    
}
