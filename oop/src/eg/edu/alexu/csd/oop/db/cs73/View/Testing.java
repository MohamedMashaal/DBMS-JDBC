package eg.edu.alexu.csd.oop.db.cs73.View;

public class Testing {
	public static void main(String [] args) {
		String x = "a>=b and b<=c";
		String z = x.replaceAll("(?<!<|>)=", " = ").replaceAll(">=", " >= ").replaceAll("<=", " <= ");
		System.out.println(z);
		String [] v = z.split("\\s+");
		for(String b : v)
			System.out.println(b);
	}
}
