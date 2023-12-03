package org.example;
// With linkedlist, treemaps, and insertion sort
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class task {
    private String title;
    private String notes;
    private Date deadline;
    private int priority;
    private boolean isComplete;

    public task(String title, Date deadline, int priority, String notes) {
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.notes = notes;
        this.isComplete = false;
    }

    public Date getDeadline() {
        return deadline;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void markComplete() {
        isComplete = true;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setPriority(int newPriority, toDoList toDoList) {
        if (newPriority < 1 || newPriority > 5) {
            System.out.println("Invalid priority. Please enter a number between 1 and 5.");
        } else {
            this.priority = newPriority;
            toDoList.addTask(this);
        }
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String status = isComplete ? "Complete" : "Incomplete";
        String notesInfo = notes.isEmpty() ? "No notes" : "Notes: " + notes;
        return String.format("%s\nTitle: %s\nDeadline: %s\nPriority: %d\nStatus: %s\n%s\n",
                isComplete ? "[X]" : "[ ]", title, dateFormat.format(deadline), priority, status, notesInfo);
    }
}

class toDoList {
    protected Map<Integer, LinkedList<task>> priorityCategories;

    public toDoList() {
        this.priorityCategories = new TreeMap<>(Collections.reverseOrder());
        for (int i = 1; i <= 5; i++) {
            priorityCategories.put(i, new LinkedList<>());
        }
    }

    // Method to add a task to the ToDoList
    public void addTask(task task) {
        int priority = task.getPriority();
        LinkedList<org.example.task> tasks = priorityCategories.get(priority);
        tasks.add(task);
        insertionSort(tasks);
    }

    // Method to display all tasks in the ToDoList
    public void displayTasks() {
        if (priorityCategories.isEmpty() || priorityCategories.values().stream().allMatch(LinkedList::isEmpty)) {
            System.out.println("No entries in the To-Do List.");
        } else {
            for (Map.Entry<Integer, LinkedList<task>> entry : priorityCategories.entrySet()) {
                int priority = entry.getKey();
                LinkedList<task> tasks = entry.getValue();

                if (!tasks.isEmpty()) {
                    System.out.println("\nPriority " + priority + " Tasks:");
                    System.out.println("=========================");
                    int index = 1;
                    for (org.example.task task : tasks) {
                        System.out.println(index + ". " + task);
                        index++;
                    }
                }
            }
        }
    }

    // Method to mark a task as complete
    public void markTaskComplete(Scanner scanner) {
        System.out.print("Enter the priority of the task to mark as complete (1-5): ");
        int priority;
        try {
            priority = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } catch (InputMismatchException e) {
            handleInvalidInput(scanner);
            return;
        }

        if (priority < 1 || priority > 5 || !priorityCategories.containsKey(priority)) {
            System.out.println("Invalid priority. Please enter a number between 1 and 5.");
            return;
        }

        LinkedList<task> tasks = priorityCategories.get(priority);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found under priority " + priority);
            return;
        }

        System.out.println("\nTasks under Priority " + priority);
        System.out.println("=========================");
        displayTasks();

        System.out.print("Enter the index of the task to mark as complete: ");
        int taskIndex;
        try {
            taskIndex = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } catch (InputMismatchException e) {
            handleInvalidInput(scanner);
            return;
        }

        if (taskIndex >= 1 && taskIndex <= tasks.size()) {
            task task = tasks.get(taskIndex - 1);
            task.markComplete();
            System.out.println("Task marked as complete: \n" + task);
            removeCompletedTasks();
        } else {
            System.out.println("Invalid task index.");
        }
    }

    // Method to add a task using user input
    public void addTask(Scanner scanner) {
        System.out.println("\nAdding a New Task");
        System.out.println("==================");

        System.out.print("Enter task title: ");
        String title = scanner.nextLine();

        System.out.print("Enter task deadline (yyyy-MM-dd): ");
        Date deadline;
        do {
            String deadlineString = scanner.nextLine();
            deadline = parseDate(deadlineString);
        } while (deadline == null);

        System.out.print("Enter task priority (1-5, where 1 is highest): ");
        int priority;
        try {
            priority = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } catch (InputMismatchException e) {
            handleInvalidInput(scanner);
            return;
        }

        System.out.print("Enter optional notes for the task: ");
        String notes = scanner.nextLine();

        task task = new task(title, deadline, priority, notes);
        addTask(task);
        System.out.println("\nTask added successfully!");
    }

    // Method to modify a task
    public void modifyTask(Scanner scanner) {
        System.out.print("Enter the priority of the task to modify (1-5): ");
        int priority;
        try {
            priority = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } catch (InputMismatchException e) {
            handleInvalidInput(scanner);
            return;
        }

        if (priority < 1 || priority > 5) {
            System.out.println("Invalid priority. Please enter a number between 1 and 5.");
            return;
        }

        LinkedList<task> tasks = priorityCategories.get(priority);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found under priority " + priority);
            return;
        }

        System.out.println("\nTasks under Priority " + priority);
        System.out.println("=========================");
        int index = 1;
        for (org.example.task task : tasks) {
            System.out.println(index + ". " + task);
            index++;
        }

        System.out.print("Enter the index of the task to modify: ");
        int taskIndex;
        try {
            taskIndex = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } catch (InputMismatchException e) {
            handleInvalidInput(scanner);
            return;
        }

        if (taskIndex >= 1 && taskIndex <= tasks.size()) {
            task task = tasks.get(taskIndex - 1);
            modifyTaskDetails(scanner, task);
            System.out.println("Task modified successfully: \n" + task);
        } else {
            System.out.println("Invalid task index.");
        }
    }

    // Method to modify details of a task
    public void modifyTaskDetails(Scanner scanner, task task) {
        while (true) {
            System.out.println("\nModify Task: ");
            System.out.println("1. Title");
            System.out.println("2. Due Date");
            System.out.println("3. Priority");
            System.out.println("4. Notes");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            } catch (InputMismatchException e) {
                handleInvalidInput(scanner);
                return;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter new title: ");
                    String newTitle = scanner.nextLine();
                    task.setTitle(newTitle);
                    System.out.println("Task title updated successfully!");
                    break;
                case 2:
                    System.out.print("Enter new due date (yyyy-MM-dd): ");
                    String newDeadlineString = scanner.nextLine();
                    Date newDeadline = parseDate(newDeadlineString);
                    if (newDeadline != null) {
                        task.setDeadline(newDeadline);
                        System.out.println("Task due date updated successfully!");
                    }
                    break;
                case 3:
                    System.out.print("Enter new priority (1-5): ");
                    int newPriority;
                    try {
                        newPriority = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                    } catch (InputMismatchException e) {
                        handleInvalidInput(scanner);
                        return;
                    }
                    task.setPriority(newPriority, this);
                    System.out.println("Task priority updated successfully!");
                    break;
                case 4:
                    System.out.print("Enter new notes: ");
                    String newNotes = scanner.nextLine();
                    task.setNotes(newNotes);
                    System.out.println("Task notes updated successfully!");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    // Method to remove completed tasks
    private void removeCompletedTasks() {
        for (LinkedList<task> tasks : priorityCategories.values()) {
            tasks.removeIf(task::isComplete);
        }
    }

    // Method to parse a date from a string
    private static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // Strict date parsing
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return null;
        }
    }

    // Method to handle invalid input
    private void handleInvalidInput(Scanner scanner) {
        System.out.println("Invalid input. Please enter a valid number.");
        scanner.next(); // Consume the invalid input to avoid an infinite loop
    }

    // Method to perform insertion sort on a list of tasks
    private void insertionSort(LinkedList<task> tasks) {
        Comparator<task> comparator = Comparator.comparing(task::getPriority).thenComparing(task::getDeadline);
        for (int i = 1; i < tasks.size(); i++) {
            task key = tasks.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(tasks.get(j), key) > 0) {
                tasks.set(j + 1, tasks.get(j));
                j--;
            }
            tasks.set(j + 1, key);
        }
    }
}

