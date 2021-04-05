package input;

import java.util.Set;

public class Triangle {

    public enum Type {

        INVALID, SCALENE, EQUILATERAL, ISOSCELES
    }

    public static Type classify(int side1, int side2, int side3, Set<Integer> coveredBranches, Set<Integer> coveredConditions) {
        Type type;
        if (coveredCondition((side1 > side2), 1, coveredConditions)) {
            coveredBranch(1, coveredBranches);
            int temp = side1;
            side1 = side2;
            side2 = temp;
        }
        if (coveredCondition((side1 > side3), 2, coveredConditions)) {
            coveredBranch(2, coveredBranches);
            int temp = side1;
            side1 = side3;
            side3 = temp;
        }
        if (coveredCondition((side2 > side3), 3, coveredConditions)) {
            coveredBranch(3, coveredBranches);
            int temp = side2;
            side2 = side3;
            side3 = temp;
        }
        if (coveredCondition((side1 + side2 <= side3), 4, coveredConditions)) {
            coveredBranch(4, coveredBranches);
            type = Type.INVALID;
        } else {
            coveredBranch(5, coveredBranches);
            type = Type.SCALENE;
            if (coveredCondition((side1 == side2), 5, coveredConditions)) {
                coveredBranch(6, coveredBranches);
                if (coveredCondition((side2 == side3), 6, coveredConditions)) {
                    coveredBranch(8, coveredBranches);
                    type = Type.EQUILATERAL;
                }
            } else {
                coveredBranch(7, coveredBranches);
                if (coveredCondition((side2 == side3), 7, coveredConditions)) {
                    coveredBranch(9, coveredBranches);
                    type = Type.ISOSCELES;
                }
            }
        }
        return type;
    }

    static void coveredBranch(int id, Set<Integer> coveredBranches) {
        if (coveredBranches != null && !coveredBranches.contains(id)) {
            // System.out.println("* covered new branch: " + id);
            coveredBranches.add(id);
        }
    }

    static boolean coveredCondition(boolean predicate, int id, Set<Integer> coveredConditions) {
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

    public static Integer numberOfBranches = 9;

    public static Integer numberOfConditions = 7;
}
