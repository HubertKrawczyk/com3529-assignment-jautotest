package jautotest.app;

import java.util.ArrayList;

/** 
 * Contains useful String-manipulation methods
 */
public class StringUtils {
  /**
   * Escapes " and \ characters
   * @param s input String
   * @return escaped String
   */
  public static String escapeString(String s) {
      String result = "";
      for (char c : s.toCharArray()) {
          if (c == '"' || c == '\\') {
              result += "\\" + c;
          } else {
              result += c;
          }
      }
      return result;
  }

    /**
     * Given an array of params returns a String with the parameters delimered by commas
     * @param params 
     * @param paramTypes
     * @return String of parameters delimered by commas
     */
    public static String paramsIntoString(Object[] params, ArrayList<String> paramTypes) {
      String s = "";
      for (int j = 0; j < params.length; j++) {
          // escape characters
          if (paramTypes.get(j).equals("java.lang.String")) {
              s += "\"";
              s += StringUtils.escapeString((String) params[j]);
              s += "\"";
          } else if (paramTypes.get(j).equals("char")) {
              s += "(char)" + (int) (char) params[j];

          } else if (paramTypes.get(j).equals("byte")) {
              s += "(byte)" + params[j];

          } else if (paramTypes.get(j).equals("short")) {
              s += "(short)" + params[j];

          } else if (paramTypes.get(j).equals("float")) {
              s += params[j] + "f";

          } else {
              s += params[j];
          }
          if (j != params.length - 1) {
              s += ", ";
          }
      }
      return s;
  }

  public enum StringCharTypes {
    ALPHABET, ALPHABET_AND_DIGITS, DIGITS, ASCII
  }

  /**
   * Given String type, minimum and maximum lengths, generates a randomString
   * @param stringCharType type of String
   * @param min minimum length
   * @param max maximum length
   * @return random String
   */
  public static String genRandomString(StringCharTypes stringCharType, int min, int max) {
    String result="";
    String range = "";
    switch (stringCharType){
        case ALPHABET:
        range = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        break;
        case ALPHABET_AND_DIGITS:
        range = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        break;
        case DIGITS:
        range = "0123456789";
        break;
        case ASCII:
        // handled in for loop
        break;
    }
    int stringLen = (int)NumberUtils.getRandomDouble(min,max);
    for(int i=0;i<stringLen;i++){
        if(stringCharType == StringCharTypes.ASCII){
            char c = (char)(int)NumberUtils.getRandomDouble(32, 126);
            if(c == '"'||c == '\\'){
                result+="\\";
            };
            result+=c;
        }else{
            result+=range.charAt((int)NumberUtils.getRandomDouble(0, range.length()));
        }
    }
    return result;
  }

  public static String capitalize(String name) {
    if(name!= null && name.length()>0)
        return name.substring(0, 1).toUpperCase()+name.substring(1);
    else
        return name;
  }
}
