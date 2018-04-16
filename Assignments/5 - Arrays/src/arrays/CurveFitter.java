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

/**
 *
 * @author Tyler Hackett
 * 
 * Fits a polynomial curve to some data based on least squared error.
 * 
 * Uses a polynomial regression model to find the 
 * coefficients of a polynomial hypothesis function.
 * 
 */
public class CurveFitter
{
    /**The data for training our hypothesis. */
    private double[][] x;
    
    /**the expected value for each row of {@code x}. */
    private double[] y;
    
    /**Regularization factor. */
    private double lambda;
    
    /**The learning step used in gradient descent. */
    private double alpha = 0.1;
    
    private int numSteps = 100;
    
    /**The degree of the polynomial hypothesis function. */
    private int degree;
    
    /**The factors of our hypothesis function. */
    private double[] theta;
    
    /**
     *  @param x the data for fitting a curve.
     *  @param y the expected value for each row of {@code x}.
     *  @param degree the degree of the polynomial hypothesis function.
     */
    public CurveFitter(double[][] x, double[] y, int degree)
    {
        this.x      = x;
        this.y      = y;
        
        this.degree = degree;
        
        this.theta = new double[x[0].length];
    }
    
    /**
     *  Automatically determines a value for {@code degree}.
     * 
     * <p>{@see CurveFitter(double[][], double[], int) CurveFitter(x, y, degree)}</p>
     */
    public CurveFitter(double[][] x, double[] y)
    {
        this(x, y, 0);
        
        findDegree();
    }
    
    /**
     *  Automatically finds a reasonable value for {@code degree}.
     * 
     *  This function will essentially try to determine the degree by counting
     *  the number of critical points and inflection points in the data.
     * 
     *  For every critical point, an extra degree is added. For every
     *  inflection point, three extra degrees are added.
     */
    public void findDegree()
    {
        //TODO find degree
        degree = 4;
    }
    
    public int getDegree()
    {
        return degree;
    }
    
    public void setTheta(double[] theta)
    {
        this.theta = theta;
    }
    
    /**
     *  Fits the curve to the data.
     * 
     *  Minimizes the cost function of our hypothesis using a gradient descent approach.
     */
    public void fit()
    {
        int n = x.length;
        
        double[] newTheta = new double[theta.length];
        
        for(int step = 0; step < numSteps; step++)
        {
            newTheta = theta.clone();
            
            for(int j = 0; j < newTheta.length; j++)
            {
                double error = (predict(x[0]) - y[0]);
                
                for(int i = 0; i < n; i ++)
                {
                    error += (predict(x[i]) - y[i])*x[i][j];
                }
                
                newTheta[j] -= alpha*(error/n);
                System.out.println("newtheta["+j+"]: " + newTheta[j]);
            }
                 
            theta = newTheta;
        }
    }
    
    public void scaleFeatures()
    {
        int numFeatures = x[0].length;
        double[] mean = new double[numFeatures];
        double[] sigma = new double[numFeatures];
        
        for(int i = 0; i < numFeatures; i++)
        {
            for(int j = 0; j < x.length; j++)
            {
                mean[i] += x[j][i];
            }
            
            mean[i] /= numFeatures;
        }
        
        for(int i = 0; i < numFeatures; i++)
        {
            int rms = 0; //Root-mean-square
            
            for(int j = 0; j < x.length; j++)
            {
                rms += Math.pow(x[j][i] - mean[j], 2);
            }
            
            sigma[i] = Math.sqrt(rms/x.length);
        }
        
        for(int i = 0; i < numFeatures; i++)
        {
            for(int j = 0; j < x.length; j++)
            {
                x[j][i] = (x[j][i] - mean[i]) / sigma[i];
            }
        }
    }
    
    /**
     *  Calculates the cost of the given parameters in {@code theta}.
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
     *  @param x the features to use in our prediction. {@code x} is passed
     *           as a vararg to make single-feature predictions simpler.
     * 
     *  @return <i>theta[0] + theta[1]*x + theta[2]*x^2 + ... theta[degree]*x^(degree-1)</i>
     */
    public double predict(double... x)
    {
        double prediction = 0;
        
        for(int i = 0; i < x.length; i++)
        {
            prediction += theta[i] * x[i];//Math.pow(x[i], i);
        }
        
        return prediction;
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
