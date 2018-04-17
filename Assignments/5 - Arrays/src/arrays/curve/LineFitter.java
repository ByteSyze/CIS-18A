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
package arrays.curve;

/**
 *
 * @author Tyler Hackett
 * 
 * Fits a line to some data based on least squared error.
 * 
 * Uses a linear regression model to find the 
 * coefficients of a linear hypothesis function.
 * 
 */
public class LineFitter
{
    /**The data for training our hypothesis. */
    protected double[][] x;
    
    /**the expected value for each row of {@code x}. */
    protected double[] y;
    
    /**The learning step used in gradient descent. */
    protected double alpha = 0.01;
    
    /**The number of times to run gradient descent. */
    protected int numSteps = 1000;
    
    /**The factors of our hypothesis function. */
    protected double[] theta;
    
    /**
     *  @param x the data for fitting a curve.
     *  @param y the expected value for each row of {@code x}.
     */
    public LineFitter(double[][] x, double[] y)
    {
        this.x     = x;
        this.y     = y;
        
        this.theta = new double[x[0].length];
    }
    
    public void setTheta(double[] theta)
    {
        this.theta = theta;
    }
    
    public double[] getTheta()
    {
        return this.theta;
    }
    
    /**
     *  Fits a line to the data by finding optimal values for theta.
     * 
     *  Minimizes the cost function of our hypothesis using a gradient descent approach.
     */
    public void fit()
    {
        double[] newTheta;
        int n = x.length;
        
        for(int step = 0; step < numSteps; step++)
        {
            newTheta = theta.clone();
            
            for(int j = 0; j < newTheta.length; j++)
            {
                double error = 0;
                
                
                for(int i = 0; i < n; i ++)
                {
                    error += (predict(x[i]) - y[i])*x[i][j];
                }
                
                newTheta[j] -= alpha*(error/n);
            }
                 
            theta = newTheta;
        }
    }
    
    
    /**
     *  Calculates the cost of the given parameters in {@code theta}.
     * 
     *  The cost function is the mean squared error of every data point to the current line.
     * 
     *  @return the mean squared error between {@code data[i][j]} and {@link predict(j)}
     */
    public double cost()
    {
        double cost = 0;
        
        for(int i = 0; i < x.length; i++)
        {
            cost += Math.pow(predict(x[i]) - y[i], 2);
        }
        
        return cost / (2*x.length);
    }
    
    /**
     *  Predicts the value at a given point {@code x}.
     * 
     *  @param x the features to use in our prediction. x[0] must be 1.
     * 
     *  @return <i>theta[0]*x[0] + theta[1]*x[1] + ... + theta[n]*x[n]</i>
     */
    public double predict(double[] x)
    {
        double prediction = 0;
        
        for(int i = 0; i < x.length; i++)
        {
            prediction += theta[i] * x[i];
        }
        
        return prediction;
    }
}
