package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static String[][] tasks = null;
    static String filePath = "tasks.csv";

    public static void main(String[] args) {

        loadFile();

        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);

        String[] options = {"add","remove","list","exit"};

        for (String option:options) {
            System.out.println(option);
        }

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){

            String input = scanner.nextLine();
            selectOption(input);

        }

    }

    public static void selectOption(String input){


        switch (input) {

            case "add":

                addTask();

                break;

            case "list":

                readFile(tasks);

                break;

            case "exit":

                exit(tasks);

// other options

            default:

                System.out.println("Please select a correct option.");

        }


    }

    public static String[][] loadFile(){
        Path path = Path.of(filePath);

        try {
            List<String> files = Files.readAllLines(path);
            tasks = new String[files.size()][files.get(0).split(",").length];

            for (int i = 0; i < files.size(); i++) {
                tasks[i] = files.get(i).split(",");
            }


        }catch (IOException e){
            System.out.println(e.getCause() + " | " + e.getMessage());
        }

        return tasks;
    }

    public static void readFile(String[][] tasks){
        System.out.println("list");
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j]);
            }
            System.out.println();
        }

    }


    public static void addTask(){

        System.out.println("add");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please add task description");
        String taskName = scanner.nextLine();

        System.out.println("Please add task due date");
        String taskDueDate = scanner.nextLine();

        System.out.println("Is your task important: true/false");
        String taskImportance = scanner.nextLine();

        String[] newTask = {taskName + " ",taskDueDate+ " ", taskImportance};

        tasks = Arrays.copyOf(tasks, tasks.length + 1);

        tasks[tasks.length-1] = newTask;
    }

    public static void exit(String[][] tasks){


        System.out.println(ConsoleColors.RED + "Bye bye");
        System.exit(0);
    }


}
