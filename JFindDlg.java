import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

public class JFindDlg 
{
	JDialog jdlg;
	JLabel jlab;
	JTextField jtf;
	JButton findNxt;
	JButton cancel;
	JCheckBox matchCase;
	JRadioButton up;
	JRadioButton down;
	ButtonGroup jbg;
	int index;
	String item;
	JFindDlg(JTextArea jta)
	{
		jdlg = new JDialog((Frame)jta.getParent(), "Font", false);
		jdlg.setLayout(new FlowLayout());
		jdlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//Allows OS to place dialog.
		jdlg.setLocationByPlatform(true); 
		jdlg.setSize(300,200); 
		jlab = new JLabel("Find what:");
		jlab.setDisplayedMnemonic('n'); 
		jtf = new JTextField(20);
		jlab.setLabelFor(jtf); 
		findNxt = new JButton("Find Next");
		findNxt.setMnemonic('F');
		cancel = new JButton("Cancel");
		matchCase = new JCheckBox("Match case");
		matchCase.setMnemonic('c'); 
		up = new JRadioButton("Up");
		up.setMnemonic('U'); 
		down = new JRadioButton("Down");
		down.setMnemonic('D'); 
		index = 0; 
		item = "";
		jbg = new ButtonGroup();
		jbg.add(up);
		jbg.add(down);
		
		// Respond to the Find Next button in the dialog. 
        findNxt.addActionListener(new ActionListener() {     
        public void actionPerformed(ActionEvent le) { 
            find(jta.getCaretPosition(),jta);
        	//Hide the dialog after info is processed
            //jdlg.setVisible(false); 
          }     
        });
		
		// Respond to the Cancel button in the dialog. 
        cancel.addActionListener(new ActionListener() {     
        public void actionPerformed(ActionEvent le) { 
            // Hide the dialog after cancel is selected
            jdlg.setVisible(false); 
          }     
        });
        
        jdlg.add(jlab);
        jdlg.add(jtf);
        jdlg.add(findNxt);
        jdlg.add(cancel);
        jdlg.add(matchCase);
        jdlg.add(up);
        jdlg.add(down);


	}
	 public void find(int start, JTextArea jta) 
	 { 
		 int idx = 0;   
		 int idx2 = 0;
		 // Get the current text as a string. 
		    String str = jta.getText(); 
		    
		    // Get the string to find. 
		    String findStr = jtf.getText(); 
		    if(!(matchCase.isSelected()))
		    {
		    	String temp = findStr.substring(0, 1);
		    	String i = temp.toUpperCase(); 
		    	//String to find uppercase
		    	String u = i + findStr.substring(1);
		    	String temp2 = findStr.substring(0, 1);
		    	String i2 = temp2.toLowerCase(); 
		    	//String to find lowercase
		    	String u2 = i2 + findStr.substring(1);
		    	if(up.isSelected())
		    	{
		    		String strUp = str.substring(0, start + 1);
		    		idx = strUp.indexOf(u, 0);
		    		idx2 = strUp.indexOf(u2, 0);
		    	}
		    	else
		    	{
		    		String strDown = str.substring(start);
		    		idx = strDown.indexOf(u, start);
		    		idx2 = strDown.indexOf(u2, start);
		    	}
		    	
		    	if(idx > -1 || idx2 > -1) 
			    {           
			      // If found, set focus to text area 
			      // and move caret to the location. 
			      if(idx > -1)
			      {	
			    	  jta.setCaretPosition(idx); 
			      	  index = idx; // update the find index 
			      }
			      else
			      {
			    	  jta.setCaretPosition(idx2); 
			      	  index = idx2; // update the find index
			      }
			    } 
			    else 
			    {
			    	JOptionPane.showMessageDialog(jta,"Cannot find " + jtf.getText(),"JNotepad",
			    			  JOptionPane.INFORMATION_MESSAGE);
		 		}
		    }
		    // Finds first occurrence of the specified string. 
		    else
		    {
		    	if(up.isSelected())
		    	{
		    		String strUp = str.substring(0, start + 1);
		    		idx = strUp.indexOf(findStr, 0);
		    	}
		    	else
		    		idx = str.indexOf(findStr, start);
		    	
		    	if(idx > -1) 
			    {           
			      // If found, set focus to text area 
			      // and move caret to the location. 
			      jta.setCaretPosition(idx); 
			      index = idx; // update the find index  
			    } 
			    else 
			    {
			    	JOptionPane.showMessageDialog(jta,"Cannot find " + jtf.getText(),"JNotepad",
			    			  JOptionPane.INFORMATION_MESSAGE);
		 		}
		    }
	 }
	 public int getIndex()
	 {
		 return index;
	 }
	public String showDialog(JFrame jfrm)
    {
        jdlg.setVisible(true);
        return item;
    }
	public static void main(String[] args)
	{
		JFrame jfrm = new JFrame("Test");
		JTextArea jta = new JTextArea();
		JButton jbtn = new JButton("Find");
		jfrm.setSize(200,200);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		jfrm.setLayout(new FlowLayout()); 
		JFindDlg jfd = new JFindDlg(jta);
		
		jbtn.addActionListener(new ActionListener() {     
	        public void actionPerformed(ActionEvent le) { 
	            // Hide the dialog after cancel is selected
	            jfd.showDialog(jfrm);  
	          }     
	        });
		jfrm.add(jbtn);
		jfrm.add(jta);
		jfrm.setVisible(true);
	}
}
