package com.personal.website.utils;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Period;

public class YearsCalculator
{
    public static int calculateYears(LocalDate from, LocalDate now)
    {
        return Period.between(from, now).getYears();
    }

    public static int calculateMonths(LocalDate from , LocalDate now)
    {
       return Period.between(from, now).getMonths();
    }

}
