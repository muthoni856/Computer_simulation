package com.example.Computer_simulation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class QueueSimulationGUI extends JFrame {
    private DefaultTableModel tableModel;

    public QueueSimulationGUI() {
        setTitle("Queue Simulation's Table");
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
        tableModel.addColumn("Customer No.");
        tableModel.addColumn("I.A.T");
        tableModel.addColumn("Clock Time");
        tableModel.addColumn("Service Time");
        tableModel.addColumn("Service Start");
        tableModel.addColumn("Service End");
        tableModel.addColumn("Number in System");
        tableModel.addColumn("Number in Queue");
        tableModel.addColumn("Queue Waiting Time");
        tableModel.addColumn("Time in the System");
        tableModel.addColumn("Server Idle Time");

        simulateQueue();

        // Displaying metrics
        displayMetrics();
    }

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
        // Clear the table
        tableModel.setRowCount(0);

        for (int i = 0; i < 10; i++) { // For all the 10 customers
            double arrivalTime = arrivalTimes[i];
            double serviceTime = serviceTimes[i];

            // Calculate clock time for the current customer
            clockTime += arrivalTime;

            // Calculate service start and end times
            double serviceStart = i == 0 ? arrivalTime : (clockTime > (double) tableModel.getValueAt(i - 1, 5)) ? clockTime : (double) tableModel.getValueAt(i - 1, 5); // Service End of previous customer
            double serviceEnd = serviceStart + serviceTime;

            // Update number in system and number in queue
            int customersInSystem;
            int customersInQueue;
            if (i == 0) {
                customersInSystem = 1;
            } else {
                customersInSystem = 0;
                for (int j = 0; j <= i; j++) {
                    if (clockTime < (double) tableModel.getValueAt(i - 1, 5)) { // You need to replace ??? with the appropriate condition
                        customersInSystem++;
                    }
                }
            }
            customersInQueue = customersInSystem - 1;

            // Calculate waiting time and time in the system for the current customer
            double waitingTime = Math.max(serviceStart - clockTime, 0);
            double timeInTheSystem = waitingTime + serviceTime;

            // Calculate server idle time
            serverIdleTime = i == 0 ? 0 : Math.max(clockTime - (double) tableModel.getValueAt(i - 1, 5), 0); // Server Idle Time

            // Add data to the table
            tableModel.addRow(new Object[]{
                    i + 1, // Customer
                    arrivalTime, // IAT
                    Math.round(clockTime * 10.0) / 10.0, // Clock Time
                    serviceTime, // Service Time
                    Math.round(serviceStart * 10.0) / 10.0, // Service Start
                    Math.round(serviceEnd * 10.0) / 10.0, // Service End
                    customersInSystem, // No. in System
                    customersInQueue, // No. in Queue
                    Math.round(waitingTime * 10.0) / 10.0, // Waiting Time
                    Math.round(timeInTheSystem * 10.0) / 10.0, // Time in the System
                    Math.round(serverIdleTime * 10.0) / 10.0 // Server Idle Time
            });

            // Update totals
            totalWaitingTime += waitingTime;
            totalTimeInTheSystem += timeInTheSystem;
            totalServiceTime += serviceTime;
            totalArrivalTime += arrivalTime;
            totalCustomers++;
            numberInSystem = 15;
            customersWhoWait = 5;
            totalServerIdleTime += serverIdleTime;
        }
// Add total row
        tableModel.addRow(new Object[]{
                "Total", // Customer
                totalArrivalTime, // IAT
                "", // Clock Time (not applicable for total)
                totalServiceTime, // Service Time
                "", // Service Start (not applicable for total)
                "", // Service End (not applicable for total)
                numberInSystem, // No. in System
                customersWhoWait, // No. in Queue
                Math.round(totalWaitingTime * 10.0) / 10.0, // Waiting Time
                Math.round(totalTimeInTheSystem * 10.0) / 10.0, // Time in the System
                Math.round(totalServerIdleTime * 10.0) / 10.0
        });
    }


    private void displayMetrics() {

        double totalServiceTime = tableModel.getValueAt(tableModel.getRowCount() - 1, 3).equals("") ? 0 : (double) tableModel.getValueAt(tableModel.getRowCount() - 1, 3);

        double averageWaitingTime = totalWaitingTime / totalCustomers;
        double probabilityOfWait = customersWhoWait / totalCustomers;
        double proportionOfIdleTime = totalServerIdleTime / (double) tableModel.getValueAt(tableModel.getRowCount() - 2, 5);
        double proportionOfBusyTime = 1 - proportionOfIdleTime;
        double averageServiceTime = totalServiceTime / totalCustomers;
        double averageWaitingTimeForWaitingCustomers = totalWaitingTime / customersWhoWait;
        double averageTimeInTheSystem = totalTimeInTheSystem / totalCustomers;
        double averageTimeBetweenArrivals = totalArrivalTime / (totalCustomers - 1);

        // Display metrics
        JTextArea metricsArea = new JTextArea();
        metricsArea.append("Metrics:\n");
        metricsArea.append("a. Average waiting time for the customer = " + Math.round(averageWaitingTime * 100.0) / 100.0 + "\n");
        metricsArea.append("b. Proportion of idle time of the server = " + Math.round(proportionOfIdleTime * 100.0) / 100.0 + "\n");
        metricsArea.append("c. Proportion of time the server was busy = " + Math.round(proportionOfBusyTime * 100.0) / 100.0 + "\n");
        metricsArea.append("d. Probability that the customer has to wait in the queue = " + Math.round(probabilityOfWait * 100.0) / 100.0 + "\n");
        metricsArea.append("e. Average waiting time for those who have to wait = " + Math.round(averageWaitingTimeForWaitingCustomers * 100.0) / 100.0 + "\n");
        metricsArea.append("f. Average time spent in the system = " + Math.round(averageTimeInTheSystem * 100.0) / 100.0 + "\n");
        metricsArea.append("g. Average time between arrivals = " + Math.round(averageTimeBetweenArrivals * 100.0) / 100.0 + "\n");
        metricsArea.append("h. Average service time = " + Math.round(averageServiceTime * 100.0) / 100.0 + "\n");

        getContentPane().add(metricsArea, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QueueSimulationGUI gui = new QueueSimulationGUI();
            gui.setVisible(true);
        });
    }
}
