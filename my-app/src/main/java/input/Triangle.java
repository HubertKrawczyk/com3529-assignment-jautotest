package input;

import java.util.Set;
import com.mycompany.app.CoverageUtils;

public class Triangle {

    public enum Type {

        INVALID, SCALENE, EQUILATERAL, ISOSCELES
    }

    public static Type classify(int side1, int side2, int side3, Set<Integer> coveredBranches, Set<Integer> coveredConditions) {
        Type type;
        if (CoverageUtils.coveredCondition((side1 > side2), 1, coveredConditions)) {
            CoverageUtils.coveredBranch(1, coveredBranches);
            int temp = side1;
            side1 = side2;
            side2 = temp;
        }
        if (CoverageUtils.coveredCondition((side1 > side3), 2, coveredConditions)) {
            CoverageUtils.coveredBranch(2, coveredBranches);
            int temp = side1;
            side1 = side3;
            side3 = temp;
        }
        if (CoverageUtils.coveredCondition((side2 > side3), 3, coveredConditions)) {
            CoverageUtils.coveredBranch(3, coveredBranches);
            int temp = side2;
            side2 = side3;
            side3 = temp;
        }
        if (CoverageUtils.coveredCondition((side1 + side2 <= side3), 4, coveredConditions)) {
            CoverageUtils.coveredBranch(4, coveredBranches);
            type = Type.INVALID;
        } else {
            CoverageUtils.coveredBranch(5, coveredBranches);
            type = Type.SCALENE;
            if (CoverageUtils.coveredCondition((side1 == side2), 5, coveredConditions)) {
                CoverageUtils.coveredBranch(6, coveredBranches);
                if (CoverageUtils.coveredCondition((side2 == side3), 6, coveredConditions)) {
                    CoverageUtils.coveredBranch(8, coveredBranches);
                    type = Type.EQUILATERAL;
                }
            } else {
                CoverageUtils.coveredBranch(7, coveredBranches);
                if (CoverageUtils.coveredCondition((side2 == side3), 7, coveredConditions)) {
                    CoverageUtils.coveredBranch(9, coveredBranches);
                    type = Type.ISOSCELES;
                }
            }
        }
        return type;
    }

    public static Integer numberOfBranches = 9;

    public static Integer numberOfConditions = 7;

    public static Integer branchesPredicatesConditions[][] = {{1}, {2}, {3}, {4}, {}, {5}, {}, {6}, {7}};
}
