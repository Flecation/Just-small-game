
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class game_main extends DBConnection{

   public static String id;

   public static String name, pw;

    static int menu;

    static boolean flag = false;

   public static HashMap<String, String> u = new HashMap<>();

   protected static Scanner sc = new Scanner(System.in);

    static login_logout user = new login_logout();

    public static void main(String[] args) throws SQLException {
        while (!flag) {
            flag = true;
            System.out.println("======Welcome From the Game======");
            System.out.println("1. Login");
            System.out.println("2. exit");

            System.out.print("menu : ");
            menu = Integer.parseInt(sc.nextLine());

            switch (menu) {
                case 1:
                    System.out.println("\t=>(1)New Register");
                    System.out.println("\t=>(2)Login old");
                    System.out.print("menu : ");
                    menu = Integer.parseInt(sc.nextLine());

                    if (menu == 1) {
                        login();
                    } else if (menu == 2) {
                        oldLogin();
                    } else {
                        System.out.println("Wronged command!!");
                        flag = false;
                    }

                    break;
                case 2:
                    System.out.println("Thank for coming");
                    System.exit(0);

                    break;
                default:
                    System.out.println("Wrong command!!");
                    flag = false;
                    break;
            }

            if (flag) {
                flag = false;

                while (!flag) {
                   

                    System.out.println("Welcome from the game!!");
                    System.out.println("1. Guesting the City name");
                    System.out.println("2. Guesting the Country name");
                    System.out.println("3. Guesting the animals");
                    System.out.println("4. Guesting the number");
                    System.out.println("5. View result");
                    System.out.println("6. Logout from the game");
                    System.out.print("menu : ");
                    menu = Integer.parseInt(sc.nextLine());

                    switch (menu) {
                        case 1:
                            city_country_game city = new city_country_game();
                            city.City_guess();
                            break;
                        case 2:
                            city_country_game country = new city_country_game();
                            country.Country_guess();

                            break;
                        case 3:
                            animal_game ag = new animal_game();
                            ag.answer();
                            break;
                        case 4:
                            number_game ng = new number_game();
                            ng.guestingNumber();
                            break;
                        case 5:
                            System.out.println("Your Result is ");
                            System.out.println(user.view_rate(id));
                            break;
                        case 6:
                            flag = true;
                            break;

                        default:
                            System.out.println("wrong command!!");
                            break;
                    }

                    if(!flag){
                        System.out.println("===========================================");
                        System.out.println("Do you want to play another games(yes/no) ?");
                        System.out.print("menu:");
                        String choice = sc.nextLine();

                        if(choice.equalsIgnoreCase("yes")){
                            System.out.println("===========================================");
                        }else{
                            flag = true;
                        }
                    }
                    
                    if (flag) {
                    logout();
                    user.disconnect();
                    System.out.println("Thank for coming the game Mr."+name);
                }
                }

                

            }
        }

    }

    static void login() throws SQLException {
        System.out.print("Enter Your name : ");
        name = sc.nextLine();
        System.out.print("Enter password : ");
        pw = sc.nextLine();
        id = user.generateId();
        if (user.userLogin(id, name, pw)) {
            u.put(name, pw);
        } else {
            flag = false;
        }

    }

    static void oldLogin() throws SQLException {
        System.out.print("Enter new Name: ");
        name = sc.nextLine();
        System.out.print("Enter new password : ");
        pw = sc.nextLine();

        if (user.userOldLogin(name, pw)) {
            System.out.println("Your account is correct!!");
            System.out.println();
            u.put(name, pw);
            id = user.findId(name, pw);
        } else {
            System.out.println("Your account is not found!!");
            flag = false;
        }

        if (!flag) {
            flag = true;

            System.out.println("\t=>(1)Login new");
            System.out.println("\t=>(2)Login again");
            System.out.println("\t=>(3)exit");
            System.out.print("menu : ");
            menu = Integer.parseInt(sc.nextLine());

            switch (menu) {
                case 1:
                    login();
                    break;
                case 2:
                    oldLogin();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong command!!");
                    flag = false;
                    break;
            }
        }
    }

    static void logout() throws SQLException {
        for (String key : u.keySet()) {
            String value = u.get(key);
            user.userLogout(key, value);
        }

    }
}
