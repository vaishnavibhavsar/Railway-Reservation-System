package railways;
import java.util.*;

public class Main {
	public static void main(String args[]) {
		Scanner sc=new Scanner(System.in);
		System.out.println("WELCOME TO CODEWAYS!!!");
		System.out.println("Our trains run smoothly; so does our code!");
		Admin a=new Admin();
		Accounts acc=new Accounts();
		String securityKey;
		
		int choice=0;
		int ch=0;
		int trying=0;
		
		do {
			System.out.println("\nPlease select your role\n");
			System.out.println("1. Admin");
			System.out.println("2. User");
			System.out.println("3. Exit");
			int status=0;
			do {
				status=0;
				try {
					System.out.print("Enter your choice: ");
					choice = sc.nextInt(); //sc.nextLine();
				}catch(InputMismatchException e) {
					System.out.println("This choice is invalid. Enter a number between 1 to 3");
					status=1;
					sc.nextLine();
				}
				if(status==0) sc.nextLine();
			}while(status==1);
			switch(choice) {
			
			case 1: do {
						System.out.print("\nEnter the security key: ");
						//sc.nextLine();
					    securityKey=sc.nextLine();
					    if(securityKey.equals(a.securityKey)) {
						    System.out.println("\n\tWelcome, Admin!");
							a.displayAdminMenu();
					    }else {
					    	System.out.println("\nThis key is invalid.");
					    	System.out.println("\nDo you want to keep trying?\n1.Yes\n2.Exit");
					    	System.out.print("\nEnter your choice: ");
					    	trying = sc.nextInt();
					    	sc.nextLine();
					    }
					}while(!securityKey.equals(a.securityKey) && trying==1);
			
					break;

			case 2: System.out.println("\n\tDo you want to create new account or login into your existing account?\n");
					System.out.println("\t1. Create Account");
					System.out.println("\t2. Login");
					System.out.println("\t3. Exit");
					int flag=0;
					do {
						flag=0;
						try {
							System.out.print("\tEnter your choice: ");
							ch = sc.nextInt(); //sc.nextLine();
						}catch(InputMismatchException e) {
							System.out.println("\tThis choice is invalid. Enter a number between 1 to 3");
							flag=1;
							sc.nextLine();
						}
						if(flag==0) sc.nextLine();
					}while(flag==1);
					switch(ch) {
					case 1: acc.createAccount();
							break;
					case 2: acc.login();
							break;
					case 3: System.out.println("\n\tThank you.Visit again!");
							break;
					default: System.out.println("\tInvalid choice");		
					}
				   
				   break;
			case 3: System.out.println("\nThank you!");
					break;
			default: System.out.println("Invalid choice!");
			}
			
		}while(choice!=3);
	}
}


