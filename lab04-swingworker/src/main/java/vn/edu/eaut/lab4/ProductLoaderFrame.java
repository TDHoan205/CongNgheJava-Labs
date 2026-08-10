package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.SwingWorker;
import java.util.List;

public class ProductLoaderFrame extends JFrame {

    private JButton btnLoad;

    private JTable table;

    private DefaultTableModel tableModel;

    private JProgressBar progressBar;

    private JLabel lblStatus;

    public ProductLoaderFrame() {

        setTitle("Bài 9 - Mô phỏng tải sản phẩm");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btnLoad =
                new JButton("Tải sản phẩm");

        progressBar =
                new JProgressBar(0, 100);

        progressBar.setStringPainted(true);

        lblStatus =
                new JLabel(
                        "Trạng thái: Chưa tải"
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

        JPanel topPanel =
                new JPanel(
                        new GridLayout(3, 1, 10, 10)
                );

        topPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        topPanel.add(btnLoad);
        topPanel.add(progressBar);
        topPanel.add(lblStatus);

        add(
                topPanel,
                BorderLayout.NORTH
        );

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        btnLoad.addActionListener(
                e -> loadProducts()
        );
    }

    private void loadProducts() {

        btnLoad.setEnabled(false);

        tableModel.setRowCount(0);

        progressBar.setValue(0);

        lblStatus.setText(
                "Trạng thái: Đang tải..."
        );

        SwingWorker<Void, Product> worker =
                new SwingWorker<>() {

            @Override
            protected Void doInBackground()
                    throws Exception {

                Product[] products = {

                        new Product(
                                "SP01",
                                "Bàn phím",
                                250000
                        ),

                        new Product(
                                "SP02",
                                "Chuột",
                                150000
                        ),

                        new Product(
                                "SP03",
                                "Màn hình",
                                2500000
                        )
                };

                for (
                        int i = 0;
                        i < products.length;
                        i++
                ) {

                    Thread.sleep(1000);

                    publish(products[i]);

                    int progress =
                            (int) (
                                    (i + 1)
                                            * 100.0
                                            / products.length
                            );

                    setProgress(progress);
                }

                return null;
            }

            @Override
            protected void process(
                    List<Product> chunks
            ) {

                for (Product product :
                        chunks) {

                    tableModel.addRow(
                            new Object[]{
                                    product.maSP,
                                    product.tenSP,
                                    product.donGia
                            }
                    );
                }
            }

            @Override
            protected void done() {

                if (!isCancelled()) {

                    progressBar.setValue(100);

                    lblStatus.setText(
                            "Trạng thái: Tải thành công "
                                    + tableModel.getRowCount()
                                    + " sản phẩm"
                    );
                }

                btnLoad.setEnabled(true);
            }
        };

        worker.addPropertyChangeListener(
                evt -> {

                    if ("progress".equals(
                            evt.getPropertyName()
                    )) {

                        progressBar.setValue(
                                (int) evt.getNewValue()
                        );
                    }
                }
        );

        worker.execute();
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