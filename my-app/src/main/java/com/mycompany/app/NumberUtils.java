package com.mycompany.app;

import com.mycompany.app.StringUtils.StringCharTypes;

/** 
 * Contains useful numberical and other methods
 */
public class NumberUtils {

  public static double getRandomDouble(double min, double max) {
    return ((Math.random() * (max - min)) + min);
  }

  public static Object getRandomValue(String type, int constructorLowerBoundInt, int constructorUpperBoundInt,
    double constructorLowerBoundDouble, double constructorUpperBoundDouble,
    StringCharTypes constructorStringCharType) {
    if (type.equals("int")) {
      return (int) getRandomDouble(constructorLowerBoundInt, constructorUpperBoundInt);
    } else if (type.equals("byte")) {
      return (byte) getRandomDouble(constructorLowerBoundDouble, constructorUpperBoundDouble);
    } else if (type.equals("short")) {
      return (short) getRandomDouble(constructorLowerBoundDouble, constructorUpperBoundDouble);
    } else if (type.equals("double")) {
      return getRandomDouble(constructorLowerBoundDouble, constructorUpperBoundDouble);
    } else if (type.equals("float")) {
      return (float) getRandomDouble(constructorLowerBoundDouble, constructorUpperBoundDouble);
    } else if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
      return Math.random() > 0.5;
    } else if (type.equals("java.lang.String")) {
      return StringUtils.genRandomString(constructorStringCharType, constructorLowerBoundInt, constructorUpperBoundInt);
    } else if (type.equals("char")) {
      return (char) (int) getRandomDouble(constructorLowerBoundInt, constructorUpperBoundInt);
    } else {
      return null;
    }
  }
}
