package id.fitsehat.app;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    private LinearLayout root, content;
    private SharedPreferences sp;
    private int step = 0;
    private final int BLUE = Color.rgb(21,94,239);
    private final int NAVY = Color.rgb(7,27,66);
    private final int GOLD = Color.rgb(227,171,46);
    private final int SOFT = Color.rgb(244,247,251);
    private final int GREEN = Color.rgb(21,148,85);
    private int selectedDays = 0;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        sp = getSharedPreferences("fitsehat", MODE_PRIVATE);
        showSplash();
    }

    private int dp(int v){ return (int)(v * getResources().getDisplayMetrics().density + .5f); }
    private GradientDrawable bg(int color, int radius){
        GradientDrawable g = new GradientDrawable(); g.setColor(color); g.setCornerRadius(dp(radius)); return g;
    }
    private GradientDrawable strokeBg(int fill,int stroke,int radius){
        GradientDrawable g=bg(fill,radius); g.setStroke(dp(1),stroke); return g;
    }
    private TextView tv(String s,int size,boolean bold){
        TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(NAVY); t.setGravity(Gravity.CENTER_VERTICAL);
        if(bold)t.setTypeface(Typeface.DEFAULT,Typeface.BOLD); return t;
    }
    private Button btn(String s){
        Button b=new Button(this); b.setText(s); b.setTextSize(16); b.setTextColor(Color.WHITE); b.setAllCaps(false); b.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        b.setBackground(bg(BLUE,22)); b.setPadding(dp(18),0,dp(18),0); b.setMinHeight(dp(56)); return b;
    }
    private LinearLayout vbox(){ LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);return l; }
    private void pad(View v,int x,int y){v.setPadding(dp(x),dp(y),dp(x),dp(y));}
    private LinearLayout.LayoutParams lp(int w,int h,int top){ LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(w,h);p.setMargins(0,dp(top),0,0);return p; }

    private void base(String title, String subtitle, boolean back){
        ScrollView sv=new ScrollView(this); root=vbox(); root.setBackgroundColor(Color.WHITE); sv.addView(root);
        LinearLayout head=new LinearLayout(this); head.setGravity(Gravity.CENTER_VERTICAL); pad(head,18,12);
        if(back){ Button x=new Button(this); x.setText("‹");x.setTextSize(28);x.setTextColor(BLUE);x.setBackgroundColor(Color.TRANSPARENT);x.setOnClickListener(v->{if(step>0){step--;showStep();}}); head.addView(x,new LinearLayout.LayoutParams(dp(52),dp(52))); }
        ImageView logo=new ImageView(this);logo.setImageResource(R.drawable.fitsehat_logo);logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);head.addView(logo,new LinearLayout.LayoutParams(0,dp(58),1));
        root.addView(head);
        if(step>0 && step<22){ ProgressBar pb=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);pb.setMax(21);pb.setProgress(step);pb.setProgressTintList(android.content.res.ColorStateList.valueOf(BLUE));pb.setProgressBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(225,231,240)));LinearLayout.LayoutParams pp=lp(-1,dp(8),2);pp.setMargins(dp(24),0,dp(24),0);root.addView(pb,pp); }
        if(title!=null){ TextView h=tv(title,30,true);h.setGravity(Gravity.CENTER);pad(h,24,0);root.addView(h,lp(-1,-2,26)); }
        if(subtitle!=null && !subtitle.isEmpty()){TextView s=tv(subtitle,17,false);s.setTextColor(Color.rgb(90,103,127));s.setGravity(Gravity.CENTER);pad(s,30,8);root.addView(s);}
        content=vbox(); pad(content,22,14); root.addView(content);
        setContentView(sv);
    }

    private void showSplash(){
        base(null,null,false);
        Space spc=new Space(this); content.addView(spc,new LinearLayout.LayoutParams(1,dp(20)));
        ImageView hero=new ImageView(this);hero.setImageResource(R.drawable.woman_hijab_hero);hero.setScaleType(ImageView.ScaleType.CENTER_CROP);hero.setBackground(bg(SOFT,28));hero.setClipToOutline(true);content.addView(hero,new LinearLayout.LayoutParams(-1,dp(310)));
        TextView t=tv("Hidup Lebih Sehat,\nLebih Terarah",34,true);t.setGravity(Gravity.CENTER);content.addView(t,lp(-1,-2,20));
        TextView sub=tv("Program olahraga, nutrisi, dan pemantauan progres yang disusun dari profilmu.",17,false);sub.setTextColor(Color.DKGRAY);sub.setGravity(Gravity.CENTER);pad(sub,10,10);content.addView(sub);
        LinearLayout features=new LinearLayout(this);features.setGravity(Gravity.CENTER);String[] fs={"Rencana personal","Panduan sehat","Pantau progres"};for(String f:fs){TextView c=tv(f,13,true);c.setGravity(Gravity.CENTER);c.setBackground(bg(SOFT,18));c.setPadding(dp(8),dp(14),dp(8),dp(14));features.addView(c,new LinearLayout.LayoutParams(0,dp(74),1));} content.addView(features,lp(-1,-2,16));
        Button b=btn("Mulai  →");b.setOnClickListener(v->{step=1;showStep();});content.addView(b,lp(-1,dp(58),22));
        TextView note=tv("Panduan umum untuk orang dewasa. Bukan pengganti diagnosis atau konsultasi tenaga kesehatan.",12,false);note.setTextColor(Color.GRAY);note.setGravity(Gravity.CENTER);content.addView(note,lp(-1,-2,12));
    }

    private void showStep(){
        switch(step){
            case 1: choice("Kamu pria atau wanita?","Pilih profil biologis untuk estimasi energi.","gender",new String[]{"Pria","Wanita"},new int[]{R.drawable.man_jog,R.drawable.woman_hijab_walk},false);break;
            case 2: intro();break;
            case 3: choice("Apa target utama kamu?","Pilih tujuan yang paling sesuai.","goal",new String[]{"Menurunkan Berat Badan","Meningkatkan Kebugaran","Menambah Massa Otot"},new int[]{R.drawable.woman_hijab_walk,R.drawable.man_jog,R.drawable.man_strength},false);break;
            case 4: choice("Pilih tipe tubuhmu saat ini","Gunakan sebagai gambaran umum, bukan diagnosis.","body",new String[]{"Kurus","Medium","Plus Size"},null,false);break;
            case 5: choice("Pilih kondisi yang kamu inginkan","Target visual hanya motivasi; hasil tiap orang berbeda.","desired",new String[]{"Lebih Ramping & Bugar","Atletis","Lebih Berotot"},null,false);break;
            case 6: choice("Pilih target area kamu","Boleh lebih dari satu.","areas",new String[]{"Perut","Dada","Lengan","Kaki","Punggung"},null,true);break;
            case 7: choice("Pilih goal tambahan","Boleh lebih dari satu.","extra",new String[]{"Merasa lebih berenergi","Jadikan fitness kebiasaan","Merasa lebih kuat","Bisa memakai pakaian lama","Mengurangi stres"},null,true);break;
            case 8: choice("Pernah menghitung kalori sebelumnya?","Kami tetap akan menjelaskan perhitungannya.","calorie_exp",new String[]{"Pernah","Tidak"},null,false);break;
            case 9: number("Berapa tinggimu?","cm","height",100,230,167);break;
            case 10:number("Berapa berat badanmu saat ini?","kg","weight",30,250,70);break;
            case 11:number("Berapa target berat badanmu?","kg","target_weight",30,250,65);break;
            case 12:number("Berapa umur kamu?","tahun","age",18,90,35);break;
            case 13:choice("Seberapa aktif kamu?","Aktivitas harian di luar olahraga.","activity",new String[]{"Tidak Aktif","Sedikit Aktif","Aktif","Sangat Aktif"},null,false);break;
            case 14:choice("Seberapa fit kamu?","Pilih berdasarkan pengalaman latihan.","fitness",new String[]{"Baru di Fitness","Pemula","Menengah","Advanced"},null,false);break;
            case 15:days();break;
            case 16:choice("Berapa kali kamu ingin olahraga dalam satu hari?","Mayoritas program cukup satu sesi terstruktur.","sessions",new String[]{"Sekali","1–2×","Dua Kali"},null,false);break;
            case 17:choice("Apakah kamu berolahraga di 30 hari terakhir?","Jawaban ini membantu menentukan level awal.","recent",new String[]{"Ya","Tidak"},null,false);break;
            case 18:choice("Pilih durasi program yang kamu inginkan","Program dapat diubah kapan saja.","duration",new String[]{"2 Minggu","3 Minggu","4 Minggu","5 Minggu","6 Minggu","8 Minggu"},null,false);break;
            case 19:choice("Apakah kamu ada acara khusus?","Opsional; ini hanya untuk membantu menetapkan tenggat realistis.","event",new String[]{"Ke Pantai","Pernikahan","Liburan","Acara Olahraga","Acara Keluarga","Reuni","Lainnya","Tidak Ada Acara"},null,false);break;
            case 20:email();break;
            case 21:analyze();break;
            case 22:result();break;
        }
    }

    private void intro(){
        base("Cukup 10–20 Menit untuk Memulai","Program dimulai ringan dan dapat ditingkatkan sesuai kemampuan.",true);
        ImageView im=new ImageView(this);im.setImageResource(R.drawable.woman_hijab_walk);im.setScaleType(ImageView.ScaleType.CENTER_CROP);im.setBackground(bg(SOFT,24));im.setClipToOutline(true);content.addView(im,new LinearLayout.LayoutParams(-1,dp(260)));
        TextView p=tv("Aktivitas singkat yang konsisten lebih baik daripada rencana berat yang sulit dipertahankan. FitSehat akan menyesuaikan rekomendasi dengan profil, aktivitas, dan tujuanmu.",16,false);pad(p,4,16);content.addView(p);
        nextButton();
    }

    private void choice(String title,String subtitle,String key,String[] opts,int[] imgs,boolean multi){
        base(title,subtitle,true);
        String current=sp.getString(key,"");
        for(int i=0;i<opts.length;i++){
            LinearLayout card=new LinearLayout(this); card.setGravity(Gravity.CENTER_VERTICAL);card.setPadding(dp(18),dp(12),dp(12),dp(12)); card.setBackground(strokeBg(Color.WHITE,Color.rgb(225,230,238),18));
            TextView name=tv(opts[i],18,true); card.addView(name,new LinearLayout.LayoutParams(0,dp(64),1));
            if(imgs!=null){ImageView im=new ImageView(this);im.setImageResource(imgs[i]);im.setScaleType(ImageView.ScaleType.CENTER_CROP);im.setBackground(bg(SOFT,14));im.setClipToOutline(true);card.addView(im,new LinearLayout.LayoutParams(dp(110),dp(70)));}
            String opt=opts[i];
            if(current.contains(opt)) card.setBackground(strokeBg(Color.rgb(246,249,255),BLUE,18));
            card.setOnClickListener(v->{
                if(multi){String old=sp.getString(key,"");Set<String> s=new LinkedHashSet<>();if(!old.isEmpty())s.addAll(Arrays.asList(old.split("\\|")));if(s.contains(opt))s.remove(opt);else s.add(opt);sp.edit().putString(key,String.join("|",s)).apply();showStep();}
                else {sp.edit().putString(key,opt).apply(); step++; showStep();}
            });
            content.addView(card,lp(-1,dp(94),10));
        }
        if(multi){ Button b=btn("Lanjut");b.setOnClickListener(v->{if(!sp.getString(key,"").isEmpty()){step++;showStep();}else toast("Pilih minimal satu.");});content.addView(b,lp(-1,dp(58),20)); }
    }

    private void number(String title,String unit,String key,int min,int max,int def){
        base(title,"Masukkan angka dalam rentang yang wajar.",true);
        EditText e=new EditText(this);e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);e.setText(sp.getString(key,String.valueOf(def)));e.setTextSize(40);e.setGravity(Gravity.CENTER);e.setTextColor(NAVY);e.setBackground(strokeBg(Color.WHITE,Color.rgb(220,225,235),18));content.addView(e,new LinearLayout.LayoutParams(-1,dp(110)));
        TextView u=tv(unit,20,true);u.setGravity(Gravity.CENTER);content.addView(u,lp(-1,-2,8));
        Button b=btn("Lanjut");b.setOnClickListener(v->{try{double n=Double.parseDouble(e.getText().toString());if(n<min||n>max){toast("Masukkan nilai antara "+min+" dan "+max+".");return;}sp.edit().putString(key,e.getText().toString()).apply();step++;showStep();}catch(Exception ex){toast("Masukkan angka yang benar.");}});content.addView(b,lp(-1,dp(58),22));
    }

    private void days(){
        base("Kamu ingin latihan hari apa?","Pilih 3–6 hari latihan.",true);selectedDays=0;LinearLayout grid=vbox();String[] ds={"Senin","Selasa","Rabu","Kamis","Jumat","Sabtu","Minggu"};String current=sp.getString("days","");
        for(String d:ds){CheckBox c=new CheckBox(this);c.setText(d);c.setTextSize(18);c.setTextColor(NAVY);c.setPadding(dp(14),dp(8),dp(14),dp(8));c.setBackground(strokeBg(Color.WHITE,Color.LTGRAY,16));c.setChecked(current.contains(d));if(c.isChecked())selectedDays++;grid.addView(c,lp(-1,dp(62),8));c.setOnCheckedChangeListener((v,checked)->{});}
        content.addView(grid);Button b=btn("Lanjut");b.setOnClickListener(v->{List<String> s=new ArrayList<>();for(int i=0;i<grid.getChildCount();i++){CheckBox c=(CheckBox)grid.getChildAt(i);if(c.isChecked())s.add(c.getText().toString());}if(s.size()<3||s.size()>6){toast("Pilih 3 sampai 6 hari.");return;}sp.edit().putString("days",String.join("|",s)).apply();step++;showStep();});content.addView(b,lp(-1,dp(58),20));
    }

    private void email(){
        base("Masukkan email kamu","Dipakai untuk menyimpan profil pada perangkat ini dan contoh alur akun.",true);EditText e=new EditText(this);e.setHint("nama@email.com");e.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);e.setText(sp.getString("email",""));e.setTextSize(18);e.setBackground(strokeBg(Color.WHITE,Color.LTGRAY,16));pad(e,16,8);content.addView(e,new LinearLayout.LayoutParams(-1,dp(64)));Button b=btn("Analisis Profil");b.setOnClickListener(v->{String x=e.getText().toString().trim();if(!x.contains("@")){toast("Masukkan email yang valid.");return;}sp.edit().putString("email",x).apply();step++;showStep();});content.addView(b,lp(-1,dp(58),18));
    }

    private void analyze(){
        base(null,null,false);Space s=new Space(this);content.addView(s,new LinearLayout.LayoutParams(1,dp(120)));ProgressBar p=new ProgressBar(this);content.addView(p,new LinearLayout.LayoutParams(-1,dp(70)));TextView t=tv("Sedang menganalisis profil, target, dan kebiasaanmu…",24,true);t.setGravity(Gravity.CENTER);pad(t,20,24);content.addView(t);new Handler().postDelayed(()->{step=22;showStep();},1500);
    }

    private void result(){
        base("Kondisi Tubuhmu Saat Ini","Ringkasan ini adalah estimasi kebugaran umum.",false);
        double h=num("height",167), w=num("weight",70), tw=num("target_weight",65);int age=(int)num("age",35);String sex=sp.getString("gender","Pria");String act=sp.getString("activity","Tidak Aktif");String goal=sp.getString("goal","Meningkatkan Kebugaran");double bmi=HealthEngine.bmi(w,h);double bmr=HealthEngine.bmr(sex,w,h,age);double tdee=HealthEngine.tdee(bmr,act);int cal=HealthEngine.calorieTarget(tdee,goal,sex);
        metric("BMI (Indeks Massa Tubuh)",String.format(Locale.US,"%.1f",bmi),HealthEngine.bmiCategory(bmi));
        metric("Estimasi BMR",Math.round(bmr)+" kkal/hari","Energi istirahat berdasarkan Mifflin–St Jeor");
        metric("Estimasi Kebutuhan Harian",Math.round(tdee)+" kkal/hari","BMR × faktor aktivitas");
        metric("Target Kalori Harian",cal+" kkal","Target konservatif berdasarkan tujuanmu");
        LinearLayout profile=vbox();profile.setBackground(bg(SOFT,20));pad(profile,18,14);profile.addView(tv("Profil & Target",20,true));profile.addView(tv("Berat saat ini: "+fmt(w)+" kg\nTarget berat: "+fmt(tw)+" kg\nLevel fitness: "+sp.getString("fitness","Pemula")+"\nDurasi: "+sp.getString("duration","4 Minggu"),16,false));TextView pace=tv(HealthEngine.targetPace(w,tw),14,false);pace.setTextColor(Color.DKGRAY);profile.addView(pace,lp(-1,-2,10));content.addView(profile,lp(-1,-2,14));
        Button b=btn("Buka Program Saya");b.setOnClickListener(v->dashboard());content.addView(b,lp(-1,dp(58),20));
        Button refs=outline("Dasar Ilmiah & Referensi");refs.setOnClickListener(v->references());content.addView(refs,lp(-1,dp(54),10));
    }

    private void dashboard(){
        base("Selamat Datang!","Langkah kecil hari ini untuk masa depan yang lebih sehat.",false);
        ImageView hero=new ImageView(this);hero.setImageResource("Wanita".equals(sp.getString("gender","Pria"))?R.drawable.woman_hijab_profile:R.drawable.man_jog);hero.setScaleType(ImageView.ScaleType.CENTER_CROP);hero.setBackground(bg(SOFT,24));hero.setClipToOutline(true);content.addView(hero,new LinearLayout.LayoutParams(-1,dp(200)));
        double h=num("height",167), w=num("weight",70),tw=num("target_weight",65);double bmi=HealthEngine.bmi(w,h);int cal=HealthEngine.calorieTarget(HealthEngine.tdee(HealthEngine.bmr(sp.getString("gender","Pria"),w,h,(int)num("age",35)),sp.getString("activity","Tidak Aktif")),sp.getString("goal","Meningkatkan Kebugaran"),sp.getString("gender","Pria"));
        metric("BMI",String.format(Locale.US,"%.1f",bmi),HealthEngine.bmiCategory(bmi));metric("Target Kalori",cal+" kkal/hari","Estimasi, sesuaikan dengan respons tubuh");
        TextView head=tv("Program Minggu Ini",22,true);content.addView(head,lp(-1,-2,18));
        actionCard("🏃  Latihan Hari Ini","Pemanasan + latihan inti + pendinginan",v->workout());
        actionCard("🥗  Nutrisi & Menu Sehat","Menu halal seimbang dan hidrasi",v->nutrition());
        actionCard("📈  Pantau Progres","Catat berat, konsistensi, dan kebiasaan",v->progress());
        actionCard("📚  Dasar Ilmiah & Referensi","WHO, Kemenkes RI, dan rumus yang digunakan",v->references());
        actionCard("⚙️  Profil & Pengaturan","Ubah target atau mulai asesmen ulang",v->settings());
    }

    private void workout(){
        base("Program Latihan Harian","Mulai sesuai kemampuan. Hentikan bila timbul nyeri, pusing, atau sesak yang tidak biasa.",false);
        ImageView im=new ImageView(this);im.setImageResource("Wanita".equals(sp.getString("gender","Pria"))?R.drawable.woman_hijab_walk:R.drawable.man_strength);im.setScaleType(ImageView.ScaleType.CENTER_CROP);im.setBackground(bg(SOFT,22));im.setClipToOutline(true);content.addView(im,new LinearLayout.LayoutParams(-1,dp(220)));
        String lvl=sp.getString("fitness","Pemula");String[] items={"Pemanasan — 5 menit","Jalan cepat / cardio ringan — 15–20 menit","Latihan kekuatan dasar — 15 menit","Pendinginan & peregangan — 5 menit"};for(String x:items) actionCard("✓  "+x,"Level: "+lvl,v->toast("Tandai selesai dari catatan progres."));
        homeButton();
    }

    private void nutrition(){
        base("Nutrisi & Menu Sehat","Contoh umum; kebutuhan individual dapat berbeda.",false);
        ImageView im=new ImageView(this);im.setImageResource(R.drawable.woman_hijab_food);im.setScaleType(ImageView.ScaleType.CENTER_CROP);im.setBackground(bg(SOFT,22));im.setClipToOutline(true);content.addView(im,new LinearLayout.LayoutParams(-1,dp(220)));
        double w=num("weight",70),h=num("height",167);int age=(int)num("age",35);String sex=sp.getString("gender","Pria");int cal=HealthEngine.calorieTarget(HealthEngine.tdee(HealthEngine.bmr(sex,w,h,age),sp.getString("activity","Tidak Aktif")),sp.getString("goal","Meningkatkan Kebugaran"),sex);metric("Target energi",cal+" kkal/hari","Estimasi awal, bukan resep medis");
        actionCard("Sarapan","Oatmeal + buah + telur / sumber protein halal",v->{});actionCard("Makan Siang","Nasi secukupnya + ayam/ikan + banyak sayur",v->{});actionCard("Makan Malam","Protein + sayur + karbohidrat sesuai kebutuhan",v->{});actionCard("Hidrasi","Minum secara teratur; kebutuhan meningkat saat panas/berkeringat",v->{});homeButton();
    }

    private void progress(){
        base("Progres Kamu","Konsistensi lebih penting daripada perubahan cepat.",false);double w=num("weight",70),tw=num("target_weight",65);metric("Berat awal",fmt(w)+" kg","Target: "+fmt(tw)+" kg");metric("Konsistensi mingguan","0%","Mulai tandai latihan yang selesai");actionCard("+ Catat berat hari ini","Simpan angka terbaru di perangkat",v->weightDialog());actionCard("Pencapaian","7 hari konsisten • target hidrasi • latihan selesai",v->{});homeButton();
    }

    private void weightDialog(){
        final EditText input=new EditText(this);input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);input.setHint("kg");new AlertDialog.Builder(this).setTitle("Berat hari ini").setView(input).setPositiveButton("Simpan",(d,w)->{sp.edit().putString("latest_weight",input.getText().toString()).apply();toast("Progres tersimpan.");}).setNegativeButton("Batal",null).show();
    }

    private void references(){
        base("Dasar Ilmiah & Referensi","Ringkasan sumber yang dipakai pada versi 1.0.",false);
        ref("Kementerian Kesehatan RI","Pedoman Nasional Pelayanan Klinis Tata Laksana Obesitas Dewasa — KMK HK.01.07/MENKES/509/2025. Digunakan sebagai rujukan konteks klinis obesitas dewasa Indonesia.");
        ref("World Health Organization","WHO Guidelines on Physical Activity and Sedentary Behaviour: dewasa dianjurkan 150–300 menit aktivitas aerobik intensitas sedang per minggu atau 75–150 menit intensitas berat; penguatan otot ≥2 hari/minggu.");
        ref("Mifflin–St Jeor","Persamaan estimasi resting energy expenditure digunakan untuk BMR. Nilai adalah estimasi, bukan pengukuran metabolik langsung.");
        ref("BMI / IMT","BMI digunakan sebagai alat skrining populasi. Komposisi tubuh, lingkar pinggang, kondisi medis, dan etnis dapat memengaruhi interpretasi klinis.");
        TextView note=tv("FitSehat tidak mendiagnosis penyakit dan tidak menggantikan dokter, ahli gizi, fisioterapis, atau tenaga kesehatan lain. Pengguna dengan penyakit kronis, kehamilan, gejala akut, atau riwayat cedera perlu mendapatkan arahan profesional sebelum memulai program intensif.",14,false);note.setTextColor(Color.DKGRAY);note.setBackground(bg(Color.rgb(255,249,230),16));pad(note,16,16);content.addView(note,lp(-1,-2,16));homeButton();
    }

    private void ref(String h,String p){LinearLayout c=vbox();c.setBackground(bg(SOFT,18));pad(c,16,14);c.addView(tv(h,18,true));TextView x=tv(p,14,false);x.setTextColor(Color.DKGRAY);c.addView(x,lp(-1,-2,6));content.addView(c,lp(-1,-2,10));}

    private void settings(){
        base("Profil & Pengaturan",sp.getString("email","Profil lokal"),false);actionCard("Ubah target","Jalankan asesmen kembali dari target utama",v->{step=3;showStep();});actionCard("Paket Pro (demo)","Tampilan alur pembayaran tanpa transaksi nyata",v->payment());actionCard("Hapus data lokal","Menghapus jawaban asesmen pada perangkat",v->new AlertDialog.Builder(this).setTitle("Hapus data?").setMessage("Semua data FitSehat lokal akan dihapus.").setPositiveButton("Hapus",(d,w)->{sp.edit().clear().apply();showSplash();}).setNegativeButton("Batal",null).show());homeButton();
    }

    private void payment(){
        base("Paket FitSehat Pro","Demo antarmuka. Tidak ada transaksi nyata pada versi offline ini.",false);metric("3 Bulan","Rp167.000","Harga contoh — integrasi payment gateway belum diaktifkan");actionCard("OVO","Pilih metode",v->paymentForm("OVO"));actionCard("ShopeePay","Pilih metode",v->paymentForm("ShopeePay"));actionCard("Kartu Kredit","Pilih metode",v->paymentForm("Kartu Kredit"));homeButton();
    }

    private void paymentForm(String method){
        final EditText name=new EditText(this);name.setHint("Nama lengkap");final EditText phone=new EditText(this);phone.setHint("Nomor telepon");phone.setInputType(InputType.TYPE_CLASS_PHONE);LinearLayout box=vbox();box.setPadding(dp(20),0,dp(20),0);box.addView(name);box.addView(phone);new AlertDialog.Builder(this).setTitle("Data "+method).setMessage("Mode demo — data tidak dikirim ke penyedia pembayaran.").setView(box).setPositiveButton("Kirim",(d,w)->new AlertDialog.Builder(this).setTitle("Mode Demo").setMessage("Pembayaran tidak diproses pada build offline ini.").setPositiveButton("OK",null).show()).setNegativeButton("Batal",null).show();
    }

    private void metric(String title,String value,String sub){LinearLayout c=vbox();c.setBackground(bg(SOFT,18));pad(c,18,14);TextView t=tv(title,16,true);TextView v=tv(value,30,true);v.setTextColor(BLUE);TextView s=tv(sub,13,false);s.setTextColor(Color.DKGRAY);c.addView(t);c.addView(v);c.addView(s);content.addView(c,lp(-1,-2,10));}
    private void actionCard(String title,String sub,View.OnClickListener l){LinearLayout c=vbox();c.setBackground(strokeBg(Color.WHITE,Color.rgb(226,232,240),18));pad(c,18,14);c.addView(tv(title,18,true));TextView s=tv(sub,14,false);s.setTextColor(Color.DKGRAY);c.addView(s,lp(-1,-2,4));c.setOnClickListener(l);content.addView(c,lp(-1,-2,10));}
    private Button outline(String text){Button b=btn(text);b.setTextColor(BLUE);b.setBackground(strokeBg(Color.WHITE,BLUE,22));return b;}
    private void nextButton(){Button b=btn("Lanjut");b.setOnClickListener(v->{step++;showStep();});content.addView(b,lp(-1,dp(58),18));}
    private void homeButton(){Button b=outline("← Kembali ke Beranda");b.setOnClickListener(v->dashboard());content.addView(b,lp(-1,dp(54),20));}
    private double num(String key,double def){try{return Double.parseDouble(sp.getString(key,String.valueOf(def)));}catch(Exception e){return def;}}
    private String fmt(double x){return x==(long)x?String.valueOf((long)x):String.format(Locale.US,"%.1f",x);}
    private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
}