public class main {
    private static void printMenu() {
        System.out.println("\n=========================");
        System.out.println("1. Add Task");
        System.out.println("2. Display Tasks");
        System.out.println("3. Mark Task Complete");
        System.out.println("4. Modify Task");
        System.out.println("5. Exit");
        System.out.println("=========================");
        System.out.print("Enter your choice: ");
    }

    private static void handleInvalidInput(Scanner scanner) {
        System.out.println("Invalid input. Please enter a valid number.");
        scanner.next(); // Consume the invalid input to avoid an infinite loop
    }

    // Method to execute the chosen option
    private static void executeOption(Scanner scanner, toDoList toDoList, int choice) {
        try {
            switch (choice) {
                case 1:
                    toDoList.addTask(scanner);
                    break;
                case 2:
                    toDoList.displayTasks();
                    break;
                case 3:
                    toDoList.markTaskComplete(scanner);
                    break;
                case 4:
                    toDoList.modifyTask(scanner);
                    break;
                case 5:
                    System.out.println("Exiting Just Do It. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } catch (InputMismatchException e) {
            handleInvalidInput(scanner);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        toDoList toDoList = new toDoList();

        System.out.println("Welcome to Just Do it!");

        while (true) {
            printMenu();

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character
            } catch (InputMismatchException e) {
                handleInvalidInput(scanner);
                continue;
            }

            executeOption(scanner, toDoList, choice);
        }
    }
}

/*
TreeMap for Priority Categories:

Reason: A TreeMap is used to organize tasks by priority. The choice of TreeMap is beneficial because it automatically sorts the priorities in descending order (due to the use of Collections.reverseOrder()), making it easy to display tasks in a prioritized manner.
Advantages: Efficient lookup and retrieval of tasks based on priority. Sorting priorities allows for a straightforward display of tasks from highest to lowest priority.
LinkedList for Tasks within Each Priority:

Reason: A LinkedList is used to store tasks for each priority. The linked list structure provides efficient insertion and removal of tasks, which is essential for dynamic task management.
Advantages: Insertion and removal of tasks are faster compared to other list implementations like ArrayList. This is important when tasks are frequently added, removed, or sorted.
Insertion Sort Algorithm:

Reason: Insertion sort is chosen to maintain the order of tasks within each priority category. The main reason is simplicity and efficiency for small datasets, which is a common scenario for a to-do list.
Advantages: Simple to implement, efficient for small datasets, and stable (preserves the order of equal elements).
Scanner for User Input:

Reason: Scanner is used to get input from the user in the console. It's a standard Java class for reading input from various sources, and in this case, it facilitates interaction with the to-do list manager.
Advantages: Easy to use, part of the Java standard library, and suitable for simple console-based applications.
The choices made in terms of data structures and algorithms prioritize simplicity, efficiency, and ease of maintenance. For a to-do list management system, where the dataset is typically small, these choices strike a good balance between performance and code readability.
*/