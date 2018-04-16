/**
 * Name: Ibrahim, Awais
 * Project: #3
 * Due: 3/10/18
 * Course: CS24501-w18
 *
 * Description:
 * Create JFontChooser to be used in the Windows version of Notepad.
 */ 
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;

public class JFontChooser 
{
    private Color color;
    private Font font;
    private String fontName;
    private static int fontStyle;
    private int fontSize;
    JDialog jdlg;

    JFontChooser(JFrame jfrm)
    {
        color = Color.BLACK;
        fontName = "Courier New";
        fontStyle = Font.PLAIN;
        fontSize = 12;
        font = new Font(fontName, fontStyle, fontSize);
        jdlg = new JDialog(jfrm, "Font", true);
        jdlg.setSize(275, 600); 
        jdlg.getContentPane().setLayout(new BoxLayout(jdlg.getContentPane(), BoxLayout.Y_AXIS));
        jdlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        String strStyle = fntToString(fontStyle);
        
        JLabel fontLab = new JLabel("Font:");
        JLabel styleLab = new JLabel("Font style:");
        JLabel sizeLab = new JLabel("Size:");
        JLabel sampleLab = new JLabel("Sample");
        JLabel sampText = new JLabel("AaBbYyZz");
        sampText.setFont(font); 
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        JButton fontColor = new JButton("Font Color");
        JTextField jtf = new JTextField(fontName, 0);
        jtf.setEditable(false);
        JTextField jtfFS = new JTextField(strStyle, 0);
        jtfFS.setEditable(false);
        JTextField jtfS = new JTextField("" + fontSize, 0);
        jtfS.setEditable(false);
        JPanel f = new JPanel();
        JPanel fc = new JPanel();
        JPanel c = new JPanel();
        
        JPanel jpan1 = new JPanel();
        JPanel jpan2 = new JPanel();
        JPanel jpan3 = new JPanel();
        jpan1.setLayout(new GridLayout(1,2)); 
        jpan2.setLayout(new GridLayout(1,2));
        jpan3.setLayout(new GridLayout(1,2));

        f.add(fontLab);
        f.add(jtf);
        jdlg.add(f);
                
        DefaultListModel dlm = new DefaultListModel();
        DefaultListModel dlmFS = new DefaultListModel();
        DefaultListModel dlmS = new DefaultListModel();
        Integer[] fStyle = {Font.PLAIN,Font.ITALIC,Font.BOLD, Font.BOLD | Font.ITALIC}; //0 2 1 3
        String[] strFStyle = {"Regular","Italic","Bold","Bold Italic"};
        Integer[] fSize = {8,9,10,11,12,14,16,18,20,22,24,26,28,36,48,72};
        GraphicsEnvironment gEnv = 
        GraphicsEnvironment.getLocalGraphicsEnvironment();
        //Creates array of all available fonts
        String fonts[] = gEnv.getAvailableFontFamilyNames();
        for(int i = 0; i < fonts.length; i++)
            dlm.addElement(fonts[i]);
        for(int j = 0; j < fStyle.length; j++)
            dlmFS.addElement(strFStyle[j]);
        for(int k = 0; k < fSize.length; k++)
            dlmS.addElement(fSize[k]);
        JList jlst = new JList(dlm);
        jlst.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION); 
        JScrollPane jsp = new JScrollPane(jlst, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
              JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Set the preferred size of the scroll pane.  
        jsp.setPreferredSize(new Dimension(250, 120));
        //Set initial value for font list
        jlst.setSelectedValue(fontName, true);
        
        JList jlstFS = new JList(dlmFS);
        jlstFS.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION); 
        JScrollPane jspFS = new JScrollPane(jlstFS, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
              JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Set the preferred size of the scroll pane.  
        jspFS.setPreferredSize(new Dimension(250, 100));
      //Set initial value for font style list
        jlstFS.setSelectedValue(fntToString(fontStyle), false);
        
        JList jlstS = new JList(dlmS);
        jlstS.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION); 
        JScrollPane jspS = new JScrollPane(jlstS, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
              JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Set the preferred size of the scroll pane.  
        jspS.setPreferredSize(new Dimension(250, 120));
      //Set initial value for font size list
        jlstS.setSelectedValue(fontSize, true);
        
        fontLab.setLabelFor(jsp);
        styleLab.setLabelFor(jspFS);
        sizeLab.setLabelFor(jspS);
        
        // Respond to the Cancel button in the dialog. 
        cancel.addActionListener(new ActionListener() {     
        public void actionPerformed(ActionEvent le) { 
            // Hide the dialog after cancel is selected
            jdlg.setVisible(false); 
          }     
        });
        
        //Responds to ok button in dialog
        ok.addActionListener(new ActionListener() {     
        public void actionPerformed(ActionEvent le) { 
        	Object valuesF[] = jlst.getSelectedValues();
            Object valuesFS[] = jlstFS.getSelectedValues();
            Object valuesS[] = jlstS.getSelectedValues();
            	
            font = new Font((String)valuesF[0],fntToInt((String)valuesFS[0]), (int)valuesS[0]);
            sampText.setFont(font); 
            
            // Hide the dialog after all info is processed
            jdlg.setVisible(false); 
        }     
     });
        //Action Listener to open Color Chooser Dialog
        fontColor.addActionListener(new ActionListener() {     
            public void actionPerformed(ActionEvent le) { 
                // Hide the dialog after cancel is selected
                //jdlg.setVisible(false); 
            	color = JColorChooser.showDialog(null,"Choose Color",Color.BLACK);
              }     
            });
        
     // Add selection listener for the Font list. 
        jlst.addListSelectionListener(new ListSelectionListener() {  
        public void valueChanged(ListSelectionEvent le) {  
        	// Get the index of the changed item.
            Object values[] = jlst.getSelectedValues();
            // Confirm that an item has been selected.
            if (values.length != 0) 
            {
                fontName = (String)values[0];
            	sampText.setFont(new Font((String)values[0],fontStyle, fontSize)); 
                jtf.setText((String)values[0]);
            	return;
            } 
        }  
       });  
        
     // Add selection listener for the Font Style list. 
        jlstFS.addListSelectionListener(new ListSelectionListener() {  
        public void valueChanged(ListSelectionEvent le) {  
        	// Get the index of the changed item.
            Object values[] = jlstFS.getSelectedValues();
            // Confirm that an item has been selected.
            if (values.length != 0) 
            {
                fontStyle = fntToInt((String)values[0]);
            	sampText.setFont(new Font(fontName,fntToInt((String)values[0]), fontSize)); 
                jtfFS.setText((String)values[0]); 
                return;
            } 
        }  
       });  
        
     // Add selection listener for the Font Size list. 
        jlstS.addListSelectionListener(new ListSelectionListener() {  
        public void valueChanged(ListSelectionEvent le) {  
        	// Get the index of the changed item.
            Object values[] = jlstS.getSelectedValues();
            // Confirm that an item has been selected.
            if (values.length != 0) 
            {
                fontSize = (int)values[0];
            	sampText.setFont(new Font(fontName,fontStyle,(int)values[0])); 
                jtfS.setText("" + values[0]); 
            	return;
            } 
        }  
       });
        
        jdlg.add(jsp);
        fc.add(styleLab);
        fc.add(jtfFS);
        jdlg.add(fc);
        jdlg.add(jspFS);
        c.add(sizeLab);
        c.add(jtfS);
        jdlg.add(c);
        jdlg.add(jspS);
        
        jpan1.add(sampleLab);
        jpan1.add(sampText);
        jpan3.add(ok);
        jpan3.add(cancel);
        
        jdlg.add(jpan1);
        jdlg.add(fontColor);
        jdlg.add(jpan3);

        jdlg.setVisible(false);
    }
    
