package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
public class InsertFrame extends JFrame implements ActionListener, FocusListener, ItemListener{
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
	JButton btnCheckSno = new JButton("학번체크");
	//pnlGender in pnlCenter
	JPanel pnlGender = new JPanel();
	ButtonGroup gender = new ButtonGroup();
	JRadioButton rdoMale = new JRadioButton("남");
	JRadioButton rdoFemale = new JRadioButton("여");
	
	//pnlSouth 
	JPanel pnlSouth = new JPanel();
	JButton btnInsertFinish = new JButton("등록하기");
	JButton btnMain = new JButton("돌아가기");
	
	boolean isOper = false;
	public InsertFrame() {
		setSize(400, 330);
		setTitle("수정하기");
		setUI();
		setListener();
		setVisible(true);
	}

	private void setListener() {
		btnCheckSno.addActionListener(this);
		btnInsertFinish.addActionListener(this);
		rdoMale.addItemListener(this);
		rdoFemale.addItemListener(this);
		for (JTextField tf : tfInputs) {
			tf.addFocusListener(this);
		}
		btnMain.addActionListener(this);

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
		pnlCenter.setLayout(new GridLayout(6, 2));
		// pnlSno in pnlCenter
		pnlSno.add(lblItems[0]);
		pnlSno.add(btnCheckSno);
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
		pnlSouth.add(btnMain);
		c.add(pnlCenter);		
		c.add(pnlSouth, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		new InsertFrame();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		StudentDao dao = StudentDao.getInstance();
		if(obj == btnInsertFinish) {
			if (isOper == false) {
				JOptionPane.showMessageDialog(InsertFrame.this, "학번체크 먼저 해주세요", "학번 체크", 
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			String sno = tfSno.getText();
			String sname = tfSname.getText();
			String strYear = tfSyear.getText();
			int syear = 0;
			String gender = "";
			if(rdoMale.isSelected()) {
				gender = rdoMale.getText();
			} else if(rdoFemale.isSelected()) {
				gender = rdoFemale.getText();
			}
			String major = tfMajor.getText();
			String strScore = tfScore.getText();
			int score = 0;
			String[] inputs = {sno, sname, strYear, gender, major, strScore};
			if (sname.trim().length() == 0) {
				tfSname.setText("값을 입력해주세요");
				tfSname.setForeground(Color.RED);
				return;
			}
			if (strYear.trim().length() == 0) {
				tfSyear.setText("값을 입력해주세요");
				tfSyear.setForeground(Color.RED);
				return;
			}
			if (major.trim().length() == 0) {
				tfMajor.setText("값을 입력해주세요");
				tfMajor.setForeground(Color.RED);
				return;
			}
			if (strScore.trim().length() == 0) {
				tfScore.setText("값을 입력해주세요");
				tfScore.setForeground(Color.RED);
				return;
			}
			if (gender.trim().length() == 0) {
				JOptionPane.showMessageDialog(InsertFrame.this, "성별체크 해주세요", "성별 체크", 
						JOptionPane.WARNING_MESSAGE);
				return;
			}
	
			//syear, score 숫자처리
			try {
				syear = Integer.parseInt(strYear);
				if(syear < 1 || syear > 4) {
					tfSyear.setForeground(Color.RED);
					tfSyear.setText("1~4사이의 숫자로 입력하세요");
					return;
				}
			} catch(NumberFormatException ex) {
				tfSyear.setForeground(Color.RED);
				tfSyear.setText("숫자로 입력하세요");
				return;
			}
			try {
				score = Integer.parseInt(strScore);
				if(score < 1 || score > 100) {
					tfSyear.setForeground(Color.RED);
					tfSyear.setText("1~100사이의 숫자로 입력하세요");
					return;
				}
			} catch(NumberFormatException ex) {
				tfScore.setForeground(Color.RED);
				tfScore.setText("숫자로 입력하세요");
				return;
			}
			
			//sname, major 숫자로 입력했을경우 
			try {
				Integer.parseInt(sname);
				tfSname.setForeground(Color.RED);
				tfSname.setText("숫자입력불가");
				return;
			} catch(NumberFormatException ex) {
				
			}
			try {
				Integer.parseInt(major);
				tfMajor.setForeground(Color.RED);
				tfMajor.setText("숫자입력불가");
				return;
			} catch(NumberFormatException ex) {
				
			}
						
			StudentVo vo = new StudentVo(sno, sname, syear, gender, major, score);
			
			
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
		} else if (obj == btnCheckSno){
			isOper = true;
			String sno = tfSno.getText();
			if (sno.trim().equals("")) {
				tfSno.setForeground(Color.RED);
				tfSno.setText("값을 입력해주세요");
				return;
			} else {
				try {
					int intSno = Integer.parseInt(sno);
					if(intSno < 1) {
						tfSno.setForeground(Color.RED);
						tfSno.setText("1이상의 숫자로 입력하세요");
						return;
					}
				} catch(NumberFormatException ex) {
					tfSno.setForeground(Color.RED);
					tfSno.setText("숫자로 입력하세요");
					return;
				}
			}
			
			StudentVo vo = dao.selectBySno(sno);
			if (vo == null) {
				//등록가능
				JOptionPane.showMessageDialog(InsertFrame.this, "등록가능한 학번입니다.", "학번 체크", 
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				//등록불가능 (있는학번)
				JOptionPane.showMessageDialog(InsertFrame.this, "등록되어있는 학번입니다.", "학번 체크", 
						JOptionPane.ERROR_MESSAGE);
				tfSno.setText("");

			}
		} else if (obj == btnMain) {
			new MainFrame();
			dispose();
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		lblItems[3].setForeground(Color.BLACK);
		lblItems[3].setText("성별");
		Object obj = e.getSource();
		
		for (JTextField tf : tfInputs) {
			if (tf.getForeground().equals(Color.RED)) {
				if (obj == tfSno) {
					tfSno.setText("");
					tfSno.setForeground(Color.BLACK);
				} else if (obj == tfSname) {
					tfSname.setText("");
					tfSname.setForeground(Color.BLACK);
				} else if (obj == tfSyear) {
					tfSyear.setText("");
					tfSyear.setForeground(Color.BLACK);
				} else if (obj == tfMajor) {
					tfMajor.setText("");
					tfMajor.setForeground(Color.BLACK);
				} else if (obj == tfScore) {
					tfScore.setText("");
					tfScore.setForeground(Color.BLACK);
				}
			} 
		}
		
		btnInsertFinish.setEnabled(true);
	}

	@Override
	public void focusLost(FocusEvent e) {
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		lblItems[3].setForeground(Color.BLACK);
		lblItems[3].setText("성별");
	}
	
}
