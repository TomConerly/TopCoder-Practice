import java.util.*;
import static java.lang.Math.*;
import java.io.*;

public class SRM391Hard {
	public static void p(Object... o){System.out.println(Arrays.deepToString(o));}
	
	long[] A;
	
	public String[] transform(String[] in) {
		int N = in.length;
		A = new long[N];
		for (int i = 0; i < N ;i++)
			A[i] = Long.valueOf(in[i]);
		
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				reduce(i, j);
				reduce(j, i);
				A[i] /= gcd(A[i], A[j]);
			}
		}
		
		String[] out = new String[N];
		for (int i = 0; i < N ;i++)
			out[i] = ""+A[i];
		return out;
	}
	
	void reduce(int i, int j) {
		long X = A[i];
		long Y = A[j];
		while (true) {
			long g1 = gcd(X, Y);
			long g2 = gcd(Y / g1, X);
			if (g2 == 1)
				break;
			X /= g2;
		}
		A[i] = X;
	}

	long gcd(long A, long B) {
		if (B == 0)
			return A;
		return gcd(B, A % B);
	}

}
