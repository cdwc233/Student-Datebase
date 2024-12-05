import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class JDialog extends javax.swing.JDialog {
	private DefaultTableModel model;
	private final JPanel contentPanel = new JPanel();
	public JTextField textField;
	public JTextField textField_1;
	public JComboBox comboBox;
	public JComboBox comboBox_1;
	public ButtonGroup sexButtonGroup;
	public JButton cancelButton;
	public JButton okButton;
	public JRadioButton rdbtnNewRadioButton;
	public JRadioButton rdbtnNewRadioButton_1;

	public static void main(String[] args) {
		DefaultTableModel model = null;
		try {
			JDialog dialog = new JDialog(model);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @wbp.parser.constructor
	 */
	public JDialog(DefaultTableModel model) {
		this.model = model;
		setTitle("增加学生信息");
		setBounds(100, 100, 450, 366);
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;// 计算窗体的左上角坐标
		int x = (screenWidth - this.getWidth()) / 2;
		int y = (screenHeight - this.getHeight()) / 2;
		this.setLocation(x, y);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			textField = new JTextField();
			textField.setBounds(110, 30, 260, 35);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		String[][] labelInfos = { { "姓名", "30" }, { "学号*", "80" }, { "性别", "130" }, { "专业", "180" }, { "班级", "230" } }; // 添加文字标签
		for (String[] labelInfo : labelInfos) {
			JLabel label = new JLabel(labelInfo[0]);
			label.setFont(new Font("宋体", Font.PLAIN, 24));
			label.setBounds(35, Integer.parseInt(labelInfo[1]), 65, 35);
			contentPanel.add(label);
		}
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(110, 80, 260, 35);
		contentPanel.add(textField_1);

		sexButtonGroup = new ButtonGroup();
		rdbtnNewRadioButton = new JRadioButton("男");
		rdbtnNewRadioButton.setFont(new Font("宋体", Font.PLAIN, 24));
		rdbtnNewRadioButton.setBounds(110, 130, 121, 35);
		contentPanel.add(rdbtnNewRadioButton);

		rdbtnNewRadioButton_1 = new JRadioButton("女");
		rdbtnNewRadioButton_1.setFont(new Font("宋体", Font.PLAIN, 24));
		rdbtnNewRadioButton_1.setBounds(240, 130, 121, 35);
		contentPanel.add(rdbtnNewRadioButton_1);

		sexButtonGroup.add(rdbtnNewRadioButton);
		sexButtonGroup.add(rdbtnNewRadioButton_1);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "", "计算机科学与技术", "网络空间安全", "智慧科学与技术" }));
		comboBox.setEditable(true);
		comboBox.setBounds(110, 180, 260, 35);
		contentPanel.add(comboBox);

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "", "一班", "二班", "三班", "四班", "五班", "六班", "七班" }));
		comboBox_1.setBounds(110, 230, 260, 35);
		contentPanel.add(comboBox_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("提交");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("清空");
				buttonPane.add(cancelButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String id = textField_1.getText(); // 获取输入的学号
						if (id.isEmpty()) {
							JOptionPane.showMessageDialog(null, "学号不能为空！");
						} else {
							boolean idExists = false; // 检查是否已经存在相同学号的学生
							try {
								String strConn = "jdbc:mysql://localhost:3306/stuinfo?useUnicode=ture&characterEncoding=utf8"; // 连接到数据库
								Connection con = DriverManager.getConnection(strConn, "root", "123456");
								String query = "SELECT COUNT(*) FROM stuinfo WHERE id = ?"; // 创建查询语句
								PreparedStatement pst = con.prepareStatement(query); // 执行查询
								pst.setString(1, id);
								ResultSet rs = pst.executeQuery();
								if (rs.next() && rs.getInt(1) > 0) {
									idExists = true;
								}
								rs.close();
								pst.close(); // 关闭数据库连接
								con.close();
							} catch (SQLException ex) {
								ex.printStackTrace();
							}
							if (idExists) {
								JOptionPane.showMessageDialog(null, "已经存在相同学号的学生！");
							} else {
								String name = textField.getText(); // 获取输入的姓名
								String sex = ""; // 获取输入的性别
								if (rdbtnNewRadioButton.isSelected()) {
									sex = "男";
								} else if (rdbtnNewRadioButton_1.isSelected()) {
									sex = "女";
								}
								String dept = comboBox.getSelectedItem().toString(); // 获取选择的专业
								String clas = comboBox_1.getEditor().getItem().toString(); // 获取选择的班级
								try {
									String strConn = "jdbc:mysql://localhost:3306/stuinfo?useUnicode=ture&characterEncoding=utf8"; // 连接到数据库
									Connection con = DriverManager.getConnection(strConn, "root", "123456");
									String query = "INSERT INTO stuinfo (id, name, sex, dept, clas) VALUES (?, ?, ?, ?, ?)"; // 创建插入语句
									PreparedStatement pst = con.prepareStatement(query); // 执行插入
									pst.setString(1, id);
									pst.setString(2, name);
									pst.setString(3, sex);
									pst.setString(4, dept);
									pst.setString(5, clas);
									pst.executeUpdate();
									pst.close(); // 关闭数据库连接
									con.close();
								} catch (SQLException ex) {
									ex.printStackTrace();
								}
								model.addRow(new Object[] { id, name, sex, dept, clas });
								JOptionPane.showMessageDialog(null, "提交成功");
								dispose();
							}
						}
					}
				});
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textField.setText("");
						textField_1.setText("");
						comboBox.setSelectedIndex(-1);
						comboBox_1.setSelectedIndex(-1);
						sexButtonGroup.clearSelection();
						JOptionPane.showMessageDialog(null, "清空成功");
					}
				});
			}
		}
	}

	public JDialog(JTable stable) {
		this(new DefaultTableModel());
		setTitle("修改学生信息");
		textField_1.setEditable(false);
		int selectedRow = stable.getSelectedRow(); // 获取选择的行
		TableModel model = stable.getModel(); // 获取TableModel
		textField.setText(model.getValueAt(selectedRow, 1).toString()); // 填充JDialog的组件数据
		textField_1.setText(model.getValueAt(selectedRow, 0).toString());
		comboBox.setSelectedItem(model.getValueAt(selectedRow, 3));
		comboBox_1.setSelectedItem(model.getValueAt(selectedRow, 4));
		String sex = model.getValueAt(selectedRow, 2).toString();
		if (sex.equals("男")) {
			sexButtonGroup.setSelected(rdbtnNewRadioButton.getModel(), true);
		} else if (sex.equals("女")) {
			sexButtonGroup.setSelected(rdbtnNewRadioButton_1.getModel(), true);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("提交");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("清空");
				buttonPane.add(cancelButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String id = textField_1.getText(); // 获取输入的学号
						if (id.isEmpty()) {
							JOptionPane.showMessageDialog(null, "学号不能为空！");
						} else {
							String name = textField.getText(); // 获取输入的姓名
							String sex = ""; // 获取输入的性别
							if (rdbtnNewRadioButton.isSelected()) {
								sex = "男";
							} else if (rdbtnNewRadioButton_1.isSelected()) {
								sex = "女";
							}
							String dept = comboBox.getSelectedItem().toString(); // 获取选择的专业
							String clas = comboBox_1.getEditor().getItem().toString(); // 获取选择的班级
							try {
								String strConn = "jdbc:mysql://localhost:3306/stuinfo?useUnicode=ture&characterEncoding=utf8"; // 连接到数据库
								Connection con = DriverManager.getConnection(strConn, "root", "123456");
								String query = "UPDATE stuinfo SET name=?, sex=?, dept=?, clas=? WHERE id=?"; // 创建插入语句
								PreparedStatement pst = con.prepareStatement(query); // 执行插入
								pst.setString(1, name);
								pst.setString(2, sex);
								pst.setString(3, dept);
								pst.setString(4, clas);
								pst.setString(5, id);
								pst.executeUpdate();
								pst.close(); // 关闭数据库连接
								con.close();
								for (int i = 0; i < model.getRowCount(); i++) {
									if (model.getValueAt(i, 0).equals(id)) {
										model.setValueAt(name, i, 1);
										model.setValueAt(sex, i, 2);
										model.setValueAt(dept, i, 3);
										model.setValueAt(clas, i, 4);
										break;
									}
								}
								JOptionPane.showMessageDialog(null, "修改成功");
								dispose();
							} catch (SQLException ex1) {
								ex1.printStackTrace();
							}
						}
					}
				});
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textField.setText("");
						textField_1.setText("");
						comboBox.setSelectedIndex(-1);
						comboBox_1.setSelectedIndex(-1);
						sexButtonGroup.clearSelection();
						JOptionPane.showMessageDialog(null, "清空成功");
					}
				});
			}
		}

	}
}
