/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.GUI.GUI_HD;

import com.sun.jdi.Value;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author vohau
 */
public class TienDoTableCellRender extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
       
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        JProgressBar tienDoBar = new JProgressBar(0,100);
        if(value!=null)
        {          
            try 
            {
            int processValue =  Integer.parseInt(value.toString());
            tienDoBar.setValue(processValue);
            if(processValue<=10)
            {
                tienDoBar.setForeground(Color.red); 
            }
            else if(processValue<=30)
            {
                 tienDoBar.setForeground(Color.orange); 
            }
             else if(processValue<=80)
            {
                 tienDoBar.setForeground(Color.yellow); 
            }
             else if(processValue<=100)
            {
                 tienDoBar.setForeground(Color.green); 
            }
            } 
            catch (Exception e) 
            {
              
            }
           
        }
      
       
        return tienDoBar;
    }
}
