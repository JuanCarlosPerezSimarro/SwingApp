package org.example;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;

public class Main extends JFrame {


    public JTree build(String pathToXml) throws Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(pathToXml);
        return new JTree(build(doc.getRootElement()));
    }

    public DefaultMutableTreeNode build(org.dom4j.Element e) {
        DefaultMutableTreeNode result = new DefaultMutableTreeNode(e.getText());
        //Select all the children of the current element and add them to the tree node recursively
        for (Object o : e.elements()) {
            Element child = (Element) o;
            result.add(build(child));
        }
        return result;
    }

    public Main() throws Exception {
        super("JSplitPane Example");

        JSplitPane principal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        JMenuBar menuBar = new JMenuBar();
        JMenu load = new JMenu("Load");
        JMenu start = new JMenu("Start");
        menuBar.add(load);
        menuBar.add(start);

        JTextArea juego = new JTextArea("Holi :p");
        JScrollPane pane2 = new JScrollPane(
                juego,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        JButton norte = new JButton("Norte");
        JButton sur = new JButton("Sur");
        JButton este = new JButton("Este");
        JButton oeste = new JButton("Oeste");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(norte, BorderLayout.NORTH);
        panel.add(sur, BorderLayout.SOUTH);
        panel.add(este, BorderLayout.EAST);
        panel.add(oeste, BorderLayout.WEST);
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

        JTextArea dialogo = new JTextArea("The adventure begins here...", 20, 20);
        JScrollPane pane4 = new JScrollPane(
                dialogo,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JSplitPane pane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        /*JTree tree = new JTree(
                new DefaultMutableTreeNode("Dungeon") {{
                    add(new DefaultMutableTreeNode("Sala central") {{
                        add(new DefaultMutableTreeNode("Puerta norte"));
                        add(new DefaultMutableTreeNode("Puerta sur"));
                        add(new DefaultMutableTreeNode("Descripcion"));
                    }});
                    add(new DefaultMutableTreeNode("Sala norte") {{
                        add(new DefaultMutableTreeNode("Puerta sur"));
                        add(new DefaultMutableTreeNode("Descripcion"));
                    }});
                    add(new DefaultMutableTreeNode("Sala sur") {{
                        add(new DefaultMutableTreeNode("Puerta norte"));
                        add(new DefaultMutableTreeNode("Descripcion"));
                    }});
                }}
        );*/

        XmlJTree tree = new XmlJTree(null);


        load.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Choose a file");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("XML file",
                        "xml");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    tree.setPath(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        });


        final Object[] currentRoom = {tree.getModel().getRoot()};

        start.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //set current room to the first room in the tree instead of the root node (Dungeon) and update the tree to show the current room as selected node
                currentRoom[0] = tree.getModel().getChild(tree.getModel().getRoot(), 0);
                tree.setSelectionPath(new TreePath(((DefaultMutableTreeNode) currentRoom[0]).getPath()));
                for (int i = 0; i < tree.getModel().getChildCount(currentRoom[0]); i++) {
                    if (tree.getModel().getChild(currentRoom[0], i).toString().equals("description")) {
                        if (tree.getModel().getChildCount(tree.getModel().getChild(currentRoom[0], i)) > 0) {
                            juego.setText(tree.getModel().getChild(tree.getModel().getChild(currentRoom[0], i), 0).toString());
                            dialogo.setText("You are in the " + currentRoom[0].toString() + " called " + tree.getModel().getChild(tree.getModel().getChild(currentRoom[0], i), 0).toString() + "\n What do you want to do?");
                        }
                    }
                }
            }
        });


        norte.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

            }
        });







        /*norte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString().equals("Sala central")) {
                    tree.setSelectionPath(new TreePath(new Object[]{tree.getModel().getRoot(), ((DefaultMutableTreeNode) tree.getModel().getRoot()).getChildAt(1)}));
                    currentRoom[0] = ((DefaultMutableTreeNode) tree.getModel().getRoot()).getChildAt(1);
                    juego.setText(((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString());
                    dialogo.setText("You are in the " + ((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString() + "\n" + "You can go to the " + ((DefaultMutableTreeNode) currentRoom[0]).getChildAt(0).toString() + "\n" + "You can also see the " + ((DefaultMutableTreeNode) currentRoom[0]).getChildAt(1).toString());
                } else if (((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString().equals("Sala sur")) {
                    tree.setSelectionPath(new TreePath(new Object[]{tree.getModel().getRoot(), ((DefaultMutableTreeNode) tree.getModel().getRoot()).getChildAt(0)}));
                    currentRoom[0] = ((DefaultMutableTreeNode) tree.getModel().getRoot()).getChildAt(0);
                    juego.setText(((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString());
                    dialogo.setText("You are in the " + ((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString() + "\n" + "You can go to the " + ((DefaultMutableTreeNode) currentRoom[0]).getChildAt(0).toString() + " or to the " + ((DefaultMutableTreeNode) currentRoom[0]).getChildAt(1).toString() + "\n" + "You can also see the " + ((DefaultMutableTreeNode) currentRoom[0]).getChildAt(2).toString());
                } else if (((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString().equals("Sala norte")) {
                    JOptionPane.showMessageDialog(null, "There is no room in that direction");
                    tree.setSelectionPath(new TreePath(new Object[]{tree.getModel().getRoot(), ((DefaultMutableTreeNode) tree.getModel().getRoot()).getChildAt(1)}));
                }
            }
        });

        sur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString().equals("Sala central")) {
                    tree.setSelectionPath(new TreePath(new Object[]{tree.getModel().getRoot(), ((DefaultMutableTreeNode) tree.getModel().getRoot()).getChildAt(2)}));
                    currentRoom[0] = ((DefaultMutableTreeNode) tree.getModel().getRoot()).getChildAt(2);
                    juego.setText(((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString());
                    dialogo.setText("You are in the " + ((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString() + "\n" + "You can go to the " + ((DefaultMutableTreeNode) currentRoom[0]).getChildAt(0).toString() + "\n" + "You can also see the " + ((DefaultMutableTreeNode) currentRoom[0]).getChildAt(1).toString());
                } else if (((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString().equals("Sala norte")) {
                    tree.setSelectionPath(new TreePath(new Object[]{tree.getModel().getRoot(), ((DefaultMutableTreeNode) tree.getModel().getRoot()).getChildAt(0)}));
                    currentRoom[0] = ((DefaultMutableTreeNode) tree.getModel().getRoot()).getChildAt(0);
                    juego.setText(((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString());
                    dialogo.setText("You are in the " + ((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString() + "\n" + "You can go to the " + ((DefaultMutableTreeNode) currentRoom[0]).getChildAt(0).toString() + " or to the " + ((DefaultMutableTreeNode) currentRoom[0]).getChildAt(1).toString() + "\n" + "You can also see the " + ((DefaultMutableTreeNode) currentRoom[0]).getChildAt(2).toString());
                } else if (((DefaultMutableTreeNode) currentRoom[0]).getUserObject().toString().equals("Sala sur")) {
                    JOptionPane.showMessageDialog(null, "There is no room in that direction");
                    tree.setSelectionPath(new TreePath(new Object[]{tree.getModel().getRoot(), ((DefaultMutableTreeNode) tree.getModel().getRoot()).getChildAt(2)}));
                }
            }
        });*/

        JScrollPane pane1 = new JScrollPane(
                //create a new TreeNode with Dungeon.xml
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        pane3.setTopComponent(panel);
        pane3.setBottomComponent(pane4);
        pane3.setDividerLocation(600);
        JTree tree2 = build("src/main/java/org/example/Dungeon.xml");
        pane1.setViewportView(tree);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                pane1, pane3);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(250);

        principal.setTopComponent(menuBar);
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

    public static class XmlJTree extends JTree {

        DefaultTreeModel dtModel = null;

        public XmlJTree(String filePath) {
            if (filePath != null)
                setPath(filePath);
        }

        public void setPath(String filePath) {
            Node root = null;
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                org.w3c.dom.Document doc = builder.parse(filePath);
                root = (Node) doc.getDocumentElement();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Can't parse file", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (root != null) {
                dtModel = new DefaultTreeModel(builtTreeNode(root));
                this.setModel(dtModel);
            }
        }

        private DefaultMutableTreeNode builtTreeNode(Node root) {
            DefaultMutableTreeNode dmtNode;

            dmtNode = new DefaultMutableTreeNode(root.getNodeName());
            NodeList nodeList = root.getChildNodes();
            for (int count = 0; count < nodeList.getLength(); count++) {
                Node tempNode = nodeList.item(count);




                if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (tempNode.getAttributes().getNamedItem("name") != null) {
                        dmtNode.add(new DefaultMutableTreeNode(tempNode.getNodeName() + " " + tempNode.getAttributes().getNamedItem("name").getNodeValue()));
                    }




                    if (tempNode.hasChildNodes()) {
                        //if a node is named "description" it will be added as a grandchild of the node
                        if (tempNode.getNodeName().equals("description")) {
                            DefaultMutableTreeNode dmtNode2 = new DefaultMutableTreeNode(tempNode.getNodeName());
                            dmtNode2.add(new DefaultMutableTreeNode(tempNode.getTextContent()));
                            dmtNode.add(dmtNode2);
                        } else {
                            dmtNode.add(builtTreeNode(tempNode));
                        }
                    }
                }
            }
            return dmtNode;
        }
    }
}