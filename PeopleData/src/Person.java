
public class Person {
    private String id;
    private String nama;
    private int tahunLahir;
    private String kategori;
    private String programStudi;

    public Person(String id, String nama, int tahunLahir,
                  String kategori, String programStudi) {
        this.id = id;
        this.nama = nama;
        this.tahunLahir = tahunLahir;
        this.kategori = kategori;
        this.programStudi = programStudi;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getTahunLahir() {
        return tahunLahir;
    }

    public String getKategori() {
        return kategori;
    }

    public String getProgramStudi() {
        return programStudi;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setTahunLahir(int tahunLahir) {
        this.tahunLahir = tahunLahir;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setProgramStudi(String programStudi) {
        this.programStudi = programStudi;
    }
}
