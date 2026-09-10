# FitSehat Android v1.0

FitSehat adalah aplikasi Android offline-first untuk asesmen kebugaran umum, estimasi BMI/BMR/TDEE, penyusunan target kalori konservatif, program latihan, nutrisi, dan pencatatan progres.

## Teknologi
- Android native Java (tanpa library eksternal di source app)
- Min SDK 24, Target SDK 35
- SharedPreferences untuk penyimpanan lokal
- UI programmatic agar ringan dan mudah dimodifikasi

## Cara membuka
1. Buka folder ini melalui Android Studio.
2. Tunggu Gradle sync.
3. Jalankan pada emulator/perangkat Android.
4. Untuk APK: Build > Build App Bundle(s) / APK(s) > Build APK(s).

## Catatan kesehatan
- BMI adalah alat skrining, bukan diagnosis.
- BMR memakai persamaan Mifflin–St Jeor sebagai estimasi resting energy expenditure.
- Rekomendasi aktivitas fisik mengacu pada WHO Guidelines on Physical Activity and Sedentary Behaviour.
- Konteks obesitas dewasa Indonesia merujuk PNPK Kemenkes RI, KMK HK.01.07/MENKES/509/2025.
- FitSehat tidak menggantikan konsultasi tenaga kesehatan.

## Privasi
Versi ini menyimpan data di perangkat. Form pembayaran hanya demo dan tidak memproses transaksi.

## Desain
Semua figur wanita pada aset aplikasi memakai hijab syar'i/modest. Figur visual dibuat untuk merepresentasikan pengguna Indonesia.

## Build APK Online
Proyek ini sudah dilengkapi GitHub Actions dan Codemagic. Lihat `BUILD_APK_ONLINE.md`. Hasil workflow bernama `FitSehat-v1.0-install.apk` dan dapat dipasang langsung pada Android untuk pengujian pribadi.
