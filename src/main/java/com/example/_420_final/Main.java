package com.example._420_final;

import com.example._420_final.Management.BookingManagement;
import com.example._420_final.Management.EventManagement;
import com.example._420_final.Management.UserManagement;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserManagement use = new UserManagement();
        Scanner s = new Scanner(System.in);
        BookingManagement b = new BookingManagement();
        EventManagement e = new EventManagement();
        int num;
        String input, input2;
    while(true){
        System.out.println("1.UserManagement\n2.EventManagement\n3.BookingManagement\n4.Waitlist Management \n5.End\nInput:");
        num = s.nextInt();
        s.nextLine();
        if(num ==1){
            while (true){
                System.out.println("User Management: \n1.Create User\n2.View User details\n3.List users\n4.Go Back\nInput: ");
                num = s.nextInt();
                s.nextLine();
                switch (num){
                    case 1:
                        use.createUser();
                        break;
                    case 2:
                        System.out.print("Enter the Id of the user:");
                        input = s.nextLine();
                        use.viewUser(input);
                        break;
                    case 3:
                        use.ListUsers();
                        break;
                    default:
                        break;


                }
                if (num == 4){
                    break;
                }
            }


        }else if(num == 2){
            while (true){
                System.out.println("Event Management: \n1.Create Event\n2.Update info\n3.Cancel event\n4.List Events \n5.Search functions \n6.filter\n7.Go Back\nInput: ");
                num = s.nextInt();
                s.nextLine();
                switch (num){
                    case 1:
                        e.createEvent();
                        break;
                    case 2:
                        e.updateEvent("");
                        break;
                    case 3:
                        e.cancelEvent("");
                        break;
                    case 4:
                        e.listEvent();
                        break;
                    case 5:
                        System.out.println("Title: ");
                        input = s.nextLine();
                        e.searchByTitle(input);
                        break;
                    case 6:
                        System.out.println("Type: ");
                        input = s.nextLine();
                        e.filterByType(input);
                        break;
                    default:
                        break;
                }
                if (num == 7){
                    break;
                }
            }
        }else if(num == 3){
            while (true){
                System.out.println("Booking Management: \n1.Book event\n2.Cancel Booking\n3.View Event Roster\n4.Go Back\nInput: ");
                num = s.nextInt();
                s.nextLine();
                switch (num){
                    case 1:
                        System.out.println("UserId: ");
                        input = s.nextLine();
                        System.out.println("EventId: ");
                        input2 = s.nextLine();
                        b.bookEvent(input, input2);
                        break;
                    case 2:
                        System.out.println("UserId: ");
                        input = s.nextLine();
                        System.out.println("EventId: ");
                        input2 = s.nextLine();
                        b.cancelEvent(input, input2);
                        break;
                    case 3:
                        System.out.println("EventId: ");
                        input2 = s.nextLine();
                        b.viewEventRoster(input2);
                        break;
                    default:
                        break;
            }
                if (num == 4){
                    break;
                }
        }
        }else if(num == 4){
            System.out.println("here waitlist");
        }else{
            break;
        }

    }

    }
}