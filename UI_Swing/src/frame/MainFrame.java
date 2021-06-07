package frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener{
	Container c = getContentPane();
	
	JButton btnSelect = new JButton("조회하기");
	JButton btnUpdate = new JButton("수정하기");
	JButton btnDelete = new JButton("삭제하기"	);
	
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("학생 정보 관리");
		setUI();
		setListener();
		setSize(300, 200);
		setVisible(true);
	}
	
	private void setListener() {
		btnSelect.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		
	}

	private void setUI() {
		c.setLayout(new FlowLayout());
		c.add(btnSelect);
		c.add(btnUpdate);
		c.add(btnDelete);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnSelect) {
			new SelectFrame();
		} else if (obj == btnUpdate) {
			
		} else if (obj == btnDelete) {

		}
	}

	
}
