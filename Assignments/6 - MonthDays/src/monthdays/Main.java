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

import java.util.Scanner;

/**
 *
 * @author Tyler Hackett
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        
        MonthDays md;
        
        String input;
        
        int year = 0;
        
        MonthDays.Month month = null;
        int monthIdx = 0;
        
        System.out.print("Enter a month by name or index: ");
        input = scanner.nextLine();
        
        try {
            monthIdx = Integer.parseInt(input);
        } catch(Exception e) {
            
            try {
                month = MonthDays.Month.valueOf(input.toUpperCase());
            } catch(Exception e2) {
                System.err.println("Unable to parse month.");
                return;
            }
        }
        
        System.out.print("Enter a year: ");
        input = scanner.nextLine();
        
        try {
            year = Integer.parseInt(input);
        } catch(Exception e) {
            System.err.println("Year Must be a valid integer.");
            
            return;
        }
        
        try {
            
        if(month != null)
            md = new MonthDays(year, month);
        else
            md = new MonthDays(year, monthIdx);
        
        } catch(Exception e)
        {
            e.printStackTrace();
            return;
        }
        
        System.out.printf("%02d/%d has %d days.\n", md.getMonthIndex(), md.getYear(), md.getNumberOfDays());
            
    }
    
}
