package ccc.y2017.senior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HighTideLowTide {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numberOfMeasurements = sc.nextInt();
        List<Integer> measurements = new ArrayList<>();
        for (int i = 0; i < numberOfMeasurements; i++) {
            measurements.add(sc.nextInt());
        }

        Collections.sort(measurements);

        int low = (numberOfMeasurements-1)/2;
        int high = low+1;
        for (int i = 0; i < numberOfMeasurements; i++) {
            if(i%2==0) {
                System.out.print(measurements.get(low--) + " ");
            } else {
                System.out.print(measurements.get(high++) + " ");
            }
        }

    }

}
