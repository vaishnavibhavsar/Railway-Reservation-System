package railways;
import java.util.*;
public class Train {
		int trainNo;
		String trainName;
		boolean ac;
		boolean sleeperCoach;
		String date;//validation
		String Source;
		String Destination;
		int maxSeat;
		int maxWait;
		String departureTime;
		String arrivalTime;
		int price;
		boolean[] seatList = new boolean[maxSeat]; // by default initialized to false, so if seatList[i]==false=>seat no. i+1 is vacant (not occupied).
		static int bookingCount=0;
		LinkedList<Seat> seatedPassengers = new LinkedList<>();
		Queue<Passenger> waiting = new LinkedList<>(); 
		//LocalDate d = LocalDate.parse(date);

		Train(int trainNo,String trainName,	boolean ac,	boolean sleeperCoach,String date,String Source,	String Destination,int maxSeat,int maxWait, String departure, String arrival, int price){
			this.trainNo=trainNo;
		    this.trainName=trainName;
		    this.ac=ac;
		    this.sleeperCoach=sleeperCoach;
		    this.date=date;
		    this.Source=Source;
		    this.Destination=Destination;
		    this.maxSeat=maxSeat;
		    this.maxWait=maxWait;
		    this.departureTime = departure;
		    this.arrivalTime = arrival;
		    this.price = price;
		    this.seatList = new boolean[maxSeat];
		}
		
		void bookTicket(String name, String gender, int age) {
			int currBookingID=0;
			if(seatedPassengers.size()==maxSeat) {
					currBookingID = ++bookingCount;
					Passenger p1=new Passenger(name,age,gender.toUpperCase(),currBookingID);
					waiting.add(p1);
					System.out.println("\n\t\tYou have been added to waiting list.");
					displayTicket(currBookingID);	
			}
			else {
				currBookingID = ++bookingCount;
				int seatNo;
				int i=0;
				for(i=0;i<maxSeat;i++) {
					if(!seatList[i]) {
						break;
					}
				}
				seatNo = i+1;
				seatList[i] = true;
				Seat s1=new Seat(name, age, gender.toUpperCase(), currBookingID, seatNo, false);
				seatedPassengers.add(s1);
				System.out.println("\n\t\tBooking confirmed. Happy Journey!");
				displayTicket(currBookingID);
			}
		}
		
		void cancel(int bookingID) {
			int i;
			int flag = 0;
			Seat s;
			int seatNo;
			Passenger lucky;
			for(i=0;i<seatedPassengers.size();i++) {
				s = seatedPassengers.get(i);
				if(s.bookingID==bookingID) {
					seatNo = seatedPassengers.get(i).seatNo;
					seatedPassengers.remove(i);
					flag=1;
					System.out.printf("\n\t\tYour booking has been cancelled. You will receive a refund of Rs. %.2f\n",price*0.7);
					seatList[seatNo-1] = false;
					if(!waiting.isEmpty()) {
						lucky = waiting.poll();
						Seat newSeat = new Seat(lucky.passengerName, lucky.age, lucky.gender, lucky.bookingID, seatNo, true);
						seatedPassengers.add(newSeat);
						seatList[seatNo-1] = true;
					}
				}
			}		
			for(Passenger p : waiting) {
				if(p.bookingID==bookingID) {
					waiting.remove(p);
					flag=1;
					System.out.printf("\n\t\tYour booking has been cancelled. You will receive a refund of Rs. %.2f\n",price*0.7);
				}
			}
			if(flag==0) {
				System.out.println("\n\t\tNo such booking found.");
			}
		}
		
		void displayStatus(int bookingID) {
			int i=0;
			int flag = 0;
			for(Passenger p : waiting) {
				i++;
				if(p.bookingID==bookingID) {
					System.out.println("\n\t\tYour position in waiting Queue is "+i);
					flag=1;
					break;
				}
			}
			if(flag==0) {
				System.out.println("\n\t\tYour booking is confirmed.");
			}
			displayTicket(bookingID);
		}
		
		void displayTicket(int bookingID) {
			System.out.println("\n\t\t============================================");//44
			System.out.format("\t\t||Train No          : %20d||\n",trainNo);
			System.out.format("\t\t||Train Name        : %20s||\n",trainName);
			boolean inSeated = false;
			for(Seat s : seatedPassengers) {
				if(s.bookingID==bookingID) {
					inSeated = true;
					System.out.format("\t\t||Booking ID        : %20d||\n",s.bookingID);
					System.out.format("\t\t||Seat No.          : %20d||\n",s.seatNo);
					System.out.format("\t\t||Passenger Name    : %20s||\n",s.passengerName);
					System.out.format("\t\t||Age               : %20d||\n",s.age);
					System.out.format("\t\t||Gender            : %20s||\n",s.gender);
					break;
				}
			}
			if(!inSeated) {
				for(Passenger p : waiting) {
					if(p.bookingID==bookingID) {
						System.out.format("\t\t||Booking ID        : %20d||\n",p.bookingID);
						System.out.format("\t\t||Passenger Name    : %20s||\n",p.passengerName);
						System.out.format("\t\t||Age               : %20d||\n",p.age);
						System.out.format("\t\t||Gender            : %20s||\n",p.gender);	
						break;
					}
				}
			}
			System.out.format("\t\t||Source            : %20s||\n",Source);
			System.out.format("\t\t||Destination       : %20s||\n",Destination);
			System.out.format("\t\t||Date of journey   : %20s||\n",date);
			System.out.format("\t\t||Arrival Time      : %20s||\n",arrivalTime);
			System.out.format("\t\t||Departure Time    : %20s||\n",departureTime);
			System.out.format("\t\t||Ticket price      : %20s||\n",price);
			System.out.println("\t\t============================================");
		}
}
