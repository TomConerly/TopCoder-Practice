import java.util.*;
import static java.lang.Math.*;

public class SRM392Hard {
	public static void p(Object... o){System.out.println(Arrays.deepToString(o));}
	public long findNext(String S) {
		long ans = Long.MAX_VALUE;
		int N = S.length();
		int[] dig = new int[N];
		for (int i = 0; i < N ;i++)
			dig[i] = S.charAt(i) - '0';
		
		int[] c = new int[10];
		for (int i = 0; i < N; i++)
			c[dig[i]]++;
		int m = 0;
		for (int i = 0; i < 10; i++)
			m = max(m, c[i]);
		boolean ok = true;
		for (int i = 0; i < 10; i++)
			ok &= c[i] == 0 || c[i] == m;
		if (ok)
			return Long.valueOf(S);
		
		for (int keep = 0; keep < N; keep++) {
			for (int next = dig[keep] + 1; next < 10; next++) {
				int[] count = new int[10];
				for (int i = 0; i < keep; i++)
					count[dig[i]]++;
				count[next]++;
				t:
				for (int times = 1; times <= 20; times++) {
					int extra = N - keep - 1;
					int[] need = new int[10];
					for (int i = 0; i < 10; i++) {
						if (count[i] == 0)
							continue;
						if (count[i] > times)
							continue t;
						need[i] = times - count[i];
						extra -= need[i];
					}
					for (int i = 0; i < 10; i++) {
						if (extra > 0 && count[i] == 0) {
							need[i] = times;
							extra -= times;
						}
					}
					if (extra != 0) {
						continue;
					}
					String a = S.substring(0, keep)+(""+next);

					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < need[i]; j++) {
							a += ""+i; 
						}
					}
					try {
					long l = Long.valueOf(a);
					ans = min(ans, l);
					} catch (Exception e) {}
				}
			}
		}
		return ans;
	}
}
