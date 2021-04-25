package jautotest.app;

import jautotest.app.StringUtils.StringCharTypes;

/** 
 * Contains useful numberical and other methods
 */
public class NumberUtils {

  public static double getRandomDouble(double min, double max) {
    return ((Math.random() * (max - min)) + min);
  }

  public static Object getRandomValue(String type, int lowerBoundInt, int upperBoundInt,
    double lowerBoundDouble, double upperBoundDouble,
    StringCharTypes stringCharType) {
    if (type.equals("int")) {
      return (int) getRandomDouble(lowerBoundInt, upperBoundInt);
    } else if (type.equals("byte")) {
      return (byte) getRandomDouble(lowerBoundDouble, upperBoundDouble);
    } else if (type.equals("short")) {
      return (short) getRandomDouble(lowerBoundDouble, upperBoundDouble);
    } else if (type.equals("double")) {
      return getRandomDouble(lowerBoundDouble, upperBoundDouble);
    } else if (type.equals("float")) {
      return (float) getRandomDouble(lowerBoundDouble, upperBoundDouble);
    } else if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
      return Math.random() > 0.5;
    } else if (type.equals("java.lang.String")) {
      return StringUtils.genRandomString(stringCharType, lowerBoundInt, upperBoundInt);
    } else if (type.equals("char")) {
      return (char) (int) getRandomDouble(lowerBoundInt, upperBoundInt);
    } else {
      return null;
    }
  }
}
