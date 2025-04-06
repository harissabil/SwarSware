# SwarSware

SwarSware adalah aplikasi Android yang dirancang untuk membantu penyandang tunarungu mendeteksi suara lingkungan di sekitarnya dan mengubahnya menjadi getaran terstruktur secara real-time. Dengan memanfaatkan teknologi LiteRT (sebelumnya TensorFlow Lite) dan foreground service, SwarSware mampu mengidentifikasi berbagai suara darurat dan situasional, memberikan peringatan proaktif melalui pola getaran yang dapat disesuaikan.

Aplikasi ini meningkatkan keamanan dan partisipasi sosial pengguna dalam kehidupan sehari-hari, sekaligus menjadi alternatif terjangkau bagi mereka yang tidak mampu membeli Alat Bantu Dengar (ABD) atau sebagai pendamping untuk meningkatkan kesadaran lingkungan bagi pengguna ABD.

## Tim Seaside Vacation

- Muhammad Haris Sabil Al Karim
- Bima Rizqy Ramadhan

## Daftar Isi
- [Video Demo](#video-demo)
- [Tangkapan Layar](#tangkapan-layar)
- [Fitur Utama](#fitur-utama)
- [Instalasi](#instalasi)

## Video Demo

https://github.com/user-attachments/assets/1e32b05a-9e4c-4c93-a8ee-b627f76ae9bd

## Tangkapan Layar

<table>
  <tbody>
    <tr>
      <td><img src="assets/screenshot/ss_1.jpg?raw=true"/></td>
      <td><img src="assets/screenshot/ss_2.jpg?raw=true"/></td>
      <td><img src="assets/screenshot/ss_3.jpg?raw=true"/></td>
    </tr>
    <tr>
      <td><img src="assets/screenshot/ss_4.jpg?raw=true"/></td>
      <td><img src="assets/screenshot/ss_5.jpg?raw=true"/></td>
      <td><img src="assets/screenshot/ss_6.jpg?raw=true"/></td>
    </tr>
    <tr>
      <td><img src="assets/screenshot/ss_7.jpg?raw=true"/></td>
      <td><img src="assets/screenshot/ss_8.jpg?raw=true"/></td>
      <td><img src="assets/screenshot/ss_9.jpg?raw=true"/></td>
    </tr>
  </tbody>
</table>

## Fitur Utama

1. Klasifikasi Suara On-Device — Menggunakan model YAMNet yang terintegrasi langsung dalam perangkat untuk mendeteksi dan mengklasifikasikan suara lingkungan secara real-time tanpa memerlukan koneksi internet.
2. Personalisasi Prioritas — Pengguna dapat menyesuaikan tingkat prioritas dari berbagai jenis suara, memungkinkan pengguna mendapatkan pengalaman yang disesuaikan dengan kebutuhan sehari-hari mereka.
3. Emergency Contact Alert — Membantu pengguna secara otomatis menghubungi kontak yang diinginkan ketika terjadi sesuatu sehingga meningkatkan keamanan pengguna dengan memastikan bantuan dapat segera dihubungi saat terdeteksi suara darurat.

## Instalasi

Untuk meng-install SwarSware, ada dua opsi yang tersedia:

### Opsi 1: Install dari Google Drive

Kunjungi link [berikut](https://drive.google.com/drive/folders/1NPU7ex2d-w7uCAk9EY8o8pt8e_jyYhZq?usp=sharing).

### Opsi 2: Build dan jalankan dari source code

1. Clone atau download proyek dan buka di Android Studio.
2. Sinkronisasi proyek dengan Gradle dan jalankan aplikasi melalui Emulator atau Device langsung.