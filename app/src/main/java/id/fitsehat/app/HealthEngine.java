package id.fitsehat.app;

public final class HealthEngine {
    private HealthEngine() {}

    public static double bmi(double kg, double cm) {
        if (kg <= 0 || cm <= 0) return 0;
        double m = cm / 100.0;
        return kg / (m * m);
    }

    public static String bmiCategory(double bmi) {
        if (bmi < 18.5) return "Berat badan kurang";
        if (bmi < 25.0) return "Rentang sehat";
        if (bmi < 30.0) return "Berat badan berlebih";
        return "Obesitas";
    }

    // Mifflin-St Jeor, adult resting energy estimate (kcal/day)
    public static double bmr(String sex, double kg, double cm, int age) {
        double base = 10.0 * kg + 6.25 * cm - 5.0 * age;
        return "Wanita".equalsIgnoreCase(sex) ? base - 161.0 : base + 5.0;
    }

    public static double activityFactor(String activity) {
        if (activity == null) return 1.2;
        switch (activity) {
            case "Sedikit Aktif": return 1.375;
            case "Aktif": return 1.55;
            case "Sangat Aktif": return 1.725;
            default: return 1.2;
        }
    }

    public static double tdee(double bmr, String activity) {
        return bmr * activityFactor(activity);
    }

    public static int calorieTarget(double tdee, String goal, String sex) {
        double target = tdee;
        if ("Menurunkan Berat Badan".equals(goal)) target -= 300;
        else if ("Menambah Massa Otot".equals(goal)) target += 250;
        // conservative product guardrail; not a medical prescription
        double floor = "Wanita".equalsIgnoreCase(sex) ? 1200 : 1500;
        return (int)Math.round(Math.max(floor, target));
    }

    public static String targetPace(double currentKg, double targetKg) {
        double delta = targetKg - currentKg;
        if (Math.abs(delta) < 0.5) return "Fokus mempertahankan berat dan meningkatkan kebugaran.";
        if (delta < 0) return "Target diturunkan bertahap; evaluasi progres setiap minggu dan hindari penurunan ekstrem.";
        return "Target dinaikkan bertahap dengan latihan kekuatan dan asupan seimbang.";
    }
}
