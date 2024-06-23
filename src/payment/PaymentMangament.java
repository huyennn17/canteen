package payment;

import java.util.ArrayList;
import java.util.Calendar;

public class PaymentMangament {
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		ArrayList <NoPaymentDate> listNoPaymentDate = NoPaymentDate.createNoPaymentDate(cal);
		System.out.println(listNoPaymentDate.get(0).getNoPaymentDate().getDay() + "-" +
				listNoPaymentDate.get(0).getNoPaymentDate().getMonth());
	}
//cho nhập ngày tháng năm, tạo object cal
	//taoj cac ngày kh tính tiền sẵn cho các năm: lễ vn
}
