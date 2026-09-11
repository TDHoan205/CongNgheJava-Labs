package vn.edu.eaut.warehouse.view;

import vn.edu.eaut.warehouse.controller.MainController;
import vn.edu.eaut.warehouse.model.Order;
import vn.edu.eaut.warehouse.model.OrderItem;
import vn.edu.eaut.warehouse.model.OrderStatusHistory;
import vn.edu.eaut.warehouse.service.WarehouseOrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainView extends JFrame {

    private MainController controller;
    private WarehouseOrderService service;

    // Tabs
    private JTabbedPane tabbedPane;
    private JPanel pendingPanel;
    private JPanel processingPanel;
    private JPanel readyPanel;

    // Pending tab components
    private JTable pendingTable;
    private DefaultTableModel pendingTableModel;
    private JButton acceptButton;

    // Processing tab components
    private JTable processingTable;
    private DefaultTableModel processingTableModel;
    private JButton packButton;
    private JButton cancelButton;

    // Ready tab components
    private JTable readyTable;
    private DefaultTableModel readyTableModel;

    // Order detail panel
    private JPanel detailPanel;
    private JTextArea itemsTextArea;
    private JTextArea historyTextArea;

    // Status bar
    private JLabel statusLabel;
    private JLabel timeLabel;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MainView() {
        this.service = WarehouseOrderService.getInstance();
        initializeComponents();
        setupLayout();
        setupController();
        setupFrame();
        startTimeUpdater();
        refreshAllTabs();
    }

    private void initializeComponents() {
        // Tabbed pane
        tabbedPane = new JTabbedPane();

        // Pending tab
        pendingPanel = new JPanel(new BorderLayout());
        String[] pendingColumns = {"ID", "Customer", "Created", "Total"};
        pendingTableModel = new DefaultTableModel(pendingColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        pendingTable = new JTable(pendingTableModel);
        pendingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        acceptButton = new JButton("Accept Order");
        JPanel pendingButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pendingButtonPanel.add(acceptButton);
        pendingButtonPanel.add(createRefreshButton("Refresh"));
        pendingPanel.add(new JScrollPane(pendingTable), BorderLayout.CENTER);
        pendingPanel.add(pendingButtonPanel, BorderLayout.SOUTH);

        // Processing tab
        processingPanel = new JPanel(new BorderLayout());
        String[] processingColumns = {"ID", "Customer", "Created", "Total"};
        processingTableModel = new DefaultTableModel(processingColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        processingTable = new JTable(processingTableModel);
        processingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        packButton = new JButton("Pack Order");
        cancelButton = new JButton("Cancel Order");
        JPanel processingButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        processingButtonPanel.add(packButton);
        processingButtonPanel.add(cancelButton);
        processingButtonPanel.add(createRefreshButton("Refresh"));
        processingPanel.add(new JScrollPane(processingTable), BorderLayout.CENTER);
        processingPanel.add(processingButtonPanel, BorderLayout.SOUTH);

        // Ready tab
        readyPanel = new JPanel(new BorderLayout());
        String[] readyColumns = {"ID", "Customer", "Created", "Total"};
        readyTableModel = new DefaultTableModel(readyColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        readyTable = new JTable(readyTableModel);
        readyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel readyButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        readyButtonPanel.add(createRefreshButton("Refresh"));
        readyButtonPanel.add(createLogoutButton());
        readyPanel.add(new JScrollPane(readyTable), BorderLayout.CENTER);
        readyPanel.add(readyButtonPanel, BorderLayout.SOUTH);

        // Detail panel
        detailPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        itemsTextArea = new JTextArea(8, 30);
        itemsTextArea.setEditable(false);
        historyTextArea = new JTextArea(8, 30);
        historyTextArea.setEditable(false);
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsPanel.setBorder(BorderFactory.createTitledBorder("Order Items"));
        itemsPanel.add(new JScrollPane(itemsTextArea));
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Status History"));
        historyPanel.add(new JScrollPane(historyTextArea));
        detailPanel.add(itemsPanel);
        detailPanel.add(historyPanel);

        // Status bar
        statusLabel = new JLabel();
        timeLabel = new JLabel();
    }

    private JButton createRefreshButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(e -> refreshCurrentTab());
        return button;
    }

    private JButton createLogoutButton() {
        JButton button = new JButton("Logout");
        button.addActionListener(e -> controller.handleLogout());
        return button;
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Add tabs
        tabbedPane.addTab("PENDING", pendingPanel);
        tabbedPane.addTab("PROCESSING", processingPanel);
        tabbedPane.addTab("READY", readyPanel);

        add(tabbedPane, BorderLayout.CENTER);
        add(detailPanel, BorderLayout.SOUTH);

        // Status bar
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        updateStatusLabel();
        statusBar.add(statusLabel, BorderLayout.WEST);
        statusBar.add(timeLabel, BorderLayout.EAST);
        add(statusBar, BorderLayout.PAGE_END);
    }

    private void setupController() {
        controller = new MainController(this);

        // Pending tab actions
        acceptButton.addActionListener(e -> handleAcceptOrder());
        pendingTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showPendingOrderDetails();
            }
        });

        // Processing tab actions
        packButton.addActionListener(e -> handlePackOrder());
        cancelButton.addActionListener(e -> handleCancelOrder());
        processingTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showProcessingOrderDetails();
            }
        });

        // Ready tab actions
        readyTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showReadyOrderDetails();
            }
        });

        // Tab change listener
        tabbedPane.addChangeListener(e -> {
            clearDetailPanel();
        });
    }

    private void setupFrame() {
        setTitle("Warehouse Management - " + service.getCurrentEmployee().getFullName());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void startTimeUpdater() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    timeLabel.setText("Time: " + LocalDateTime.now().format(DATE_FORMAT));
                });
            }
        }, 0, 1000);
    }

    private void updateStatusLabel() {
        if (service.getCurrentEmployee() != null) {
            statusLabel.setText("Logged in: " + service.getCurrentEmployee().getFullName() + " (WAREHOUSE)");
        }
    }

    // Table population methods
    public void refreshPendingOrders() {
        List<Order> orders = controller.loadPendingOrders();
        pendingTableModel.setRowCount(0);
        for (Order order : orders) {
            pendingTableModel.addRow(new Object[]{
                order.getId(),
                order.getCustomerName(),
                order.getCreatedAt().format(DATE_FORMAT),
                String.format("%.2f", order.getTotalAmount())
            });
        }
    }

    public void refreshProcessingOrders() {
        List<Order> orders = controller.loadProcessingOrders();
        processingTableModel.setRowCount(0);
        for (Order order : orders) {
            processingTableModel.addRow(new Object[]{
                order.getId(),
                order.getCustomerName(),
                order.getCreatedAt().format(DATE_FORMAT),
                String.format("%.2f", order.getTotalAmount())
            });
        }
    }

    public void refreshReadyOrders() {
        List<Order> orders = controller.loadReadyOrders();
        readyTableModel.setRowCount(0);
        for (Order order : orders) {
            readyTableModel.addRow(new Object[]{
                order.getId(),
                order.getCustomerName(),
                order.getCreatedAt().format(DATE_FORMAT),
                String.format("%.2f", order.getTotalAmount())
            });
        }
    }

    public void refreshAllTabs() {
        refreshPendingOrders();
        refreshProcessingOrders();
        refreshReadyOrders();
        clearDetailPanel();
    }

    private void refreshCurrentTab() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        switch (selectedIndex) {
            case 0 -> refreshPendingOrders();
            case 1 -> refreshProcessingOrders();
            case 2 -> refreshReadyOrders();
        }
        clearDetailPanel();
    }

    private void clearDetailPanel() {
        itemsTextArea.setText("");
        historyTextArea.setText("");
    }

    // Action handlers
    private void handleAcceptOrder() {
        int selectedRow = pendingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to accept", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        long orderId = (Long) pendingTableModel.getValueAt(selectedRow, 0);
        controller.acceptOrder(orderId);
    }

    private void handlePackOrder() {
        int selectedRow = processingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to pack", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        long orderId = (Long) processingTableModel.getValueAt(selectedRow, 0);
        controller.packOrder(orderId);
    }

    private void handleCancelOrder() {
        int selectedRow = processingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to cancel", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        long orderId = (Long) processingTableModel.getValueAt(selectedRow, 0);
        controller.cancelOrder(orderId);
    }

    // Detail panel methods
    private void showPendingOrderDetails() {
        int selectedRow = pendingTable.getSelectedRow();
        if (selectedRow == -1) return;
        long orderId = (Long) pendingTableModel.getValueAt(selectedRow, 0);
        showOrderDetails(orderId);
    }

    private void showProcessingOrderDetails() {
        int selectedRow = processingTable.getSelectedRow();
        if (selectedRow == -1) return;
        long orderId = (Long) processingTableModel.getValueAt(selectedRow, 0);
        showOrderDetails(orderId);
    }

    private void showReadyOrderDetails() {
        int selectedRow = readyTable.getSelectedRow();
        if (selectedRow == -1) return;
        long orderId = (Long) readyTableModel.getValueAt(selectedRow, 0);
        showOrderDetails(orderId);
    }

    private void showOrderDetails(long orderId) {
        List<OrderItem> items = controller.loadOrderItems(orderId);
        List<OrderStatusHistory> history = controller.loadOrderHistory(orderId);

        StringBuilder itemsBuilder = new StringBuilder();
        itemsBuilder.append(String.format("%-5s %-20s %-8s %-10s %-10s%n", "ID", "Product", "Qty", "Price", "Subtotal"));
        itemsBuilder.append("-".repeat(60)).append("\n");
        for (OrderItem item : items) {
            itemsBuilder.append(String.format("%-5d %-20s %-8d %-10.2f %-10.2f%n",
                item.getId(),
                truncate(item.getProductName(), 20),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal()));
        }
        itemsTextArea.setText(itemsBuilder.toString());

        StringBuilder historyBuilder = new StringBuilder();
        historyBuilder.append(String.format("%-20s %-12s %-12s %-15s%n", "Time", "From", "To", "Note"));
        historyBuilder.append("-".repeat(70)).append("\n");
        for (OrderStatusHistory h : history) {
            historyBuilder.append(String.format("%-20s %-12s %-12s %-15s%n",
                h.getChangedAt().format(DATE_FORMAT),
                h.getOldStatus(),
                h.getNewStatus(),
                truncate(h.getNote() != null ? h.getNote() : "", 15)));
        }
        historyTextArea.setText(historyBuilder.toString());
    }

    private String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}
