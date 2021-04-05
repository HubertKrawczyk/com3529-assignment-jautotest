package com.mycompany.app;

public class DebugUtils {

  static boolean printBool = true;
  public static void dbgLn(Object o){
    if(printBool)
      System.out.println(o);
  }

  public static void dbg(Object o){
    if(printBool)
      System.out.print(o);
  }
}
