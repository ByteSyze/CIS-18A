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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Tyler Hackett
 */
public class Main 
{   
    private static final int MAX_THREADS = 1000;
    private static final int MAX_NUMBER = 1000000;
    
    public static void main(String[] args) throws Exception
    {
        List<PerfectNumberFinder> numberFinders = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        int numThreads, number;
        int range, remainder;
        
        long startTime, endTime;
        
        ExecutorService exeService;
        
        System.out.println("Please enter the highest number that you want to test: ");
        number = Math.min(Integer.parseInt(scanner.nextLine()), MAX_NUMBER);
        
        System.out.println("Please enter the number of threads to create: ");
        numThreads = Math.min(Integer.parseInt(scanner.nextLine()), MAX_THREADS);
        numThreads = (numThreads < 1) ? 1 : numThreads;
        
        startTime = System.currentTimeMillis();
        
        range     = (number) / numThreads;
        remainder = ((number) % numThreads) + 1; //Add 1 because PerfectNumberFinder is exclusive upper bound.
        
        for(int i = 0; i < numThreads-1; i++)
        {
            numberFinders.add(new PerfectNumberFinder(i*range + 1, (i+1)*range));
        }
        
        //The final thread gets whatever the leftovers are.
        numberFinders.add(new PerfectNumberFinder((numThreads-1)*range + 1, (numThreads)*range + remainder));
        
        exeService = Executors.newFixedThreadPool(numThreads);
        
        List<Future<String>> results = exeService.invokeAll(numberFinders);
        
        System.out.println("Printing perfect numbers...");
        
        for(Future<String> f : results)
        {
            System.out.print(f.get());
        }
        
        System.out.println();
        
        exeService.shutdown();
        
        endTime = System.currentTimeMillis();
        
        System.out.println("Done. Total time (ms): " + (endTime - startTime));
    }
    
}
