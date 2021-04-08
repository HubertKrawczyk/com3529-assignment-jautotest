package com.mycompany.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public abstract class Search {

  Constructor chosenConstructor;
  Method testedMethod;
  Class<?> testedClass;
  ArrayList<Object[]> successfulInputs;
  ArrayList<Object[]> successfulConstructorInputs;
  ArrayList<Object> successfulOutputs;
  Set<Integer> coveredBranches;
  Set<Integer> coveredConditions;
  Set<Integer> coveredMasterConditions;

  public ArrayList<Object[]> getSuccessfulInputs(){
    return successfulInputs;
  }
  public ArrayList<Object[]> getSuccessfulConstructorInputs(){
    return successfulConstructorInputs;
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
  public Set<Integer> getCoveredMasterConditions(){
    return coveredMasterConditions;
  }
  public Constructor getChosenConstructor(){
    return chosenConstructor;
  }
  public Search(Method testedMethod, Class<?> testedClass){
    this.testedClass = testedClass;
    this.testedMethod = testedMethod;
  }

  public abstract boolean search(Scanner keyboard);
}
