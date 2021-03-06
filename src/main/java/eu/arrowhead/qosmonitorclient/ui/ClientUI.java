/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.arrowhead.qosmonitorclient.ui;

import eu.arrowhead.qosmonitorclient.database.ServiceRegistryEntry;
import eu.arrowhead.qosmonitorclient.httprequests.HttpRequestHandler;
import java.time.LocalDateTime;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author IL0016G
 */
public class ClientUI extends javax.swing.JFrame {

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        clientTextArea = new javax.swing.JTextArea();
        subscribeButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        changeServiceRegistryAddressMenuItem = new javax.swing.JMenuItem();
        changeEventHandlerAddressMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QoS Monitor Client");

        clientTextArea.setEditable(false);
        clientTextArea.setColumns(20);
        clientTextArea.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        clientTextArea.setRows(5);
        jScrollPane1.setViewportView(clientTextArea);
        DefaultCaret caret = (DefaultCaret)clientTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        subscribeButton.setText("Subscribe");
        subscribeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subscribeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addComponent(subscribeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addGap(205, 205, 205))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(subscribeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        jMenu2.setText("Edit");

        changeServiceRegistryAddressMenuItem.setText("Change Service Registry address");
        changeServiceRegistryAddressMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeServiceRegistryAddressMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(changeServiceRegistryAddressMenuItem);

        changeEventHandlerAddressMenuItem.setText("Change Event Handler address");
        changeEventHandlerAddressMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeEventHandlerAddressMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(changeEventHandlerAddressMenuItem);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void subscribeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subscribeButtonActionPerformed
        ClientUI.entry = HttpRequestHandler.registerToServiceRegistry();
        HttpRequestHandler.subscribe();
        
        appendTextToTextArea(
                "[" + LocalDateTime.now().toString() + "]  "
                + "Listening for events...\n\n"
        );
    }//GEN-LAST:event_subscribeButtonActionPerformed

    private void changeServiceRegistryAddressMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeServiceRegistryAddressMenuItemActionPerformed
        JTextField serviceRegistryAddress = new JTextField(HttpRequestHandler.getServiceRegistryAddress());
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Service Registry address"),
                serviceRegistryAddress
        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Change Service Registry address", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            HttpRequestHandler.setServiceRegistryAddress(serviceRegistryAddress.getText());
        }
    }//GEN-LAST:event_changeServiceRegistryAddressMenuItemActionPerformed

    private void changeEventHandlerAddressMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeEventHandlerAddressMenuItemActionPerformed
        JTextField eventHandlerAddress = new JTextField(HttpRequestHandler.getEventHandlerAddress());
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Event Handler address"),
                eventHandlerAddress
        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Change Event Handler address", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            HttpRequestHandler.setEventHandlerAddress(eventHandlerAddress.getText());
        }
    }//GEN-LAST:event_changeEventHandlerAddressMenuItemActionPerformed

    private static ClientUI instance = null;
    private static ServiceRegistryEntry entry = null;

    protected ClientUI() {
        initComponents();
    }

    public static ClientUI getInstance() {
        if (instance == null) {
            instance = new ClientUI();
        }
        return instance;
    }

    public void appendTextToTextArea(String text) {
        this.clientTextArea.append(text);
    }
    
    public ServiceRegistryEntry getServiceRegistryEntry() {
        return this.entry;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem changeEventHandlerAddressMenuItem;
    private javax.swing.JMenuItem changeServiceRegistryAddressMenuItem;
    private javax.swing.JTextArea clientTextArea;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton subscribeButton;
    // End of variables declaration//GEN-END:variables
}
