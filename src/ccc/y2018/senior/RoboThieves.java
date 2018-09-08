package ccc.y2018.senior;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class RoboThieves {

    static char[][] maze;
    
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
        for(int i = 0; i < width; i++) {
            String[] in = {};
            try {
                in = br.readLine().trim().split("");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            char[] a = new char[in.length];
            for(int j = 0; j < in.length; j++) {
                a[j] = in[j].charAt(0);
            }
            maze[i] = a;
        }
        cameraUpdate();
//        for (int i = 0; i < maze.length; i++) {
//            for (int j = 0; j < maze[i].length; j++) {
//                if (maze[i][j] == '.' || maze[i][j] == 'M') {
//                    Stack x = new Stack<Tuple>();
//                    x.push(new Tuple(i, j));
//                    int min = minimumMoveToLocation(i, j, x);
//                    System.out.println(min == -1 ? min : min -1);
//                }
//            }
//        }
        int i = 2;
        int j = 1;
        Stack x = new Stack<Tuple>();
        x.push(new Tuple(i, j));
        int min = minimumMoveToLocation(i, j, x);
        System.out.println(min == -1 ? min : min -1);

    }

    public static void cameraUpdate() {
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[i].length; j++) {
                if(maze[i][j] == 'C') {
                    int k = i-1;
                    int l = j;
                    while((k > 0 && k < maze.length && l > 0 && l < maze[k].length) && (maze[k][l] != 'W' && maze[k][l] != 'M')) {
                        if(maze[k][l] != 'L' &&  maze[k][l] != 'U' &&  maze[k][l] != 'D' &&  maze[k][l] != 'R' && maze[k][l] != 'S') {
                            maze[k][l] = 'M';
                        }
                        k--;
                    }
                    k = i+1;
                    l = j;
                    while((k > 0 && k < maze.length && l > 0 && l < maze[k].length) && (maze[k][l] != 'W' && maze[k][l] != 'M')) {
                        if(maze[k][l] != 'L' &&  maze[k][l] != 'U' &&  maze[k][l] != 'D' &&  maze[k][l] != 'R' && maze[k][l] != 'S') {
                            maze[k][l] = 'M';
                        }
                        k++;
                    }
                    k = i;
                    l = j+1;
                    while((k > 0 && k < maze.length && l > 0 && l < maze[k].length) && (maze[k][l] != 'W' && maze[k][l] != 'M')) {
                        if(maze[k][l] != 'L' &&  maze[k][l] != 'U' &&  maze[k][l] != 'D' &&  maze[k][l] != 'R' && maze[k][l] != 'S') {
                            maze[k][l] = 'M';
                        }
                        l++;
                    }
                    k = i;
                    l = j-1;
                    while((k > 0 && k < maze.length && l > 0 && l < maze[k].length) && (maze[k][l] != 'W' && maze[k][l] != 'M')) {
                        if(maze[k][l] != 'L' &&  maze[k][l] != 'U' &&  maze[k][l] != 'D' &&  maze[k][l] != 'R' && maze[k][l] != 'S') {
                            maze[k][l] = 'M';
                        }
                        l--;
                    }
                }
            }
        }
    }

    public static int minimumMoveToLocation(int row, int col, Stack<Tuple> stack) {
        System.out.println(stack);
        if(row < 0 || row >= maze.length || col < 0 || col >= maze[row].length || maze[row][col] == 'W' || maze[row][col] == 'M') {
            stack.pop();
            return -1;
        }
        
        // Does the spot make me do something
        if (maze[row][col] == 'U') {
            row++;
        }
        if (maze[row][col] == 'D') {
            row--;
        }
        if (maze[row][col] == 'L') {
            col++;
        }
        if (maze[row][col] == 'R') {
            col--;
        }
        if(maze[row][col] == 'S') {
            int x = stack.size();
            stack.pop();
            return x;
        }
        
        int min = Integer.MAX_VALUE;
        if (!stack.contains(new Tuple(row + 1, col))) {
            stack.push(new Tuple(row + 1, col));
            int tempMin = minimumMoveToLocation(row + 1, col, stack);
            min = Math.min(min, tempMin == -1 ? Integer.MAX_VALUE : tempMin);
        }
        if (!stack.contains(new Tuple(row - 1, col))) {
            stack.push(new Tuple(row - 1, col));
            int tempMin = minimumMoveToLocation(row - 1, col, stack);
            min = Math.min(min, tempMin == -1 ? Integer.MAX_VALUE : tempMin);
        }
        if (!stack.contains(new Tuple(row, col + 1))) {
            stack.push(new Tuple(row, col + 1));
            int tempMin = minimumMoveToLocation(row, col + 1, stack);
            min = Math.min(min, tempMin == -1 ? Integer.MAX_VALUE : tempMin);
        }
        if (!stack.contains(new Tuple(row, col - 1))) {
            stack.push(new Tuple(row, col - 1));
            int tempMin = minimumMoveToLocation(row, col - 1, stack);
            min = Math.min(min, tempMin == -1 ? Integer.MAX_VALUE : tempMin);
        }
        if(min == Integer.MAX_VALUE) {
            min = -1;
        }
        stack.pop();
        return min;
    }

}

class Tuple {

    public int x;
    public int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString() {
        return "(x: " + x + ", y: " + y + ")";
    }
   
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Tuple)) {
            return false;
        }
        Tuple t = (Tuple) obj;
        return t.x == x && t.y == y;
    }

}
