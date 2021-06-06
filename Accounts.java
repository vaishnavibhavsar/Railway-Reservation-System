package railways;
import java.util.*;

public class Accounts {
	LinkedList<User>userList=new LinkedList<>();
	Scanner sc=new Scanner(System.in);
	
	void createAccount() {
		int flag=0;
		do{
			flag=0;
			System.out.print("\n\tEnter the username you want to use for your account: ");
			String userName = sc.nextLine();
			for(User u : userList) {
				if(u.userName.equals(userName)) {
					System.out.println("\tAlready exists. Try a new username.");
					flag=1;
					break;
				}
			}
			if(flag==0){
				System.out.print("\n\tEnter the password to secure your account: ");
				String password = sc.nextLine();
				User u1 = new User(userName,password);
				userList.add(u1);
				System.out.println("\n\tAccount created successfully!!");
				u1.userMenu();
			}
		
		}while(flag==1);
		
	}

	public void login() {
		int flag=0, trying=0;
		do{
			System.out.print("\n\tEnter the userName: ");
			String userName=sc.nextLine();
			System.out.print("\n\tEnter the password: ");
			String password=sc.nextLine();
			User u1 = null;
			for(User u:userList) {
				if(u.userName.equals(userName) && u.password.equals(password) ){
					flag=1;
					u1=u;
					break;
				}
			}
			if(flag==1) {
				System.out.println("\n\tLogin Successful!!");
				u1.userMenu();
			}
			else {
				System.out.println("\tPlease enter proper credentials.\n");
				System.out.println("\tDo you want to keep trying?\n\t1.Yes\n\t2.Exit");
				System.out.print("\n\tEnter your choice: ");
		    	trying = sc.nextInt();
		    	sc.nextLine();
			}
		}while(flag==0 && trying==1);	
	}
}

