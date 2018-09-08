package ccc.y2018.senior;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class RoboThieves {

    static char[][] maze;
    static int[][] minMaze;
    static Tuple start;
    static Stack<Tuple> smallestTuple;

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
        int height = Integer.parseInt(mazeSize[1]);
        maze = new char[width][];
        minMaze = new int[width][height];
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
                if (in[j].charAt(0) == 'S') {
                    start = new Tuple(i, j);
                }
                a[j] = in[j].charAt(0);
            }
            maze[i] = a;
        }
        cameraUpdate();

        boolean cameraScam = maze[start.x - 1][start.y] == 'C' || maze[start.x + 1][start.y] == 'C'
                || maze[start.x][start.y - 1] == 'C' || maze[start.x][start.y + 1] == 'C';
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == '.' || maze[i][j] == 'M') {
                    Stack<Tuple> x = new Stack<Tuple>();
                    x.push(start);
                    if (cameraScam) {
                        System.out.println(-1);
                    } else {
                        int min = minimumMoveToLocation(i, j, x);
                        updateDP();
                        System.out.println(min == -1 ? min : min - 1);
                    }
                }
            }
        }
    }

    public static void cameraUpdate() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
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
    }

    public static int minimumMoveToLocation(int row, int col, Stack<Tuple> stack) {
        smallestTuple = new Stack<>();
        return backwards(start.x, start.y, row, col, stack);
    }

    public static int backwards(int cRow, int cCol, int gRow, int gCol, Stack<Tuple> stack) {
        if (minMaze[gRow][gCol] != 0) {
            return minMaze[gRow][gCol];
        }
        
        if (cRow < 0 || cRow >= maze.length || cCol < 0 || cCol >= maze[cRow].length || maze[cRow][cCol] == 'W' || maze[cRow][cCol] == 'M') {
            stack.pop();
            return -1;
        }
        
        if (maze[cRow][cCol] == 'U') {
            cRow--;
        }
        if (maze[cRow][cCol] == 'D') {
            cRow++;
        }
        if (maze[cRow][cCol] == 'L') {
            cCol--;
        }
        if (maze[cRow][cCol] == 'R') {
            cCol++;
        }
        if (cRow == gRow && cCol == gCol) {
            int x = stack.size();
            stack.pop();
            if(smallestTuple.size() == 0 || x < smallestTuple.size()-1) {
                smallestTuple = (Stack) stack.clone();
                smallestTuple.push(new Tuple(gRow, gCol));
            }
            return x;
        }
        
        int min = Integer.MAX_VALUE;
        if (!stack.contains(new Tuple(cRow + 1, cCol))) {
            stack.push(new Tuple(cRow + 1, cCol));
            int tempMin = backwards(cRow + 1, cCol, gRow, gCol, stack);
            min = Math.min(min, tempMin == -1 ? Integer.MAX_VALUE : tempMin);
        }
        if (!stack.contains(new Tuple(cRow - 1, cCol))) {
            stack.push(new Tuple(cRow - 1, cCol));
            int tempMin = backwards(cRow - 1, cCol, gRow, gCol, stack);
            min = Math.min(min, tempMin == -1 ? Integer.MAX_VALUE : tempMin);
        }
        if (!stack.contains(new Tuple(cRow, cCol + 1))) {
            stack.push(new Tuple(cRow, cCol + 1));
            int tempMin = backwards(cRow, cCol + 1, gRow, gCol, stack);
            min = Math.min(min, tempMin == -1 ? Integer.MAX_VALUE : tempMin);
        }
        if (!stack.contains(new Tuple(cRow, cCol - 1))) {
            stack.push(new Tuple(cRow, cCol - 1));
            int tempMin = backwards(cRow, cCol - 1, gRow, gCol, stack);
            min = Math.min(min, tempMin == -1 ? Integer.MAX_VALUE : tempMin);
        }
        if (min == Integer.MAX_VALUE) {
            min = -1;
        }
        stack.pop();
        return min;
    }

    public static void updateDP() {
       while(!smallestTuple.isEmpty()) {
           Tuple t = smallestTuple.pop();
           if(smallestTuple.size() != 1) {
               minMaze[t.x][t.y] = smallestTuple.size();
           }
       }
    }
}

class Tuple {
    public int x;
    public int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(x: " + x + ", y: " + y + ")";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Tuple)) {
            return false;
        }
        Tuple t = (Tuple) obj;
        return t.x == x && t.y == y;
    }
}
