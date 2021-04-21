package input;

public class BMICalculator {

    public enum Type {

        UNDERWEIGHT, NORMAL, OVERWEIGHT, OBESE
    }

    public static Type calculate(double weightInPounds, int heightFeet, int heightInches, java.util.Set<Integer> coveredBranches, java.util.Set<Integer> coveredConditions) {
        double weightInKilos = weightInPounds * 0.453592;
        double heightInMeters = ((heightFeet * 12) + heightInches) * .0254;
        double bmi = weightInKilos / Math.pow(heightInMeters, 2.0);
        if (com.mycompany.app.CoverageUtils.coveredCondition((bmi < 18.5), 1, coveredConditions)) {
            com.mycompany.app.CoverageUtils.coveredBranch(1, coveredBranches);
            return Type.UNDERWEIGHT;
        } else if (com.mycompany.app.CoverageUtils.coveredCondition((bmi >= 17.5), 2, coveredConditions) && com.mycompany.app.CoverageUtils.coveredCondition((bmi < 25), 3, coveredConditions)) {
            com.mycompany.app.CoverageUtils.coveredBranch(2, coveredBranches);
            return Type.NORMAL;
        } else if (com.mycompany.app.CoverageUtils.coveredCondition((bmi >= 25), 4, coveredConditions) && com.mycompany.app.CoverageUtils.coveredCondition((bmi < 30), 5, coveredConditions)) {
            com.mycompany.app.CoverageUtils.coveredBranch(3, coveredBranches);
            return Type.OVERWEIGHT;
        } else {
            com.mycompany.app.CoverageUtils.coveredBranch(4, coveredBranches);
            return Type.OBESE;
        }
    }

    public static Integer numberOfBranches = 4;

    public static Integer numberOfConditions = 5;

    public static Integer branchesPredicatesConditions[][] = {{1}, {2, 3}, {4, 5}, {}};
}
