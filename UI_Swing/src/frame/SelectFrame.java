package frame;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SelectFrame extends JFrame{
	Container c = getContentPane();
	
	//pnlBtn
	JPanel pnlBtn = new JPanel();
	String[] options = {"전체", "학번", "전공"};
	JComboBox<String> combo = new JComboBox<String>(options);
	JTextField tfSelect = new JTextField(10);
	JButton btnSelect = new JButton("조회하기");
	
	JTextArea txaSelect = new JTextArea();
	
	
	
	public SelectFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("조회하기");
		setUI();
		setSize(600, 500);
		setVisible(true);
	}
	
	private void setUI() {
		pnlBtn.add(combo);
		pnlBtn.add(tfSelect);
		pnlBtn.add(btnSelect);
		
		c.add(pnlBtn, BorderLayout.NORTH);
		c.add(new JScrollPane(txaSelect));
		
	}

	public static void main(String[] args) {
		new SelectFrame();
	}
}
