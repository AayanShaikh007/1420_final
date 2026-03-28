package com.example._420_final;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManagement {
    private static ArrayList<User> userList = new ArrayList<User>();

    public UserManagement() {
    }

    public void addUser(User user) {
        userList.add(user);
    }

    /**
     * GUI-friendly method to create users.
     * Fulfills Phase 1: Validates ID/Email and handles Inheritance (Student/Staff/Guest).
     */
    public String createUserGui(String type, String userId, String name, String email) {
        // 1. Validation Logic
        if (checkType(type)) {
            return "Error: Invalid type. Use Student, Staff, or Guest.";
        }
        if (checkId(userId)) {
            return "Error: User ID already exists.";
        }
        if (checkEmail(email)) {
            return "Error: Invalid email format.";
        }

        // 2. Polymorphic Object Creation
        // This ensures the correct booking capacity is set (Student=3, Staff=5, Guest=1)
        User newUser;
        switch (type.toLowerCase()) {
            case "student":
                newUser = new Student(userId, name, email);
                break;
            case "staff":
                newUser = new Staff(userId, name, email);
                break;
            case "guest":
                newUser = new Guest(userId, name, email);
                break;
            default:
                return "Error: Selection error.";
        }

        userList.add(newUser);
        return "Success: Added " + name + " (" + type + ")";
    }

    public User getUser(String userId) {
        if (userList != null) {
            for (User user : userList) {
                if (userId.equals(user.getUserId())) {
                    return user;
                }
            }
        }
        return null;
    }

    public boolean checkId(String userId) {
        for (User user : userList) {
            if (userId.equals(user.getUserId())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkType(String type) {
        if (type == null) return true;
        List<String> acceptable = List.of("student", "staff", "guest");
        return !acceptable.contains(type.toLowerCase());
    }

    public boolean checkEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email == null || !email.matches(regex);
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public void createUser() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Create a user (Student/Staff/Guest): ");
        String type = myObj.nextLine();
        System.out.print("User ID: ");
        String id = myObj.nextLine();
        System.out.print("Name: ");
        String name = myObj.nextLine();
        System.out.print("Email: ");
        String email = myObj.nextLine();

        // Call our GUI method to handle the actual creation logic
        String result = createUserGui(type, id, name, email);
        System.out.println(result);
    }

    public void viewUser(String userId) {
        User user = getUser(userId);
        if (user != null) {
            user.print();
        } else {
            System.out.println("User with ID " + userId + " not found.");
        }
    }

    public void ListUsers() {
        if (getUserList().isEmpty()) {
            System.out.println("No users in the system.");
        } else {
            for (User user : getUserList()) {
                // This prints to the console for your old Main.java code
                System.out.println("ID: " + user.getUserId() + " | Name: " + user.getName());
            }
        }
    }

    public static void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users.txt"))) {
            for (User u : userList) {
                String type = u.getClass().getSimpleName();
                writer.println(type + "," + u.getUserId() + "," + u.getName() + "," + u.getEmail());
            }
        } catch (IOException e) {
            System.err.println("Save Error: " + e.getMessage());
        }
    }

    public static void loadUsers() {
        File file = new File("users.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            userList.clear();
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length < 4) continue;

                String type = p[0], id = p[1], name = p[2], email = p[3];
                User user = null;
                if (type.equalsIgnoreCase("Student")) user = new Student(id, name, email);
                else if (type.equalsIgnoreCase("Staff")) user = new Staff(id, name, email);
                else if (type.equalsIgnoreCase("Guest")) user = new Guest(id, name, email);

                if (user != null) userList.add(user);
            }
        } catch (Exception e) {
            System.err.println("Load Error: " + e.getMessage());
        }
    }
}