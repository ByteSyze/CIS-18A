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
package pennies;

import java.util.Scanner;

/**
 *
 * @author Tyler Hackett
 */
public class Pennies
{
    
    /**The minimum number of days that the user is allowed to enter*/
    public static final int MIN_DAYS = 1;
    /**The maximum number of days that the user is allowed to enter*/
    public static final int MAX_DAYS = 45;
    
    /**Padding for each column of the payment table*/
    private static final int PADDING = 20;
    
    /**The formatting for a header with two columns*/
    private static final String TABLE_HEADER = String.format("%1$s%2$ds%1$s%2$ds \n", "%-", PADDING);

    /**The formatting for a row with three columns of strings*/
    private static final String TABLE_ROW = String.format("%1$s%3$ds%2$ss%2$s%3$ds \n", "%-", "%", PADDING);
    
    private static final String LINE_BREAK = "-----------------------------------------";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        int days = 0;
        
        System.out.print("For how many days will the pay double? ");
        
        days = bound(scanner.nextInt(), MIN_DAYS, MAX_DAYS);
        
        System.out.printf(TABLE_HEADER, "Day", "Total Pay");
        System.out.println(LINE_BREAK);
        
        for(int i = 1; i < days+1; i++)
        {
            System.out.printf(TABLE_ROW, i, "$", (Math.pow(2, i-1)/100));
        }
        
        System.out.println(LINE_BREAK);
        
        System.out.printf(TABLE_ROW, "Total", "$", (Math.pow(2, days)-1)/100);
    }
    
    /**
     * @param value the value to be tested inside of the range [min, max]
     * @param min   the minimum allowable value to return
     * @param max   the maximum allowable value to return
     * 
     * @return {@code value} if {@code value} is within [min, max]. Otherwise, 
     *          the closest bound to {@code value} is returned.
     */
    public static int bound(int value, int min, int max)
    {   
        return Math.min(Math.max(value, min), max);
    }
}
