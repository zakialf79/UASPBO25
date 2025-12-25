// Import library SQL untuk database
import java.sql.*;

// Class KaryawanKontrak adalah turunan (Inheritance) dari class Karyawan
public class KaryawanKontrak extends Karyawan {
    
    // Variabel khusus upah per hari
    private double upahHarian;
    
    // Variabel khusus jumlah hari masuk kerja
    private int jumlahHariMasuk;

    // Constructor KaryawanKontrak
    public KaryawanKontrak(String nama, String idKaryawan, double upahHarian, int jumlahHariMasuk) {
        // Memanggil constructor parent class
        super(nama, idKaryawan);
        
        // Set nilai upah harian
        this.upahHarian = upahHarian;
        
        // Set nilai jumlah hari masuk
        this.jumlahHariMasuk = jumlahHariMasuk;
    }

    // Override method hitungGaji (Polymorphism)
    @Override
    public double hitungGaji() {
        // Rumus: upah harian dikali jumlah hari masuk
        return upahHarian * jumlahHariMasuk;
    }

    // Implementasi simpan ke database untuk Karyawan Kontrak
    @Override
    public void simpanKeDatabase() {
        try {
            // Buka koneksi ke database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uas_pbo_2025", "root", "");
            
            // Query Insert Data
            String sql = "INSERT INTO karyawan (nama, tipe_karyawan, gaji_total, tanggal_input) VALUES (?, ?, ?, NOW())";
            
            // Siapkan statement
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            // Isi parameter nama
            pstmt.setString(1, this.nama);
            
            // Isi parameter tipe sebagai 'Kontrak'
            pstmt.setString(2, "Kontrak");
            
            // Isi parameter total gaji
            pstmt.setDouble(3, this.hitungGaji());
            
            // Eksekusi query
            pstmt.executeUpdate();
            
            // Cetak pesan sukses
            System.out.println("Sukses: Data Karyawan Kontrak berhasil disimpan ke Database.");
            
            // Tutup koneksi
            conn.close();
        } catch (SQLException e) {
            // Tangkap error jika ada
            System.out.println("Error Database: " + e.getMessage());
        }
    }

    // Tampilkan info karyawan kontrak
    @Override
    public void tampilkanInfo() {
        System.out.println("=== INFO KARYAWAN KONTRAK ===");
        // Menampilkan nama kapital
        System.out.println("Nama : " + this.nama.toUpperCase());
        // Menampilkan hasil hitungan gaji
        System.out.println("Total Gaji : Rp " + this.hitungGaji());
    }
}