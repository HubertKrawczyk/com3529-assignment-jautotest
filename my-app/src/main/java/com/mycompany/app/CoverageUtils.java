package com.mycompany.app;

import java.util.Set;

public class CoverageUtils {
  public static void coveredBranch(int id, Set<Integer> coveredBranches) {
    if (coveredBranches != null && !coveredBranches.contains(id)) {
        // System.out.println("* covered new branch: " + id);
        coveredBranches.add(id);
    }
  }
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
