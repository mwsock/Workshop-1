package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static String[][] tasks = null;
    static String filePath = "tasks.csv";
    static String datePattern = "yyyy-mm-dd";
    static String[] options = {"add", "remove", "list", "exit"};

    public static void main(String[] args) {

        loadFile();
        displayOptions();
        loadInterface();

    }

    public static void loadFile() {
        Path path = Path.of(filePath);

        try {
            List<String> files = Files.readAllLines(path);
            tasks = new String[files.size()][files.get(0).split(",").length];

            for (int i = 0; i < files.size(); i++) {
                tasks[i] = files.get(i).split(",");
            }

        } catch (IOException e) {
            System.out.println("File not exist.");
            System.exit(0);
        }

    }

    private static void loadInterface() {

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {

            String input = scanner.nextLine();
            selectOption(input);
            displayOptions();
        }
    }

    public static void displayOptions(){
        System.out.println("\n" + ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);

        for (String option : options) {
            System.out.println(option);
        }
    }

    public static void selectOption(String input) {

        switch (input) {

            case "add":

                addTask();

                break;

            case "list":

                readFile(tasks);

                break;

            case "remove":

                remove();

                break;

            case "exit":

                exit(tasks);

            default:

                System.out.println("Please select a correct option.");
        }
    }


    public static void readFile(String[][] tasks) {
        System.out.println("list");
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j]);
            }
            System.out.println();
        }
    }
    public static void addTask() {

        System.out.println("add");
        Scanner scanner = new Scanner(System.in);
        String input = "";

        System.out.println("Please add task description");
        String taskName = scanner.nextLine().replace(',', ' ');

        System.out.println("Please add task due date. Use " + datePattern + " date format.");
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            if (dateValidator(input)) break;
        }
        String taskDueDate = input;

        System.out.println("Is your task important: true/false");
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            if (importanceValidator(input)) break;
        }
        String taskImportance = input;


        String[] newTask = {taskName + " ", taskDueDate + " ", taskImportance};

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = newTask;

        System.out.println("Task successfully added.");
    }

    public static boolean importanceValidator(String input) {
        if (input.equals("true") || input.equals("false")) {
            return true;
        } else {
            System.out.println("Invalid input! Available options: true/false");
            return false;
        }
    }
    public static boolean dateValidator(String input) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
            String date = String.valueOf(simpleDateFormat.parse(input));
            return !date.isEmpty();
        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("Invalid date format! " + e.getMessage());
            return false;
        }
    }

    public static void remove() {
        System.out.println("remove");
        System.out.println("Please select number to remove.");

        Scanner scanner = new Scanner(System.in);
        try {
            int taskNumber = scanner.nextInt();
            tasks = ArrayUtils.remove(tasks, taskNumber);
            System.out.println("Value was successfully deleted.");
        } catch (IndexOutOfBoundsException | InputMismatchException e) {
            System.out.println("Incorect argument passed. Please give number greater or equal 0");
        }
    }

    public static void exit(String[][] tasks) {
        System.out.println("exit");
        String line = "";
        for (String[] task : tasks) {
            line += task[0].trim() + ", " + task[1].trim() + ", " + task[2].trim() + "\n";
        }

        try {
            Files.write(Path.of(filePath), line.getBytes());
        } catch (IOException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
        }

        System.out.println(ConsoleColors.RED + "Bye bye");
        System.exit(0);
    }


}
