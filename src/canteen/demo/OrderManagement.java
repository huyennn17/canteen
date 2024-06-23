package canteen.demo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class OrderManagement {

	public static void main(String[] args) {
		try

		{
			List<Snacks> listSnacks = Snacks.viewList();
			List<Appetizer> listAppetizer = Appetizer.viewList();
			List<Proteins> listProtein = Proteins.viewList();
			List<Carb> listCarb = Carb.viewList();

			
			LocalDate date = LocalDate.now();
			int snackId = 0;
			
			for (Snacks snacks1 : listSnacks) {
			
				if (snacks1.getName().equalsIgnoreCase("Cake/Pastries")) {
					 snackId = snacks1.getId();
				}
				
			}
			Random random = new Random();
			int random1 = random.nextInt(9)+ 1;
			int random2 = random.nextInt(20)+ 1;
			
			List<Integer> listPork = new ArrayList();
			for (Proteins protein1 : listProtein) {
				
				if (protein1.getType().equalsIgnoreCase("Pork")) {
					 listPork.add(protein1.getId());
				}
			}
				int random3 = random.nextInt(listPork.size());
				int x = listPork.get(random3);

				int carbId = 0;
				for (Carb carb1 : listCarb) {
					
					if (carb1.getName().equalsIgnoreCase("Steamed rice")) {
						 carbId = carb1.getId();
					}
				}
				int random4 = random.nextInt(2)+1;
					
			for (int i = 0; i < 1; i++) {
				switch (date.getDayOfWeek()) {
					case SUNDAY:
						DailyMenu menuSunday = new DailyMenu();
						menuSunday.createMenu(date, snackId, random1,random2, x, carbId, random4);
						break;
				}
					
				}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error");

		}


	}
}
