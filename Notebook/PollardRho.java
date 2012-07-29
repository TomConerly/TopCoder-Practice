/*
 * Not fast enough for primes up to 10^18.
 * http://en.wikipedia.org/wiki/Pollard's_rho_algorithm
 */

import java.util.*;
import static java.lang.Math.*;
import java.math.BigInteger;

public class PollardRho {
	Random R = new Random();
	ArrayList<Long> factor(long V) {
		ArrayList<Long> ans = new ArrayList<Long>();
		if (BigInteger.valueOf(V).isProbablePrime(20)) {
			ans.add(V);
		} else if (V != 1) {
			long f = rho(V);
			ans.addAll(factor(f));
			ans.addAll(factor(V/f));
		}
		return ans;
	}
	long f(long x, long c, long V) {
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
				x = f(x, c, V);
				y = f(f(y, c, V), c, V);
				d = gcd(abs(x-y), V);
			}
			if (d != V) {
				return d;
			}
		}
	}
	long randInt(long st, long end) {
		return st + (abs(R.nextLong()) % (end - st));
	}
	long gcd(long A, long B) {
		if (B == 0)
			return A;
		return gcd(B, A % B);
	}
}
