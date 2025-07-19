
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class city_country_game extends game_main{
        private int[] questions = { 1, 2, 3, 4, 5 ,6 ,7 ,8, 9 };
        private String[] countries = { "England", "France", "Spain", "Italy", "Germany", "Scotland", "Wales", "United Arab Emirates", "China" };
        private String[] capitals = { "London", "Paris", "Madrid", "Rome", "Berlin", "Edinburgh", "Cardiff", "Abu Dhabi", "Beijing" };

        private int count;
        public String guess;

        private HashMap<String,String> game = new HashMap<>();
        

        public city_country_game(){
            for(int i  =0;i<countries.length;i++){
                game.put(countries[i], capitals[i]);
            }
        }

        public String getGuess() {
            return guess;
        }

        public void setGuess(String guess) {
            this.guess = guess;
        }

        public void Country_guess() throws SQLException{
            String gid = "g03";
            System.out.println("--Welcome From the Country guesting game--");
            System.out.println("Ok Firstly I will show you the city name and guess it");
            System.out.println("If you want to exit enter 'surrender'");

            String choice = "yes";
            while(choice.equalsIgnoreCase("yes")){
                String country = random();
                boolean flag = true;
                while(flag){
                    System.out.println("What is the country of "+game.get(country));
                    System.out.print("=>");
                    setGuess(game_main.sc.nextLine());

                    if(getGuess().equalsIgnoreCase(country)){
                        System.out.println("Your answer is correct !!");
                        flag = false;
                        Connection con = super.connect();
                        CallableStatement cs = con.prepareCall("{CALL insert_score(?,?,?)}");
                        cs.setString(1, game_main.id);
                        cs.setString(2, gid);
                        cs.setInt(3, count);
                        cs.execute();
                        cs.close();
                        con.close();
                    }else if (getGuess().equalsIgnoreCase("surrender")) {
                        Connection con = super.connect();
                        CallableStatement cs = con.prepareCall("{CALL surrender_score(?,?,?)}");
                        cs.setString(1, game_main.id);
                        cs.setString(2, gid);
                        cs.setInt(3, count);
                        cs.execute();
                        cs.close();
                        con.close();
                        flag = false;
                    }else{
                        System.out.println("Your guested country is not corret!!");
                        System.out.println("try again");
                        count++;
                    }

                }
                if(!flag){
                    System.out.println("Do you want to guess one more time?(yes/no)");
                    choice = game_main.sc.nextLine();
                }
                
            }


           

        }

        public void City_guess() throws SQLException{
             String gid = "g02";
            System.out.println("--Welcome From the City guesting game--");
            System.out.println("Ok Firstly I will show you the country name and guess it");
            System.out.println("If you want to exit enter 'surrender'");

            String choice = "yes";
            while(choice.equalsIgnoreCase("yes")){
                String country = random();
                boolean flag = true;
                while(flag){
                    System.out.println("What is the country of "+country);
                    System.out.print("=>");
                    setGuess(game_main.sc.nextLine());

                    if(getGuess().equalsIgnoreCase(game.get(country))){
                        System.out.println("Your answer is correct !!");
                        flag = false;
                        Connection con = super.connect();
                        CallableStatement cs = con.prepareCall("{CALL insert_score(?,?,?)}");
                        cs.setString(1, game_main.id);
                        cs.setString(2, gid);
                        cs.setInt(3, count);
                        cs.execute();
                        cs.close();
                        con.close();
                    }else if (getGuess().equalsIgnoreCase("surrender")) {
                        Connection con = super.connect();
                        CallableStatement cs = con.prepareCall("{CALL surrender_score(?,?,?)}");
                        cs.setString(1, game_main.id);
                        cs.setString(2, gid);
                        cs.setInt(3, count);
                        cs.execute();
                        cs.close();
                        con.close();
                        flag = false;
                    }else{
                        System.out.println("Your guested city is not corret!!");
                        System.out.println("try again");
                        count++;
                    }

                }
                if(!flag){
                    System.out.println("Do you want to guess one more time?(yes/no)");
                    choice = game_main.sc.nextLine();
                }
            }
        }

        public String random(){
            Random ra = new Random();
            List<String> key =new ArrayList<>(game.keySet());
            int index = ra.nextInt(key.size());
            return key.get(index); 
        }

}
