package ccc.y2016.senior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TandemBicyle {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        boolean questionOne = sc.nextInt() == 1;

        int citizens = sc.nextInt();
        List<Integer> citA = new ArrayList<>();
        List<Integer> citB = new ArrayList<>();

        for (int i = 0; i < citizens; i++) {
            citA.add(sc.nextInt());
        }
        for (int i = 0; i < citizens; i++) {
            citB.add(sc.nextInt());
        }

        Collections.sort(citA);
        Collections.sort(citB);
        
        int sum = 0;
        if (questionOne) {
            for(int i = 0; i < citizens; i++) {
                sum += Math.max(citA.get(i), citB.get(i));
            }
        } else {
            for(int i = 0; i < citizens; i++) {
                sum += Math.max(citA.get(citizens-i-1), citB.get(i));
            }
        }
        System.out.println(sum);
    }

}
