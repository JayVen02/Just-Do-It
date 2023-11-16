import java.util.Scanner;

class Task{
      String description;
      int priority;
      String dueDate;
      boolean isComplete;

      Task(String description, int priority, String dueDate){
        this.description = description;
        this.priority = priority;
        this.priority = priority;
        this.dueDate = dueDate;
        this.isComplete = false;
      }
}

//Method of addTask, TaskList, markTaskComplete, and displayTask
class taskList{

}

public class main{
    public static void main(String[] args){
        Scanner option = new Scanner(System.in);

        //Option
        while(true){
            System.out.println("1. Add Task");
            System.out.println("2. Display Tasks");
            System.out.println("3. Mark Task as Complete");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = option.nextInt();
            option.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Task Description");
                    String description = option.nextLine();
                    System.out.print("Enter Priority (1-5):");
                    int priority = option.nextInt();
                    option.nextLine();
                    System.out.print("Enter Due date");
                    String dueDate = option.nextLine();

                    Task newTask = new Task(description, priority, dueDate);
                    taskList.addTask(newTask);
                    System.out.println("Task added Succesfully");
                    break;
                case 2:
                    System.out.println("Task List:");
                    taskList.displayTasks();
                    break;
                case 3:
                    System.out.print("Enter Task Description to mark as complete: ");
                    String taskDescription = scanner.nextLine();
                    taskList.markTaskComplete(taskDescription);
                    System.out.println("Task marked as complete!");
                    break;
                case 4:
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }
}