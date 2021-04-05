package input;

import java.util.Set;

public class BMICalculator {

    public enum Type {

        UNDERWEIGHT, NORMAL, OVERWEIGHT, OBESE
    }

    public static Type calculate(double weightInPounds, int heightFeet, int heightInches, Set<Integer> coveredBranches, Set<Integer> coveredConditions) {
        double weightInKilos = weightInPounds * 0.453592;
        double heightInMeters = ((heightFeet * 12) + heightInches) * .0254;
        double bmi = weightInKilos / Math.pow(heightInMeters, 2.0);
        if (coveredCondition((bmi < 18.5), 1, coveredConditions)) {
            coveredBranch(1, coveredBranches);
            return Type.UNDERWEIGHT;
        } else if (coveredCondition((bmi >= 17.5), 2, coveredConditions) && coveredCondition((bmi < 25), 3, coveredConditions)) {
            coveredBranch(2, coveredBranches);
            return Type.NORMAL;
        } else if (coveredCondition((bmi >= 25), 4, coveredConditions) && coveredCondition((bmi < 30), 5, coveredConditions)) {
            coveredBranch(3, coveredBranches);
            return Type.OVERWEIGHT;
        } else {
            coveredBranch(4, coveredBranches);
            return Type.OBESE;
        }
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

    public static Integer numberOfBranches = 4;

    public static Integer numberOfConditions = 5;
}
