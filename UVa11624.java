import java.util.*;

public class Main {
	Scanner cin = new Scanner(System.in);

	static final int M = 1000 + 25;
	static final int INF = 10000000 + 25;

	static final int[] dirx = new int[] { 0, 1, 0, -1 };
	static final int[] diry = new int[] { -1, 0, 1, 0 };

	int R, C;
	int X, Y;

	char[][] map = new char[M][M];
	boolean[][] used = new boolean[M][M];

	int[][] fireTime = new int[M][M];

	Node[] que = new Node[INF];
	int head, tail;

	void dfs_init(int x, int y, int sum) {
		fireTime[x][y] = sum;
		for (int i = 0; i < 4; i++) {
			int tx = x + dirx[i];
			int ty = y + diry[i];
			if (tx >= 1 && tx <= R && ty >= 1 && ty <= C && map[tx][ty] != '#'
					&& sum + 1 < fireTime[tx][ty]) {
				dfs_init(tx, ty, sum + 1);
			}
		}
	}

	void init() {
		for (int i = 1; i <= R; i++)
			for (int j = 1; j <= C; j++)
				fireTime[i][j] = INF;
		for (int i = 1; i <= R; i++)
			for (int j = 1; j <= C; j++)
				if (map[i][j] == 'F') {
					dfs_init(i, j, 0);
				}
	}

	int bfs() {
		if (X == 1 || X == R || Y == 1 || Y == C)
			return 1;
		for (int i = 1; i <= R; i++)
			for (int j = 1; j <= C; j++)
				used[i][j] = false;
		head = tail = 0;
		que[tail++] = new Node(X, Y, 0);
		used[X][Y] = true;
		tail %= INF;
		while (head != tail) {
			Node pre = que[head++];
			head %= INF;
			for (int i = 0; i < 4; i++) {
				int tx = pre.x + dirx[i];
				int ty = pre.y + diry[i];
				Node now = new Node(tx, ty, pre.step + 1);
				if (tx >= 1 && tx <= R && ty >= 1 && ty <= C
						&& map[tx][ty] != '#' && !used[tx][ty]) {
					if (now.step >= fireTime[tx][ty])
						continue;
					if (tx == 1 || tx == R || ty == 1 || ty == C)
						return now.step + 1;
					used[tx][ty] = true;
					que[tail++] = now;
					tail %= INF;

				}
			}
		}
		return -1;
	}

	Main() {
		int N = cin.nextInt();
		while (N-- > 0) {
			R = cin.nextInt();
			C = cin.nextInt();
			char[] tmpStr = new char[M];
			for (int i = 1; i <= R; i++) {
				tmpStr = cin.next().toCharArray();
				for (int j = 1; j <= C; j++) {
					map[i][j] = tmpStr[j - 1];
					if (map[i][j] == 'J') {
						X = i;
						Y = j;
					}
				}
			}
			init();
			int ans = bfs();
			if (ans == -1)
				System.out.println("IMPOSSIBLE");
			else
				System.out.println(ans);
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}

class Node {
	int x, y;
	int step;

	Node() {
	}

	Node(int x, int y, int step) {
		this.x = x;
		this.y = y;
		this.step = step;
	}
}
