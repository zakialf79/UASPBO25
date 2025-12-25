// Membuat abstract class Karyawan yang mengimplementasikan interface DatabaseAction
// Class ini abstract karena tidak akan dibuat objeknya secara langsung (hanya template)
public abstract class Karyawan implements DatabaseAction {
    
    // Variabel protected agar bisa diakses oleh class anak (Sub Class)
    protected String nama; 
    
    // Variabel untuk menyimpan ID Karyawan
    protected String idKaryawan;
    
    // Constructor untuk mengisi data awal saat objek dibuat
    public Karyawan(String nama, String idKaryawan) {
        // Mengisi variabel nama class ini dengan data dari parameter
        this.nama = nama;
        
        // Mengisi variabel idKaryawan class ini dengan data dari parameter
        this.idKaryawan = idKaryawan;
    }

    // Method abstrak hitungGaji yang wajib dibuat ulang (override) oleh class anak
    public abstract double hitungGaji();
    
    // Method getter untuk mengambil nilai nama (Encapsulation)
    public String getNama() {
        // Mengembalikan nilai nama
        return nama;
    }
}