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
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @author Tyler Hackett
 */
public class Main extends Application
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch();
    }
    

    @Override
    public void start(Stage stage) 
    {
        Scanner scanner = new Scanner(System.in);
        
        LineFitter line;
        
        double[][] features;
        double[] values;
        int numValues;
        
        System.out.print("How many double-precision values do you want to store in your array? ");
        numValues = scanner.nextInt();
        
        values = new double[numValues];
        features = new double[numValues][2];
        
        for(int i = 0; i < numValues; i++)
        {
            System.out.printf("Enter a value for #%d:", i+1);
            values[i] = scanner.nextDouble();
        }
        
        /*
            Map our features.
        
            I am effectively deciding that each number entered by the 
            user is 1 unit farther in one of the dimensions of our features.
        */
        for(int i = 0; i < numValues; i++)
        {
            features[i][0] = 1;
            features[i][1] = i;
        }
        
        line = new LineFitter(features, values);
        
        line.fit();
        
        /////////////////////////////////
        //
        // Print our results
        //
        /////////////////////////////////
        
        final String formatting = "%35s%10.3f\n";
        
        System.out.print("Trained parameters: ");
        print(line.getTheta());
        System.out.println();
        
        System.out.println("The statistics for your user entered array is:");
        System.out.println("----------------------------------------------");
        System.out.printf(formatting, "Minimum:", min(values));
        System.out.printf(formatting, "Maximum:", max(values));
        System.out.printf(formatting, "Average:", mean(values));
        System.out.printf(formatting, "Standard deviation (population):", standardDeviation(false, values));
        System.out.printf(formatting, "Standard deviation (sample):", standardDeviation(true, values));
        
        /////////////////////////////////
        //
        // Set up JavaFX graph
        //
        /////////////////////////////////
        
        stage.setTitle("Line Graph");
        
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        LineChart<Number,Number> userDataChart = new LineChart<>(xAxis,yAxis);
        LineChart<Number,Number> predictionChart = new LineChart<>(xAxis,yAxis);
        
        XYChart.Series userDataSeries = new XYChart.Series();
        XYChart.Series predictionSeries = new XYChart.Series();
        
        xAxis.setLabel("x");
        yAxis.setLabel("y");
                
        userDataChart.setTitle("Your Entered Data");
        predictionChart.setTitle("Linear Regression");
        
        userDataSeries.setName("User Data");
        predictionSeries.setName("Prediction Model");
        
        for(int i = 0; i < numValues; i++)
        {
            userDataSeries.getData().add(new XYChart.Data(i, values[i]));
            predictionSeries.getData().add(new XYChart.Data(i, line.predict(new double[]{1, i})));
        }
        
        Scene scene  = new Scene(userDataChart,800,600);
        userDataChart.getData().add(userDataSeries);
        userDataChart.getData().add(predictionSeries);
       
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     *  @param values the values to calculate a mean average from.
     * 
     *  @return the mean value of {@code values}
     */
    public static double mean(double... values)
    {
        double sum = 0;
        
        for(double v : values)
        {
            sum += v;
        }
        
        return (sum/values.length);
    }
    
    /**
     *  @param values the values containing a minimum.
     * 
     *  @return the minimum value of {@code values}
     */
    public static double min(double... values)
    {
        double min = values[0];
        
        for(double v : values)
        {
            if(v < min)
                min = v;
        }
        
        return min;
    }
    
    /**
     *  @param values the values containing a maximum.
     * 
     *  @return the maximum value of {@code values}
     */
    public static double max(double... values)
    {
        double max = values[0];
        
        for(double v : values)
        {
            if(v > max)
                max = v;
        }
        
        return max;
    }
    
    /**
     *  @param sample determines whether to return population or sample standard deviation
     *  @param values the values to calculate the population standard deviation from.
     * 
     *  @return the standard deviation (sigma) of {@code values}
     */
    public static double standardDeviation(boolean sample, double... values)
    {
        double rms = 0; //Root-Mean-Square
        double mean = mean(values);
        
        int n = sample ? values.length - 1 : values.length;
        
        for(double v : values)
        {
            rms += Math.pow(v - mean, 2);
        }
        
        return Math.sqrt(rms/n);
    }
    
    public static void print(double... array)
    {
        System.out.print("[ ");
        
        for(double d : array)
            System.out.print(d + " ");
        
        System.out.print("]");
    }
}
