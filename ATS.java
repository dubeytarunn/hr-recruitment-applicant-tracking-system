import java.sql.*;
import java.util.Scanner;

public class ATS {

    // ===== DATABASE CONFIG =====
    static final String DB_URL = "jdbc:mysql://localhost:3306/hr_ats";
    static final String DB_USER = "root";
    static final String DB_PASS = "2006"; // your MySQL root password
    // ===========================

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== HR RECRUITMENT & ATS ===");
            System.out.println("1. HR Login");
            System.out.println("2. Candidate Login");
            System.out.println("3. Candidate Registration");
            System.out.println("4. Exit");
            System.out.print("Choice: ");

            int choice = readInt(1, 4);

            switch (choice) {
                case 1 -> hrLogin();
                case 2 -> candidateLogin();
                case 3 -> candidateRegister();
                case 4 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
            }
        }
    }

    // ========= SAFE INPUT METHOD (KEY FIX) =========
    static int readInt(int min, int max) {
        while (true) {
            String input = sc.nextLine().trim();
            try {
                int val = Integer.parseInt(input);
                if (val >= min && val <= max) {
                    return val;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Enter a number between " + min + " and " + max + ": ");
        }
    }

    // ================= DATABASE CONNECTION =================
    static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // ================= HR LOGIN =================
    static void hrLogin() {
        try (Connection con = getConnection()) {

            System.out.print("HR Email: ");
            String email = sc.nextLine().trim();

            System.out.print("Password: ");
            String pass = sc.nextLine().trim();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE email=? AND password=? AND role='HR'");
            ps.setString(1, email);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                hrMenu(rs.getInt("user_id"));
            } else {
                System.out.println("Invalid HR credentials");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= HR MENU =================
    static void hrMenu(int hrId) throws Exception {
        while (true) {
            System.out.println("\n--- HR DASHBOARD ---");
            System.out.println("1. Post Job");
            System.out.println("2. View Applications");
            System.out.println("3. Update Application Status");
            System.out.println("4. Logout");
            System.out.print("Choice: ");

            int ch = readInt(1, 4);

            switch (ch) {
                case 1 -> postJob(hrId);
                case 2 -> viewApplications();
                case 3 -> updateStatus();
                case 4 -> {
                    return;
                }
            }
        }
    }

    // ================= POST JOB =================
    static void postJob(int hrId) throws Exception {
        try (Connection con = getConnection()) {

            System.out.print("Job Title: ");
            String title = sc.nextLine();

            System.out.print("Description: ");
            String desc = sc.nextLine();

            System.out.print("Location: ");
            String loc = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO jobs (title,description,location,hr_id) VALUES (?,?,?,?)");
            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, loc);
            ps.setInt(4, hrId);
            ps.executeUpdate();

            System.out.println("Job posted successfully");
        }
    }

    // ================= VIEW APPLICATIONS =================
    static void viewApplications() throws Exception {
        try (Connection con = getConnection()) {

            String q = """
                    SELECT a.application_id, j.title, u.name, a.status
                    FROM applications a
                    JOIN jobs j ON a.job_id = j.job_id
                    JOIN users u ON a.candidate_id = u.user_id
                    """;

            ResultSet rs = con.createStatement().executeQuery(q);

            System.out.println("\nID | Job | Candidate | Status");
            while (rs.next()) {
                System.out.println(
                        rs.getInt(1) + " | " +
                                rs.getString(2) + " | " +
                                rs.getString(3) + " | " +
                                rs.getString(4));
            }
        }
    }

    // ================= UPDATE STATUS =================
    static void updateStatus() throws Exception {
        try (Connection con = getConnection()) {

            System.out.print("Application ID: ");
            int appId = readInt(1, Integer.MAX_VALUE);

            System.out.print("New Status (INTERVIEW / SELECTED / REJECTED): ");
            String status = sc.nextLine().trim();

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE applications SET status=? WHERE application_id=?");
            ps.setString(1, status);
            ps.setInt(2, appId);
            ps.executeUpdate();

            System.out.println("Status updated successfully");
        }
    }

    // ================= CANDIDATE REGISTRATION =================
    static void candidateRegister() {
        try (Connection con = getConnection()) {

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Password: ");
            String pass = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users (name,email,password,role) VALUES (?,?,?,'CANDIDATE')");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.executeUpdate();

            System.out.println("Candidate registered successfully");

        } catch (Exception e) {
            System.out.println("Registration failed (email may already exist)");
        }
    }

    // ================= CANDIDATE LOGIN =================
    static void candidateLogin() {
        try (Connection con = getConnection()) {

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Password: ");
            String pass = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE email=? AND password=? AND role='CANDIDATE'");
            ps.setString(1, email);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                candidateMenu(rs.getInt("user_id"));
            } else {
                System.out.println("Invalid credentials");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= CANDIDATE MENU =================
    static void candidateMenu(int cid) throws Exception {
        while (true) {
            System.out.println("\n--- CANDIDATE DASHBOARD ---");
            System.out.println("1. View Jobs");
            System.out.println("2. Apply for Job");
            System.out.println("3. View My Applications");
            System.out.println("4. Logout");
            System.out.print("Choice: ");

            int ch = readInt(1, 4);

            switch (ch) {
                case 1 -> viewJobs();
                case 2 -> applyJob(cid);
                case 3 -> myApplications(cid);
                case 4 -> {
                    return;
                }
            }
        }
    }

    // ================= VIEW JOBS =================
    static void viewJobs() throws Exception {
        try (Connection con = getConnection()) {

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM jobs");

            System.out.println("\nJob ID | Title | Location");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("job_id") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("location"));
            }
        }
    }

    // ================= APPLY JOB =================
    static void applyJob(int cid) throws Exception {
        try (Connection con = getConnection()) {

            System.out.print("Enter Job ID: ");
            int jid = readInt(1, Integer.MAX_VALUE);

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO applications (job_id,candidate_id,status) VALUES (?,?, 'APPLIED')");
            ps.setInt(1, jid);
            ps.setInt(2, cid);
            ps.executeUpdate();

            System.out.println("Applied successfully");
        }
    }

    // ================= MY APPLICATIONS =================
    static void myApplications(int cid) throws Exception {
        try (Connection con = getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "SELECT j.title, a.status FROM applications a " +
                            "JOIN jobs j ON a.job_id=j.job_id WHERE candidate_id=?");
            ps.setInt(1, cid);

            ResultSet rs = ps.executeQuery();

            System.out.println("\nJob | Status");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " | " + rs.getString(2));
            }
        }
    }
}
