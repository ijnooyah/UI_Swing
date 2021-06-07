package frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UpdateFrame extends JFrame{
	Container c = getContentPane();
	
	// pnlNorth
	JPanel pnlNorth = new JPanel();
	JLabel lblExpln = new JLabel("수정하고 싶은 학생의 학번을 입력하세요.");
	JTextField tfUpdate = new JTextField(10);
	JButton btnUpdate = new JButton("수정하기");
	
	//pnlCenter
	JPanel pnlCenter = new JPanel();
	String[] strTitles = { "학번", "이름", "학년", "성별", "전공", "점수"};
	JLabel[] lblItems = new JLabel[strTitles.length];
	JTextField tfSno = new JTextField(10);
	JTextField tfSname = new JTextField(10);
	JTextField tfSyear = new JTextField(10);
	JTextField tfGender = new JTextField(10);
	JTextField tfMajor = new JTextField(10);
	JTextField tfScore = new JTextField(10);
	JTextField[] tfInputs = {tfSno, tfSname, tfSyear, tfGender, tfMajor, tfScore};
	JButton btnUpdateFinish = new JButton("수정완료");
	//pnlSno in pnlCenter
	JPanel pnlSno = new JPanel();
	JButton btnCheckNum = new JButton("학번체크");
	
	public UpdateFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setTitle("수정하기");
		setUI();
		setVisible(true);
	}
	
	private void setUI() {
		// 라벨아이템 만들기
		for (int i = 0; i < strTitles.length; i++) {
			lblItems[i] = new JLabel(strTitles[i]);
		}
		
		//pnlNorth
		pnlNorth.setLayout(new GridLayout(2, 1));
		pnlNorth.add(lblExpln);
		pnlNorth.add(tfUpdate);
		pnlNorth.add(btnUpdate);
		
		//pnlCenter
		pnlCenter.setLayout(new GridLayout(7, 1));
		for (int i = 0; i < strTitles.length; i++) {
			pnlCenter.add(lblItems[i]);
			pnlCenter.add(tfInputs[i]);
			pnlCenter.add(btnUpdateFinish);
		}
		c.add(pnlNorth, BorderLayout.NORTH);
		c.add(pnlCenter);
	}

	public static void main(String[] args) {
		new UpdateFrame();
	}
}
