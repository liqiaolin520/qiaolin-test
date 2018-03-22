
public class Test {
	public static void main(String[] args) {
		long sum = 2;
		for (int i = 1; i < 31; i++) {
			 sum = sum * 2;
		}
		System.out.println(sum);
		System.out.println(Integer.MAX_VALUE);
		
//		int a = 2147483648;
		String val = "2147483648999999999";
		Double d = Double.valueOf(val);
		System.out.println("此时值-> " + val);
		System.out.println("经过 intValue " + d.intValue());
		
		System.out.println((Double.MAX_VALUE+"").length());
		System.out.println((Integer.MAX_VALUE+"").length());
		
		
	}
}
