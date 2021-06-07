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
public class UpdateFrame extends JFrame implements ActionListener{
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
	String[] strTitles = { "학번", "이름", "학년", "성별", "전공", "점수"};
	JLabel[] lblItems = new JLabel[strTitles.length];
	JTextField tfSno = new JTextField(10);
	JTextField tfSname = new JTextField(10);
	JTextField tfSyear = new JTextField(10);
	JTextField tfMajor = new JTextField(10);
	JTextField tfScore = new JTextField(10);
	JTextField[] tfInputs = {tfSno, tfSname, tfSyear, tfMajor, tfScore};
	JButton btnUpdateFinish = new JButton("수정완료");
	//pnlSno in pnlCenter
	JPanel pnlSno = new JPanel();
	JButton btnCheckNum = new JButton("학번체크");
	//pnlGender in pnlCenter
	JPanel pnlGender = new JPanel();
	ButtonGroup gender = new ButtonGroup();
	JRadioButton rdoMale = new JRadioButton("남");
	JRadioButton rdoFemale = new JRadioButton("여");
	
	String oldSno = "";
	public UpdateFrame() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 330);
		setTitle("수정하기");
		setUI();
		setListener();
		setVisible(true);
	}
	
	private void setListener() {
		btnUpdate.addActionListener(this);
		btnCheckNum.addActionListener(this);
		btnUpdateFinish.addActionListener(this);
		
	}

	private void setUI() {
		// 컴포넌트들 완성하기
		for (int i = 0; i < strTitles.length; i++) {
			lblItems[i] = new JLabel(strTitles[i]);
			lblItems[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		gender.add(rdoMale);
		gender.add(rdoFemale);
		
		//pnlNorth
		lblExpln.setHorizontalAlignment(SwingConstants.CENTER);
		pnlNorth.setLayout(new BorderLayout());
		pnlUpdate.add(tfUpdate);
		pnlUpdate.add(btnUpdate);
		pnlNorth.add(lblExpln, BorderLayout.NORTH);
		pnlNorth.add(pnlUpdate);
		
		//pnlCenter
		pnlCenter.setLayout(new GridLayout(7, 1));
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
			pnlCenter.add(btnUpdateFinish);
		}
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
			oldSno = tfUpdate.getText();
			// 학번에 맞는 정보 가져오기
			StudentVo oldVo = dao.selectBySno(oldSno);
			tfSno.setText(oldSno);
			tfSname.setText(oldVo.getSname());
			tfSyear.setText(String.valueOf(oldVo.getSyear()));
			if(oldVo.getGender().equals("남")) {
				rdoMale.setSelected(true);
			} else {
				rdoFemale.setSelected(true);
			}
			tfMajor.setText(oldVo.getMajor());
			tfScore.setText(String.valueOf(oldVo.getScore()));
			
		} else if(obj == btnCheckNum) {
			
		} else if (obj == btnUpdateFinish) {
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
			
			StudentVo newVo = new StudentVo(sno, sname, syear, gender, major, score);
			System.out.println(newVo);
			System.out.println(oldSno);
			boolean b = dao.updateStudent(oldSno, newVo);
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
		}
	}
}
