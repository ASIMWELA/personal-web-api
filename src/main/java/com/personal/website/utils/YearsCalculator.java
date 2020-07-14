package com.personal.website.utils;

import java.time.LocalDate;
import java.time.Period;

public class YearsCalculator
{
    public static int calculateYears(LocalDate from, LocalDate now)
    {
        return Period.between(from, now).getYears();
    }
}
