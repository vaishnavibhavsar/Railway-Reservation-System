package railways;
import java.util.*;
public class User {
	double userID;
	String userName;
	String password;
	LinkedList<Booking> bookingList = new LinkedList<Booking>();
	User(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	Scanner sc = new Scanner(System.in);
	public void displayAvailableTrains(String source, String destination, String date) {
		int flag=0;
		int ch;
		//Train t1 = null;
		ArrayList<Integer> availableTrains = new ArrayList<Integer>();
		for(Train t: Admin.trainList) {
			if((t.Source).equalsIgnoreCase(source)&&(t.Destination).equalsIgnoreCase(destination)&&(t.date).equalsIgnoreCase(date)) {
				if(flag==0) {
					System.out.println("\n\t\tFollowing are the details of all available trains:");
					System.out.println("\t\t_________________________________________________________________________________________________________________________");//85
					System.out.println("\t\t|NO.|        NAME        |   DATE   |     SOURCE    |  DESTINATION  |DEPARTURE|ARRIVAL|PRICE|  AC  |SLEEPER|SEATS|WAITING|");
					System.out.println("\t\t|___|____________________|__________|_______________|_______________|_________|_______|_____|______|_______|_____|_______|");
				}
				flag=1;
				String a = t.ac ? "Yes":"No";
				String sleep = t.sleeperCoach ? "Yes":"No";
				availableTrains.add(t.trainNo);
				System.out.format("\t\t|%3d|%20s|%10s|%15s|%15s|%9s|%7s|%5d|%6s|%7s|%5d|%7d|\n",t.trainNo,t.trainName,t.date,t.Source,t.Destination,t.departureTime,t.arrivalTime,t.price,a,sleep,t.maxSeat-(t.seatedPassengers).size(),t.maxWait-(t.waiting).size());
			}
		}
		if(flag==1) {
			System.out.println("\t\t|___|____________________|__________|_______________|_______________|_________|_______|_____|______|_______|_____|_______|");
			System.out.println("\n\t\tDo you want to continue booking?\n\t\t1.Yes\n\t\t2.Exit ");
			System.out.print("\t\tEnter your choice: ");
			ch = sc.nextInt(); sc.nextLine();
			if(ch==1){
				System.out.print("\n\t\tEnter train number which you want to book: ");
				int n = sc.nextInt(); sc.nextLine();
				if(!availableTrains.contains(n)) {
					System.out.println("\n\t\tInvalid train number. Try again.");
					return;
				}
				int i=0;
				for(i=0;i<(Admin.trainList).size();i++) {
					if(Admin.trainList.get(i).trainNo==n) {
						break;
					}
				}
				if(Admin.trainList.get(i).maxSeat-(Admin.trainList.get(i).seatedPassengers).size()==0 && Admin.trainList.get(i).maxWait-(Admin.trainList.get(i).waiting).size()==0) {
					System.out.println("\n\t\tThis train does not have any seats available.");
					System.out.println("\t\tSorry for the incovenience.");
					return;
				}
				System.out.print("\n\t\tEnter Number of tickets you want to book: ");
				int tickets = sc.nextInt();sc.nextLine();
				boolean toBook = false;
				int seats = Admin.trainList.get(i).maxSeat-(Admin.trainList.get(i).seatedPassengers).size();
				int wait = Admin.trainList.get(i).maxWait-(Admin.trainList.get(i).waiting).size();
				String choice =null;
				if(tickets<=seats) {
					System.out.println("\n\t\tTickets for "+tickets+" passengers are available.");
					toBook=true;
				}
				else if(tickets<=wait+seats) {
					if(seats==0) {
						System.out.println("\n\t\tNo confirm tickets in this train are available.All your "+tickets+" tickets will be booked in waiting.");
						System.out.print("\n\t\tDo you want to continue? (Y/N): ");
						choice=sc.nextLine();
					}
					else {
						System.out.println("\n\t\tYou will get "+seats+" tickets as confirmed and remaining "+(tickets-seats)+" tickets will be added to waiting.");
						System.out.print("\n\t\tDo you want to continue? (Y/N): ");
						choice=sc.nextLine();
					}
					toBook = choice.equals("Y")||choice.equals("y") ? true:false;
				}
				else { //tickets>wait+seats;
					System.out.println("\n\t\tThis train does not have "+tickets+" tickets available.");
					if(seats==0) {
						System.out.println("\t\tYou will get only "+wait+" tickets in waiting.");
						System.out.print("\n\t\tDo you want to continue? (Y/N): ");
						choice = sc.nextLine();
					}
					else {
						System.out.print("\t\tYou will get "+seats+" tickets as confirmed and "+wait+" tickets in waiting.");
						System.out.println("\n\t\tDo you want to continue? (Y/N): ");
						choice = sc.nextLine();
					}
					toBook = choice.equals("Y")||choice.equals("y") ? true:false;
					tickets=wait+seats;
				}
				if(toBook) {
					List<Passenger> yourList = new LinkedList<>();
					System.out.println("\n\t\tPlease enter passenger details respectively:");
					for(int t=0;t<tickets;t++) {
						System.out.println("\n\t\tPassenger #"+(t+1));
						System.out.print("\n\t\tName of passenger: ");
						String name = sc.nextLine();
						String gender;
						do {
							System.out.print("\n\t\tGender(M/F): ");
							gender = sc.nextLine();
							if(!gender.equalsIgnoreCase("M")&&!gender.equalsIgnoreCase("F")) {
								System.out.println("\t\tInvalid Input!");								
							}
						}while(!gender.equalsIgnoreCase("M")&&!gender.equalsIgnoreCase("F"));
						System.out.print("\n\t\tAge: ");
						int age = sc.nextInt(); sc.nextLine();
						yourList.add(new Passenger(name,age,gender,0));
					}
					System.out.println("\n\t\tYour ticket details are as follows:");
					int t=1;
					for(Passenger p : yourList) {
						System.out.println("\n\t\tPassenger #"+t);
						Admin.trainList.get(i).bookTicket(p.passengerName, p.gender, p.age);
						int id = Train.bookingCount;
						Booking b = new Booking(n, id);
						bookingList.add(b);
						t++;
					}
					
				}
				else {
					System.out.println("\n\t\tSorry for the incovenience.");
				}
			}
		}
		else if(flag==0) {
			System.out.println("\n\t\tNo trains available. :(");
		}
	}
	public void userBook() {
		//sc.nextLine();
		System.out.print("\n\t\tEnter source: ");
		String source = sc.nextLine();
		System.out.print("\n\t\tEnter destination: ");
		String destination = sc.nextLine();
		Validation v=new Validation();
		boolean isValid=false; String date;//int status=0;
		do {
		System.out.print("\n\t\tEnter date of journey in dd/mm/yyyy format only: ");
			date = sc.nextLine();
			isValid=v.dateValidation(date);
			if(!isValid) {
				System.out.println("\t\tThe date format is invalid.");
			}
		}while(!isValid);
		
		displayAvailableTrains(source, destination, date);
	}
	public void userCancel() {
		System.out.print("\n\t\tEnter your booking ID: ");
		int id = sc.nextInt();sc.nextLine();
		int trainNo = 0;
		int flag=0;
		Booking b1=null;
		for(Booking b : bookingList) {
			if(b.bookingID==id) {
				trainNo=b.trainNo;
				b1=b;
				//bookingList.remove(b);
				break;
			}
		}
		for(Train t2: Admin.removedTrains) {
			if(t2.trainNo==trainNo) {
				System.out.println("\n\t\tOops! This train is cancelled. You will receive Rs."+t2.price+" refund within 5 days.");
				flag=1;
				break;
			}
		}
		if(flag==0 && trainNo!=0) {
			bookingList.remove(b1);
			for(Train t : Admin.trainList) {
				if(t.trainNo==trainNo) {
					t.cancel(id);
					break;
				}
			}

		}
		if(trainNo==0) {
			System.out.println("\n\t\tNo such booking found");
		}
	}
	
	public void userStatus() {
		System.out.print("\n\t\tEnter your booking ID: ");
		int id = sc.nextInt();sc.nextLine();
		int trainNo = 0;
		for(Booking b : bookingList) {
			if(b.bookingID==id) {
				trainNo=b.trainNo;
				break;
			}
		}
		if(trainNo==0) {
			System.out.println("\n\t\tNo such booking found.");
		}
		for(Train t2: Admin.removedTrains) {
			if(t2.trainNo==trainNo) {
				System.out.println("\n\t\tOops! This train is cancelled. You will receive refund of Rs."+t2.price+" within 5 days.");
				//break;
				return;
			}
		}
		//Train t =null;
		
		for(Train t1 : Admin.trainList) {
			if(t1.trainNo==trainNo) {
				//t=t1;
				t1.displayStatus(id);
				break;
			}
		}
		//if(t==null) System.out.println("\n\t\tOops! This train is cancelled.");

		//if(t==null) System.out.println("\n\t\tOops! This train is cancelled.");
	}
	
	public void userMenu() {
		int choice=0;
		System.out.println("\n\t\tWelcome to your account");
		do {
			System.out.println("\n\t\t****MENU****\n");
			System.out.println("\t\t1. Book ticket");
			System.out.println("\t\t2. Cancel ticket");
			System.out.println("\t\t3. Display status of ticket");
			System.out.println("\t\t4. Exit");
			int status=0;
			do {
				status=0;
				try {
					System.out.print("\t\tEnter your choice: ");
					choice = sc.nextInt(); //sc.nextLine();
				}catch(InputMismatchException e) {
					System.out.println("\t\tThis choice is invalid. Please Enter number between 1 to 4.");
					status=1;
					sc.nextLine();
				}
				if(status==0) sc.nextLine();
			}while(status==1);
			switch(choice) {
			case 1:
				userBook();
				break;
			case 2:
				userCancel();
				break;
			case 3:
				userStatus();
				break;
			case 4:
				System.out.println("\n\t\tThank you for using our services!!");
				break;
			default:
				System.out.println("\n\t\tInvalid Choice. Please try again");
			}
		}while(choice!=4);
	}

}
