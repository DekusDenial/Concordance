// Team#8, Cong Tu, Yan Chen, Yung Lam

package finalproject;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;

public class concordance {
	
	private HashMap<String,LinkedList> hashmap1=new HashMap<String,LinkedList>();
	private HashMap<String,LinkedList> hashmap2=new HashMap<String,LinkedList>();
	private HashMap<String,LinkedList> hashmap3=new HashMap<String,LinkedList>();
	private HashMap<String,LinkedList> hashmap4=new HashMap<String,LinkedList>();
	private ArrayList lineoftoken;
	private int lineNumber;
	static private JTextArea scene1=new JTextArea();
	static private JTextArea scene2=new JTextArea();
	static private JTextArea scene3=new JTextArea();
	static private JTextArea scene4=new JTextArea();
	JButton search = new JButton("Search");
	JButton scene1b = new JButton("Scene 1");
	JButton scene2b = new JButton("Scene 2");
	JButton scene3b = new JButton("Scene 3");
	JButton scene4b = new JButton("Scene 4");

	static private final String[] allCharacterNamesAbbre={"Glo.", "Clar.", "Brak.", "Hast.",
		"Anne.", "Riv.", "Grey.", "Q. Eliz.", "Buck.", "Stan.", "Q. Mar.", "Dors.", "Cates", 
		"First Murd", "Sec Murd"};
	//private ArrayList<String> RemoveNarr=new ArrayList<String>();
	private JTextField textsearched=new JTextField("Enter a word to search...");
	private JTextArea result=new JTextArea("Concordance of The Tragedy of King Richard the Third\nAct 1 Scene 1 to 4 of the edited versions...");


	public void ReadAndBuild(String filename1,String filename2,String filename3,String filename4)
	{
		this.ReadTheScript(filename1, scene1);
		this.HashToTable(hashmap1);
		this.ReadTheScript(filename2, scene2);
		this.HashToTable(hashmap2);
		this.ReadTheScript(filename3, scene3);
		this.HashToTable(hashmap3);
		this.ReadTheScript(filename4, scene4);
		this.HashToTable(hashmap4);
	}

	public void ReadTheScript(String filename, JTextArea scene)
	{
		try
		{
			InputStreamReader fileIN = new InputStreamReader(getClass().getResourceAsStream(filename));
			BufferedReader buffIN = new BufferedReader(fileIN); 
			String lineRead;
			lineoftoken=new ArrayList();
			while ((lineRead=buffIN.readLine())!=null)  // if there is still something to read in
			{
				scene.append(lineRead+"\n");
				if(lineRead=="\n" || lineRead=="" || lineRead.isEmpty())
					continue;
				StringTokenizer toker=new StringTokenizer(lineRead," \t\n\r\f,.?!;:\"[](){}()");
				lineoftoken.add(Collections.list(toker)); // this add each separated words into the listarray
				// doesn't care what the length of the word is now
			}
			buffIN.close();
			fileIN.close();
		}
		catch (IOException event)
		{
			System.out.println("Exception Occurs: "+event);
		}
	}

	public void HashToTable(HashMap<String,LinkedList> hashmap)
	{// now lineoftoken should be an arraylist of "line", each line contain all the word on the line
		// the size of lineoftoken will be equal to the total line of the text file
		for(lineNumber=0; lineNumber<lineoftoken.size();lineNumber++) //line by line
		{
			ArrayList currentLine=(ArrayList)lineoftoken.get(lineNumber); // convert line of string to arraylist

			for(int wordIndex=0;wordIndex<currentLine.size();wordIndex++) // now from word to word
			{
				String temp=((String)currentLine.get(wordIndex)).toLowerCase(); // convert to lower case first since the hashtable keys are all lowercase
				if(temp.length()<4) // catalog words of 4 or more chars only
					continue;       // this will skip most of the character name abbreviation
				// since some are less than 4 chars after tokenized
				LinkedList<Integer> placesOfOccurrence;	
				if(!hashmap.containsKey(temp))  // if first time showing up
				{
					placesOfOccurrence=new LinkedList<Integer>();  // make new linked list
					hashmap.put(temp, placesOfOccurrence);
				}
				else // already is a key and has linked list as value?
					placesOfOccurrence=(LinkedList)hashmap.get(temp); // get the old linked list

				if(!placesOfOccurrence.contains(lineNumber+1))
				{placesOfOccurrence.addLast(lineNumber+1);} // refresh it with new link
			}	
		}	
	}

	public void listOccurrence(String word)
	{
		String ind;
		String total=""; // clear up the textarea everytime a new search result found
		word=word.toLowerCase();
		if (hashmap1.containsKey(word))
		{
			ind=hashmap1.get(word).toString();
			total=total+"Act 1 Scene 1, line: "+ind+"\n\n";
		}
		if (hashmap2.containsKey(word))
		{
			ind=hashmap2.get(word).toString();
			total=total+"Act 1 Scene 2, line: "+ind+"\n\n";
		}
		if (hashmap3.containsKey(word))
		{
			ind=hashmap3.get(word).toString();
			total=total+"Act 1 Scene 3, line: "+ind+"\n\n";
		}
		if (hashmap4.containsKey(word))
		{
			ind=hashmap4.get(word).toString();
			total=total+"Act 1 Scene 4, line: "+ind+"\n\n";
		}
		if (total.isEmpty())
		{
			total="No result is found! Please try another word!...";
		}
		result.setText(total);
	}

