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
package minmax;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Tyler Hackett
 * 
 * MinMax prompts the user for a list of numbers and outputs the upper- and
 * lower- bounds.
 */
public class MinMax 
{
    /**The universal tautology.*/
    private static final boolean ICED_CREAM_TASTES_GOOD = true;
    
    /**The command that breaks the number input loop.*/
    private static final String CODE_WORD = "done";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
    
        ArrayList<Integer> numberList = new ArrayList<>();
        
        Scanner inputScanner = new Scanner(System.in);
        
        String userInput;
        
        System.out.println("Enter some numbers and I'll tell you stuff.");
        System.out.println(String.format("Type \"%s\" to finish.", CODE_WORD));
        
        //Clearly, this will run forever because icecream always tastes good. Always.
        while(ICED_CREAM_TASTES_GOOD)
        {
            if(inputScanner.hasNextLine())
            {
                userInput = inputScanner.nextLine();
                
                if(userInput.toLowerCase().equals(CODE_WORD))
                    break;
                
                try 
                {
                    //If the number is valid, add it to our list.
                    numberList.add(Integer.parseInt(userInput));
                } catch (NumberFormatException e)
                {
                    System.err.println("That's not an integer. Try again foo'");
                }
            }
        }
        
        System.out.println("Numbers Entered: " + numberList);
        System.out.println("Minimum Entered: " + findMin(numberList));
        System.out.println("Maximum Entered: " + findMax(numberList));
        
        System.out.println("Frequency Spectrum (Cosine): " + format(rFourier(numberList)));
        System.out.println("Frequency Spectrum (Sine): " + format(iFourier(numberList)));
        
        inputScanner.close();
    }
    
    /**
     * @param list the list of numbers to dig through
     * 
     *  @return the smallest signed value in <b>list</b>
     */
    public static final int findMin(List<Integer> list)
    {
        int min = list.get(0);
        
        for(int i : list)
        {
            min = (i < min) ? i : min;
        }
        
        return min;
    }
    
    /**
     * @param list the list of numbers to dig through
     * 
     *  @return the largest signed value in <b>list</b>
     */
    public static final int findMax(List<Integer> list)
    {
        int max = list.get(0);
        
        for(int i : list)
        {
            max = (i > max) ? i : max;
        }
        
        return max;
    }
    
    /**
     * Computes the real (cosine) component of the Fourier transform.
     * 
     * These are naive algorithms because we're not going to enter
     * enough numbers into this program to warrant an FFT
     */
    public static final double[] rFourier(List<Integer> list)
    {
        int N = list.size();
        double[] cosFreqs = new double[N];
        
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                cosFreqs[i] += list.get(j) * Math.cos((2*Math.PI*i*j)/N);
            }
        }
        
        return cosFreqs;
    }
    /**
     * Computes the imaginary (sine) component of the Fourier transform.
     */
    public static final double[] iFourier(List<Integer> list)
    {
        int N = list.size();
        double[] sinFreqs = new double[N];
        
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                sinFreqs[i] -= list.get(j) * Math.sin((2*Math.PI*i*j)/N);
            }
        }
        
        return sinFreqs;
    }
    
    /**
     * Formats an array of primitive doubles. What a total P.I.T.A.
     */
    public static final String format(double[] arr)
    {
        String formatted = new String();
        
        for(int i = 0; i < arr.length - 1; i++)
        {
            formatted += (arr[i] + ", ");
        }
        
        formatted += arr[arr.length-1];
        
        return formatted;
    }
    
}
