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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

/**
 *
 * @author Tyler Hackett
 * 
 * A quick parent class for the custom Dialogs in this project.
 */
public abstract class TaxDialog extends JDialog implements ActionListener
{
    private final GridBagConstraints constraints = new GridBagConstraints(); 
    
    public TaxDialog(SalesTax owner)
    {
        super(owner);
        
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setLocation(owner.getLocation());
        this.setLayout(new GridBagLayout());
        
        getConstraints().gridwidth  = 1;
        getConstraints().gridheight = 1;
        
        getConstraints().weightx = 1;
        getConstraints().fill = GridBagConstraints.BOTH;
    }
    
    @Override
    public void setVisible(boolean visible)
    {
        super.setVisible(visible);
        
        /*Clear the input fields every time the dialog is hidden.*/
        if(visible == false)
        {
            resetFields();
        }
        else
        {
            this.setLocation(getOwner().getLocation());
        }
    }
    
    protected final GridBagConstraints getConstraints()
    {
        return constraints;
    }
    
    /**
     * Resets all of the fields inside of this Dialog to their proper initial state.
     */
    public abstract void resetFields();
}
