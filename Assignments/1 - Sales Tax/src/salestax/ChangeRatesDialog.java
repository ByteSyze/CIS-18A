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

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Tyler Hackett
 * 
 * A JDialog for changing the tax rates.
 */
public class ChangeRatesDialog extends TaxDialog
{
    private final JTextField countyTaxField = new JTextField();
    private final JTextField stateTaxField = new JTextField();
    
    private final JButton confirmButton = new JButton("Update Tax Rates");
    
    public ChangeRatesDialog(SalesTax owner)
    {
        super(owner);
        
        this.setTitle("Change Tax Rates");
        
        this.add(new JLabel("County Tax Rate"), getConstraints());
        
        getConstraints().gridx = 1;
        
        this.add(countyTaxField, getConstraints());
        
        getConstraints().gridx = 0;
        getConstraints().gridy = 1;
        
        this.add(new JLabel("State Tax Rate"), getConstraints());
        
        getConstraints().gridx = 1;
        
        this.add(stateTaxField, getConstraints());
        
        getConstraints().gridx = 0;
        getConstraints().gridy = 2;
        
        getConstraints().gridwidth = 2;
        
        this.add(confirmButton, getConstraints());
        
        //A kludgey way of packing things neatly. If it works then it works, right?
        this.pack();
        this.setSize(300, getHeight());
        
        confirmButton.addActionListener(this);
        
        resetFields();
    }

    @Override
    public void resetFields()
    {
        countyTaxField.setText(String.valueOf(((SalesTax)getOwner()).getCountyTaxRate()));
        stateTaxField.setText(String.valueOf(((SalesTax)getOwner()).getStateTaxRate()));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == confirmButton)
        {
            double countyTax = 0;
            double stateTax = 0;
            
            try 
            {
                countyTax = Double.parseDouble(countyTaxField.getText());
            } catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "County Tax field is not a valid number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try 
            {
                stateTax = Double.parseDouble(stateTaxField.getText());
            } catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "State Tax field is not a valid number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            ((SalesTax)getOwner()).setCountyTaxRate(countyTax);
            ((SalesTax)getOwner()).setStateTaxRate(stateTax);
            
            ((SalesTax)getOwner()).updateTaxSummary();
            
            this.setVisible(false);
        }
    }
}
