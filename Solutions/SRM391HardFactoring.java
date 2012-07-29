import java.util.*;
import static java.lang.Math.*;
import java.io.*;
import java.math.BigInteger;

public class SRM391HardFactoring {
	public static void p(Object... o){System.out.println(Arrays.deepToString(o));}

	public String[] transform(String[] in) {
		int N = in.length;
		long[] A = new long[N];
		for (int i = 0; i < N ;i++)
			A[i] = Long.valueOf(in[i]);

		HashMap<Long, Entry> hm = new HashMap<Long, Entry>();
		for (int i = 0; i < N ;i++) {
			ArrayList<Long> F = factor(A[i]);
			p(A[i], F);
			HashMap<Long, Integer> count = new HashMap<Long, Integer>();
			for (long f:F) {
				if (count.containsKey(f))
					count.put(f, count.get(f)+1);
				else
					count.put(f, 1);
			}
			for (long key : count.keySet()) {
				int value = count.get(key);
				Entry e = hm.get(key);
				if (e == null || value >= e.count) {
					hm.put(key, new Entry(i, value));
				}
			}
		}
		Arrays.fill(A, 1);
		for (long key : hm.keySet()) {
			Entry e = hm.get(key);
			for (int i = 0; i < e.count; i++)
				A[e.idx] *= key;
		}

		String[] out = new String[N];
		for (int i = 0; i < N ;i++)
			out[i] = ""+A[i];
		return out;
	}

	ArrayList<Long> factor(long V) {
		long t0 = System.currentTimeMillis();
		ArrayList<Long> ans = new ArrayList<Long>();
		if (BigInteger.valueOf(V).isProbablePrime(20)) {
			ans.add(V);
		} else if (V != 1) {
			long f = rho(V);
			ans.addAll(factor(f));
			ans.addAll(factor(V/f));
		}
		long t1 = System.currentTimeMillis();
		p(V, t1 - t0);
		return ans;
	}
	class Entry {
		int idx, count;
		public Entry(int idx, int count) {
			this.idx = idx;
			this.count = count;
		}
	}

	Random R = new Random();
	long f1(long x, long c, long V) {
		return (x*x + c) % V;
	}
	long rho(long V) {
		if (V % 2 == 0) {
			return 2;
		}
		while (true) {
			long x = randInt(1, V-1);
			long y = x;
			long d = 1;
			long c = randInt(1, V-1);
			while (d == 1) {
				x = f1(x, c, V);
				y = f1(f1(y, c, V), c, V);
				d = gcd(abs(x-y), V);
			}
			if (d != V) {
				return d;
			}
		}
	}
	
	private long randInt(long st, long end) {
		return st + (abs(R.nextLong()) % (end - st));
	}

	long gcd(long A, long B) {
		if (B == 0)
			return A;
		return gcd(B, A % B);
	}
}
