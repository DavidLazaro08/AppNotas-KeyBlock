package vista;

import bbdd.GestorBBDD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PanelAdmin extends JFrame {
    private JTable tabla;
    private JButton btnRefrescar;

    public PanelAdmin() {
        setTitle("Panel de Administración");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tabla = new JTable();
        btnRefrescar = new JButton("Refrescar");

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(btnRefrescar, BorderLayout.SOUTH);

        btnRefrescar.addActionListener(e -> cargarNotas());

        cargarNotas();

        setVisible(true);
    }

    private void cargarNotas() {
        try {
            Connection conn = bbdd.GestorBBDD.getConnection(); // Asegúrate que ese es tu paquete
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM notas");

            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();
            String[] nombres = new String[columnas];
            for (int i = 0; i < columnas; i++) {
                nombres[i] = meta.getColumnName(i + 1);
            }

            DefaultTableModel modelo = new DefaultTableModel(nombres, 0);

            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

            tabla.setModel(modelo);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }
    }
}