    public void setDefault(Object initial)
    {
        try
        {
            font = (Font)initial;
            fontName = font.getFontName();
            fontStyle = font.getStyle();
            fontSize = font.getSize();
        }
        catch(Exception e)
        {          
        }
        
        try
        {
            color = (Color)initial;
        }
        catch(Exception e)
        {           
        }
    }
    
    public static String fntToString(int font)
    {
    	String strStyle = "";
    	if(fontStyle == 0)
        	strStyle = "Regular";
        else if(fontStyle == 1)
        	strStyle = "Bold";
        else if(fontStyle == 2)
        	strStyle = "Italics";
        else if(fontStyle == 3)
        	strStyle = "Bold Italic";
    	
    	return strStyle;
    }
    
    public static int fntToInt(String font)
    {
    	if(font.equals("Regular"))
    		return 0;
    	else if(font.equals("Bold"))
    		return 1;
    	else if(font.equals("Italic"))
    		return 2;
    	else
    		return 3;
    }
    
    public Font getFont()
    {
        return font;
    }
    
    public Color getColor()
    {
    	return color;
    }
    
    public String getStyle()
    {
    	return fntToString(fontStyle);
    }
    
    public int getSize()
    {
    	return fontSize;
    }
    
    public boolean showDialog(JFrame jfrm)
    {
        jdlg.setVisible(true);
        return true;
    }
}
