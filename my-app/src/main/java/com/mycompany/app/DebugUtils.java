package com.mycompany.app;

/** 
 * Contains useful debug methods
 */
public class DebugUtils {

  public static void printLn(Object o){
    System.out.println(o);
  }

  public static void print(Object o){
    System.out.print(o);
  }

  public static void printProgressBar(int progressBarSteps, int barsCompleted, double progress){
    print("[" + "=".repeat(barsCompleted) + " ".repeat(progressBarSteps - barsCompleted) + "] "
    + String.format("%.2f", progress * 100.0, 2) + "%\r");
  }
}
