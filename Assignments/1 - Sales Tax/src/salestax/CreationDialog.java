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
 * A JDialog for collecting necessary information to compute sales tax.
 */
class CreationDialog extends TaxDialog
{
    private final JTextField monthField     = new JTextField();
    private final JTextField yearField      = new JTextField();
    private final JTextField collectedField = new JTextField();
    
    private final JButton confirmButton = new JButton("Create Tax Summary");
    
    public CreationDialog(SalesTax owner)
    {
        super(owner);
        
        this.setTitle("Enter Sales Information");
        
        this.add(new JLabel("Month"), getConstraints());
        
        getConstraints().gridx = 1;
        this.add(monthField, getConstraints()); 
        
        getConstraints().gridx = 0;
        getConstraints().gridy = 1;
        
        this.add(new JLabel("Year"), getConstraints());
        
        getConstraints().gridx = 1;
        this.add(yearField, getConstraints());
        
        getConstraints().gridx = 0;
        getConstraints().gridy = 2;
        
        this.add(new JLabel("Total Collected"), getConstraints());
        
        getConstraints().gridx = 1;
        this.add(collectedField, getConstraints()); 
        
        getConstraints().gridy = 3;
        getConstraints().gridx = 0;
        getConstraints().gridwidth = 2;
        this.add(confirmButton, getConstraints());
        
        //A kludgey way of packing things neatly. If it works then it works, right?
        this.pack();
        this.setSize(300, getHeight());
        
        confirmButton.addActionListener(this);
    }

    @Override
    public void resetFields()
    {
        monthField.setText("");
        yearField.setText("");
        collectedField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == confirmButton)
        {
            String month = monthField.getText();
            int year = 0;
            double collected = 0;
            
            if(month.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Month field cannot be empty.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try 
            {
                year = Integer.parseInt(yearField.getText());
            } catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Year field is not a valid number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try 
            {
                collected = Double.parseDouble(collectedField.getText());
            } catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Collected field is not a valid number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            ((SalesTax)getOwner()).createNewTaxSummary(month, year, collected);
            this.setVisible(false);
        }
    }
}
