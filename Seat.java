package railways;

public class Seat extends Passenger{
	int seatNo;
	Boolean window;
	Seat(String passengerName, int age, String gender, int bookingID, int seatNo, boolean window){
		super(passengerName, age, gender, bookingID);
		this.seatNo=seatNo;
		this.window=window;
	}
}
