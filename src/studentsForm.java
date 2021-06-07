import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class studentsForm {
    private JTextField txtName;
    private JTextField txtId;
    private JTextField txtCourse;
    private JTextArea txtGrade;
    private JLabel studentName;
    private JLabel studentId;
    private JLabel courseName;
    private JLabel courseGrade;
    private JButton saveButton;
    private JButton readButton;
    private JPanel students;

    Connection con;
    PreparedStatement pst;

    public void Connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/students?serverTimezone=EAT&jdbcCompliantTruncation=false", "root","");
            System.out.println("Connection established");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public studentsForm() {
        Connect();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name,id,course,grade;

                name = txtName.getText();
                id = txtId.getText();
                course = txtCourse.getText();
                grade = txtGrade.getText();

                try {
                    pst = con.prepareStatement("insert into details(sname,reg,course,grade)values(?,?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, id);
                    pst.setString(3, course);
                    pst.setString(4, grade);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Added!");

                    txtName.setText("");
                    txtId.setText("");
                    txtCourse.setText("");
                    txtGrade.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }


            }
        });
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    studentDetails.main(new String[0]);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("studentsForm");
        frame.setContentPane(new studentsForm().students);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
