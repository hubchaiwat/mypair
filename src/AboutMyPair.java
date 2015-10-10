
import java.awt.*;
import javax.swing.*;

public class AboutMyPair extends JFrame{	
	
	private static final int FRAME_WIDTH = 620; //Declare attribute for setting frame's width. 
	private static final int FRAME_HEIGHT = 250; //Declare attribute for setting frame's height. 
	private static final int FRAME_X_ORIGIN = 170; //Declare attribute for setting start point of x-axis of frame.
	private static final int FRAME_Y_ORIGIN = 50; //Declare attribute for setting start point of y-axis of frame.
	private static final String ICON_PATH = "pic/icon.jpg";
	private static final String LOGO_PATH = "pic/logo.jpg";
	
	private JPanel panel;
	private JLabel labelLogo;
	private Icon iconProgram;
	private String strDeveloper;
	private JTextArea textArea;
	
	public AboutMyPair(){
		//Create Objects
		iconProgram = new ImageIcon(LOGO_PATH);
		this.setTitle("About MyPair");
		panel = new JPanel();
		labelLogo = new JLabel(iconProgram);
		strDeveloper = "";
		textArea = new JTextArea();  
		
		//Setting this
		//this.setLayout(new FlowLayout());		
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT); //Setting size of frame.
		this.setTitle("About MyPair");; //Setting title of frame.
		this.setLocation(FRAME_X_ORIGIN,FRAME_Y_ORIGIN); //Setting x-axis and y-axis of frame.						
		
		this.setVisible(true);
		this.setBackground(Color.WHITE);
		this.setResizable(false);
		
		
		//Setting panel
		panel.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		panel.setBackground(Color.WHITE);
		
		//Add components to panel
		panel.add(labelLogo);
		panel.add(textArea);
		
		initializeDeveloper();
		
		//Add panel to this
		this.add(panel);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(ICON_PATH)); 
	}
	
	private void initializeDeveloper(){
		this.strDeveloper = this.strDeveloper+"MyPair by Coconut Group\n\n";
		this.strDeveloper = this.strDeveloper+"Development by\n";
		this.strDeveloper = this.strDeveloper+"Chaiwat    Khamphaya     1520700079 No.8 (Leader)\n";
		this.strDeveloper = this.strDeveloper+"Supaporn   Thongrod      1520200773 No.6\n";
		this.strDeveloper = this.strDeveloper+"Thanaphon  Wanwarn       1520306190 No.7\n";
		this.strDeveloper = this.strDeveloper+"Piangnapa  Tangkhantikul 1520700111 No.9\n";
		this.strDeveloper = this.strDeveloper+"Siriluk    Sanooklum     1520700442 No.10\n";
		this.strDeveloper = this.strDeveloper+"Boonjaratt Kerdbarame    1520702505 No.32\n";
		this.strDeveloper = this.strDeveloper+"Piyanuch   Khantisuk     1520702505 No.35\n\n";
		this.strDeveloper = this.strDeveloper+"ITX316 Java Programming\n";
		this.strDeveloper = this.strDeveloper+"Section 6281 Semester I Acdemic Year 2011\n";
		this.strDeveloper = this.strDeveloper+"Bangkok University Founded 1962";
		textArea.setText(strDeveloper);
		textArea.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		textArea.setEditable(false);
		
	}
}
