package canteen.demo;

import java.util.ArrayList;
import java.util.Scanner;

public class Comment {
	public static void generateComment() {
		Scanner data = new Scanner(System.in);

		ArrayList<String> ListComment = new ArrayList<String>();

		System.out.println("Feedback? 1 = yes; 0 = no");
		int i = data.nextInt();

		Scanner input = new Scanner(System.in);

		if (i == 0) {
			System.out.println("No comment for this menu");
		}

		if (i == 1) {
			while (i == 1) {
				System.out.println("Enter your feedback");
				String comment = input.nextLine();
				ListComment.add(comment);
				System.out.println("Any other feedback? 1 = yes; 0 = no ");
				i = data.nextInt();
			}
			System.out.println("All feedback: ");
			for (String x : ListComment) {
				System.out.println(x);
			}
		}

	}

}
