public class TestFunctions {
  
  public static int longestRepetition (String s, char c) {
    int result = 0;
    int maxResult = 0;

    if(s.length() == 0 ) {
      return 0;
    }

    for(int i = 0; i < s.length(); i++){

      if(s.charAt(i)==c){
        result ++;
      } else{
        result = 0;
      }
      if(result > maxResult){
        maxResult = result;
      }
    }


    return maxResult;
  }


  public String removeNonAlphanumericChars(String s){
    String newString = "";
    for(int i = 0; i < s.length(); i++){
      char c = s.charAt(i);
      if((c>='a' && c<='z') || (c>='A'&& c<='Z') || (c>='0'&& c<='9')){
        newString+=c;
      }
    }
    return newString;
  }
}
