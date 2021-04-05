package com.mycompany.app;

public class DebugUtils {

  static boolean printBool = true;
  public static void dbgLn(String s){
    if(printBool)
      System.out.println(s);
  }

  public static void dbg(String s){
    if(printBool)
      System.out.print(s);
  }
}
