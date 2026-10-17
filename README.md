# TP5DPBO2526C1

## Janji

Saya Keysha Ega Magani dengan NIM 2507925 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## 🎓 PeopleData — Student Data Management

**PeopleData** merupakan aplikasi desktop sederhana untuk mengelola data mahasiswa yang dibuat menggunakan **Java Swing** sebagai graphical user interface (GUI) dan **MySQL** sebagai database.

Program ini dibuat untuk memenuhi tugas praktikum 5 mata kuliah Desain dan Pemrograman Berorientasi Objek.

Aplikasi memungkinkan pengguna menambahkan, melihat, mengubah, dan menghapus data mahasiswa melalui antarmuka yang interaktif. Data mahasiswa disimpan di database MySQL sehingga perubahan data dapat tersimpan dan ditampilkan kembali melalui aplikasi.

Program menggunakan **JDBC (Java Database Connectivity)** untuk menghubungkan aplikasi Java dengan database MySQL melalui MySQL Connector/J.

---

## Fitur Program

Program memiliki beberapa fitur utama untuk mengelola data mahasiswa yang tersimpan di database.

### Data Mahasiswa

Data mahasiswa yang dikelola meliputi:

- NIM
- Nama
- Tahun Lahir
- Kategori
- Program Studi

Kategori mahasiswa yang tersedia:

- Mahasiswa Aktif
- Mahasiswa Non Aktif
- Alumni

Data mahasiswa disimpan pada tabel `person` di database `db_people`.

### 1. Tampilkan Data Mahasiswa

Menampilkan data mahasiswa dari database MySQL dalam bentuk tabel pada antarmuka aplikasi.

Informasi yang ditampilkan meliputi NIM, nama, tahun lahir, kategori, dan program studi.

Data ditampilkan dengan mengambil informasi dari database, bukan hanya dari penyimpanan sementara di dalam program.

### 2. Tambah Data Mahasiswa

Pengguna dapat menambahkan data mahasiswa baru melalui form yang tersedia.

Pengguna mengisi NIM, nama, dan tahun lahir, kemudian memilih kategori mahasiswa serta program studi melalui komponen pilihan.

Sebelum data disimpan, program memeriksa kelengkapan dan validitas input. NIM yang sudah terdaftar tidak dapat ditambahkan kembali.

Jika data valid, aplikasi menyimpan data ke database MySQL dan memperbarui tampilan tabel.

### 3. Ubah Data Mahasiswa

Pengguna dapat memilih data mahasiswa pada tabel untuk mengubah informasi yang diperlukan.

Setelah perubahan disimpan, aplikasi memperbarui data pada database dan menampilkan perubahan tersebut pada tabel.

### 4. Hapus Data Mahasiswa

Pengguna dapat menghapus data mahasiswa yang dipilih dari tabel.

Sebelum data dihapus, program menampilkan dialog konfirmasi untuk memastikan bahwa pengguna benar-benar ingin menghapus data tersebut.

Jika penghapusan dikonfirmasi, data dihapus dari database MySQL dan tabel diperbarui.

### 5. Bersihkan Form

Pengguna dapat mengosongkan kembali form input untuk memasukkan data baru atau membatalkan pengisian data yang sedang dilakukan.

Fitur ini juga mengembalikan pilihan kategori dan program studi ke kondisi awal.

### 6. Koneksi Database

Aplikasi menggunakan JDBC dan MySQL Connector/J untuk menghubungkan program Java dengan database MySQL.

Koneksi digunakan untuk menjalankan operasi pengambilan, penambahan, perubahan, dan penghapusan data pada tabel `person`.

---

## Konsep OOP dan Database

### Class `Person`

Class `Person` digunakan untuk merepresentasikan data seseorang atau mahasiswa yang dikelola oleh aplikasi.

Atribut yang digunakan:

- `id` atau NIM
- `nama`
- `tahunLahir`
- `kategori`
- `prodi`

Class ini berfungsi sebagai representasi data mahasiswa yang dikelola melalui aplikasi.

### Class `PeopleMenu`

Class `PeopleMenu` digunakan untuk membangun antarmuka grafis dan menangani interaksi pengguna dengan aplikasi.

Tanggung jawab class ini meliputi:

- Menampilkan form input data mahasiswa.
- Menampilkan data mahasiswa dalam tabel.
- Menangani tombol tambah, ubah, hapus, dan bersihkan.
- Memproses interaksi pengguna.
- Memanggil operasi database.
- Memperbarui tampilan tabel setelah data berubah.
- Menampilkan pesan validasi dan dialog konfirmasi.

### Class `Database`

Class `Database` digunakan untuk menangani koneksi antara aplikasi Java dan database MySQL.

Tanggung jawab class ini meliputi:

- Membuat koneksi ke database `db_people`.
- Menjalankan query untuk mengambil data.
- Menjalankan query untuk menambah data.
- Menjalankan query untuk mengubah data.
- Menjalankan query untuk menghapus data.

### Database MySQL

Database yang digunakan bernama `db_people`, dengan tabel `person`.

Tabel tersebut memiliki lima kolom utama:

| Kolom | Keterangan |
|---|---|
| `NIM` | Nomor induk mahasiswa dan primary key |
| `nama` | Nama mahasiswa |
| `tahun_lahir` | Tahun lahir mahasiswa |
| `kategori` | Status kategori mahasiswa |
| `program_studi` | Program studi mahasiswa |

---

## Error Handling

Program melakukan validasi terhadap input pengguna agar data yang dimasukkan sesuai dengan ketentuan.

Validasi yang diterapkan meliputi:

- NIM tidak boleh kosong.
- NIM tidak boleh duplikat ketika menambahkan data baru.
- Nama tidak boleh kosong.
- Tahun lahir harus berupa angka yang valid.
- Kategori harus dipilih dari pilihan yang tersedia.
- Program studi harus dipilih dari pilihan yang tersedia.
- Data harus dipilih sebelum melakukan perubahan atau penghapusan.
- Penghapusan data harus melalui dialog konfirmasi.
- Kesalahan ketika mengakses database ditangani dengan pesan error.

Jika input tidak sesuai atau operasi tidak dapat dilakukan, program menampilkan pesan kepada pengguna agar kesalahan dapat diperbaiki.

## Struktur Folder

Struktur folder project secara umum:

```text
PeopleData/
│
├── src/
│   ├── Person.java
│   ├── PeopleMenu.java
│   └── Database.java
│
├── lib/
│   └── mysql-connector-j-....jar
│
├── dokumentasi/
│   └── (screenshot, GIF, atau video dokumentasi)
│
├── db_people.sql
├── .gitignore
├── README.md
└── PeopleData.iml
```

Struktur di atas merupakan gambaran umum. Sesuaikan dengan struktur folder aktual project yang diunggah ke GitHub.

---

