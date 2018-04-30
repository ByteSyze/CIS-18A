/*
 * Copyright (C) 2018 Tyler Hackett
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package monthdays;

/**
 *
 * @author Tyler Hackett
 */
public class MonthDays
{
    public enum Month
    {
        JANUARY, 
        FEBRUARY, 
        MARCH, 
        APRIL, 
        MAY, 
        JUNE, 
        JULY, 
        AUGUST, 
        SEPTEMBER, 
        OCTOBER, 
        NOVEMBER, 
        DECEMBER
    }
    
    //Grab the number of months from the enum. You know, in case we ever have more than 12 months...
    private static final int NUM_MONTHS = Month.values().length;
    
    private Month month;
    private int year;
    
    public MonthDays(int year, int month)
    {
        this.setYear(year);
        this.setMonth(month);
    }
    
    public MonthDays(int year, Month month)
    {
        this.setYear(year);
        this.setMonth(month);
    }
    
    public int getYear()
    {
        return year;
    }
    
    /**
     *  @throw IllegalArgumentException if {@code year} is negative.
     */
    public void setYear(int year) throws IllegalArgumentException
    {
        if(year < 0)
            throw new IllegalArgumentException("Year cannot be negative: " + year);
        
        this.year = year;
    }
    
    /**
     *  @return the index of the current month. The index starts at 1=January
     */
    public int getMonthIndex()
    {
        return month.ordinal() + 1;
    }
    
    public Month getMonth()
    {
        return month;
    }
    
    public void setMonth(Month month)
    {
        this.month = month;
    }
    
    /**
     *  Sets the current month to the month with the corresponding index.
     * 
     *  @param month the index of the month. 1 = Jan, 2 = Feb, ... 12 = Dec
     * 
     *  @throw IllegalArgumentException if {@code month} is outside of the range [1,12]
     */
    public void setMonth(int month) throws IllegalArgumentException
    {
        if((month < 1) || (month > NUM_MONTHS))
            throw new IllegalArgumentException(String.format("Month index must be in range [1,%d]", NUM_MONTHS));
        
        setMonth(Month.values()[month - 1]);
    }
    
    /**
     *  @return the number of days in the given month and year.
     */
    public int getNumberOfDays()
    {
        switch(month)
        {
            case FEBRUARY:
                
                if(isLeapYear())
                    return 29;
                else
                    return 28;
                
            default:
                
                int idx = getMonthIndex();
                
                if ((9*idx/8) % 2 == 0)
                    return 30;
                else
                    return 31;
        }
    }
    
    /**
     *  @return true if the current year is a leap year.
     */
    public boolean isLeapYear()
    {
        if((year % 100 == 0) && (year % 400 == 0))
            return true;
        else if(year % 4 == 0)
            return true;
        else
            return false;
    }
    
}
