package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import db.StudentDao;
import db.StudentVo;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener{
	Container c = getContentPane();
	
	//pnlBtn 
	JPanel pnlBtn = new JPanel();
	String[] options = {"전체", "이름", "전공"};
	JComboBox<String> combo = new JComboBox<String>(options);
	JTextField tfSelect = new JTextField(10);
	JButton btnSelect = new JButton("조회하기");
	JButton btnInsert = new JButton("등록하기");
	JButton btnUpdate = new JButton("수정하기");
	JButton btnDelete = new JButton("삭제하기");
	
	JTextArea txaSelect = new JTextArea();
	
	public MainFrame() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("학생 정보 관리");
		setUI();
		setListener();
		setSize(600, 500);
		setVisible(true);
	}
	
	private void setListener() {
		btnSelect.addActionListener(this);
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		combo.addActionListener(this);
		
	}

	private void setUI() {
		tfSelect.setEnabled(false);

		pnlBtn.add(combo);
		pnlBtn.add(tfSelect);
		pnlBtn.add(btnSelect);
		pnlBtn.add(btnInsert);
		pnlBtn.add(btnUpdate);
		pnlBtn.add(btnDelete);
		
		txaSelect.setText("학번 | 이름 | 학년 | 성별 | 전공 | 점수 \n");
		txaSelect.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		txaSelect.setEditable(false);
		c.add(pnlBtn, BorderLayout.NORTH);
		c.add(new JScrollPane(txaSelect));
	}


	public static void main(String[] args) {
		new MainFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		txaSelect.setForeground(Color.BLACK);
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
				//이름
				String name = tfSelect.getText();
				// 제약조건
				String trimName = name.trim();
				if (trimName.equals("")) {
					txaSelect.setForeground(Color.RED);
					txaSelect.setText("검색어를 입력하세요.");
					return;
				} 
				List<StudentVo> list = dao.selectBySname(name);
				if(list.size() == 0) {
					txaSelect.setForeground(Color.RED);
					txaSelect.setText("해당하는 이름이 없습니다.");
				}
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
			} else if (index == 2) {
				//전공
				String strMajor = tfSelect.getText();
				// 제약조건
				String trimMajor = strMajor.trim();
				if (trimMajor.equals("")) {
					txaSelect.setForeground(Color.RED);
					txaSelect.setText("검색어를 입력하세요.");
					return;
				} 
				List<StudentVo> list = dao.selectByMajor(strMajor);
				if(list.size() == 0) {
					txaSelect.setForeground(Color.RED);
					txaSelect.setText("해당하는 전공이 없습니다.");
				}
				for (StudentVo vo : list) {
					String sno = vo.getSno();
					String sname = vo.getSname();
					int syear = vo.getSyear();
					String gender = vo.getGender();
					String major = vo.getMajor();
					int score = vo.getScore();
					System.out.println(major);
					String strStudent = sno + " | " + sname +  " | " + syear +  " | " 
							            + gender +  " | " + major +  " | " + score + "\n";
					txaSelect.append(strStudent);
				}
			}
		} else if (obj == combo) {
			int index = combo.getSelectedIndex();
			if (index == 0) {
				tfSelect.setEnabled(false);
			} else {
				tfSelect.setFocusable(true);
				tfSelect.setEnabled(true);
			}
		} else if (obj == btnInsert) {
			new InsertFrame();
		} else if (obj == btnUpdate) {
			new UpdateFrame();
		} else if (obj == btnDelete) {
			new DeleteFrame();
		}
		
	}
}
