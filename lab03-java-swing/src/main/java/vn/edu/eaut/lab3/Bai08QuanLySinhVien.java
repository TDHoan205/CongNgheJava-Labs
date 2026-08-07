package vn.edu.eaut.lab3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Bai08QuanLySinhVien extends JFrame {

    private JTextField txtMa;
    private JTextField txtTen;
    private JTextField txtDiem;

    private JTable table;
    private DefaultTableModel model;

    private JButton btnThem;

    public Bai08QuanLySinhVien() {

        setTitle("Bài 8 - Quản lý sinh viên");

        setSize(700,450);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10,10));

        //==========================
        // Panel nhập dữ liệu
        //==========================

        JPanel pnlInput = new JPanel(new GridLayout(3,2,10,10));

        pnlInput.add(new JLabel("Mã sinh viên"));
        txtMa = new JTextField();
        pnlInput.add(txtMa);

        pnlInput.add(new JLabel("Họ và tên"));
        txtTen = new JTextField();
        pnlInput.add(txtTen);

        pnlInput.add(new JLabel("Điểm"));
        txtDiem = new JTextField();
        pnlInput.add(txtDiem);

        add(pnlInput, BorderLayout.NORTH);

        //==========================
        // Table
        //==========================

        model = new DefaultTableModel();

        model.addColumn("Mã SV");
        model.addColumn("Họ tên");
        model.addColumn("Điểm");

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        //==========================
        // Button
        //==========================

        JPanel pnlButton = new JPanel(new FlowLayout());

        btnThem = new JButton("Thêm");

        pnlButton.add(btnThem);

        add(pnlButton, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> themSinhVien());

    }

    //====================================
    // Thêm sinh viên
    //====================================

    private void themSinhVien() {

        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        String diemText = txtDiem.getText().trim();

        // Không để trống

        if(ma.isEmpty() || ten.isEmpty() || diemText.isEmpty()){

            JOptionPane.showMessageDialog(this,
                    "Không được để trống dữ liệu!");

            return;
        }

        // Kiểm tra tên

        if(!ten.matches("[\\p{L}\\s]+")){

            JOptionPane.showMessageDialog(this,
                    "Họ tên chỉ được chứa chữ!");

            return;

        }

        double diem;

        try{

            diem = Double.parseDouble(diemText);

        }

        catch(NumberFormatException ex){

            JOptionPane.showMessageDialog(this,
                    "Điểm phải là số!");

            return;

        }

        // Điểm hợp lệ

        if(diem < 0 || diem > 10){

            JOptionPane.showMessageDialog(this,
                    "Điểm phải nằm trong khoảng 0 - 10!");

            return;

        }

        // Kiểm tra mã trùng

        for(int i=0;i<model.getRowCount();i++){

            String maCu = model.getValueAt(i,0).toString();

            if(ma.equalsIgnoreCase(maCu)){

                JOptionPane.showMessageDialog(this,
                        "Mã sinh viên đã tồn tại!");

                return;

            }

        }

        // Tạo đối tượng

        Student sv = new Student(ma,ten,diem);

        model.addRow(new Object[]{

                sv.getMaSV(),
                sv.getHoTen(),
                sv.getDiem()

        });

        JOptionPane.showMessageDialog(this,
                "Thêm sinh viên thành công!");

        txtMa.setText("");
        txtTen.setText("");
        txtDiem.setText("");

        txtMa.requestFocus();

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->

                new Bai08QuanLySinhVien().setVisible(true));

    }

}