package ccc.y1996.senior;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PatternGenerator {
  
  public static void main(String[] args) throws NumberFormatException, IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final int testCases = Integer.parseInt(br.readLine().trim());
    
    for (int t = 1; t <= testCases; t++) {
      String[] temp = br.readLine().trim().split(" ");
      final int length = Integer.parseInt(temp[0]);
      final int numberOfOnes = Integer.parseInt(temp[1]);
      System.out.println("The bit patterns are");
      
      printBinaryPatterns(length, numberOfOnes, "");
      
      if (t != testCases) {
        System.out.println();
      }
    }
    
  }
  
  public static void printBinaryPatterns(int length, int numberOfOnes, String s) {
    if (s.length() == length && s.length() != 0) {
      System.out.println(s);
      return;
    }
    
    if(canAdd1(length, numberOfOnes, s)) {
      printBinaryPatterns(length, numberOfOnes, s+"1");
    }
    if(canAdd0(length, numberOfOnes, s)) {
      printBinaryPatterns(length, numberOfOnes, s+"0");
    }
    
  }
  
  public static boolean canAdd0(int length, int numberOfOnes, String s) {
    int count = 0;
    for (char c : s.toCharArray()) {
      if (c == '0') {
        count++;
      }
    }
    
    return count < length - numberOfOnes;
  }
  
  public static boolean canAdd1(int length, int numberOfOnes, String s) {
    int count = 0;
    for (char c : s.toCharArray()) {
      if (c == '1') {
        count++;
      }
    }
    
    return count < numberOfOnes;
  }
  
}
