package vn.edu.eaut.warehouse.view;

import vn.edu.eaut.warehouse.model.Order;
import vn.edu.eaut.warehouse.model.OrderItem;
import vn.edu.eaut.warehouse.model.OrderStatusHistory;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderDetailDialog extends JDialog {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public OrderDetailDialog(Frame parent, Order order, List<OrderItem> items, List<OrderStatusHistory> history) {
        super(parent, "Order Details - #" + order.getId(), true);
        setupUI(order, items, history);
        pack();
        setLocationRelativeTo(parent);
    }

    private void setupUI(Order order, List<OrderItem> items, List<OrderStatusHistory> history) {
        setLayout(new BorderLayout(10, 10));

        // Order info panel
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Order Information"));
        infoPanel.add(new JLabel("Order ID:"));
        infoPanel.add(new JLabel(String.valueOf(order.getId())));
        infoPanel.add(new JLabel("Customer:"));
        infoPanel.add(new JLabel(order.getCustomerName()));
        infoPanel.add(new JLabel("Created:"));
        infoPanel.add(new JLabel(order.getCreatedAt().format(DATE_FORMAT)));
        infoPanel.add(new JLabel("Total:"));
        infoPanel.add(new JLabel(String.format("%.2f", order.getTotalAmount())));
        infoPanel.add(new JLabel("Status:"));
        infoPanel.add(new JLabel(order.getStatus().name()));
        infoPanel.add(new JLabel("Note:"));
        infoPanel.add(new JLabel(order.getNote() != null ? order.getNote() : ""));

        add(infoPanel, BorderLayout.NORTH);

        // Items table
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsPanel.setBorder(BorderFactory.createTitledBorder("Order Items"));
        String[] itemColumns = {"ID", "Product", "Quantity", "Unit Price", "Subtotal"};
        Object[][] itemData = new Object[items.size()][5];
        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            itemData[i][0] = item.getId();
            itemData[i][1] = item.getProductName();
            itemData[i][2] = item.getQuantity();
            itemData[i][3] = String.format("%.2f", item.getUnitPrice());
            itemData[i][4] = String.format("%.2f", item.getSubtotal());
        }
        JTable itemsTable = new JTable(itemData, itemColumns);
        itemsTable.setEnabled(false);
        itemsPanel.add(new JScrollPane(itemsTable), BorderLayout.CENTER);

        // History panel
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Status History"));
        String[] historyColumns = {"Time", "From", "To", "Changed By", "Platform", "Note"};
        Object[][] historyData = new Object[history.size()][6];
        for (int i = 0; i < history.size(); i++) {
            OrderStatusHistory h = history.get(i);
            historyData[i][0] = h.getChangedAt().format(DATE_FORMAT);
            historyData[i][1] = h.getOldStatus();
            historyData[i][2] = h.getNewStatus();
            historyData[i][3] = h.getChangedBy();
            historyData[i][4] = h.getPlatform();
            historyData[i][5] = h.getNote() != null ? h.getNote() : "";
        }
        JTable historyTable = new JTable(historyData, historyColumns);
        historyTable.setEnabled(false);
        historyPanel.add(new JScrollPane(historyTable), BorderLayout.CENTER);

        // Center panel with items and history
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        centerPanel.add(itemsPanel);
        centerPanel.add(historyPanel);
        add(centerPanel, BorderLayout.CENTER);

        // Close button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
