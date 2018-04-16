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

import arrays.curve.LineFitter;
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
        
        LineFitter line;
        
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
        
        //Map our features
        for(int i = 0; i < numValues; i++)
        {
            features[i][0] = 1;
            features[i][1] = i;
        }
        
        line = new LineFitter(features, values);
        
        line.fit();
        
        System.out.println(line.cost());
    }
    
    public static double mean(double[] values)
    {
        double sum = 0;
        
        for(double v : values)
        {
            sum += v;
        }
        
        return (sum/values.length);
    }
    
    public static double standardDeviation(double[] values)
    {
        double rms = 0; //Root-Mean-Square
        double mean = mean(values);
        
        for(double v : values)
        {
            rms += Math.pow(v - mean, 2);
        }
        
        return Math.sqrt(rms/values.length);
    }
}
