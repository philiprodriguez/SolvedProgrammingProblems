import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*
 * 
 * @author Ahmad Mamdouh
 */
public class IntegerEstateAgent {
	static final int MAX = (int) (1e6 + 10);

	public static void main(String[] args) {
		InputReader r = new InputReader(System.in);
		int[] result = new int[MAX];
		for (int i = 2; i < MAX; i++) {
			int s = 0;
			for (int j = i; j < MAX; j++) {
				s += j;
				if (s >= MAX)
					break;
				result[s]++;
			}
		}
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int n = r.nextInt();
			if (n == 0)
				break;
			out.println(result[n]);
		}
		out.close();
	}

	static class InputReader {
		private BufferedReader reader;
		private StringTokenizer tokenizer;

		public InputReader(String file) throws FileNotFoundException {
			reader = new BufferedReader(new FileReader(file));
			tokenizer = null;
		}

		public InputReader(InputStream stream) {
			reader = new BufferedReader(new InputStreamReader(stream));
			tokenizer = null;
		}

		public String nextLine() {
			try {
				return reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		public String next() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return tokenizer.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}
}
