### Kelompok 35 - IFTTW
### Anggota:
- Eka Novendra W. / 13507011
- T. Antra Oksidian Tafly / 13517020

--------------------------
# Deskripsi Aplikasi
Aplikasi IFTTW ini akan menerima sebuah kondisi yang berupa dalam bentuk waktu, nilai sensor, atau kecerahan cuaca melalui OpenAPI. Jika kondisi tersebut terpenuhi, sebuah aksi yang ditentukan pengguna akan dijalankan. Aksi ini dapat berupa sebuah notifikasi atau pengubahan status WiFi.

# Cara Kerja Aplikasi & Pemenuhan Spesifikasi
## Cara kerja
1. Pada Tampilan utama, tekan tombol `THIS` atau `WHAT?` untuk memilih modul prekondisi (`THIS`) atau aksi (`WHAT?`).
2. Setelah salah satu tombol ditekan, pilih modul melalui tab (baik ditekan atau digeser).
3. Isi input dan tekan tombol yang sesuai.
4. Setelah memilih kondisi dan aksi, tuliskan nama *routine*.
5. Tekan tombol `+`.
6. Untuk melihat *routine* yang aktif, geser ke kiri atau pilih *Active routine list* dari tab.
7. *Routine* yang aktif dapat di-deaktifasi dengan menekan tombol `DEACTIVATE`. *Routine* juga dapat dihapus dengan menekan tombol `DELETE`.
8. *Routine* yang tidak aktif dapat dilihat dengan menggeser ke kiri lagi atau menekan pilih *Inactive routine list* dari tab.
9. *Routine* yang tidak aktif dapat dihapus dengan menekan tombol `DEACTIVATE`.

## Pemenuhan spesifikasi
- Pengguna dapat membuat rutin baru : Terpenuhi
- Aplikasi dapat menjalankan rutin walau aplikasi tidak dijalankan : Terpenuhi
- Pengguna dapat mengaktifkan/menonaktifkan rutin : Terpenuhi
- Pengguna dapat menghapus rutin : Terpenuhi
- Modul kondisi timer : Terpenuhi (Once, Daily, Weekly)
- Modul kondisi sensor : Terpenuhi (Proximity)
- Modul aksi NotifyMe : Terpenuhi
- Modul aksi WiFi : Terpenuhi
- Data disimpan dalam sqlite : Terpenuhi
- Implementasi `Fragment` dalam `TabLayout` : Terpenuhi

# Library yang digunakan & justifikasi.
- SQLiteDatabase & SQLiteOpenHelper : Agar aplikasi dapat menyimpan data dalam bentuk sqlite.
- Volley : Agar aplikasi dapat melakukan HTTP *request* ke API
- Sensor, SensorManager, SensorEvent, SensorEventListener : Agar aplikasi dapat menerima input dari sensor *proxiumity*
- WifiManager : Agar aplikasi dapat menghidupkan/mematikan WiFi
- NotificationManagerCompat : Untuk mengirim notifikasi.
- AlarmManager : Agar rutin dapat berjalan walau aplikasi ditutup

# Screenshots
