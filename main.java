import java.util.Scanner;

public class main{
    public static void main(String[] args){
        Scanner option = new Scanner(System.in);

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
                    
                    break;
            
                default:
                    break;
            }
        }
    }
}