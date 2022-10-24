package org.example;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        super("JSplitPane Example");

        JSplitPane principal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        JToolBar toolBar = new JToolBar();
        toolBar.add(new JButton("Opciones"));

        JScrollPane pane2 = new JScrollPane(
                new JTextArea("Holi :p", 20, 20),
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JButton("Ir hacia el norte"), BorderLayout.NORTH);
        panel.add(new JButton("Ir hacia el sur"), BorderLayout.SOUTH);
        panel.add(new JButton("Ir hacia el este"), BorderLayout.EAST);
        panel.add(new JButton("Ir hacia el oeste"), BorderLayout.WEST);
        panel.add(pane2, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));

        panel.setBackground(Color.black);

        for (Component c : panel.getComponents()) {
            if (c instanceof JButton) {
                c.setBackground(Color.decode("#4C2C1A"));
                c.setForeground(Color.white);
            }
        }

        pane2.getViewport().getView().setBackground(Color.decode("#36404E"));
        pane2.getViewport().getView().setForeground(Color.white);
        pane2.getVerticalScrollBar().setBackground(Color.decode("#36404E"));
        pane2.getHorizontalScrollBar().setBackground(Color.decode("#36404E"));

        JScrollPane pane4 = new JScrollPane(
                new JTextArea("The adventure begins here...", 20, 20),
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JSplitPane pane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);


        JScrollPane pane1 = new JScrollPane(
                //create a new TreeNode with Dungeon.xml
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        pane3.setTopComponent(panel);
        pane3.setBottomComponent(pane4);
        pane3.setDividerLocation(600);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                pane1, pane3);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(250);

        principal.setTopComponent(toolBar);
        principal.setBottomComponent(splitPane);

        this.getContentPane().add("Center", principal);
        this.setSize(900, 900);
        this.setLocationRelativeTo(null);
        this.show();
    }


    public static void main(String[] args) {
        new Main();
    }
}