package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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
public class UpdateFrame extends JFrame implements ActionListener, FocusListener{
	Container c = getContentPane();
	
	// pnlNorth
	JPanel pnlNorth = new JPanel();
	JLabel lblExpln = new JLabel("수정하고 싶은 학생의 학번을 입력하세요.");
	//pnlUpdate in pnlNorth
	JPanel pnlUpdate = new JPanel();
	JTextField tfUpdate = new JTextField(10);
	JButton btnUpdate = new JButton("수정하기");
	
	//pnlCenter
	JPanel pnlCenter = new JPanel();
	String[] strTitles = {"이름", "학년", "성별", "전공", "점수"};
	JLabel[] lblItems = new JLabel[strTitles.length];
	JTextField tfSname = new JTextField(10);
	JTextField tfSyear = new JTextField(10);
	JTextField tfMajor = new JTextField(10);
	JTextField tfScore = new JTextField(10);
	JTextField[] tfInputs = {tfSname, tfSyear, tfMajor, tfScore};
	JButton btnUpdateFinish = new JButton("수정완료");
	JButton btnMain = new JButton("돌아가기");
	//pnlGender in pnlCenter
	JPanel pnlGender = new JPanel();
	ButtonGroup gender = new ButtonGroup();
	JRadioButton rdoMale = new JRadioButton("남");
	JRadioButton rdoFemale = new JRadioButton("여");
	
	String oldSno = "";
	
	public UpdateFrame() {
		setSize(400, 330);
		setTitle("수정하기");
		setUI();
		setListener();
		setVisible(true);
	}
	
	private void setListener() {
		btnUpdate.addActionListener(this);
		btnUpdateFinish.addActionListener(this);
		btnMain.addActionListener(this);
		tfUpdate.addFocusListener(this);
		for (JTextField tf : tfInputs) {
			tf.addFocusListener(this);
		}
	}

	private void setUI() {
		// 컴포넌트들 완성하기
		for (int i = 0; i < strTitles.length; i++) {
			lblItems[i] = new JLabel(strTitles[i]);
			lblItems[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		gender.add(rdoMale);
		gender.add(rdoFemale);
		for (int i = 0; i < tfInputs.length; i++) {
			tfInputs[i].setFocusable(false);
		}
		rdoMale.setEnabled(false);
		rdoFemale.setEnabled(false);
		
		
		
		//pnlNorth
		lblExpln.setHorizontalAlignment(SwingConstants.CENTER);
		pnlNorth.setLayout(new BorderLayout());
		pnlUpdate.add(tfUpdate);
		pnlUpdate.add(btnUpdate);
		pnlNorth.add(lblExpln, BorderLayout.NORTH);
		pnlNorth.add(pnlUpdate);
		
		//pnlCenter
		pnlCenter.setLayout(new GridLayout(7, 2));
		// pnlGender in pnlCenter
		pnlGender.add(rdoMale);
		pnlGender.add(rdoFemale);
		for (int i = 0; i < strTitles.length; i++) {
			pnlCenter.add(lblItems[i]);
			if(i == 2) {
				pnlCenter.add(pnlGender);
			} else if(i > 2){
				pnlCenter.add(tfInputs[i-1]);
			} else {
				pnlCenter.add(tfInputs[i]);
			}
		}
		pnlCenter.add(btnUpdateFinish);
		pnlCenter.add(btnMain);
		c.add(pnlNorth, BorderLayout.NORTH);
		c.add(pnlCenter);
	}

	public static void main(String[] args) {
		new UpdateFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		StudentDao dao = StudentDao.getInstance();
		if(obj == btnUpdate) {
			//input값 체크
			oldSno = tfUpdate.getText();
			if (oldSno.trim().equals("")) {
				tfUpdate.setForeground(Color.RED);
				tfUpdate.setText("검색어를 입력하세요.");
				return;
			} else {
				try {
					int intSno = Integer.parseInt(oldSno);
					if(intSno < 1) {
						tfUpdate.setForeground(Color.RED);
						tfUpdate.setText("1이상의 숫자로 입력하세요");
						return;
					}
				} catch(NumberFormatException ex) {
					tfUpdate.setForeground(Color.RED);
					tfUpdate.setText("숫자로 입력하세요");
					return;
				}
			}
			
			
			// 학번에 맞는 정보 가져오기
			StudentVo oldVo = dao.selectBySno(oldSno);
			//학번에맞는 정보 없을때
			if(oldVo == null) {
				JOptionPane.showMessageDialog(UpdateFrame.this, "해당 학번에 해당하는 학생이 없습니다", "학생 없음", 
						JOptionPane.WARNING_MESSAGE);
				return;
			} 
			//있을때
			tfSname.setText(oldVo.getSname());
			tfSyear.setText(String.valueOf(oldVo.getSyear()));
			if(oldVo.getGender().equals("남")) {
				rdoMale.setSelected(true);
			} else {
				rdoFemale.setSelected(true);
			}
			tfMajor.setText(oldVo.getMajor());
			tfScore.setText(String.valueOf(oldVo.getScore()));
			for (int i = 0; i < tfInputs.length; i++) {
				tfInputs[i].setFocusable(true);
			}
			rdoMale.setEnabled(true);
			rdoFemale.setEnabled(true);
			
		} else if (obj == btnUpdateFinish) {
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
			String[] inputs = {sname, strYear, gender, major, strScore};
			for (int i = 0; i < inputs.length; i++) {
				//빈칸 
				if(inputs[i].trim().equals("")) {
					System.out.println(i);
					if(i == 2) {
						lblItems[i].setText("성별입력필수");
						lblItems[i].setForeground(Color.RED);
					} else if(i > 2){
						tfInputs[i-1].setForeground(Color.RED);
						tfInputs[i-1].setText("값을 입력해주세요");
					} else {
						tfInputs[i].setForeground(Color.RED);
						tfInputs[i].setText("값을 입력해주세요");
					}
				} 
				//syear, score 숫자처리
				else {
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
				} 
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
			
			for (int i = 0; i < inputs.length; i++) {
				if(inputs[i].trim().equals("")) {
					return;
				}
			}
			
			StudentVo newVo = new StudentVo(oldSno, sname, syear, gender, major, score);
			boolean b = dao.updateStudent(newVo);
			if (b == true) {
				int result = JOptionPane.showConfirmDialog(UpdateFrame.this, "수정이 정상적으로 완료되었습니다.\n"
						+ "메인화면으로 돌아가겠습니까?", "수정완료", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.CLOSED_OPTION) {
					
				} else if (result == JOptionPane.YES_OPTION) {
					new MainFrame();
					dispose();
				} else if (result == JOptionPane.NO_OPTION) {
					
				}
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
		if(obj == tfUpdate) {
			tfUpdate.setForeground(Color.BLACK);
			tfUpdate.setText("");
		}
		for (JTextField tf : tfInputs) {
			if (tf.getForeground().equals(Color.RED)) {
				if (obj == tfSname) {
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
	}

	@Override
	public void focusLost(FocusEvent e) {
		
	}
}
