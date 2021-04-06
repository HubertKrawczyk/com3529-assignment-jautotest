package input;

import java.util.Set;
import com.mycompany.app.CoverageUtils;

public class Calendar {

    public static int daysBetweenTwoDates(int year1, int month1, int day1, int year2, int month2, int day2, Set<Integer> coveredBranches, Set<Integer> coveredConditions) {
        int days = 0;
        // sanitize month inputs
        if (CoverageUtils.coveredCondition((month1 < 1), 1, coveredConditions)) {
            CoverageUtils.coveredBranch(1, coveredBranches);
            month1 = 1;
        }
        if (CoverageUtils.coveredCondition((month2 < 1), 2, coveredConditions)) {
            CoverageUtils.coveredBranch(2, coveredBranches);
            month2 = 1;
        }
        if (CoverageUtils.coveredCondition((month1 > 12), 3, coveredConditions)) {
            CoverageUtils.coveredBranch(3, coveredBranches);
            month1 = 12;
        }
        if (CoverageUtils.coveredCondition((month2 > 12), 4, coveredConditions)) {
            CoverageUtils.coveredBranch(4, coveredBranches);
            month2 = 12;
        }
        // sanitize day inputs
        if (CoverageUtils.coveredCondition((day1 < 1), 5, coveredConditions)) {
            CoverageUtils.coveredBranch(5, coveredBranches);
            day1 = 1;
        }
        if (CoverageUtils.coveredCondition((day2 < 1), 6, coveredConditions)) {
            CoverageUtils.coveredBranch(6, coveredBranches);
            day2 = 1;
        }
        if (CoverageUtils.coveredCondition((day1 > daysInMonth(month1, year1)), 7, coveredConditions)) {
            CoverageUtils.coveredBranch(7, coveredBranches);
            day1 = daysInMonth(month1, year1);
        }
        if (CoverageUtils.coveredCondition((day2 > daysInMonth(month2, year2)), 8, coveredConditions)) {
            CoverageUtils.coveredBranch(8, coveredBranches);
            day2 = daysInMonth(month2, year2);
        }
        // swap dates if year2, month2, day2 is before year1, month1, day1
        if ((CoverageUtils.coveredCondition((year2 < year1), 9, coveredConditions)) || (CoverageUtils.coveredCondition((year2 == year1), 10, coveredConditions) && CoverageUtils.coveredCondition((month2 < month1), 11, coveredConditions)) || (CoverageUtils.coveredCondition((year2 == year1), 12, coveredConditions) && CoverageUtils.coveredCondition((month2 == month1), 13, coveredConditions) && CoverageUtils.coveredCondition((day2 < day1), 14, coveredConditions))) {
            CoverageUtils.coveredBranch(9, coveredBranches);
            int t = month2;
            month2 = month1;
            month1 = t;
            t = day2;
            day2 = day1;
            day1 = t;
            t = year2;
            year2 = year1;
            year1 = t;
        }
        // calculate days
        if (CoverageUtils.coveredCondition((month1 == month2), 15, coveredConditions) && CoverageUtils.coveredCondition((year1 == year2), 16, coveredConditions)) {
            CoverageUtils.coveredBranch(10, coveredBranches);
            days = day2 - day1;
        } else {
            CoverageUtils.coveredBranch(11, coveredBranches);
            days += daysInMonth(month1, year1) - day1;
            days += day2;
            if (CoverageUtils.coveredCondition((year1 == year2), 17, coveredConditions)) {
                CoverageUtils.coveredBranch(12, coveredBranches);
                int month = month1 + 1;
                while (month < month2) {
                    days += daysInMonth(month, year1);
                    month++;
                }
            } else {
                CoverageUtils.coveredBranch(13, coveredBranches);
                int year;
                int month = month1 + 1;
                while (month <= 12) {
                    days += daysInMonth(month, year1);
                    month++;
                }
                month = 1;
                while (month < month2) {
                    days += daysInMonth(month, year2);
                    month++;
                }
                year = year1 + 1;
                while (year < year2) {
                    days += 365;
                    if (CoverageUtils.coveredCondition((isLeapYear(year)), 18, coveredConditions)) {
                        CoverageUtils.coveredBranch(14, coveredBranches);
                        days++;
                    }
                    year++;
                }
            }
        }
        return days;
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public static int daysInMonth(int month, int year) {
        int[] daysInMonthNonLeapYear = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        return month == 2 && isLeapYear(year) ? 29 : daysInMonthNonLeapYear[month - 1];
    }

    public static Integer numberOfBranches = 14;

    public static Integer numberOfConditions = 18;

    public static Integer branchesPredicatesConditions[][] = {{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9, 10, 11, 12, 13, 14}, {15, 16}, {}, {17}, {}, {18}};
}
