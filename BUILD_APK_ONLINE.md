# Membuat APK FitSehat Secara Online

Proyek ini sudah disiapkan untuk menghasilkan **APK debug yang ditandatangani otomatis dan dapat langsung dipasang di Android**.

## Cara paling mudah — GitHub Actions

1. Buat akun GitHub bila belum punya.
2. Buat repository baru, misalnya `FitSehatAndroid`.
3. Unggah **seluruh isi folder proyek ini** ke repository. Pastikan folder `.github/workflows` ikut terunggah.
4. Buka tab **Actions** di repository.
5. Pilih **Build FitSehat APK**.
6. Tekan **Run workflow** → **Run workflow**.
7. Tunggu build selesai dengan tanda centang hijau.
8. Buka hasil workflow tersebut, lalu pada bagian **Artifacts** tekan **FitSehat-APK**.
9. Unduh ZIP artifact, ekstrak, lalu instal file `FitSehat-v1.0-install.apk` di HP.

> Bila Android memblokir pemasangan, izinkan **Install unknown apps / Instal aplikasi tidak dikenal** untuk browser atau file manager yang dipakai membuka APK.

## Alternatif — Codemagic

1. Masuk ke Codemagic dan hubungkan repository GitHub FitSehat.
2. Pilih proyek Android.
3. Codemagic akan membaca file `codemagic.yaml` di root proyek.
4. Pilih workflow **FitSehat Installable APK**.
5. Tekan **Start new build**.
6. Setelah build selesai, unduh artifact `FitSehat-v1.0-install.apk`.
7. Buka APK di HP dan instal.

## Catatan

- APK yang dihasilkan dari workflow ini adalah **debug APK**, cocok untuk instalasi pribadi, pengujian, dan demo.
- Untuk distribusi resmi di Google Play, gunakan **release signing key** milik Anda sendiri dan hasilkan Android App Bundle (`.aab`).
- Jangan memasukkan password, token, API key, atau data pembayaran nyata ke source code/repository publik.
