import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

@SuppressWarnings({"unchecked", "unlikely-arg-type"})
public class MyFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	//Creating Variables and Objects
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	JComboBox<?> letters;
	public static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
	public static final String NUMBERS = "1234567890";
	public static final String SYMBOLS = "!@#$%^&*()-_=+\\/~?";
	JLabel labels[] = new JLabel[4];
	JButton button[] = new JButton[2];
	Random random = new Random();
	JTextField field = new JTextField();
	
	@SuppressWarnings("rawtypes")
	MyFrame(){
		String Lab[] = {"Pass Generator", "Note: 16 digits is more secure.", "----------------------", "Choose digits of pass:"};
		String But[] = {"Generate", "Copy"};
		for(int i=0; i<4; i++) {
			//Creating Labels
			labels[i] = new JLabel(Lab[i]);
			labels[i].setBackground(new Color(123,100,255));
			labels[i].setForeground(Color.black);
			labels[i].setFont(new Font("MV Boli", Font.PLAIN, 17));
			labels[i].setFocusable(false);
			labels[i].setVisible(true);
			this.add(labels[i]);
			
			//Creating Button
			if(i<2) {
				button[i] = new JButton(But[i]);
				button[i].setForeground(Color.BLACK);
				button[i].setBackground(new Color(123,100,255));
				button[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
				button[i].setFocusable(false);
				button[i].setVisible(true);
				button[i].addActionListener(this);
				this.add(button[i]);
			}
		}
	
		//Choose Letters
		String digits[] = {"8 Digits", "16 Digits"};
		letters = new JComboBox(digits);
		letters.setSelectedItem("8 Digits");
		letters.addActionListener(this);
		letters.setBounds(200, 70, 100, 25);
	
		//Settings Positions
		labels[0].setFont(new Font("MV Boli", Font.PLAIN, 20));
		labels[0].setForeground(Color.RED);
		labels[0].setBounds(90, 20, 180, 25);	
		labels[2].setBounds(80, 20, 180, 50);	
		labels[2].setForeground(Color.RED);
		labels[1].setForeground(Color.RED);
		labels[1].setBounds(20, 160, 250, 25);	
		labels[3].setBounds(20, 55, 180, 50);
		button[0].setBounds(90, 115, 140, 25);
		
		//Text field for password
		field = new JTextField();
		field.setEditable(true);
		field.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		
		//Main Frame
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(new Dimension(345, 250));
		this.setVisible(true);
		this.add(letters);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Logic for Generate and Back button
		if(e.getSource()==button[0]) {
			if(button[0].getText().equals("Generate")) {
				String pass = "";
				int[] done = null;
				if(String.valueOf(letters.getSelectedItem()).equals("8 Digits")) {
					done = new int[9];
				}
				if(String.valueOf(letters.getSelectedItem()).equals("16 Digits")) {
					done = new int[17];
				}
				int index = 0;
				for(int i=0; i<Integer.parseInt(String.valueOf(letters.getSelectedItem()).replace(" Digits", "")); i++) {
					int r = random.nextInt(4);
					while(Arrays.asList(done).contains(r) && !(Arrays.asList(done).contains(0) && Arrays.asList(done).contains(1) && Arrays.asList(done).contains(2) && Arrays.asList(done).contains(3)))
						r = random.nextInt(4);
					if(r == 0) {
						done[index] = 0;
						index++;
						pass+=UPPERCASE_LETTERS.charAt(random.nextInt(26));
					}
					else if(r == 1) {
						done[index] = 1;
						index++;
						pass+=LOWERCASE_LETTERS.charAt(random.nextInt(26));
					}
					else if(r == 2) {
						done[index] = 2;
						index++;
						pass+=NUMBERS.charAt(random.nextInt(10));
					}
					else if(r == 3) {
						done[index] = 3;
						index++;
						pass+=SYMBOLS.charAt(random.nextInt(18));
					}
				}
				this.remove(letters);
				this.remove(labels[3]);
				this.add(field);
				this.add(button[1]);
				field.setText(pass);
				button[1].setBounds(220, 70, 100, 25);
				field.setBounds(20, 70, 180, 25);
				button[0].setText("Back");
				labels[1].setText("");
				super.paint(getGraphics());
			}
			else {
				this.add(letters);
				this.add(labels[3]);
				this.remove(field);
				this.remove(button[1]);
				field.setText("");
				labels[3].setBounds(20, 55, 180, 50);
				letters.setBounds(200, 70, 100, 25);
				button[0].setText("Generate");
				super.paint(getGraphics());
				labels[1].setForeground(Color.RED);
				labels[1].setText("Note: 16 digits is more secure.");
			}
		}
		
		//Logic for copy button
		if(e.getSource()==button[1]) {
			StringSelection selection = new StringSelection(field.getText());
			clipboard.setContents(selection, selection);
			labels[1].setForeground(Color.GREEN);
			labels[1].setText("Text Copied to Clipboard!");
		}
	}
}