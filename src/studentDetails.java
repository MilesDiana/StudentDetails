import java.awt.BorderLayout;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class studentDetails
{

    private static Connection con = null;
    private static String URL = "jdbc:mysql://localhost/students?serverTimezone=EAT";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String user = "root";
    private static String pass = "";

    public static void main(String[] args) throws SQLException
    {

        Statement stmt;
        String query;
        ResultSet rs;

        Object rowData[][] = {{"Row1-Col1", "Row1-Col2", "Row1-Col3", "Row1-Col4"}};
        Object columnNames[] = {"Name", "Student ID", "Course", "Grade"};

        DefaultTableModel newModel = new DefaultTableModel(rowData, columnNames);
        JTable table = new JTable(newModel);

        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(URL, user, pass);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

        query = "SELECT sname, reg, course, grade FROM details";
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);

        // the gui
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(500, 200);
        frame.setVisible(true);

        // get rid of the temp row
        newModel.removeRow(0);

        //temporary object array
        Object[] rows;

        while (rs.next()) {
            rows = new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)};
            newModel.addRow(rows);
        }
    }
}