	public void tester()
	{
		JFrame tester=new JFrame("Spring 2009 CSC 221 Final Project by Team 8 ( Cong Tu, Yan Chen, Yung Lam )");
		tester.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); // closes all frame at one time
		tester.setSize(607,560); 
		tester.setVisible(true);
		tester.setLocation(150, 100);
		tester.setLayout(null); // set this layout so we can add component wherever we want
		tester.setResizable(false);

		textsearched.setFont(new Font(textsearched.getFont().getFamily(), Font.BOLD, 16));
		JLabel imglabel1 = new JLabel();
		JLabel imglabel2 = new JLabel();
		JLabel imglabel3 = new JLabel();
		JLabel click2view=new JLabel("Click to view each scene");
		click2view.setFont(new Font(textsearched.getFont().getFamily(), Font.BOLD, 14));

		JScrollPane scrollPane=new JScrollPane(result); // the scrollpane add scroll to the textarea
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		imglabel1.setIcon(new ImageIcon(getClass().getResource("1.jpg")));
		imglabel2.setIcon(new ImageIcon(getClass().getResource("2.jpg")));
		imglabel3.setIcon(new ImageIcon(getClass().getResource("3.jpg")));
		click2view.setForeground(Color.RED);

		tester.add(textsearched); //add every component we have
		tester.add(imglabel1);
		tester.add(imglabel2);
		tester.add(imglabel3);
		tester.add(click2view);
		tester.add(search);
		tester.add(scene1b);
		tester.add(scene2b);
		tester.add(scene3b);
		tester.add(scene4b);
		tester.add(scrollPane);

		imglabel1.setBounds(0, 0, 200, 299);  // set components' location inside the frame
		imglabel2.setBounds(200, 0, 200, 299);
		imglabel3.setBounds(400, 0, 200, 299);
		textsearched.setBounds(0,300, 400, 30);
		search.setBounds(401, 300, 200, 29);
		click2view.setBounds(5, 330, 195, 29);	
		scene1b.setBounds(198, 330, 100, 29);
		scene2b.setBounds(299, 330, 100, 29);
		scene3b.setBounds(400, 330, 100, 29);
		scene4b.setBounds(501, 330, 100, 29);
		scrollPane.setBounds(0,360, 602, 170);

		textsearched.setBackground(Color.YELLOW);
		search.setFont(new Font(textsearched.getFont().getFamily(), Font.BOLD, 14));
		scene1b.setFont(new Font(textsearched.getFont().getFamily(), Font.BOLD, 14));
		scene2b.setFont(new Font(textsearched.getFont().getFamily(), Font.BOLD, 14));
		scene3b.setFont(new Font(textsearched.getFont().getFamily(), Font.BOLD, 14));
		scene4b.setFont(new Font(textsearched.getFont().getFamily(), Font.BOLD, 14));
		result.setBackground(Color.LIGHT_GRAY);
		result.setFont(new Font(textsearched.getFont().getFamily(), Font.BOLD, 14));
		result.setLineWrap(true);
		result.setEditable(false);
		scene1.setBackground(Color.LIGHT_GRAY);
		scene1.setFont(new Font(textsearched.getFont().getFamily(), Font.BOLD, 12));
		scene1.setEditable(false);
		scene2.setBackground(Color.LIGHT_GRAY);
		scene2.setFont(new Font(textsearched.getFont().getFamily(), Font.BOLD, 12));
		scene2.setEditable(false);
		scene3.setBackground(Color.LIGHT_GRAY);
		scene3.setFont(new Font(textsearched.getFont().getFamily(), Font.BOLD, 12));
		scene3.setEditable(false);
		scene4.setBackground(Color.LIGHT_GRAY);
		scene4.setFont(new Font(textsearched.getFont().getFamily(), Font.BOLD, 12));
		scene4.setEditable(false);
		
		textsearched.addFocusListener(new FocusListener()
		{
			public void focusGained (FocusEvent e){textsearched.setText(null);}
			public void focusLost (FocusEvent e) {textsearched.setText(textsearched.getText().trim());}
		});

