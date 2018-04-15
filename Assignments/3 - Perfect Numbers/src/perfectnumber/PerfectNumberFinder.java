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
package perfectnumber;

import java.util.concurrent.Callable;

/**
 *
 * @author Tyler Hackett
 * 
 * Checks a range of integer values for perfect integers.
 */
public class PerfectNumberFinder implements Callable<String> 
{
    /**The lower bound (inclusive) for the range of numbers to look through.*/
    private final int low;
    
    /**The upper bound (exclusive) for the range of numbers to look through.*/
    private final int high;
    
    /**
     *  @param low  the lower bound of the range to search through
     *  @param high the higher bound of the range to search through
     * 
     *  Calling {@link run()} will generate a list of perfect integers in the range [low, high)
     */
    public PerfectNumberFinder(int low, int high)
    {
        this.low = low;
        this.high = high;
    }
    
    @Override
    public String call()
    {
        String perfectNumbers = "";
        
        for(int i = low; i < high; i++)
        {
            String factors = isPerfect(i);
            if(factors != null)
                perfectNumbers += String.format("%d: %s\n", i, factors);
        }
        
        return perfectNumbers;
    }
    
    /**
     *  @param number the integer value to be tested for perfectness.
     * 
     *  @return A string of factors if the sum of the factors of {@code number} is equal to {@code number}, or null if otherwise.
     */
    public final String isPerfect(int number)
    {
        int sumOfFactors = 0;
        String factors = "";
        
        for(int i = 1; i < number; i++)
        {
            if(number/((float)i) == (number/i))
            {
                sumOfFactors += i;
                factors += String.format("%d ", i);
            }
        }
        
        return (sumOfFactors == number) ? factors : null;
    }
}
