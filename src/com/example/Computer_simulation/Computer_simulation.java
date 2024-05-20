package com.example.Computer_simulation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class QueueSimulationGUI extends JFrame {
    private DefaultTableModel tableModel;

    public QueueSimulationGUI() {
        setTitle("Queue Simulation Results");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        getContentPane().add(panel);

        tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add columns to the table
        tableModel.addColumn("Customer");
        tableModel.addColumn("IAT");
        tableModel.addColumn("Clock Time");
        tableModel.addColumn("Service Time");
        tableModel.addColumn("Service Start");
        tableModel.addColumn("Service End");
        tableModel.addColumn("No. in System");
        tableModel.addColumn("No. in Queue");
        tableModel.addColumn("Waiting Time");
        tableModel.addColumn("Time in the System");
        tableModel.addColumn("Server Idle Time");

        simulateQueue();

        // Calculate and display metrics
        displayMetrics();
    }
    // Initialize variables
    double clockTime = 0; // Start with 0
    double serverIdleTime = 0;
    double totalServerIdleTime = 0;

    double totalWaitingTime = 0;
    double totalTimeInTheSystem = 0;
    double totalServiceTime = 0;
    double totalArrivalTime = 0;
    int totalCustomers = 0;
    int customersInQueue = 0;
    int customersWhoWait = 0;
    int numberInSystem = 0;

    private void simulateQueue() {
        double[] arrivalTimes = {1.9, 1.3, 1.1, 1.0, 2.2, 2.1, 1.8, 2.8, 2.7, 2.4};
        double[] serviceTimes = {1.7, 1.8, 1.5, 0.9, 0.6, 1.7, 1.1, 1.8, 0.8, 0.5};

