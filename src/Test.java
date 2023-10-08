import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter a number 1-3: ");
        int num = sc.nextInt();
        while(num < 1 || num > 3) {
            System.out.println("Please enter a valid number 1-3: ");
            num = sc.nextInt();
        }
        System.out.println("Reading file");
    }
}
