// Import library SQL untuk database
import java.sql.*;
// Import ArrayList untuk menampung data (Collection Framework)
import java.util.ArrayList;
// Import Date untuk mendapatkan tanggal saat ini
import java.util.Date;
// Import Scanner untuk input user
import java.util.Scanner;
// Import SimpleDateFormat untuk memformat tampilan tanggal
import java.text.SimpleDateFormat;

// Class Utama (Main Class)
public class SistemKepegawaian {
    
    // Inisialisasi Scanner untuk membaca input dari keyboard
    static Scanner scanner = new Scanner(System.in);
    
    // Inisialisasi ArrayList untuk menyimpan objek Karyawan sementara di memori
    static ArrayList<Karyawan> listKaryawan = new ArrayList<>();

    // Method main: titik awal program berjalan
    public static void main(String[] args) {
        // Variabel penanda agar program terus berjalan (looping)
        boolean isRunning = true;
        
        // Mengambil waktu saat ini
        Date tanggalSekarang = new Date();
        
        // Membuat format tanggal (Hari Bulan Tahun Jam:Menit)
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        
        // Menampilkan pesan selamat datang beserta waktu server
        System.out.println("Selamat Datang di HRIS System. Waktu Server: " + formatter.format(tanggalSekarang));

        // Melakukan perulangan menu selama isRunning bernilai true
        while (isRunning) {
            // Menampilkan daftar menu
            System.out.println("\n--- MENU UTAMA ---");
            System.out.println("1. Tambah Karyawan Tetap (Create)");
            System.out.println("2. Tambah Karyawan Kontrak (Create)");
            System.out.println("3. Lihat Database (Read)");
            System.out.println("4. Update Nama Karyawan (Update)");
            System.out.println("5. Hapus Data (Delete)");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu [1-6]: ");
            
            // Variabel untuk menampung pilihan user
            int pilihan = 0;
            
            // Try-catch untuk mencegah error jika user input huruf
            try {
                // Membaca input dan mengubah string menjadi integer
                pilihan = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Pesan peringatan jika input bukan angka
                System.out.println("Peringatan: Input harus berupa angka!");
                // Melanjutkan ke iterasi loop berikutnya (skip kode di bawah)
                continue; 
            }

            // Percabangan switch untuk menentukan aksi berdasarkan pilihan
            switch (pilihan) {
                case 1: 
                    inputKaryawanTetap(); // Panggil method input tetap
                    break;
                case 2: 
                    inputKaryawanKontrak(); // Panggil method input kontrak
                    break;
                case 3: 
                    lihatDatabase(); // Panggil method lihat data
                    break;
                case 4: 
                    updateNamaKaryawan(); // Panggil method update data
                    break;
                case 5: 
                    hapusDataDatabase(); // Panggil method hapus data
                    break;
                case 6: 
                    // Mengubah status looping menjadi false
                    isRunning = false; 
                    System.out.println("Program dihentikan. Terima kasih!");
                    break;
                default: 
                    // Jika pilihan tidak ada di case 1-6
                    System.out.println("Menu tidak tersedia.");
            }
        }
    }

    // Method static untuk input data Karyawan Tetap
    static void inputKaryawanTetap() {
        System.out.println("\n--- Input Karyawan Tetap ---");
        // Minta input nama
        System.out.print("Nama Lengkap: ");
        String nama = scanner.nextLine();
        
        // Minta input ID
        System.out.print("ID Karyawan: ");
        String id = scanner.nextLine();
        
        try {
            // Minta input gaji pokok (konversi String ke double)
            System.out.print("Gaji Pokok: ");
            double gapok = Double.parseDouble(scanner.nextLine());
            
            // Minta input tunjangan
            System.out.print("Tunjangan: ");
            double tunjangan = Double.parseDouble(scanner.nextLine());

            // Membuat objek KaryawanTetap baru
            KaryawanTetap kt = new KaryawanTetap(nama, id, gapok, tunjangan);
            
            // Menambahkan objek ke ArrayList
            listKaryawan.add(kt);
            
            // Menyimpan data ke database MySQL
            kt.simpanKeDatabase();
            
            // Menampilkan info ke console
            kt.tampilkanInfo();
        } catch (NumberFormatException e) {
            // Error handling jika input gaji bukan angka
            System.out.println("Gagal: Gaji/Tunjangan harus angka.");
        }
    }

