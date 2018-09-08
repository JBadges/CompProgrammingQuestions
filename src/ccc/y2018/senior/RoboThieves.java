package ccc.y2018.senior;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoboThieves {

    static char[][] maze;
    static Tuple start;
    static ArrayList<Tuple> dots = new ArrayList<>();
    static ArrayList<Tuple> cameras = new ArrayList<>();
    static HashMap<Tuple, Integer> distMap;

    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] mazeSize = null;
        try {
            mazeSize = br.readLine().trim().split(" ");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int width = Integer.parseInt(mazeSize[0]);
        maze = new char[width][];
        for (int i = 0; i < width; i++) {
            String[] in = {};
            try {
                in = br.readLine().trim().split("");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            char[] a = new char[in.length];
            for (int j = 0; j < in.length; j++) {
                char c = in[j].charAt(0);
                if (c == 'S') {
                    start = new Tuple(i, j);
                }
                if (c == '.') {
                    dots.add(new Tuple(i, j));
                }
                if (c == 'C') {
                    cameras.add(new Tuple(i, j));
                }
                a[j] = c;
            }
            maze[i] = a;
        }
        dots.add(start);
        cameraUpdate();
        bfs();
        boolean cameraScam = maze[start.x - 1][start.y] == 'C' || maze[start.x + 1][start.y] == 'C'
                || maze[start.x][start.y - 1] == 'C' || maze[start.x][start.y + 1] == 'C';
        for (Tuple dot : dots) {
            if (!dot.equals(start)) {
                if (!cameraScam) {
                    System.out.println(distMap.get(dot) == Integer.MAX_VALUE ? -1 : distMap.get(dot));
                } else {
                    System.out.println(-1);
                }
            }
        }
    }

    public static void cameraUpdate() {
        for (Tuple t : cameras) {
            int i = t.x;
            int j = t.y;
            if (maze[i][j] == 'C') {
                int k = i - 1;
                int l = j;
                while ((k > 0 && k < maze.length && l > 0 && l < maze[k].length)
                        && (maze[k][l] != 'W' && maze[k][l] != 'M')) {
                    if (maze[k][l] != 'L' && maze[k][l] != 'U' && maze[k][l] != 'D' && maze[k][l] != 'R'
                            && maze[k][l] != 'S') {
                        maze[k][l] = 'M';
                    }
                    k--;
                }
                k = i + 1;
                l = j;
                while ((k > 0 && k < maze.length && l > 0 && l < maze[k].length)
                        && (maze[k][l] != 'W' && maze[k][l] != 'M')) {
                    if (maze[k][l] != 'L' && maze[k][l] != 'U' && maze[k][l] != 'D' && maze[k][l] != 'R'
                            && maze[k][l] != 'S') {
                        maze[k][l] = 'M';
                    }
                    k++;
                }
                k = i;
                l = j + 1;
                while ((k > 0 && k < maze.length && l > 0 && l < maze[k].length)
                        && (maze[k][l] != 'W' && maze[k][l] != 'M')) {
                    if (maze[k][l] != 'L' && maze[k][l] != 'U' && maze[k][l] != 'D' && maze[k][l] != 'R'
                            && maze[k][l] != 'S') {
                        maze[k][l] = 'M';
                    }
                    l++;
                }
                k = i;
                l = j - 1;
                while ((k > 0 && k < maze.length && l > 0 && l < maze[k].length)
                        && (maze[k][l] != 'W' && maze[k][l] != 'M')) {
                    if (maze[k][l] != 'L' && maze[k][l] != 'U' && maze[k][l] != 'D' && maze[k][l] != 'R'
                            && maze[k][l] != 'S') {
                        maze[k][l] = 'M';
                    }
                    l--;
                }
            }
        }
    }

    public static void bfs() {
        distMap = new HashMap<>();
        HashMap<Tuple, Boolean> flagMap = new HashMap<>();

        for (Tuple t : dots) {
            flagMap.put(t, false);
            distMap.put(t, Integer.MAX_VALUE);
        }
        Queue<Tuple> q = new LinkedList<>();
        q.add(start);
        distMap.put(start, 0);
        while (!q.isEmpty()) {
            Tuple v = q.poll();
            for (Tuple t : validMovesFrom(v)) {
                if (!flagMap.get(t)) {
                    flagMap.put(t, true);
                    distMap.put(t, distMap.get(v) + 1);
                    q.add(t);
                }
            }
        }
    }

    public static List<Tuple> validMovesFrom(Tuple t) {
        List<Tuple> moves = new ArrayList<>();
        Tuple up = t.up();
        Tuple down = t.down();
        Tuple left = t.left();
        Tuple right = t.right();
        List<Tuple> directions = new ArrayList<>(Arrays.asList(up, down, left, right));
        for (Tuple tu : directions) {
            if (tu.x < 0 || tu.x >= maze.length || tu.y < 0 || tu.y >= maze[tu.x].length) {
                continue;
            }
            int counter = 0;
            while (maze[tu.x][tu.y] == 'L' || maze[tu.x][tu.y] == 'R' || maze[tu.x][tu.y] == 'U'
                    || maze[tu.x][tu.y] == 'D') {
                if (counter > 100 * 100) {
                    break;
                }
                switch (maze[tu.x][tu.y]) {
                case 'L':
                    tu = tu.left();
                    break;
                case 'R':
                    tu = tu.right();
                    break;
                case 'D':
                    tu = tu.down();
                    break;
                case 'U':
                    tu = tu.up();
                    break;
                }
                counter++;
            }
            if (!(maze[tu.x][tu.y] == 'W' || maze[tu.x][tu.y] == 'M' || maze[tu.x][tu.y] == 'S' || counter >= 100)) {
                moves.add(tu);
            }
        }
        return moves;
    }
}

class Tuple {
    public int x;
    public int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tuple up() {
        return new Tuple(x - 1, y);
    }

    public Tuple down() {
        return new Tuple(x + 1, y);
    }

    public Tuple left() {
        return new Tuple(x, y - 1);
    }

    public Tuple right() {
        return new Tuple(x, y + 1);
    }

    @Override
    public String toString() {
        return "(x: " + x + ", y: " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        Tuple t = (Tuple) obj;
        return t.x == x && t.y == y;
    }

    @Override
    public int hashCode() {
        return x * x * x + y * y * y;
    }

}
