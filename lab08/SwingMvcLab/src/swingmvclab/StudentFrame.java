package swingmvclab;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/*
 * A megjelen�tend� ablakunk oszt�lya.
 */
public class StudentFrame extends JFrame {
    
    /*
     * Ebben az objektumban vannak a hallgat�i adatok.
     * A program indul�s ut�n bet�lti az adatokat f�jlb�l, bez�r�skor pedig kimenti oda.
     * 
     * NE M�DOS�TSD!
     */
    private StudentData data;
    
    /*
     * Itt hozzuk l�tre �s adjuk hozz� az ablakunkhoz a k�l�nb�z� komponenseket
     * (t�bl�zat, beviteli mez�, gomb).
     */
    private void initComponents() {
        data.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                data.fireTableChanged(e);
            }
        });
        this.setLayout(new BorderLayout());
        JTable mainData = new JTable(data);
        mainData.setAutoCreateRowSorter(true);
        mainData.setFillsViewportHeight(true);
        setUpSportColumn(mainData, mainData.getColumn(mainData.getColumnModel().getColumn(3).getIdentifier()));
        mainData.setDefaultRenderer(String.class, new StudentTableCellRenderer(new JTable().getDefaultRenderer(String.class)));
        mainData.setDefaultRenderer(Boolean.class, new StudentTableCellRenderer(new JTable().getDefaultRenderer(Boolean.class)));
        mainData.setDefaultRenderer(Integer.class, new StudentTableCellRenderer(new JTable().getDefaultRenderer(Integer.class)));
        this.add(new JScrollPane(mainData) , BorderLayout.CENTER);

        JTextField nameInput = new JTextField(20);
        JTextField neptunInput = new JTextField(6);
        JButton addButton = new JButton("Felvesz!");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("N�v"));
        bottomPanel.add(nameInput);
        bottomPanel.add(new JLabel("Neptun"));
        bottomPanel.add(neptunInput);
        bottomPanel.add(addButton);
        addButton.addActionListener((event)->{
            try {
                data.addStudent(nameInput.getText(), neptunInput.getText());
                data.fireTableRowsInserted(0, data.students.size()-1);
            } catch (NeptunExistsException e) {
                JOptionPane.showMessageDialog(getContentPane(), e.toString());
            }
        });
        add(bottomPanel, BorderLayout.SOUTH);
        // ...
    }

    public void setUpSportColumn(JTable table,
                                 TableColumn sportColumn) {
        //Set up the editor for the sport cells.
        JComboBox<Integer> comboBox = new JComboBox<>();
        comboBox.addItem(0);
        comboBox.addItem(1);
        comboBox.addItem(2);
        comboBox.addItem(3);
        comboBox.addItem(4);
        comboBox.addItem(5);
        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        TableCellRenderer renderer =
                new StudentTableCellRenderer(new DefaultTableCellRenderer());
        sportColumn.setCellRenderer(renderer);
    }

    /*
     * Az ablak konstruktora.
     * 
     * NE M�DOS�TSD!
     */
    @SuppressWarnings("unchecked")
    public StudentFrame() {
        super("Hallgat�i nyilv�ntart�s");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Indul�skor bet�ltj�k az adatokat
        try {
            data = new StudentData();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"));
            data.students = (List<Student>)ois.readObject();
            ois.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        // Bez�r�skor mentj�k az adatokat
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"));
                    oos.writeObject(data.students);
                    oos.close();
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Fel�p�tj�k az ablakot
        setMinimumSize(new Dimension(500, 200));
        initComponents();
    }

    /*
     * A program bel�p�si pontja.
     * 
     * NE M�DOS�TSD!
     */
    public static void main(String[] args) {
        // Megjelen�tj�k az ablakot
        StudentFrame sf = new StudentFrame();
        sf.setVisible(true);
    }

    class StudentTableCellRenderer implements TableCellRenderer {

        private TableCellRenderer renderer;

        public StudentTableCellRenderer(TableCellRenderer defRenderer) {
            this.renderer = defRenderer;
        }

        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component component = renderer.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            Student student = data.students.get(table.getRowSorter().convertRowIndexToModel(row));
            if (!student.hasSignature() || student.getGrade() < 2){
                component.setBackground(new Color(0xFFCCCC));
            } else {
                component.setBackground(new Color(0xafe875));
            }
            return component;
        }
    }


//    class StudentTableCellRenderer extends DefaultTableCellRenderer {
//
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//            Component tableCellRenderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
////            System.out.println((boolean) table.getValueAt(row , 2));
//
//            if (!((boolean) table.getValueAt(row , 2))){
//                tableCellRenderer.setBackground(new Color(0xFFCCCC));
//            } else if ((boolean) table.getValueAt(row , 2)){
//                tableCellRenderer.setBackground(new Color(0xafe875));
//            }
//            return tableCellRenderer;
//        }
//    }
}
