import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

class Activity {
    String type;
    int duration;
    double caloriesBurned;
    LocalDate date;

    public Activity(String type, int duration, LocalDate date) {
        this.type = type;
        this.duration = duration;
        this.date = date;
        this.caloriesBurned = calculateCalories();
    }

    public double calculateCalories() {
        if (type.equalsIgnoreCase("Running")) {
            return duration * 10;
        } else if (type.equalsIgnoreCase("Cycling")) {
            return duration * 8;
        } else {
            return duration * 5;
        }
    }

    public void showActivity() {
        System.out.printf("%-10s | Date: %s | Duration: %3d mins | Calories Burned: %.2f%n",
                type, date, duration, caloriesBurned);
    }
}

class User {
    String name;
    int age;
    String gender;

    public User(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public void showUserInfo() {
        System.out.println("\n=== User Information ===");
        System.out.println("Name   : " + name);
        System.out.println("Age    : " + age);
        System.out.println("Gender : " + gender);
    }
}

public class fitness {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Welcome to the Simple Fitness Tracker ===");
        System.out.print("Enter your name    : ");
        String name = sc.nextLine();
        System.out.print("Enter your age     : ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your gender  : ");
        String gender = sc.nextLine();

        User user = new User(name, age, gender);

        System.out.print("\nHow many activities do you want to log? ");
        int n = sc.nextInt();
        sc.nextLine();

        Activity[] activities = new Activity[n];
        double totalCalories = 0;

        for (int i = 0; i < n; i++) {
            System.out.println("\nActivity " + (i + 1));
            System.out.print("Enter activity type (Running/Cycling/Other): ");
            String type = sc.nextLine();
            System.out.print("Enter duration in minutes: ");
            int duration = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter activity date (YYYY-MM-DD): ");
            String dateStr = sc.nextLine();
            LocalDate date = LocalDate.parse(dateStr);

            activities[i] = new Activity(type, duration, date);
            totalCalories += activities[i].caloriesBurned;
        }

        // Show user and activity summary
        System.out.println("\n=== Fitness Summary ===");
        user.showUserInfo();
        System.out.println("\nActivities:");
        for (Activity a : activities) {
            a.showActivity();
        }

        double avgCalories = totalCalories / n;
        System.out.printf("\nTotal Calories Burned: %.2f%n", totalCalories);
        System.out.printf("Average Calories Burned per Activity: %.2f%n", avgCalories);

        // Filter option
        System.out.print("\nDo you want to filter by activity type? (yes/no): ");
        String filterChoice = sc.nextLine();
        if (filterChoice.equalsIgnoreCase("yes")) {
            System.out.print("Enter activity type to filter (e.g., Running): ");
            String filterType = sc.nextLine();
            System.out.println("\nFiltered Activities:");
            for (Activity a : activities) {
                if (a.type.equalsIgnoreCase(filterType)) {
                    a.showActivity();
                }
            }
        }

        // Save to file
        String filename = "fitness_report_" + LocalDate.now() + ".txt";
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {
            writer.println("=== Fitness Report ===");
            writer.println("User: " + user.name + ", Age: " + user.age + ", Gender: " + user.gender);
            writer.println();

            for (Activity a : activities) {
                writer.printf("%-10s | Date: %s | Duration: %3d mins | Calories Burned: %.2f%n",
                        a.type, a.date, a.duration, a.caloriesBurned);
            }
            writer.printf("\nTotal Calories Burned: %.2f%n", totalCalories);
            writer.printf("Average Calories Burned per Activity: %.2f%n", avgCalories);
            writer.println("\nThank you for using the Fitness Tracker!");
            System.out.println(" Fitness report saved to '" + filename + "'");
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }

        sc.close();
    }
}
