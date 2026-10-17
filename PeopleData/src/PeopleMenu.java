import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PeopleMenu {
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField tahunLahirField;
    private JComboBox<String> kategoriComboBox;
    private JComboBox<String> prodiComboBox;
    private JButton tambahButton;
    private JButton hapusButton;
    private JButton bersihkanButton;
    private JButton ubahButton;
    private JTable personTable;
    private DefaultTableModel tableModel;
    private Database database;

    public PeopleMenu() {
        database = new Database();
        buatKomponen();
        initTable();
        pasangAksiTombol();
        SetTable();
    }

    private void buatKomponen() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel judul = new JLabel("STUDENT DATA MANAGEMENT", SwingConstants.CENTER);
        judul.setFont(new Font("SansSerif", Font.BOLD, 20));
        mainPanel.add(judul, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;

        idField = new JTextField(18);
        namaField = new JTextField(18);
        tahunLahirField = new JTextField(18);
        kategoriComboBox = new JComboBox<>(new String[]{
                "Pilih Kategori", "Mahasiswa Aktif", "Mahasiswa Non Aktif", "Alumni"
        });
        prodiComboBox = new JComboBox<>(new String[]{
                "Pilih Program Studi", "Ilmu Komputer", "Pendidikan Ilmu Komputer",
                "Matematika", "Pendidikan Matematika", "Kimia", "Pendidikan Kimia",
                "Fisika", "Pendidikan Fisika", "Biologi", "Pendidikan Biologi",
                "IPSE", "Lainnya"
        });

        tambahButton = new JButton("Tambah");
        ubahButton = new JButton("Ubah");
        hapusButton = new JButton("Hapus");
        bersihkanButton = new JButton("Bersihkan");

        tambahBarisForm(formPanel, c, 0, "NIM", idField, tambahButton);
        tambahBarisForm(formPanel, c, 1, "Nama Mahasiswa", namaField, ubahButton);
        tambahBarisForm(formPanel, c, 2, "Tahun Lahir", tahunLahirField, hapusButton);
        tambahBarisForm(formPanel, c, 3, "Kategori", kategoriComboBox, bersihkanButton);
        tambahBarisForm(formPanel, c, 4, "Program Studi", prodiComboBox, new JLabel(""));

        JPanel atas = new JPanel(new BorderLayout(8, 8));
        atas.add(judul, BorderLayout.NORTH);
        atas.add(formPanel, BorderLayout.CENTER);
        mainPanel.remove(judul);
        mainPanel.add(atas, BorderLayout.NORTH);
    }

    private void tambahBarisForm(JPanel panel, GridBagConstraints c, int baris,
                                 String label, JComponent input, JComponent tombol) {
        c.gridy = baris;
        c.gridx = 0;
        c.weightx = 0;
        panel.add(new JLabel(label), c);
        c.gridx = 1;
        c.weightx = 1;
        panel.add(input, c);
        c.gridx = 2;
        c.weightx = 0;
        panel.add(tombol, c);
    }

    private void initTable() {
        String[] columnNames = {
                "NIM", "Nama Mahasiswa", "Tahun Lahir", "Kategori", "Program Studi"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        personTable = new JTable(tableModel);
        personTable.setRowHeight(25);
        personTable.getTableHeader().setReorderingAllowed(false);
        personTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personTable.setShowGrid(true);
        personTable.setGridColor(new Color(225, 225, 225));
        personTable.setFillsViewportHeight(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        personTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        personTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        personTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        personTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        personTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        personTable.getColumnModel().getColumn(3).setPreferredWidth(130);
        personTable.getColumnModel().getColumn(4).setPreferredWidth(180);

        mainPanel.add(new JScrollPane(personTable), BorderLayout.CENTER);
    }

    private void pasangAksiTombol() {
        tambahButton.addActionListener(e -> tambahData());
        ubahButton.addActionListener(e -> ubahData());
        hapusButton.addActionListener(e -> hapusData());
        bersihkanButton.addActionListener(e -> bersihkanForm());

        personTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tampilkanDataTerpilih();
            }
        });
    }

    // Menampilkan data dari database ke JTable.
    private void SetTable() {
        tableModel.setRowCount(0);
        String sql = "SELECT NIM, nama, tahun_lahir, kategori, program_studi "
                + "FROM person ORDER BY NIM";

        try (PreparedStatement ps = database.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("NIM"),
                        rs.getString("nama"),
                        rs.getInt("tahun_lahir"),
                        rs.getString("kategori"),
                        rs.getString("program_studi")
                });
            }
        } catch (SQLException e) {
            tampilkanError("Gagal mengambil data dari database.\n" + e.getMessage());
        }
    }

    private boolean validasiInput() {
        String id = idField.getText().trim();
        String nama = namaField.getText().trim();
        String tahun = tahunLahirField.getText().trim();

        if (id.isEmpty() || nama.isEmpty() || tahun.isEmpty()
                || kategoriComboBox.getSelectedIndex() <= 0
                || prodiComboBox.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(mainPanel, "Semua kolom wajib diisi.",
                    "Data Belum Lengkap", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!id.matches("\\d{1,7}")) {
            JOptionPane.showMessageDialog(mainPanel,
                    "NIM harus berupa angka maksimal 7 digit.",
                    "NIM Tidak Valid", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            int tahunLahir = Integer.parseInt(tahun);
            if (tahunLahir < 1900 || tahunLahir > 2026) {
                JOptionPane.showMessageDialog(mainPanel,
                        "Tahun lahir harus antara 1900 dan 2026.",
                        "Tahun Lahir Tidak Valid", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainPanel, "Tahun lahir harus berupa angka.",
                    "Input Tidak Valid", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // CREATE
    private void tambahData() {
        if (!validasiInput()) return;

        String sqlCek = "SELECT NIM FROM person WHERE NIM = ?";
        try (PreparedStatement cek = database.getConnection().prepareStatement(sqlCek)) {
            cek.setString(1, idField.getText().trim());
            try (ResultSet rs = cek.executeQuery()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(mainPanel,
                            "NIM tersebut sudah terdaftar. Gunakan NIM lain.",
                            "NIM Duplikat", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            String sql = "INSERT INTO person "
                    + "(NIM, nama, tahun_lahir, kategori, program_studi) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = database.getConnection().prepareStatement(sql)) {
                ps.setString(1, idField.getText().trim());
                ps.setString(2, namaField.getText().trim());
                ps.setInt(3, Integer.parseInt(tahunLahirField.getText().trim()));
                ps.setString(4, kategoriComboBox.getSelectedItem().toString());
                ps.setString(5, prodiComboBox.getSelectedItem().toString());
                ps.executeUpdate();
            }

            SetTable();
            bersihkanForm();
            JOptionPane.showMessageDialog(mainPanel, "Data mahasiswa berhasil ditambahkan.");
        } catch (SQLException e) {
            tampilkanError("Gagal menambahkan data.\n" + e.getMessage());
        }
    }

    // UPDATE
    private void ubahData() {
        int baris = personTable.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Pilih data pada tabel yang ingin diubah.",
                    "Data Belum Dipilih", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!validasiInput()) return;

        String nimLama = personTable.getValueAt(baris, 0).toString();
        String sql = "UPDATE person SET nama = ?, tahun_lahir = ?, kategori = ?, "
                + "program_studi = ? WHERE NIM = ?";
        try (PreparedStatement ps = database.getConnection().prepareStatement(sql)) {
            ps.setString(1, namaField.getText().trim());
            ps.setInt(2, Integer.parseInt(tahunLahirField.getText().trim()));
            ps.setString(3, kategoriComboBox.getSelectedItem().toString());
            ps.setString(4, prodiComboBox.getSelectedItem().toString());
            ps.setString(5, nimLama);
            ps.executeUpdate();

            SetTable();
            bersihkanForm();
            JOptionPane.showMessageDialog(mainPanel, "Data mahasiswa berhasil diubah.");
        } catch (SQLException e) {
            tampilkanError("Gagal mengubah data.\n" + e.getMessage());
        }
    }

    // DELETE
    private void hapusData() {
        int baris = personTable.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Pilih data pada tabel yang ingin dihapus.",
                    "Data Belum Dipilih", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String nim = personTable.getValueAt(baris, 0).toString();
        String nama = personTable.getValueAt(baris, 1).toString();
        int konfirmasi = JOptionPane.showConfirmDialog(mainPanel,
                "Hapus data mahasiswa " + nama + " (" + nim + ")?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM person WHERE NIM = ?";
            try (PreparedStatement ps = database.getConnection().prepareStatement(sql)) {
                ps.setString(1, nim);
                ps.executeUpdate();
                SetTable();
                bersihkanForm();
                JOptionPane.showMessageDialog(mainPanel, "Data mahasiswa berhasil dihapus.");
            } catch (SQLException e) {
                tampilkanError("Gagal menghapus data.\n" + e.getMessage());
            }
        }
    }

    private void tampilkanDataTerpilih() {
        int baris = personTable.getSelectedRow();
        if (baris != -1) {
            idField.setText(personTable.getValueAt(baris, 0).toString());
            namaField.setText(personTable.getValueAt(baris, 1).toString());
            tahunLahirField.setText(personTable.getValueAt(baris, 2).toString());
            kategoriComboBox.setSelectedItem(personTable.getValueAt(baris, 3).toString());
            prodiComboBox.setSelectedItem(personTable.getValueAt(baris, 4).toString());
            idField.setEditable(false);
        }
    }

    private void bersihkanForm() {
        idField.setText("");
        namaField.setText("");
        tahunLahirField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        prodiComboBox.setSelectedIndex(0);
        idField.setEditable(true);
        personTable.clearSelection();
        idField.requestFocusInWindow();
    }

    private void tampilkanError(String pesan) {
        JOptionPane.showMessageDialog(mainPanel, pesan, "Database Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                PeopleMenu menu = new PeopleMenu();
                JFrame frame = new JFrame("Student Data Management");
                frame.setContentPane(menu.mainPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setMinimumSize(new Dimension(850, 580));
                frame.pack();
                frame.setSize(1000, 650);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        "Aplikasi Gagal Dibuka", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 3, new Insets(15, 15, 15, 15), 10, 8));
        final JLabel label1 = new JLabel();
        label1.setText("STUDENT DATA MANAGEMENT");
        mainPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("NIM");
        mainPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        idField = new JTextField();
        mainPanel.add(idField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(220, -1), null, 0, false));
        tambahButton = new JButton();
        tambahButton.setText("Tambah");
        mainPanel.add(tambahButton, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Nama Mahasiswa");
        mainPanel.add(label3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        namaField = new JTextField();
        mainPanel.add(namaField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(220, -1), null, 0, false));
        ubahButton = new JButton();
        ubahButton.setText("Ubah");
        mainPanel.add(ubahButton, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Tahun Lahir");
        mainPanel.add(label4, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tahunLahirField = new JTextField();
        mainPanel.add(tahunLahirField, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(220, -1), null, 0, false));
        hapusButton = new JButton();
        hapusButton.setText("Hapus");
        mainPanel.add(hapusButton, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Kategori");
        mainPanel.add(label5, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        kategoriComboBox = new JComboBox();
        mainPanel.add(kategoriComboBox, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(220, -1), null, 0, false));
        bersihkanButton = new JButton();
        bersihkanButton.setText("Bersihkan");
        mainPanel.add(bersihkanButton, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Program Studi");
        mainPanel.add(label6, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        prodiComboBox = new JComboBox();
        mainPanel.add(prodiComboBox, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(220, -1), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        mainPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(500, 220), null, 0, false));
        personTable = new JTable();
        scrollPane1.setViewportView(personTable);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
