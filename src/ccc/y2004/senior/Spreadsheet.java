package ccc.y2004.senior;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Spreadsheet {

    static String[][] sheet = new String[10][];

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 10; i++) {
            try {
                sheet[i] = br.readLine().trim().split(" ");
            } catch (IOException e) {
            }
        }
        for (int i = sheet.length-1; i >= 0; i--) {
            for (int j = 0; j < sheet[i].length; j++) {
                sheet[i][j] = calcCell(i, j, 0);
            }
        }
        for (int i = 0; i < sheet.length; i++) {
            for (int j = 0; j < sheet[i].length; j++) {
                System.out.print(sheet[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static String calcCell(int row, int col, int depth) {
        if (depth > 50) {
            return "*";
        }

        String cell = sheet[row][col];
        if (!cell.contains("+") && cell.charAt(0) < 65 && cell.charAt(0) < 65 + 26) {
            return cell;
        } else {
            String[] adders = cell.trim().split("\\+");
            int sum = 0;
            for (int i = 0; i < adders.length; i++) {
                int rowB = adders[i].charAt(0) - 65;
                int colB = Integer.parseInt(Character.toString(adders[i].charAt(1))) - 1;
                String c = calcCell(rowB, colB, depth + 1);
                if (c.equals("*")) {
                    return c;
                }
                sum += Integer.parseInt(c);
            }
            return Integer.toString(sum);
        }
    }

}
