// Import library untuk koneksi database SQL
import java.sql.*;

// Class KaryawanTetap adalah turunan (Inheritance) dari class Karyawan
public class KaryawanTetap extends Karyawan {
    
    // Variabel private khusus untuk Karyawan Tetap
    private double gajiPokok;
    
    // Variabel private untuk menyimpan tunjangan
    private double tunjangan;

    // Constructor untuk inisialisasi objek KaryawanTetap
    public KaryawanTetap(String nama, String idKaryawan, double gajiPokok, double tunjangan) {
        // Memanggil constructor dari Super Class (Karyawan)
        super(nama, idKaryawan); 
        
        // Mengisi nilai gajiPokok
        this.gajiPokok = gajiPokok;
        
        // Mengisi nilai tunjangan
        this.tunjangan = tunjangan;
    }

    // Override method hitungGaji sesuai rumus Karyawan Tetap
    @Override
    public double hitungGaji() {
        // Mengembalikan hasil penjumlahan gaji pokok dan tunjangan
        return gajiPokok + tunjangan; 
    }

    // Implementasi method simpanKeDatabase (Syarat CRUD - Create)
    @Override
    public void simpanKeDatabase() {
        // Menggunakan try-catch untuk menangani kemungkinan error koneksi database
        try {
            // Membuat koneksi ke database MySQL dengan nama database 'uas_pbo_2025'
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uas_pbo_2025", "root", "");
            
            // Menyiapkan query SQL untuk memasukkan data (INSERT)
            String sql = "INSERT INTO karyawan (nama, tipe_karyawan, gaji_total, tanggal_input) VALUES (?, ?, ?, NOW())";
            
            // Menggunakan PreparedStatement agar aman dari SQL Injection
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            // Mengisi parameter ke-1 (nama)
            pstmt.setString(1, this.nama);
            
            // Mengisi parameter ke-2 (tipe karyawan) secara manual string 'Tetap'
            pstmt.setString(2, "Tetap");
            
            // Mengisi parameter ke-3 (gaji total) dari hasil perhitungan method hitungGaji()
            pstmt.setDouble(3, this.hitungGaji());
            
            // Menjalankan perintah update ke database
            pstmt.executeUpdate();
            
            // Menampilkan pesan sukses ke layar
            System.out.println("Sukses: Data Karyawan Tetap berhasil disimpan ke Database.");
            
            // Menutup koneksi database agar tidak memberatkan server
            conn.close(); 
        } catch (SQLException e) {
            // Menampilkan pesan error jika terjadi masalah SQL
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }
    }

    // Override method untuk menampilkan info spesifik Karyawan Tetap
    @Override
    public void tampilkanInfo() {
        // Cetak header info
        System.out.println("=== INFO KARYAWAN TETAP ===");
        
        // Cetak nama dengan huruf besar (Manipulasi String)
        System.out.println("Nama : " + this.nama.toUpperCase());
        
        // Cetak total gaji
        System.out.println("Total Gaji : Rp " + this.hitungGaji());
    }
}