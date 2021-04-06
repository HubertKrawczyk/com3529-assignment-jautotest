package com.mycompany.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public abstract class Search {

  public enum StringCharTypes {
    ALPHABET,
    ALPHABET_AND_DIGITS,
    DIGITS,
    ASCII
  }

  Constructor chosenConstructor;
  Method testedMethod;
  Class<?> testedClass;
  ArrayList<Object[]> successfulInputs;
  ArrayList<Object> successfulOutputs;
  Set<Integer> coveredBranches;
  Set<Integer> coveredConditions;

  public ArrayList<Object[]> getSuccessfulInputs(){
    return successfulInputs;
  }
  public ArrayList<Object> getSuccessfulOutputs(){
    return successfulOutputs;
  }
  public Set<Integer> getCoveredBranches(){
    return coveredBranches;
  }
  public Set<Integer> getCoveredConditions(){
    return coveredConditions;
  }
  public Search(Method testedMethod, Class<?> testedClass){
    this.testedClass = testedClass;
    this.testedMethod = testedMethod;
  }

  static String genRandomString(StringCharTypes stringCharType, int min, int max) {
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
    }
    int stringLen = (int)getRandomDouble(min,max);
    for(int i=0;i<stringLen;i++){
        if(stringCharType == StringCharTypes.ASCII){
            char c = (char)(int)getRandomDouble(32, 126);
            if(c == '"'||c == '\\'){
                result+="\\";
            };
            result+=c;
        }else{
            result+=range.charAt((int)getRandomDouble(0, range.length()-1));
        }
    }
    return result;
  }
  static double getRandomDouble(double min, double max) {
    return ((Math.random() * (max - min)) + min);
  }
  public abstract boolean search(Method meth,  Class<?> cls, ArrayList<String> paramTypes, ArrayList<String> paramNames);
}
