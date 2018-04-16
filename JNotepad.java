/**
 * Name: Ibrahim, Awais
 * Project: #3
 * Due: 3/12/18
 * Course: CS24501-w18
 *
 * Description:
 * Create the Windows version of Notepad while also incorporating JFontChooser.
 */ 

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JNotepad implements ActionListener, DocumentListener
{
    private boolean changed;
	JFrame jfrm;
    JTextArea jta;
    JFontChooser jfontc;
    JFileChooser jfilec;
    JPopupMenu jpu;
    ImageIcon img;
    DateFormat format;
    Date date;
    File file;
    String fileName;
    String searchItem;
    JCheckBoxMenuItem jmiWordWrap;
    JFindDlg jfd;
    int index;
    JNotepad()
    {
        changed = false;
        index = 0;
        searchItem = "";
    	jta = new JTextArea();
    	jta.getDocument().addDocumentListener(this);
    	jta.setCaretPosition(0); 
        fileName = "Untitled";
        jfrm = new JFrame(fileName + "- JNotepad");
        //Lets the OS determine the frame location
        jfrm.setLocationByPlatform(true); 
        JMenuBar jmb = new JMenuBar();
        jpu = new JPopupMenu();
        jfd = new JFindDlg(jta);
        jfrm.setLayout(new BorderLayout());
        jfrm.setSize(600,600);
        jfrm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        img = new ImageIcon("JNotepad.png");
        jfrm.setIconImage(img.getImage());
        JScrollPane jscrlp = new JScrollPane(jta); 
        //jscrlp.setPreferredSize(jfrm.getSize()); 
        jfontc = new JFontChooser(jfrm);
        jfilec = new JFileChooser();
        jta.setFont(new Font("Courier New",Font.PLAIN,12));
        jta.setForeground(Color.BLACK); 
        
        //File Menu Components
        JMenu jmFile = new JMenu("File");
        jmFile.setMnemonic('F'); 
        JMenuItem jmiNew = new JMenuItem("New",KeyEvent.VK_N);
        jmiNew.setMnemonic('N');
        jmiNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
        JMenuItem jmiOpen = new JMenuItem("Open...",KeyEvent.VK_O);
        jmiOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
        JMenuItem jmiSave = new JMenuItem("Save", KeyEvent.VK_S);
        jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
        JMenuItem jmiSaveAs = new JMenuItem("Save As...");
        JMenuItem jmiPageSetup = new JMenuItem("Page Setup...");
        jmiPageSetup.setMnemonic('u');
        JMenuItem jmiPrint = new JMenuItem("Print...",KeyEvent.VK_P);
        jmiPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
        JMenuItem jmiExit = new JMenuItem("Exit");
        jmiExit.setMnemonic('x');
        
        jmFile.add(jmiNew);
        jmFile.add(jmiOpen);
        jmFile.add(jmiSave);
        jmFile.add(jmiSaveAs);
        jmFile.addSeparator();
        jmFile.add(jmiPageSetup);
        jmFile.add(jmiPrint);
        jmFile.addSeparator();
        jmFile.add(jmiExit);
        jmb.add(jmFile);
        
        //Edit Menu Components
        JMenu jmEdit = new JMenu("Edit");
        jmEdit.setMnemonic('E');
        JMenuItem jmiUndo = new JMenuItem("Undo");
        JMenuItem jmiCut = new JMenuItem("Cut", KeyEvent.VK_X);
        jmiCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
        jmiCut.setActionCommand("Cut");
        JMenuItem jmiCopy = new JMenuItem("Copy",KeyEvent.VK_C);
        jmiCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
        jmiCopy.setActionCommand("Copy");
        JMenuItem jmiPaste = new JMenuItem("Paste",KeyEvent.VK_V);
        jmiPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK));
        jmiPaste.setActionCommand("Paste");
        JMenuItem jmiDelete = new JMenuItem("Delete",KeyEvent.VK_DELETE);
        jmiDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
        JMenuItem jmiFind = new JMenuItem("Find...",KeyEvent.VK_F);
        jmiFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,InputEvent.CTRL_MASK));
        JMenuItem jmiFindNext = new JMenuItem("Find Next");
        JMenuItem jmiReplace = new JMenuItem("Replace...",KeyEvent.VK_H);
        jmiReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,InputEvent.CTRL_MASK));
        JMenuItem jmiGoTo = new JMenuItem("Go To...",KeyEvent.VK_G);
        jmiGoTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,InputEvent.CTRL_MASK));
        JMenuItem jmiSelectAll = new JMenuItem("Select All",KeyEvent.VK_A);
        jmiSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
        JMenuItem jmiTimeDate = new JMenuItem("Time/Date",KeyEvent.VK_F5);
        jmiTimeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
        
        jmEdit.add(jmiUndo);
        jmEdit.addSeparator();
        jmEdit.add(jmiCut);
        jmEdit.add(jmiCopy);
        jmEdit.add(jmiPaste);
        jmEdit.add(jmiDelete);
        jmEdit.addSeparator();
        jmEdit.add(jmiFind);
        jmEdit.add(jmiFindNext);
        jmEdit.add(jmiReplace);
        jmEdit.add(jmiGoTo);
        jmEdit.addSeparator();
        jmEdit.add(jmiSelectAll);
        jmEdit.add(jmiTimeDate);
        jmb.add(jmEdit);
        
        JMenu jmFormat = new JMenu("Format");
        jmFormat.setMnemonic('o');
        jmiWordWrap = new JCheckBoxMenuItem("Word Wrap");
        jmiWordWrap.setMnemonic('W');
        JMenuItem jmiFont = new JMenuItem("Font...");
        jmiFont.setMnemonic('F');
        
        jmFormat.add(jmiWordWrap);
        jmFormat.add(jmiFont);
        jmb.add(jmFormat);
        
        JMenu jmView = new JMenu("View");
        jmView.setMnemonic('V');
        JCheckBoxMenuItem jmiStatusBar = new JCheckBoxMenuItem("Status Bar");
        jmiStatusBar.setMnemonic('S');
        
        jmView.add(jmiStatusBar);
        jmb.add(jmView);
        
        JMenu jmHelp = new JMenu("Help");
        jmHelp.setMnemonic('H');
        JMenuItem jmiViewHelp = new JMenuItem("View Help");
        jmiViewHelp.setMnemonic('H');
        JMenuItem jmiAbout = new JMenuItem("About JNotepad");
        
        jmHelp.add(jmiViewHelp);
        jmHelp.addSeparator();
        jmHelp.add(jmiAbout);
        jmb.add(jmHelp);
        JMenuItem[] arr = {jmiNew,jmiOpen,jmiSave,jmiSaveAs,jmiPageSetup,jmiPrint,jmiExit,
        		jmiUndo,jmiCut,jmiCopy,jmiPaste,jmiDelete,jmiFind,jmiFindNext,jmiReplace,
        		jmiGoTo,jmiSelectAll,jmiTimeDate,jmiWordWrap,jmiFont,jmiStatusBar,
        		jmiViewHelp,jmiAbout};
        for(int i = 0; i < arr.length; i++)
        	arr[i].addActionListener(this);
        
        jpu.add(jmiCut);
        jpu.add(jmiCopy);
        jpu.add(jmiPaste);
        
     // Add a listener for for the popup trigger. 
        jta.addMouseListener(new MouseAdapter() {  
          public void mousePressed(MouseEvent me) { 
            if(me.isPopupTrigger())  
              jpu.show(me.getComponent(), me.getX(), me.getY()); 
          }  
          public void mouseReleased(MouseEvent me) { 
            if(me.isPopupTrigger())  
              jpu.show(me.getComponent(), me.getX(), me.getY()); 
          }  
        }); 
        jfrm.addWindowListener(new WindowAdapter() {
        	   public void windowClosing(WindowEvent evt) {
        	     jmiExit.doClick();
        	   }
        	  });
  
        jfrm.add(jscrlp, BorderLayout.CENTER);
        jfrm.setJMenuBar(jmb);
        jfrm.setVisible(true);
    }
    
    // Handle menu item action events. 
    public void actionPerformed(ActionEvent ae) 
    {  
      // Get the action command from the menu selection. 
      String menuItem = ae.getActionCommand(); 
      //Components for Save dialog
      JButton jbns = new JButton("Save");
	  JButton jbnds = new JButton("Don't Save");
	  JButton jbnc = new JButton("Cancel");
	  Object[] arr = {jbns,jbnds,jbnc};
	  JOptionPane msg = new JOptionPane("Do you want to save changes to Untitled?", 
			  JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_CANCEL_OPTION,img,arr,jbns);
	  final JDialog dlg = msg.createDialog("JNotepad");
	  dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	  jbns.addActionListener(new ActionListener() {     
	      public void actionPerformed(ActionEvent le) { 
	        saveAs();   	        
	        dlg.setVisible(false);
	        if(menuItem.equals("Exit"))
	        	System.exit(0);
	      }     
	    }); 
	  jbnds.addActionListener(new ActionListener() {     
	      public void actionPerformed(ActionEvent le) { 
	    	  if(menuItem.equals("Open..."))
	    		  open();
	        dlg.setVisible(false);
	        if(menuItem.equals("New"))
	        	jta.setText("");
	        if(menuItem.equals("Exit"))
	        	System.exit(0);
	      }     
	    }); 
	  jbnc.addActionListener(new ActionListener() {     
	      public void actionPerformed(ActionEvent le) { 
	    	  dlg.setVisible(false);
	      }     
	    }); 
      
      // If user chooses Exit, then check if file is saved 
	  if(menuItem.equals("New"))
      {
    	  try
    	  {
    		  String text = jta.getText();
    		  if(fileName.equals("Untitled") && !(text.equals("")))
    		  {
    			    dlg.setVisible(true); 			 
    		  }
    		  else if(fileName.equals("Untitled") && text.equals(""))
    			  newFile();
    		  else
    		  {	  
    			  if(changed == true)
        			  save();
    			  jta.setText(""); 
    			  jfrm.setTitle(fileName + "- JNotePad");
    		  }
    	  }
    	  catch(NullPointerException e)  
    	  {		
    		  open();
    	  }
      }
	  else if(menuItem.equals("Open..."))
      { 
    	  try
    	  {
    		  String text = jta.getText();
    		  if(fileName.equals("Untitled") && !(text.equals("")))
    		  {
    			    dlg.setVisible(true); 			 
    		  }
    		  else
    		  {	  
    			  open();   		  
    		  }
    	  }
    	  catch(NullPointerException e)  
    	  {		
    		  open();
    	  }
      }
      else if(menuItem.equals("Save"))
      {
    	  if(fileName.equals("Untitled"))
    		  saveAs();
    	  else
    	  {  
    		  if(changed == true)
    			  save();
    	  }
      }
      else if(menuItem.equals("Save As..."))
      {
    	  saveAs();
      }
	  else if(menuItem.equals("Exit")) 
      {
    	  String text = jta.getText();
    	  try
    	  {   		  
    		  if(fileName.equals("Untitled") && !(text.equals("")))
    		  {
    			    dlg.setVisible(true); 			 
    		  }
    		  else if(fileName.equals("Untitled") && (text.equals("")))
    		  {	  
    			  System.exit(0);   		  
    		  }
    		  else
    		  {
    			  save();
    			  System.exit(0);
    		  }
    	  }
    	  catch(NullPointerException e)  
    	  {		
    		  open();
    	  }
      }
      else if(menuItem.equals("Cut"))
    	  jta.cut();
      else if(menuItem.equals("Copy"))
    	  jta.copy();
      else if(menuItem.equals("Paste"))
    	  jta.paste();
      else if(menuItem.equals("Delete"))
      {
    	  try
    	  {
    		  jta.setText(jta.getText().replace(jta.getSelectedText(),""));
    	  }
    	  catch(NullPointerException e)  
    	  {		  
    	  }
      }
      else if(menuItem.equals("Find..."))
      {
    	  searchItem = jfd.showDialog(jfrm);
      }
      else if(menuItem.equals("Find Next"))
      {
    	  jfd.find(jta.getCaretPosition(), jta); 
      }
      else if(menuItem.equals("Select All"))
    	  jta.selectAll();
      else if(menuItem.equals("Time/Date"))
      {
    	//Creates the Time/Date format for when Time/Date is selected
    	  format = new SimpleDateFormat("h:mm a MM/d/YYYY");
          date = new Date();
    	  jta.replaceSelection(format.format(date)); 
      }
      else if(menuItem.equals("Word Wrap"))
      {
    	  if( jmiWordWrap.isSelected())
    	  {
    		  jmiWordWrap.setSelected(true);
    		  jta.setLineWrap(true);
    	  }
    	  else
    	  {
    		  jmiWordWrap.setSelected(false);
    		  jta.setLineWrap(false);
    	  }
      }
      else if(menuItem.equals("Font..."))
      {
    	  jfontc.showDialog(jfrm);
    	  jta.setFont(jfontc.getFont());
		  jta.setForeground(jfontc.getColor());
      }
      else if(menuItem.equals("About JNotepad"))
      {	
    	  JOptionPane.showMessageDialog(jfrm,"(c) Awais Ibrahim","About JNotepad",
    			  JOptionPane.INFORMATION_MESSAGE,img); 
      }
    }  
    
    public void insertUpdate(DocumentEvent e)
    {
      changed = true;
    }
    public void removeUpdate(DocumentEvent e)
    {
      changed = true;
    }
    public void changedUpdate(DocumentEvent e)
    {
      changed = true;
    }
    public void open()
    {
    	FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        ".txt & .Java files", "txt", "java");
        jfilec.setFileFilter(filter);
        int result = jfilec.showOpenDialog(jfrm);	    
	  
        if(result == JFileChooser.APPROVE_OPTION)
        {
        	file = jfilec.getSelectedFile();
        	fileName = file.getName();
        	try
        	{
        		BufferedReader in = new BufferedReader(new FileReader(file));
        		String line = in.readLine();
        		jta.setText(""); 
        		while(line != null)
        		{
        			jta.append(line + "\n");
        			line = in.readLine();
        		}
        		in.close();
        		jfrm.setTitle(fileName + "- JNotePad");
        		jta.setCaretPosition(0);
        	}
        	catch(Exception e)
        	{     	
        		JOptionPane.showMessageDialog(jfrm, "File not found. Check the file name and try again.", 
        			"Open", JOptionPane.ERROR_MESSAGE);
        		open();
        	}  
    	}
    }
    
    public void saveAs()
    {
    	JFileChooser saveAs = new JFileChooser();
    	saveAs.setApproveButtonText("Save");
    	int result = saveAs.showOpenDialog(jfrm);
    	if (result != JFileChooser.APPROVE_OPTION) 
    		return;

        file = new File(saveAs.getSelectedFile() + ".txt"); 
        fileName = file.getName();
        BufferedWriter outFile = null;
        try 
        {
           outFile = new BufferedWriter(new FileWriter(file));
           jta.write(outFile);   
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
        }
        finally 
        {
           if (outFile != null) 
           {
              try 
              {
                 outFile.close();
              } 
              catch (IOException e) 
              {
              }
           }
        }
         jfrm.setTitle(fileName + "- JNotePad");   
    }
    
    public void save()
    {
    	BufferedWriter outFile = null;
        try 
        {
           outFile = new BufferedWriter(new FileWriter(file));
           jta.write(outFile);   
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
        }
        finally 
        {
           if (outFile != null) 
           {
              try 
              {
                 outFile.close();
              } 
              catch (IOException e) 
              {
              }
           }
        }   
    }
    public void newFile()
    {
    	SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                new JNotepad();
            }
        });
    }
    public static void main(String args[]) 
    {
        // Create the frame on the event dispatching thread. 
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                new JNotepad();
            }
        });
    }
}
