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
package salestax;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Tyler Hackett
 * 
 * Sales Tax Calculator
 */
public class SalesTax extends JFrame implements ActionListener
{
    
    /**The name of the application*/
    private static final String FRAME_TITLE = "Sales Tax Calculator";
    
    private double totalCollected = 0;
    
    /*Types of sales tax*/
    private double stateTaxRate  = 0.0675d;
    private double countyTaxRate = 0.0200d;
    
    private double totalTaxRate  = stateTaxRate + countyTaxRate;
    
    /**Custom Dialog for collecting necessary info for tax summary.*/
    private final CreationDialog creationDialog = new CreationDialog(this);
    
    /**Custom Dialog for changing the tax rates.*/
    private final ChangeRatesDialog taxRateDialog = new ChangeRatesDialog(this);
    
    private final BorderLayout mainLayout = new BorderLayout();
    private final GridLayout contentLayout = new GridLayout(7,2);
    
    private final JPanel mainPanel = new JPanel();
    private final JPanel contentPanel = new JPanel();
    
    private final JMenuBar menuBar = new JMenuBar();
        
    private final JMenu optionsMenu = new JMenu("Options");
    private final JMenuItem changeRatesItem = new JMenuItem("Change Tax Rates");
    
    private final JMenu fileMenu = new JMenu("File");
    private final JMenuItem newTaxItem = new JMenuItem("New");
    
    private final JLabel monthLabel          = new JLabel("N/A");
    private final JLabel yearLabel           = new JLabel("N/A");
    private final JLabel totalCollectedLabel = new JLabel("N/A");
    private final JLabel totalSalesLabel     = new JLabel("N/A");
    private final JLabel countyTaxLabel      = new JLabel("N/A");
    private final JLabel stateTaxLabel       = new JLabel("N/A");
    private final JLabel totalSalesTaxLabel  = new JLabel("N/A");
    
    private final DecimalFormat formatter = new DecimalFormat("#.00");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        new SalesTax();
    }
    
    public SalesTax()
    {
        //Obligatory JFrame initialization junk.
        this.setTitle(FRAME_TITLE);
        this.setVisible(true);
        this.setSize(300, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /*
        Set up the two panels.
        
        The "main" panel is only necessary for properly positioning the menu bar.
        Don't you just love UI design in Java??
        */
        
        mainPanel.setLayout(mainLayout);
        contentPanel.setLayout(contentLayout);
        
        /*Set up the file menu.*/
        
        newTaxItem.addActionListener(this);
        
        fileMenu.add(newTaxItem);
        
        /*Set up the options menu.*/
        
        changeRatesItem.addActionListener(this);
        
        optionsMenu.add(changeRatesItem);
        
        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        
        //Laying out the meat & potatoes...
        
        contentPanel.add(new JLabel("Month"));
        contentPanel.add(monthLabel);
        contentPanel.add(new JLabel("Year"));
        contentPanel.add(yearLabel);
        contentPanel.add(new JLabel("Total Collected"));
        contentPanel.add(totalCollectedLabel);
        contentPanel.add(new JLabel("Sales"));
        contentPanel.add(totalSalesLabel);
        contentPanel.add(new JLabel("County Sales Tax"));
        contentPanel.add(countyTaxLabel);
        contentPanel.add(new JLabel("State Sales Tax"));
        contentPanel.add(stateTaxLabel);
        contentPanel.add(new JLabel("Total Sales Tax"));
        contentPanel.add(totalSalesTaxLabel);
        
        /*Now that everything is ready, set up the main panel and plop it into our JFrame. */
        
        mainPanel.add(menuBar, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        this.add(mainPanel);
    }
    
    public double getStateTaxRate()
    {
        return stateTaxRate;
    }
    
    public void setStateTaxRate(double taxRate)
    {
        stateTaxRate = taxRate;
        
        updateTotalTaxRate();
    }
    
    public double getCountyTaxRate()
    {
        return countyTaxRate;
    }
    
    public void setCountyTaxRate(double taxRate)
    {
        countyTaxRate = taxRate;
        
        updateTotalTaxRate();
    }
    
    private final void updateTotalTaxRate()
    {
        totalTaxRate = stateTaxRate + countyTaxRate;
    }
    
    /**
     *  Creates a brand-spankin'-new tax summary, just for you!
     */
    public void createNewTaxSummary(String month, int year, double collected)
    {
        
        totalCollected = collected;
        
        monthLabel.setText(month);
        yearLabel.setText(String.valueOf(year));
        totalCollectedLabel.setText("$" + formatter.format(collected));
        
        updateTaxSummary();
    }
    
    /**
     * Updates the tax calculations for the same month, year, and total collected.
     */
    public void updateTaxSummary()
    {
        /*Calculate total tax (Total Collected - Total Sales)*/
        double totalTax = totalCollected - (totalCollected/(1 + totalTaxRate));
        
        double countySalesTax = totalTax*(countyTaxRate/totalTaxRate);
        double stateSalesTax  = totalTax*(stateTaxRate/totalTaxRate);
        
        totalSalesLabel.setText("$" + formatter.format(totalCollected/(1.0d + stateTaxRate + countyTaxRate)));
        countyTaxLabel.setText("$" + formatter.format(countySalesTax));
        stateTaxLabel.setText("$" + formatter.format(stateSalesTax));
        totalSalesTaxLabel.setText("$" + formatter.format(totalTax));
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == newTaxItem)
        {
            creationDialog.setVisible(true);
        }
        else if(e.getSource() == changeRatesItem)
        {
            taxRateDialog.setVisible(true);
        }
    }
}
