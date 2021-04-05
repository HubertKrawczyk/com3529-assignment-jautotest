package input;

import java.util.Set;

public class Calendar {

    public static int daysBetweenTwoDates(int year1, int month1, int day1, int year2, int month2, int day2, Set<Integer> coveredBranches, Set<Integer> coveredConditions) {
        int days = 0;
        // sanitize month inputs
        if (coveredCondition((month1 < 1), 1, coveredConditions)) {
            coveredBranch(1, coveredBranches);
            month1 = 1;
        }
        if (coveredCondition((month2 < 1), 2, coveredConditions)) {
            coveredBranch(2, coveredBranches);
            month2 = 1;
        }
        if (coveredCondition((month1 > 12), 3, coveredConditions)) {
            coveredBranch(3, coveredBranches);
            month1 = 12;
        }
        if (coveredCondition((month2 > 12), 4, coveredConditions)) {
            coveredBranch(4, coveredBranches);
            month2 = 12;
        }
        // sanitize day inputs
        if (coveredCondition((day1 < 1), 5, coveredConditions)) {
            coveredBranch(5, coveredBranches);
            day1 = 1;
        }
        if (coveredCondition((day2 < 1), 6, coveredConditions)) {
            coveredBranch(6, coveredBranches);
            day2 = 1;
        }
        if (coveredCondition((day1 > daysInMonth(month1, year1)), 7, coveredConditions)) {
            coveredBranch(7, coveredBranches);
            day1 = daysInMonth(month1, year1);
        }
        if (coveredCondition((day2 > daysInMonth(month2, year2)), 8, coveredConditions)) {
            coveredBranch(8, coveredBranches);
            day2 = daysInMonth(month2, year2);
        }
        // swap dates if year2, month2, day2 is before year1, month1, day1
        if (coveredCondition(((year2 < year1)), 9, coveredConditions) || coveredCondition(((year2 == year1 && month2 < month1)), 10, coveredConditions) || coveredCondition(((year2 == year1 && month2 == month1 && day2 < day1)), 11, coveredConditions)) {
            coveredBranch(9, coveredBranches);
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
        if (coveredCondition((month1 == month2 && year1 == year2), 12, coveredConditions)) {
            coveredBranch(10, coveredBranches);
            days = day2 - day1;
        } else {
            coveredBranch(11, coveredBranches);
            days += daysInMonth(month1, year1) - day1;
            days += day2;
            if (coveredCondition((year1 == year2), 13, coveredConditions)) {
                coveredBranch(12, coveredBranches);
                int month = month1 + 1;
                while (month < month2) {
                    days += daysInMonth(month, year1);
                    month++;
                }
            } else {
                coveredBranch(13, coveredBranches);
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
                    if (coveredCondition((isLeapYear(year)), 14, coveredConditions)) {
                        coveredBranch(14, coveredBranches);
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

    static void coveredBranch(int id, Set<Integer> coveredBranches) {
        if (coveredBranches != null && !coveredBranches.contains(id)) {
            // System.out.println("* covered new branch: " + id);
            coveredBranches.add(id);
        }
    }

    static boolean coveredCondition(boolean predicate, int id, Set<Integer> coveredConditions) {
        if (coveredConditions != null && !coveredConditions.contains(id)) {
            // System.out.println("* covered new condition: " + id);
            coveredConditions.add(id);
        }
        return predicate;
    }

    public static Integer numberOfBranches = 14;

    public static Integer numberOfConditions = 14;
}
