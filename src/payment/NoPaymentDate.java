package payment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import canteen.demo.Student;

public class NoPaymentDate {
	private int id;
	private DateObject noPaymentDate;
	private String description;
	
	public static ArrayList<NoPaymentDate> createNoPaymentDate(Calendar cal){
		ArrayList<NoPaymentDate> listNoPayment = new ArrayList();
		
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH) + 1;
		Integer year = null;
		System.out.println("Is it no payment day for EVERY year? ");
		Scanner input = new Scanner(System.in);
		int answer = input.nextInt();
		if (answer==0) {
			year =  cal.get(Calendar.YEAR);
		}
		
		DateObject date = new DateObject(dayOfMonth, month, year);
		
		System.out.println("Why is it no payment date? ");
		String description = input.next();
		
		NoPaymentDate noPaymentDate = new NoPaymentDate(date, description);
		listNoPayment.add(noPaymentDate);
		return listNoPayment;
		
	}
	
	public NoPaymentDate(DateObject noPaymentDate, String description) {
		this.noPaymentDate = noPaymentDate;
		this.description = description;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DateObject getNoPaymentDate() {
		return noPaymentDate;
	}

	public void setNoPaymentDate(DateObject noPaymentDate) {
		this.noPaymentDate = noPaymentDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
 
	
}
