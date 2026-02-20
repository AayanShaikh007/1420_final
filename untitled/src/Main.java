import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        UserManagement use = new UserManagement();
        Scanner s = new Scanner(System.in);
        int num;
    while(true){
        System.out.println("choose: 1 or 2");
        num = s.nextInt();
        if(num ==1){
            use.createUser();

        }else{
            break;
        }

    }

    }
}