package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.SwingWorker;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ProductCsvFrame extends JFrame {

    private JTextField txtMaSP;
    private JTextField txtTenSP;
    private JTextField txtDonGia;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnSave;
    private JButton btnLoad;

    private JTable table;
    private DefaultTableModel tableModel;

    private File selectedFile;

    public ProductCsvFrame() {

        setTitle("Bài 10 - Quản lý sản phẩm CSV");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createInterface();
    }

    private void createInterface() {

        txtMaSP = new JTextField();

        txtTenSP = new JTextField();

        txtDonGia = new JTextField();

        JPanel inputPanel =
                new JPanel(
                        new GridLayout(3, 2, 10, 10)
                );

        inputPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Thông tin sản phẩm"
                )
        );

        inputPanel.add(
                new JLabel("Mã sản phẩm:")
        );

        inputPanel.add(txtMaSP);

        inputPanel.add(
                new JLabel("Tên sản phẩm:")
        );

        inputPanel.add(txtTenSP);

        inputPanel.add(
                new JLabel("Đơn giá:")
        );

        inputPanel.add(txtDonGia);

        btnAdd =
                new JButton("Thêm");

        btnEdit =
                new JButton("Sửa");

        btnDelete =
                new JButton("Xóa");

        btnClear =
                new JButton("Làm mới");

        btnSave =
                new JButton("Lưu CSV");

        btnLoad =
                new JButton("Đọc CSV");

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnLoad);

        JPanel northPanel =
                new JPanel(
                        new BorderLayout()
                );

        northPanel.add(
                inputPanel,
                BorderLayout.CENTER
        );

        northPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(
                northPanel,
                BorderLayout.NORTH
        );

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Mã SP",
                                "Tên SP",
                                "Đơn giá"
                        },
                        0
                );

        table =
                new JTable(tableModel);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        btnAdd.addActionListener(
                e -> addProduct()
        );

        btnEdit.addActionListener(
                e -> editProduct()
        );

        btnDelete.addActionListener(
                e -> deleteProduct()
        );

        btnClear.addActionListener(
                e -> clearForm()
        );

        btnSave.addActionListener(
                e -> saveCsv()
        );

        btnLoad.addActionListener(
                e -> loadCsv()
        );

        table.getSelectionModel()
                .addListSelectionListener(
                        e -> fillFormFromTable()
                );
    }

    private void addProduct() {

        Product product =
                readProductFromForm();

        if (product == null) {
            return;
        }

        tableModel.addRow(
                new Object[]{
                        product.maSP,
                        product.tenSP,
                        product.donGia
                }
        );

        clearForm();
    }

    private void editProduct() {

        int row =
                table.getSelectedRow();

        if (row < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn sản phẩm cần sửa"
            );

            return;
        }

        Product product =
                readProductFromForm();

        if (product == null) {
            return;
        }

        tableModel.setValueAt(
                product.maSP,
                row,
                0
        );

        tableModel.setValueAt(
                product.tenSP,
                row,
                1
        );

        tableModel.setValueAt(
                product.donGia,
                row,
                2
        );

        clearForm();
    }

    private void deleteProduct() {

        int row =
                table.getSelectedRow();

        if (row < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn sản phẩm cần xóa"
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Bạn có chắc muốn xóa?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirm ==
                        JOptionPane.YES_OPTION
        ) {

            tableModel.removeRow(row);

            clearForm();
        }
    }

    private Product readProductFromForm() {

        String maSP =
                txtMaSP.getText().trim();

        String tenSP =
                txtTenSP.getText().trim();

        String donGiaText =
                txtDonGia.getText().trim();

        if (
                maSP.isEmpty()
                        ||
                        tenSP.isEmpty()
                        ||
                        donGiaText.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ thông tin"
            );

            return null;
        }

        double donGia;

        try {

            donGia =
                    Double.parseDouble(
                            donGiaText
                    );

            if (donGia < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Đơn giá không được âm"
                );

                return null;
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Đơn giá phải là số"
            );

            return null;
        }

        return new Product(
                maSP,
                tenSP,
                donGia
        );
    }

    private void fillFormFromTable() {

        int row =
                table.getSelectedRow();

        if (row < 0) {
            return;
        }

        txtMaSP.setText(
                tableModel.getValueAt(
                        row,
                        0
                ).toString()
        );

        txtTenSP.setText(
                tableModel.getValueAt(
                        row,
                        1
                ).toString()
        );

        txtDonGia.setText(
                tableModel.getValueAt(
                        row,
                        2
                ).toString()
        );
    }

    private void clearForm() {

        txtMaSP.setText("");

        txtTenSP.setText("");

        txtDonGia.setText("");

        table.clearSelection();

        txtMaSP.requestFocus();
    }

    private void saveCsv() {

        if (tableModel.getRowCount() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Danh sách sản phẩm đang trống"
            );

            return;
        }

        JFileChooser chooser =
                new JFileChooser();

        int result =
                chooser.showSaveDialog(this);

        if (
                result !=
                        JFileChooser.APPROVE_OPTION
        ) {

            return;
        }

        selectedFile =
                chooser.getSelectedFile();

        if (
                !selectedFile
                        .getName()
                        .toLowerCase()
                        .endsWith(".csv")
        ) {

            selectedFile =
                    new File(
                            selectedFile.getAbsolutePath()
                                    + ".csv"
                    );
        }

        List<Product> products =
                getProductsFromTable();

        btnSave.setEnabled(false);
        btnLoad.setEnabled(false);

        SwingWorker<Void, Void> worker =
                new SwingWorker<>() {

            @Override
            protected Void doInBackground()
                    throws Exception {

                List<String> lines =
                        new ArrayList<>();

                lines.add(
                        "MaSP,TenSP,DonGia"
                );

                for (Product product :
                        products) {

                    lines.add(
                            product.maSP
                                    + ","
                                    + product.tenSP
                                    + ","
                                    + product.donGia
                    );
                }

                Files.write(
                        selectedFile.toPath(),
                        lines,
                        StandardCharsets.UTF_8
                );

                return null;
            }

            @Override
            protected void done() {

                btnSave.setEnabled(true);
                btnLoad.setEnabled(true);

                try {

                    get();

                    JOptionPane.showMessageDialog(
                            ProductCsvFrame.this,
                            "Lưu CSV thành công!"
                    );

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            ProductCsvFrame.this,
                            "Lỗi khi lưu CSV:\n"
                                    + ex.getMessage()
                    );
                }
            }
        };

        worker.execute();
    }

    private void loadCsv() {

        JFileChooser chooser =
                new JFileChooser();

        int result =
                chooser.showOpenDialog(this);

        if (
                result !=
                        JFileChooser.APPROVE_OPTION
        ) {

            return;
        }

        selectedFile =
                chooser.getSelectedFile();

        btnSave.setEnabled(false);
        btnLoad.setEnabled(false);

        SwingWorker<List<Product>, Void> worker =
                new SwingWorker<>() {

            @Override
            protected List<Product> doInBackground()
                    throws Exception {

                List<Product> products =
                        new ArrayList<>();

                List<String> lines =
                        Files.readAllLines(
                                selectedFile.toPath(),
                                StandardCharsets.UTF_8
                        );

                for (int i = 1;
                     i < lines.size();
                     i++) {

                    String line =
                            lines.get(i).trim();

                    if (line.isEmpty()) {
                        continue;
                    }

                    String[] parts =
                            line.split(",");

                    if (parts.length < 3) {
                        continue;
                    }

                    String maSP =
                            parts[0].trim();

                    String tenSP =
                            parts[1].trim();

                    double donGia =
                            Double.parseDouble(
                                    parts[2].trim()
                            );

                    products.add(
                            new Product(
                                    maSP,
                                    tenSP,
                                    donGia
                            )
                    );
                }

                return products;
            }

            @Override
            protected void done() {

                try {

                    List<Product> products =
                            get();

                    tableModel.setRowCount(0);

                    for (Product product :
                            products) {

                        tableModel.addRow(
                                new Object[]{
                                        product.maSP,
                                        product.tenSP,
                                        product.donGia
                                }
                        );
                    }

                    JOptionPane.showMessageDialog(
                            ProductCsvFrame.this,
                            "Đọc CSV thành công!"
                    );

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            ProductCsvFrame.this,
                            "Lỗi khi đọc CSV:\n"
                                    + ex.getMessage()
                    );
                }

                btnSave.setEnabled(true);
                btnLoad.setEnabled(true);
            }
        };

        worker.execute();
    }

    private List<Product> getProductsFromTable() {

        List<Product> products =
                new ArrayList<>();

        for (
                int i = 0;
                i < tableModel.getRowCount();
                i++
        ) {

            String maSP =
                    tableModel
                            .getValueAt(i, 0)
                            .toString();

            String tenSP =
                    tableModel
                            .getValueAt(i, 1)
                            .toString();

            double donGia =
                    Double.parseDouble(
                            tableModel
                                    .getValueAt(i, 2)
                                    .toString()
                    );

            products.add(
                    new Product(
                            maSP,
                            tenSP,
                            donGia
                    )
            );
        }

        return products;
    }

    private static class Product {

        String maSP;

        String tenSP;

        double donGia;

        Product(
                String maSP,
                String tenSP,
                double donGia
        ) {

            this.maSP = maSP;
            this.tenSP = tenSP;
            this.donGia = donGia;
        }
    }
}