package com.amaal.ghads.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // 1. إنشاء متغير ساكن (static) وخاص (private) يحمل النسخة الوحيدة من الكلاس
    private static DatabaseConnection instance;
    private Connection connection;

    // بيانات الاتصال بقاعدة البيانات عبر الـ Localhost (XAMPP)
    // ملاحظة: تأكدي أن اسم قاعدة البيانات في الـ phpMyAdmin هو ghads_db
    private final String url = "jdbc:mysql://localhost:3306/ghads_db";
    private final String username = "root";
    private final String password = ""; // افتراضياً في XAMPP تكون كلمة المرور فارغة

    // 2. جعل الـ Constructor خاص (Private) لمنع أي كلاس خارجي من استخدام كلمة 'new'
    private DatabaseConnection() {
        try {
            // تحميل مكتبة ومُعرّف الـ MySQL ليفهمها مشروع الجافا
            Class.forName("com.mysql.cj.jdbc.Driver");
            // فتح الاتصال الفعلي وتخزينه في متغير connection
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("✅ تم الاتصال بقاعدة البيانات بنجاح وبأمان!");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ لم يتم العثور على مكوّن الـ MySQL Driver! تأكدي من إضافته للـ pom.xml");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ فشل الاتصال بقاعدة البيانات! تأكدي من تشغيل برامج Apache و MySQL في XAMPP.");
            e.printStackTrace();
        }
    }

    // 3. الميثود العامة والوحيدة التي تسمح لأي كلاس آخر بالحصول على نسخة الاتصال
    // ميزة synchronized تضمن عدم حدوث تضارب إذا حاول موظفان الاتصال في نفس الملي ثانية
    public static synchronized DatabaseConnection getInstance() {
        try {
            // إذا لم يتم إنشاء النسخة بعد، أو تم إغلاق الاتصال لسبب ما، قم بإنشائها فوراً
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DatabaseConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }

    // ميثود مساعدة لاسترجاع كائن الـ Connection لاستخدامه في جمل الاستعلام (Queries)
    public Connection getConnection() {
        return connection;
    }
}