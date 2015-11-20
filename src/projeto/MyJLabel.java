/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author wellitongb
 */
public class MyJLabel extends JLabel{
    
    public MyJLabel(Icon Originalimage, Icon image, JLabel label, JFrame framePatern, JFrame frameChild) {
        super(image);
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                label.setIcon(Originalimage);
                JDialog dialog = new JDialog(framePatern, frameChild.getTitle(), true);
                dialog.setJMenuBar(frameChild.getJMenuBar());
                dialog.getContentPane().add(frameChild.getContentPane());
                dialog.pack();
                dialog.setVisible(true);  
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
               
            }
            
        });
        
    }
    
    
    public MyJLabel(String text){
        super(text);
    }
}