		search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)  // override old method
			{	
				String wordSearch=textsearched.getText().trim();
				if (wordSearch.isEmpty())
				{result.setText("Please enter a word to search...");}
				else if (wordSearch.length()<4)
				{result.setText("You know you will get nothing if the word searched is less than 4 characters...");}
				else
				{listOccurrence(wordSearch);}
			} 
		});

		textsearched.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				String wordSearch=textsearched.getText().trim();
				if (wordSearch.isEmpty())
				{result.setText("Please enter a word to search...");}
				else if (wordSearch.length()<4)
				{result.setText("You know you will get nothing if the word searched is less than 4 characters...");}
				else
				{listOccurrence(wordSearch);}
			} 
		});
		
		
		scene1b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				String wordSearch=textsearched.getText().trim();
				if (wordSearch.isEmpty())
				{
					result.setText("Please enter a word to search...");
					view(scene1, "xyz"); // search a non-existed string to highlight nothing
				}
				else if (wordSearch.length()<4)
				{result.setText("You know you will get nothing if the word searched is less than 4 characters...");}
				else
				{
					view(scene1, wordSearch);
				} 
			} 
		});
		scene2b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				String wordSearch=textsearched.getText().trim();
				if (wordSearch.isEmpty())
				{
					result.setText("Please enter a word to search...");
					view(scene2, "xyz");
					}
				else if (wordSearch.length()<4)
				{result.setText("You know you will get nothing if the word searched is less than 4 characters...");}
				else
				{
					view(scene2, wordSearch);
				} 
			} 
		});
		scene3b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				String wordSearch=textsearched.getText().trim();
				if (wordSearch.isEmpty())
				{
					result.setText("Please enter a word to search...");
					view(scene3, "xyz");
					}
				else if (wordSearch.length()<4)
				{result.setText("You know you will get nothing if the word searched is less than 4 characters...");}
				else
				{
					view(scene3, wordSearch);
				} 
			} 
		});
		scene4b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				String wordSearch=textsearched.getText().trim();
				if (wordSearch.isEmpty())
				{
					result.setText("Please enter a word to search...");
					view(scene4, "xyz");
				}
				else if (wordSearch.length()<4)
				{result.setText("You know you will get nothing if the word searched is less than 4 characters...");}
				else
				{
					view(scene4, wordSearch);
				} 
			} 
		});
	}
	
	/*private class Actioner implements ActionListener
	{
		private JTextArea scene;
		private String wordsearch;
		public Actioner(JTextArea s, String w){scene=s;wordsearch=w;}
		public void actionPerformed(ActionEvent e) {view(scene, wordsearch);} 
	}*/
	
	public void highlight(JTextArea textarea, String word) 
	{ // remove all old highlights
        removeOldHighlights(textarea);
    
        try 
        {
            Highlighter light = textarea.getHighlighter();
            Document doc = textarea.getDocument();
            String text = doc.getText(0, doc.getLength()).toLowerCase(); // convert to lowercase to include first character is capitalized, like "lord" and "Lord"
            int index=0;

            while ((index=text.indexOf(word,index))>= 0) // keep finding the position of the word matched
            {
            	light.addHighlight(index, index+word.length(), new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW)); //add highlighter at position just found
            	index += word.length();
            }
            
        } catch (BadLocationException e) {System.out.println("Exception Occurs: "+e);}
    }
    
    public void removeOldHighlights(JTextArea textarea) {
        Highlighter light = textarea.getHighlighter();
        Highlighter.Highlight[] high = light.getHighlights();
    
        for (int i=0; i<high.length; i++) { //remove highlighter from each position
            if (high[i].getPainter() instanceof DefaultHighlighter.DefaultHighlightPainter) {light.removeHighlight(high[i]);}
        }
    }

	/*public class ImagePanel extends JPanel {

		  Toolkit images = Toolkit.getDefaultToolkit();
		  private Image img1 = images.getImage(getClass().getResource("1.jpg"));
		  private Image img2 = images.getImage(getClass().getResource("2.jpg"));
		  private Image img3 = images.getImage(getClass().getResource("3.jpg"));

		    public ImagePanel() 
		    {
		        super();
		    }

		    public void paintComponent(Graphics g) 
		    {
		        super.paintComponent(g);        
		        g.drawImage(img1, 0, 0, null);
		        g.drawImage(img2, 200, 0, null);
		        g.drawImage(img3, 400, 0, null);
		    }
		}*/

	public void view(JTextArea scene, String searchword)
	{
		JFrame viewer=new JFrame("Team 8 ( Cong Tu, Yan Chen, Yung Lam )"); // default
		viewer.setSize(400,400); 
		viewer.setVisible(true);
		viewer.setLocation(0,0);
		viewer.setLayout(null);
		viewer.setResizable(false);
		
		if (scene.equals(scene1))
		{viewer.setTitle("Act 1, Scene 1");viewer.setLocation(0, 0);}
		else if (scene.equals(scene2))
		{viewer.setTitle("Act 1, Scene 2");viewer.setLocation(50, 50);}
		else if (scene.equals(scene3))
		{viewer.setTitle("Act 1, Scene 3");viewer.setLocation(100, 100);}
		else
		{viewer.setTitle("Act 1, Scene 4");viewer.setLocation(150, 150);}
		//viewer.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		JScrollPane viewscroll=new JScrollPane(scene); 
		viewscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		viewscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		viewer.add(viewscroll);
		viewscroll.setBounds(0, 0, 395, 371);
		// add highlighter to the searched word if there is any
		highlight(scene, searchword);
	}

	public static void main(String args[]) throws IOException
	{ 
		concordance shakespear = new concordance();
		shakespear.ReadAndBuild("Act1Scene1_edited.txt", "Act1Scene2_edited.txt", "Act1Scene3_edited.txt", "Act1Scene4_edited.txt");
		shakespear.tester();
	}
}
