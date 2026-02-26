import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManagement {
    private static  ArrayList<User> userList= new ArrayList<User>();
    public String userId, name, email, type;
    public Scanner myObj = new Scanner(System.in);
    public boolean unique;


    public UserManagement(){
    }
    public void createUser(){
        User user = new User();
        unique = true;
        System.out.println("Create a user data needed: "); // change this when needed
        while (unique){
            System.out.print("Student\nStaff\nGuest\nType of user: ");
            type = myObj.nextLine();
            unique = checkType(type);
        }
        // here should be a set to add the type, but i need to ask something first.

        unique = true;
        while(unique){
            System.out.print("userId: ");
            userId = myObj.nextLine();
            unique = checkId(userId);
        }


        System.out.print("Name: ");
        name = myObj.nextLine();

        unique = true;
        while(unique){
            System.out.print("Email: ");
            email = myObj.nextLine();
            unique = checkEmail(email);
        }
        unique = true;
        while (unique){
            System.out.print("Student\nStaff\nGuest\nType of user: ");
            type = myObj.nextLine();
            unique = checkType(type);
        }
        user.setUserId(userId);
        user.setName(name);
        user.setEmail(email);
        if(type.equalsIgnoreCase("student")){
            Student s = new Student(userId, name, email);
            userList.add((s));
        }else if(type.equalsIgnoreCase("staff")){
            Staff st = new Staff(userId, name, email);
            userList.add((st));
        }else if(type.equalsIgnoreCase("guest")){
            Guest g = new Guest(userId, name, email);
            userList.add((g));
        }
    }

    public void viewUser(String userId){ // when making the GUI this should be changed to a return user to give each data to the GUI
        for(User user : userList){
            if (userId.equals(user.getUserId())) {
                System.out.println(user.getUserId());
                System.out.println(user.getEmail());
                System.out.println(user.getName());
                //probably type too and bookings
            }
        }
    }

    public void ListUsers(){
        for(User user : userList){
            System.out.println(user.getUserId()); // when making the gui this can be changed to show them better organize
        }
    }
    public boolean checkId(String userId){
        int size = userList.size();
        if(size > 0){
            for (User user : userList) {
                if (userId.equals(user.getUserId())) {
                    System.out.println("The user Id is already being used");
                    return true;
                }
            }

        }
        return false;
    }
    public boolean checkType(String type){
        List<String> acceptableAnswers = List.of("student", "staff", "guest");
        if (!acceptableAnswers.contains(type.toLowerCase())) {
            System.out.println("The type given is not accepted");
            return true;
        }
        return false;
    }
    public boolean checkEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(regex)) {
            System.out.println("Invalid email format");
            return false;
        }else{
            System.out.println("The email needs to contain @");
            return true;
        }


    }

    public ArrayList<User> getUserList(){
        return userList;
    }
}
