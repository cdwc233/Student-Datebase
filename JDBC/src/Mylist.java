import java.awt.Toolkit;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JOptionPane;

public class Mylist extends JFrame implements ActionListener {
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public Mylist() {
		this.setSize(650, 450); // 计算屏幕的宽度和高度
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int x = (screenWidth - this.getWidth()) / 2; // 计算窗体的左上角坐标
		int y = (screenHeight - this.getHeight()) / 2;
		this.setLocation(x, y);
		this.setTitle("学生信息查询系统");
		super.btnNewButton.addActionListener(this);
		super.btnNewButton_1.addActionListener(this);
	}

	public boolean getConn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String strConn = "jdbc:mysql://localhost:3306/stuinfo?useUnicode=ture&characterEncoding=utf8";
			conn = DriverManager.getConnection(strConn, "root", "123456");
			if (conn == null)
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void displayTableData(Mylist test) {
		try {
			String sqlstr = "select * from stuinfo";
			test.stmt = test.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			test.rs = test.stmt.executeQuery(sqlstr);
			// 循环遍历结果集，将数据添加到JTable中
			while (test.rs.next()) {
				String id = test.rs.getString("id");
				String name = test.rs.getString("name");
				String sex = test.rs.getString("sex");
				String dept = test.rs.getString("dept");
				String clas = test.rs.getString("clas");
				model.addRow(new Object[] { id, name, sex, dept, clas }); // 将数据添加到JTable中
			}
			if (test.getConn()) {
				test.rs.close();
				test.stmt.close();
				test.conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) { 
		Mylist test = new Mylist();
		if (test.getConn())
			System.out.println("连接成功！");
		else
			System.out.println("连接不成功！");
		new Mylist().setVisible(true);
		displayTableData(test);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == super.btnNewButton) {
			new JDialog(model).setVisible(true);
		}
		if (e.getSource() == super.btnNewButton_1) {
			Mylist test = new Mylist();
			if (test.getConn())
				System.out.println("连接成功！");
			int[] selectedRows = stable.getSelectedRows();
			if (selectedRows == null || selectedRows.length == 0) {
				JOptionPane.showMessageDialog(null, "未选中要删除的行");
			} else {
				for (int i = selectedRows.length - 1; i >= 0; i--) {
					Object selectedData = model.getValueAt(selectedRows[i], 0);
					System.out.println("选定行的id值是：" + selectedData.toString());
					String sql = "DELETE FROM stuinfo WHERE id = " + selectedData.toString();
					try {
						test.stmt = test.conn.createStatement();
						test.stmt.executeUpdate(sql);
						model.removeRow(selectedRows[i]);
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}
}