    // Method static untuk input data Karyawan Kontrak
    static void inputKaryawanKontrak() {
        System.out.println("\n--- Input Karyawan Kontrak ---");
        System.out.print("Nama Lengkap: ");
        String nama = scanner.nextLine();
        System.out.print("ID Karyawan: ");
        String id = scanner.nextLine();
        
        try {
            System.out.print("Upah Harian: ");
            double upah = Double.parseDouble(scanner.nextLine());
            System.out.print("Jumlah Hari Masuk: ");
            int hari = Integer.parseInt(scanner.nextLine());

            // Membuat objek KaryawanKontrak
            KaryawanKontrak kk = new KaryawanKontrak(nama, id, upah, hari);
            
            // Simpan ke collection dan database
            listKaryawan.add(kk);
            kk.simpanKeDatabase();
            kk.tampilkanInfo();
        } catch (NumberFormatException e) {
            System.out.println("Gagal: Input upah/hari salah.");
        }
    }

    // Method untuk melihat isi database (Fitur READ)
    static void lihatDatabase() {
        System.out.println("\n--- DATA DATABASE (Live Update) ---");
        try {
            // Buka koneksi database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uas_pbo_2025", "root", "");
            
            // Buat statement SQL
            Statement stmt = conn.createStatement();
            
            // Query SELECT semua data karyawan diurutkan ID terbaru
            String sql = "SELECT * FROM karyawan ORDER BY id DESC";
            
            // Eksekusi query dan simpan hasilnya di ResultSet
            ResultSet rs = stmt.executeQuery(sql);

            // Penanda apakah ada data atau tidak
            boolean adaData = false;
            
            // Loop membaca baris per baris dari hasil query
            while (rs.next()) {
                adaData = true;
                // Ambil data kolom per kolom
                int id = rs.getInt("id");
                String nama = rs.getString("nama");
                String tipe = rs.getString("tipe_karyawan");
                double gaji = rs.getDouble("gaji_total");
                String tgl = rs.getString("tanggal_input");
                
                // Cetak data dengan format rapi
                System.out.println(String.format("[%d] %s (%s) - Gaji: Rp %.2f - Tgl: %s", id, nama, tipe, gaji, tgl));
            }
            // Jika tidak ada data sama sekali
            if (!adaData) System.out.println("Database masih kosong.");
            
            // Tutup koneksi
            conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal membaca database: " + e.getMessage());
        }
    }

    // Method untuk mengubah nama karyawan (Fitur UPDATE)
    static void updateNamaKaryawan() {
        // Tampilkan dulu data yang ada
        lihatDatabase();
        System.out.println("\n--- UPDATE DATA KARYAWAN ---");
        System.out.print("Masukkan ID Karyawan yang akan diedit namanya: ");
        try {
            // Baca ID target
            int idEdit = Integer.parseInt(scanner.nextLine());
            
            // Baca nama baru
            System.out.print("Masukkan Nama Baru: ");
            String namaBaru = scanner.nextLine();
            
            // Koneksi database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uas_pbo_2025", "root", "");
            
            // Query Update dengan parameter
            String sql = "UPDATE karyawan SET nama = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            // Set parameter query
            pstmt.setString(1, namaBaru);
            pstmt.setInt(2, idEdit);
            
            // Jalankan update dan cek berapa baris yang berubah
            int rowsAffected = pstmt.executeUpdate();
            
            // Cek hasil update
            if (rowsAffected > 0) System.out.println("Sukses Update Data.");
            else System.out.println("Gagal: ID tidak ditemukan.");
            
            // Tutup koneksi
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method untuk menghapus data (Fitur DELETE)
    static void hapusDataDatabase() {
        lihatDatabase();
        System.out.print("\nMasukkan ID (angka) yang ingin dihapus: ");
        try {
            // Baca ID yang akan dihapus
            int idHapus = Integer.parseInt(scanner.nextLine());
            
            // Koneksi database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uas_pbo_2025", "root", "");
            
            // Query Delete
            String sql = "DELETE FROM karyawan WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            // Set parameter ID
            pstmt.setInt(1, idHapus);
            
            // Jalankan perintah delete
            int rowsAffected = pstmt.executeUpdate();
            
            // Cek apakah berhasil dihapus
            if (rowsAffected > 0) System.out.println("Sukses Hapus Data.");
            else System.out.println("ID tidak ditemukan.");
            
            // Tutup koneksi
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}