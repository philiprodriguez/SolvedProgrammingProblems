import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TwoKnights_artur {

  static char[][] lo = { "qwertyuiop".toCharArray(), "asdfghjkl;".toCharArray(), "zxcvbnm,./".toCharArray(), "++      ++".toCharArray() };
  static char[][] hi = { "QWERTYUIOP".toCharArray(), "ASDFGHJKL:".toCharArray(), "ZXCVBNM<>?".toCharArray(), "++      ++".toCharArray() };

  static int h = lo.length, w = lo[0].length;

  static char[] a;
  static Boolean[][][][][] memo = new Boolean[101][4][4][10][10];
  static int[] dx = { -2, -2, -1, -1, 1, 1, 2, 2 };
  static int[] dy = { -1, 1, -2, 2, -2, 2, -1, 1 };

  public static void main(String[] args) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    Point left = new Point(h - 1, 0), right = new Point(h - 1, w - 1);
    for (String s = in.readLine(); !"*".equals(s); s = in.readLine()) {
      for (Boolean[][][][] pos : memo)
        for (Boolean[][][] i : pos)
          for (Boolean[][] j : i)
            for (Boolean[] k : j)
              Arrays.fill(k, null);

      a = s.toCharArray();
      System.out.println(dfs(0, left, right) ? 1 : 0);
    }
  }

  static boolean dfs(int i, Point p, Point q) {
    if (i > a.length)
      return true;
    if (memo[i][p.x][q.x][p.y][q.y] != null)
      return memo[i][p.x][q.x][p.y][q.y];

    if (i > 0) {
      boolean ok = !(p.x == q.x && p.y == q.y);
      ok &= lo[p.x][p.y] == '+' || a[i - 1] == (lo[q.x][q.y] == '+' ? hi[p.x][p.y] : lo[p.x][p.y]);
      if (!ok)
        return memo[i][p.x][q.x][p.y][q.y] = false;
    }

    return memo[i][p.x][q.x][p.y][q.y] = (advance(i, q, p) || advance(i, p, q));
  }

  static boolean advance(int i, Point p, Point q) {
    Point t = new Point();
    for (int k = 0; k < dx.length; k++) {
      t.x = q.x + dx[k];
      if (0 <= t.x && t.x < h) {
        t.y = q.y + dy[k];
        if (0 <= t.y && t.y < w)
          if (dfs(i + (lo[t.x][t.y] == '+' ? 0 : 1), t, p))
            return true;
      }
    }
    return false;
  }
}
