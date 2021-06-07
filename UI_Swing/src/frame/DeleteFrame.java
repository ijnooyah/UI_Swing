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
public class DeleteFrame extends JFrame implements ActionListener{
	Container c = getContentPane();
	
	// pnlNorth
	JPanel pnlNorth = new JPanel();
	JLabel lblExpln = new JLabel("삭제하고 싶은 학생의 학번을 입력하세요.");
	//pnlUpdate in pnlNorth
	JPanel pnlDelete = new JPanel();
	JTextField tfUpdate = new JTextField(10);
	JButton btnDelete = new JButton("삭제하기");
	
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
	JButton btnDeleteFinish = new JButton("삭제완료");
	//pnlGender in pnlCenter
	JPanel pnlGender = new JPanel();
	ButtonGroup gender = new ButtonGroup();
	JRadioButton rdoMale = new JRadioButton("남");
	JRadioButton rdoFemale = new JRadioButton("여");
	String sno = "";
	public DeleteFrame() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 330);
		setTitle("삭제하기");
		setUI();
		setListener();
		setVisible(true);
	}

	private void setListener() {
		btnDelete.addActionListener(this);
		btnDeleteFinish.addActionListener(this);
		
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
		pnlDelete.add(tfUpdate);
		pnlDelete.add(btnDelete);
		pnlNorth.add(lblExpln, BorderLayout.NORTH);
		pnlNorth.add(pnlDelete);
		
		//pnlCenter
		pnlCenter.setLayout(new GridLayout(7, 1));
		// pnlGender in pnlCenter
		pnlGender.add(rdoMale);
		pnlGender.add(rdoFemale);
		for (int i = 0; i < strTitles.length; i++) {
			pnlCenter.add(lblItems[i]);
			if(i == 3) {
				pnlCenter.add(pnlGender);
			} else if(i > 3){
				pnlCenter.add(tfInputs[i-1]);
			} else {
				pnlCenter.add(tfInputs[i]);
			}
			pnlCenter.add(btnDeleteFinish);
		}
		c.add(pnlNorth, BorderLayout.NORTH);
		c.add(pnlCenter);

	}
	
	public static void main(String[] args) {
		new DeleteFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		StudentDao dao = StudentDao.getInstance();
		if(obj == btnDelete) {
			sno = tfUpdate.getText();
			// 학번에 맞는 정보 가져오기
			StudentVo vo = dao.selectBySno(sno);
			tfSno.setText(sno);
			tfSname.setText(vo.getSname());
			tfSyear.setText(String.valueOf(vo.getSyear()));
			if(vo.getGender().equals("남")) {
				rdoMale.setSelected(true);
			} else {
				rdoFemale.setSelected(true);
			}
			tfMajor.setText(vo.getMajor());
			tfScore.setText(String.valueOf(vo.getScore()));
 		} else if (obj == btnDeleteFinish) {
 			System.out.println("sno=" + sno);
 			boolean b = dao.deleteStudent(sno);
 			if (b == true) {
				int result = JOptionPane.showConfirmDialog(DeleteFrame.this, "삭제가 정상적으로 완료되었습니다.\n"
						+ "메인화면으로 돌아가겠습니까?", "삭제완료", JOptionPane.YES_NO_OPTION);
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
