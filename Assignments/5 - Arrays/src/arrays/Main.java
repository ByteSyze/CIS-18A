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
package arrays;

import java.util.Scanner;

/**
 *
 * @author Tyler Hackett
 */
public class Main 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        
        CurveFitter cv;
        
        double[][] features;
        double[] values;
        int numValues;
        
        System.out.print("How many number of the type double do you want to store in your array? ");
        numValues = scanner.nextInt();
        
        values = new double[numValues];
        features = new double[numValues][2];
        
        for(int i = 0; i < numValues; i++)
        {
            System.out.printf("Enter a value for #%d:", i+1);
            values[i] = scanner.nextDouble();
        }
        
        for(int x = 0; x < numValues; x++)
        {
            features[x][0] = 1;
            features[x][1] = x;
        }
        
        
        cv = new CurveFitter(features, values, 4);
        cv.fit();
        
        System.out.println(cv.cost());
    }
    
}
