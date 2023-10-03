package controler;

import Connection.ConnectionQLTV;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Model_Sach;

public class Sach_Controller {

    public static Connection conn = ConnectionQLTV.getInstance().getConnection();
    public static Statement state;
    public static PreparedStatement pstate;
    public static String sql;
    public static ResultSet rs;
    public static List<Model_Sach> arrSach = new ArrayList<>();

    // Lấy nguồn : Dùng để lấy nguồn dữ liệu từ CSDL và đưa vào mảng ArrayList
    public static List<Model_Sach> LayNguonSach(String ten) throws SQLException {
        arrSach = new ArrayList<>();
        conn = null;
        state = null;
        try {
            if (ten.equals("")) {
                sql = "Select * from (sach inner join nhaxuatban on sach.MaNXB=nhaxuatban.MaNXB) inner join theloai on sach.MaTL=theloai.MaTL ";
            } else {
                sql = "Select * from (sach inner join nhaxuatban on sach.MaNXB=nhaxuatban.MaNXB) inner join theloai on sach.MaTL=theloai.MaTL Where TenSach like '%" + ten + "%'";
            }
            conn = ConnectionQLTV.getInstance().getConnection();
            state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Model_Sach temp = new Model_Sach();
                temp.setMaSach(rs.getString("MaSach"));
                temp.setTenSach(rs.getString("TenSach"));
                temp.setMaTL(rs.getString("MaTL"));
                temp.setTacGia(rs.getString("TacGia"));
                temp.setMaNXB(rs.getString("MaNXB"));
                temp.setNamXB(rs.getString("NamXB"));
                temp.setTenTL(rs.getString("TenTL"));
                temp.setSoLuong(rs.getInt("SoLuong"));
                temp.setTenNXB(rs.getString("TenNXB"));

                arrSach.add(temp);
            }
            state.close();

        } catch (SQLException ex) {
            Logger.getLogger(Sach_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrSach;
    }

    public static String automasach() throws SQLException {
        String mas = "", maht = "";
        conn = ConnectionQLTV.getInstance().getConnection();
        sql = "SELECT MAX(MaSach) FROM sach";
        state = conn.createStatement();
        ResultSet rs = state.executeQuery(sql);
        if (rs.next()) {
            maht = rs.getString(1);
        }
        if (maht.isEmpty()) {
            mas = "S001"; // Nếu không có mã thể loại hiện tại, bắt đầu từ mã TL001
        } else {
            // Tách phần số của mã thể loại hiện tại
            String so = maht.substring(2);
            // Chuyển phần số thành số nguyên và tăng giá trị lên 1
            int soMoi = Integer.parseInt(so) + 1;
            // Format số mới để có độ dài 3 chữ số (ví dụ: 001, 010, 100)
            String soMoiFormatted = String.format("%03d", soMoi);
            // Tạo mã thể loại mới bằng cách kết hợp "TL" với số mới đã định dạng
            mas = "S" + soMoiFormatted;
        }
        return mas;
    }

    // 1.insert : Thêm độc giả
    public static void ThemMoi(Model_Sach sach) {

        pstate = null;

        try {

            conn = ConnectionQLTV.getInstance().getConnection();
            sql = "Insert into sach values(?,?,?,?,?,?,?)";
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, sach.getMaSach());
            pstate.setString(2, sach.getTenSach());
            pstate.setString(3, sach.getMaTL());
            pstate.setString(4, sach.getTacGia());
            pstate.setString(5, sach.getMaNXB());
            pstate.setString(6, sach.getNamXB());
            pstate.setInt(7, 0);
            pstate.executeUpdate();
            pstate.close();

        } catch (SQLException ex) {
            Logger.getLogger(Sach_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // 2.update : Cập nhật Sách
    public static void CapNhat(Model_Sach sach) {

        pstate = null;
        try {
            conn = ConnectionQLTV.getInstance().getConnection();
            sql = "Update sach set TenSach=?,MaTL=?,TacGia=?,MaNXB=?,NamXB=?,SoLuong=? Where MaSach=?";
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, sach.getTenSach());
            pstate.setString(2, sach.getMaTL());
            pstate.setString(3, sach.getTacGia());
            pstate.setString(4, sach.getMaNXB());
            pstate.setString(5, sach.getNamXB());
            pstate.setInt(6, sach.getSoLuong());
            pstate.setString(7, sach.getMaSach());
            pstate.execute();
            pstate.close();

        } catch (SQLException ex) {
            Logger.getLogger(Sach_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // 5. check : Kiểm tra dữ liệu PK
    public static boolean KiemTraTrungMa(String manhap, boolean ktThem, String macu) {
        boolean kq = false;

        state = null;
        try {
            conn = ConnectionQLTV.getInstance().getConnection();
            sql = "Select MaSach from sach where MaSach='" + manhap + "'";
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            if (rs.next()) {
                kq = true;
            } else {
                kq = false;
            }
            state.close();
//        
        } catch (SQLException ex) {
            Logger.getLogger(Sach_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return kq;

    }

    public static List<Model_Sach> TimKiemTheoTen(String ten) {
        List<Model_Sach> arrDG = new ArrayList<>();
        pstate = null;

        try {
            conn = ConnectionQLTV.getInstance().getConnection();
            sql = "SELECT * FROM sach WHERE TenSach LIKE '%" + ten + "'";
            pstate = conn.prepareStatement(sql);

            rs = pstate.executeQuery();

            while (rs.next()) {
                Model_Sach temp = new Model_Sach();
                temp.setMaSach(rs.getString("MaSach"));
                temp.setTenSach(rs.getString("TenSach"));
                temp.setMaTL(rs.getString("MaTL"));
                temp.setTenTL(rs.getString("TenTL"));
                temp.setTacGia(rs.getString("TacGia"));
                temp.setMaNXB(rs.getString("MaNXB"));
                temp.setTenNXB(rs.getString("TenNXB"));
                temp.setNamXB(rs.getString("NamXB"));
                temp.setSoLuong(rs.getInt("SoLuong"));
                arrDG.add(temp);
            }

            pstate.close();
        } catch (SQLException ex) {
            Logger.getLogger(Sach_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrDG;
    }

}
