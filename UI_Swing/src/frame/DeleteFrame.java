package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
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
import javax.swing.JTextArea;
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
	JTextField tfDelete = new JTextField(10);
	JButton btnDelete = new JButton("삭제하기");
	
	//Center
	JTextArea txaDelete = new JTextArea();
	//pnlSouth
	JPanel pnlSouth = new JPanel();
	JButton btnDeleteFinish = new JButton("삭제완료");
	JButton btnMain = new JButton("돌아가기");
	String sno = "";
	boolean isOper = false;
	public DeleteFrame() {
		setSize(400, 330);
		setTitle("삭제하기");
		setUI();
		setListener();
		setVisible(true);
	}

	private void setListener() {
		btnDelete.addActionListener(this);
		btnDeleteFinish.addActionListener(this);
		btnMain.addActionListener(this);

	}

	private void setUI() {
		txaDelete.setFocusable(false);
		txaDelete.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		//pnlNorth
		lblExpln.setHorizontalAlignment(SwingConstants.CENTER);
		pnlNorth.setLayout(new BorderLayout());
		pnlDelete.add(tfDelete);
		pnlDelete.add(btnDelete);
		pnlNorth.add(lblExpln, BorderLayout.NORTH);
		pnlNorth.add(pnlDelete);
		
		//pnlSouth
		pnlSouth.add(btnDeleteFinish);
		pnlSouth.add(btnMain);
		
		c.add(pnlNorth, BorderLayout.NORTH);
		c.add(txaDelete);
		c.add(pnlSouth, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		new DeleteFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		StudentDao dao = StudentDao.getInstance();
		if(obj == btnDelete) {
			isOper = true; //btnDelete클릭 btnDeleteFinish의 선행조건만들기
			txaDelete.setText("");
			sno = tfDelete.getText();
			if (sno.trim().equals("")) {
				tfDelete.setForeground(Color.RED);
				tfDelete.setText("검색어를 입력하세요.");
				return;
			} else {
				try {
					int intSno = Integer.parseInt(sno);
					if(intSno < 1) {
						tfDelete.setForeground(Color.RED);
						tfDelete.setText("1이상의 숫자로 입력하세요");
						return;
					}
				} catch(NumberFormatException ex) {
					tfDelete.setForeground(Color.RED);
					tfDelete.setText("숫자로 입력하세요");
					return;
				}
			}
			// 학번에 맞는 정보 가져오기
			StudentVo vo = dao.selectBySno(sno);
			//없을때
			if(vo == null) {
				JOptionPane.showMessageDialog(DeleteFrame.this, "해당 학번에 해당하는 학생이 없습니다", "학생 없음", 
						JOptionPane.WARNING_MESSAGE);
				return;
			} 
			//있을때
			String sno = vo.getSno();
			String sname = vo.getSname();
			int syear = vo.getSyear();
			String gender = vo.getGender();
			String major = vo.getMajor();
			int score = vo.getScore();
			
			String strStudent = "학번: " + sno + "\n"
					+ "이름: " + sname + "\n"
							+ "학년: " + syear + "\n"
									+ "성별: " + gender + "\n"
									+ "전공: " + major + "\n"
									+ "점수: " + score + "\n"; 
			txaDelete.append(strStudent);
 		} else if (obj == btnDeleteFinish) {
 			if (!isOper) {
 				JOptionPane.showMessageDialog(DeleteFrame.this, "삭제하고 싶은 학번을 입력해주세요.", "경고", 
						JOptionPane.WARNING_MESSAGE);
 				return;
 			}
 			if (txaDelete.getText().trim().length() == 0) {
 				JOptionPane.showMessageDialog(DeleteFrame.this, "입력해주세요", "경고", 
						JOptionPane.WARNING_MESSAGE);
 				return;
 			}
 			int result = JOptionPane.showConfirmDialog(DeleteFrame.this, "정말 삭제하겠습니까?", 
 					"경고", JOptionPane.YES_NO_OPTION);
 			if (result == JOptionPane.YES_OPTION) {
 				boolean b = dao.deleteStudent(sno);
 				if (b == true) {
 					txaDelete.setText("학번 " + sno + "학생의 정보가 정상적으로 삭제되었습니다.");
 				} else {
 					txaDelete.setText("학번 " + sno + "학생의 정보 삭제에 실패했습니다.");

 				}
 			} else {
 				
 			}
 		} else if (obj == btnMain) {
 			new MainFrame();
 			dispose();
 		}
		
	}
}
