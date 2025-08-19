import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Programacion extends JFrame {

    private final JTextField txtNombre;
    private final JComboBox<String> comboTipo;
    private final DefaultTableModel modeloTabla;

    public Programacion() {
        setTitle("Registro de Marcación");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(30, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));

        JPanel panelNombre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtNombre = new JTextField(30);
        txtNombre.setToolTipText("Ingrese nombre del empleado");
        panelNombre.add(new JLabel("Nombre:"));
        panelNombre.add(txtNombre);

        JPanel panelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboTipo = new JComboBox<>(new String[]{"Entrada", "Salida"});
        comboTipo.setSelectedIndex(-1);
        JButton btnRegistrar = new JButton("Registrar");
        panelTipo.add(new JLabel("Tipo Marcación:"));
        panelTipo.add(comboTipo);
        panelTipo.add(btnRegistrar);

        panelCampos.add(panelNombre);
        panelCampos.add(panelTipo);

        modeloTabla = new DefaultTableModel(new String[]{"Nombre", "Marcación", "Fecha Registro"}, 0);
        JTable tabla = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tabla);

        btnRegistrar.addActionListener(e -> registrarMarcacion());

        panel.add(panelCampos, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);

        add(panel);
    }

    private void registrarMarcacion() {
        String nombre = txtNombre.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();

        if (nombre.isEmpty() || tipo == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar el nombre y seleccionar el tipo de marcación.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea registrar la marcación?\n\nNombre: " + nombre + "\nTipo: " + tipo,
                "Confirmar Registro", JOptionPane.OK_CANCEL_OPTION);

        if (confirm == JOptionPane.OK_OPTION) {
            String fechaActual = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            modeloTabla.addRow(new Object[]{nombre, tipo, fechaActual});
            txtNombre.setText("");
            comboTipo.setSelectedIndex(-1);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Programacion ventana = new Programacion();
            ventana.setVisible(true);
        });
    }
}