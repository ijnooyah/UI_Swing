package frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import db.StudentDao;
import db.StudentVo;

@SuppressWarnings("serial")
public class InsertFrame extends JFrame implements ActionListener{
	Container c = getContentPane();
	
	//pnlCenter
	JPanel pnlCenter = new JPanel();
	String[] strTitles = { "학번", "이름", "학년", "성별", "전공", "점수"};
	JLabel[] lblItems = new JLabel[strTitles.length];
	JTextField tfSno = new JTextField(10);
	JTextField tfSname = new JTextField(10);
	JTextField tfSyear = new JTextField(10);
	JTextField tfMajor = new JTextField(10);
	JTextField tfScore = new JTextField(10);
	JTextField[] tfInputs = {tfSno, tfSname, tfSyear, tfMajor, tfScore};
	//pnlSno in pnlCenter
	JPanel pnlSno = new JPanel();
	JButton btnCheckNum = new JButton("학번체크");
	//pnlGender in pnlCenter
	JPanel pnlGender = new JPanel();
	ButtonGroup gender = new ButtonGroup();
	JRadioButton rdoMale = new JRadioButton("남");
	JRadioButton rdoFemale = new JRadioButton("여");
	
	//pnlSouth 
	JPanel pnlSouth = new JPanel();
	JButton btnInsertFinish = new JButton("등록하기");
	
	public InsertFrame() {
		setSize(300, 330);
		setTitle("수정하기");
		setUI();
		setListener();
		setVisible(true);
	}

	private void setListener() {
		btnCheckNum.addActionListener(this);
		btnInsertFinish.addActionListener(this);
		
	}

	private void setUI() {
		// 컴포넌트들 완성하기
		for (int i = 0; i < strTitles.length; i++) {
			lblItems[i] = new JLabel(strTitles[i]);
			lblItems[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		gender.add(rdoMale);
		gender.add(rdoFemale);
		
		//pnlCenter
		pnlCenter.setLayout(new GridLayout(6, 1));
		// pnlSno in pnlCenter
		pnlSno.add(lblItems[0]);
		pnlSno.add(btnCheckNum);
		// pnlGender in pnlCenter
		pnlGender.add(rdoMale);
		pnlGender.add(rdoFemale);
		for (int i = 0; i < strTitles.length; i++) {
			if(i == 0) {
				pnlCenter.add(pnlSno);
			} else {
				pnlCenter.add(lblItems[i]);
			}
			if(i == 3) {
				pnlCenter.add(pnlGender);
			} else if(i > 3){
				pnlCenter.add(tfInputs[i-1]);
			} else {
				pnlCenter.add(tfInputs[i]);
			}
			pnlCenter.add(btnInsertFinish);
		}
		
		//pnlSouth
		pnlSouth.add(btnInsertFinish);
		c.add(pnlCenter);		
		c.add(pnlSouth, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		new InsertFrame();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == btnInsertFinish) {
			//데이터들 vo에 넣기
			String sno = tfSno.getText();
			String sname = tfSname.getText();
			int syear = Integer.parseInt(tfSyear.getText());
			String gender = null;
			if(rdoMale.isSelected()) {
				gender = rdoMale.getText();
			} else if(rdoFemale.isSelected()) {
				gender = rdoFemale.getText();
			}
			String major = tfMajor.getText();
			int score = Integer.parseInt(tfScore.getText());
			
			StudentVo vo = new StudentVo(sno, sname, syear, gender, major, score);
			StudentDao dao = StudentDao.getInstance();
			boolean b = dao.insertStudent(vo);
			if (b == true) {
				int result = JOptionPane.showConfirmDialog(InsertFrame.this, "등록이 정상적으로 되었습니다.\n"
						+ "메인화면으로 돌아가겠습니까?", "등록완료", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.CLOSED_OPTION) {
					
				} else if (result == JOptionPane.YES_OPTION) {
					new MainFrame();
					dispose();
				} else if (result == JOptionPane.NO_OPTION) {
					
				}
			}
		} else if (obj == btnCheckNum){
			
		}
	}
	
}
