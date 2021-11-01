package swingmvclab;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/*
 * A hallgatók adatait tároló osztály.
 */
public class StudentData extends AbstractTableModel{

    /*
     * Ez a tagváltozó tárolja a hallgatói adatokat.
     * NE MÓDOSÍTSD!
     */
    List<Student> students = new ArrayList<Student>();

    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = students.get(rowIndex);
        switch(columnIndex) {
            case 0: return student.getName();
            case 1: return student.getNeptun();
            case 2: return student.hasSignature();
            default: return student.getGrade();
        }
    }

    @Override
    public String getColumnName(int column) {
        switch(column) {
            case 0: return "Név";
            case 1: return "Neptun";
            case 2: return "Aláírás";
            default: return "Jegy";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
            case 1:
                return String.class;
            case 2: return Boolean.class;
            default: return Integer.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        Student student = students.get(rowIndex);
        switch(columnIndex) {
            case 0:
            case 1: return false;
            default: return true;
        }
    }

    @Override
    public void fireTableChanged(TableModelEvent e) {
        super.fireTableChanged(e);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Student student = students.get(rowIndex);
        switch(columnIndex) {
            case 2:
                boolean newSignature = (boolean) aValue;
                student.setSignature(newSignature);
                break;
            case 3:
                int newGrade = (int) aValue;
                student.setGrade(newGrade);
                break;
            default:
                break;
        }
//        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void addStudent(String name, String neptun) throws NeptunExistsException{
        if (contains(neptun)){
            throw new NeptunExistsException();
        }

        students.add(new Student(name ,neptun , false , 0));
    }

    private boolean contains(String neptun){
        boolean contains = false;
        for (Student student: students){
            contains = student.getNeptun().equals(neptun) || contains;
        }
        return contains;
    }

    @Override
    public void fireTableCellUpdated(int row, int column) {
        super.fireTableCellUpdated(row, column);
    }
}
