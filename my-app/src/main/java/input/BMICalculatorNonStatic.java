package input;

import java.util.Set;
import com.mycompany.app.CoverageUtils;

public class BMICalculatorNonStatic {

    public enum Type {

        UNDERWEIGHT, NORMAL, OVERWEIGHT, OBESE
    }

    public BMICalculatorNonStatic(char z) {
    }

    public Type calculate(double weightInPounds, int heightFeet, int heightInches, Set<Integer> coveredBranches, Set<Integer> coveredConditions) {
        double weightInKilos = weightInPounds * 0.453592;
        double heightInMeters = ((heightFeet * 12) + heightInches) * .0254;
        double bmi = weightInKilos / Math.pow(heightInMeters, 2.0);
        if (CoverageUtils.coveredCondition((bmi < 18.5), 1, coveredConditions)) {
            CoverageUtils.coveredBranch(1, coveredBranches);
            return Type.UNDERWEIGHT;
        } else if (CoverageUtils.coveredCondition((bmi >= 17.5), 2, coveredConditions) && CoverageUtils.coveredCondition((bmi < 25), 3, coveredConditions)) {
            CoverageUtils.coveredBranch(2, coveredBranches);
            return Type.NORMAL;
        } else if (CoverageUtils.coveredCondition((bmi >= 25), 4, coveredConditions) && CoverageUtils.coveredCondition((bmi < 30), 5, coveredConditions)) {
            CoverageUtils.coveredBranch(3, coveredBranches);
            return Type.OVERWEIGHT;
        } else {
            CoverageUtils.coveredBranch(4, coveredBranches);
            return Type.OBESE;
        }
    }

    public static Integer numberOfBranches = 4;

    public static Integer numberOfConditions = 5;

    public static Integer branchesPredicatesConditions[][] = {{1}, {2, 3}, {4, 5}, {}};
}
