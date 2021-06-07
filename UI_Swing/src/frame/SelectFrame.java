package frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import db.StudentDao;
import db.StudentVo;

@SuppressWarnings("serial")
public class SelectFrame extends JFrame implements ActionListener{
	Container c = getContentPane();
	
	//pnlBtn
	JPanel pnlBtn = new JPanel();
	String[] options = {"전체", "학번", "전공"};
	JComboBox<String> combo = new JComboBox<String>(options);
	JTextField tfSelect = new JTextField(10);
	JButton btnSelect = new JButton("조회하기");
	
	JTextArea txaSelect = new JTextArea();
	
	
	
	public SelectFrame() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("조회하기");
		setUI();
		setListener();
		setSize(600, 500);
		setVisible(true);
	}
	
	private void setListener() {
		btnSelect.addActionListener(this);
		
	}

	private void setUI() {
		pnlBtn.add(combo);
		pnlBtn.add(tfSelect);
		pnlBtn.add(btnSelect);
		
		txaSelect.setText("학번 | 이름 | 학년 | 성별 | 전공 | 점수 \n");
		txaSelect.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		c.add(pnlBtn, BorderLayout.NORTH);
		c.add(new JScrollPane(txaSelect));
		
	}

	public static void main(String[] args) {
		new SelectFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		txaSelect.setText("학번 | 이름 | 학년 | 성별 | 전공 | 점수 \n");
		Object obj = e.getSource();
		StudentDao dao = StudentDao.getInstance();
		if(obj == btnSelect) {
			int index = combo.getSelectedIndex();
			if(index == 0) {
				//전체
				List<StudentVo> list = dao.selectAll();
				for (StudentVo vo : list) {
					String sno = vo.getSno();
					String sname = vo.getSname();
					int syear = vo.getSyear();
					String gender = vo.getGender();
					String major = vo.getMajor();
					int score = vo.getScore();
					
					String strStudent = sno + " | " + sname +  " | " + syear +  " | " 
							            + gender +  " | " + major +  " | " + score + "\n";
					txaSelect.append(strStudent);
				}
			} else if (index == 1) {
				//학번
				String sno = tfSelect.getText();
				StudentVo vo = dao.selectBySno(sno);
				String sname = vo.getSname();
				int syear = vo.getSyear();
				String gender = vo.getGender();
				String major = vo.getMajor();
				int score = vo.getScore();
				
				String strStudent = sno + " | " + sname +  " | " + syear +  " | " 
						            + gender +  " | " + major +  " | " + score + "\n";
				txaSelect.append(strStudent);
			} else if (index == 2) {
				//전공
				String major = tfSelect.getText();
				List<StudentVo> list = dao.selectByMajor(major);
				for (StudentVo vo : list) {
					String sno = vo.getSno();
					String sname = vo.getSname();
					int syear = vo.getSyear();
					String gender = vo.getGender();
					int score = vo.getScore();
					
					String strStudent = sno + " | " + sname +  " | " + syear +  " | " 
							            + gender +  " | " + major +  " | " + score + "\n";
					txaSelect.append(strStudent);
				}
			}
		}
		
	}
}
