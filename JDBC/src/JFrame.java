import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

public class JFrame extends javax.swing.JFrame {
	public JPanel contentPane;
	public JButton btnNewButton;
	public JButton btnNewButton_1;
	public JButton btnNewButton_2;
	public JTable stable;
	public static DefaultTableModel model = new DefaultTableModel(new Object[][] {},
			new String[] { "学号", "姓名", "性别", "专业", "班级" });
	private JPasswordField passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 466);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnNewButton = new JButton("增加");
		btnNewButton.setFont(new Font("宋体", Font.BOLD, 18));
		btnNewButton.setBackground(new Color(128, 255, 0));
		btnNewButton.setBounds(60, 360, 140, 40);
		contentPane.add(btnNewButton);
		btnNewButton.setToolTipText("向管理系统中添加学生信息");

		btnNewButton_1 = new JButton("删除*");
		btnNewButton_1.setFont(new Font("宋体", Font.BOLD, 18));
		btnNewButton_1.setBackground(new Color(255, 0, 0));
		btnNewButton_1.setBounds(440, 360, 140, 40);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.setToolTipText("删除所选中的行的学生信息，支持多选删除");

		btnNewButton_2 = new JButton("修改*");
		btnNewButton_2.setFont(new Font("宋体", Font.BOLD, 18));
		btnNewButton_2.setBackground(new Color(255, 255, 0));
		btnNewButton_2.setBounds(250, 360, 140, 40);
		contentPane.add(btnNewButton_2);
		btnNewButton_2.setToolTipText("修改所选中的行的学生信息，也可以双击此行执行修改");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(60, 30, 520, 300);
		contentPane.add(scrollPane);

		stable = new JTable();
		stable.setFont(new Font("宋体", Font.PLAIN, 16));
		stable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		stable.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		stable.setModel(model);
		stable.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(stable);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(207, 10, 306, 21);
		contentPane.add(passwordField);

		TableColumnModel columnModel = stable.getColumnModel(); // 设置每一列的宽度
		TableColumn column0 = columnModel.getColumn(0);
		column0.setPreferredWidth(100);
		TableColumn column1 = columnModel.getColumn(1);
		column1.setPreferredWidth(90);
		TableColumn column2 = columnModel.getColumn(2);
		column2.setPreferredWidth(50);
		TableColumn column3 = columnModel.getColumn(3);
		column3.setPreferredWidth(150);
		TableColumn column4 = columnModel.getColumn(4);
		column4.setPreferredWidth(80);

		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mylist test = new Mylist();
				if (test.getConn())
					System.out.println("连接成功！");
				int[] selectedRows = stable.getSelectedRows();
				if (selectedRows == null || selectedRows.length == 0) {
					JOptionPane.showMessageDialog(null, "未选中要修改的行");
				} else if (selectedRows.length > 1) {
					JOptionPane.showMessageDialog(null, "仅支持选中一行进行修改！");
				} else {
					new JDialog(stable).setVisible(true);
				}
			}
		});

		stable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					new JDialog(stable).setVisible(true);
				}
			}
		});
	}
}
