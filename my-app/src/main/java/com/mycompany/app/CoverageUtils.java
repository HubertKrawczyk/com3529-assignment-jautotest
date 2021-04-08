package com.mycompany.app;

import java.util.Set;

/** 
 * Contains methods to be used by parsed method
*/
public class CoverageUtils {
  /**
   * Adds id to the set of covered branches
   * @param id the id
   * @param coveredBranches the covered branches set
   */
  public static void coveredBranch(int id, Set<Integer> coveredBranches) {
    if (coveredBranches != null && !coveredBranches.contains(id)) {
        coveredBranches.add(id);
    }
  }

  /**
   * Adds id to the set of covered condition if the predicate is true. Adds -id otehrwise
   * @param predicate the predicate
   * @param id the id
   * @param coveredConditions the covered conditions set
   * @return passes the predicate
   */
  public static boolean coveredCondition(boolean predicate, int id, Set<Integer> coveredConditions) {
    if (coveredConditions != null) {
        if (predicate) {
            if (!coveredConditions.contains(id)) {
                coveredConditions.add(id);
            }
        } else {
            if (!coveredConditions.contains(-id)) {
                coveredConditions.add(-id);
            }
        }
    }
    return predicate;
  }
}
