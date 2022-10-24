package org.example;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.io.File;
import java.util.TreeMap;

import static javax.swing.SwingUtilities.getRoot;

public class Main extends JFrame {


    public JTree build(String pathToXml) throws Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(pathToXml);
        return new JTree(build(doc.getRootElement()));
    }

    public DefaultMutableTreeNode build(org.dom4j.Element e) {
        DefaultMutableTreeNode result = new DefaultMutableTreeNode(e.getText());
        for(Object o : e.elements()) {
            org.dom4j.Element child = (Element) o;
            result.add(build(child));
        }
        return result;
    }

    public Main() throws Exception {
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
        JTree tree = build("src/main/java/org/example/Dungeon.xml");
        pane1.setViewportView(tree);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                pane1, pane3);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(250);

        principal.setTopComponent(toolBar);
        principal.setBottomComponent(splitPane);
        //tree for each element of the Dungeon.xml
        getContentPane().add(principal);



        this.getContentPane().add("Center", principal);
        this.setSize(900, 900);
        this.setLocationRelativeTo(null);
        this.show();
    }


    public static void main(String[] args) throws Exception {
        new Main();
    }
}
