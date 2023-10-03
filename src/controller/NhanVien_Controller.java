/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import model.Model_NhanVien;

/**
 *
 * @author Think Pad
 */
public class NhanVien_Controller {

    public static Connection conn = ConnectionQLTV.getInstance().getConnection();
    public static Statement state;
    public static PreparedStatement pstate;
    public static String sql;
    public static ResultSet rs;

    // Lấy nguồn : Dùng để lấy nguồn dữ liệu từ CSDL và đưa vào mảng ArrayList
    public static List<Model_NhanVien> LayNguonNV(String ma) throws SQLException {
        List<Model_NhanVien> arr = new ArrayList<>();
        conn = null;
        state = null;
        try {
            conn = ConnectionQLTV.getInstance().getConnection();
            if (ma.equals("")) {
                sql = "Select * from nhanvien";

            } else if (!ma.equals("")) {
                sql = "Select * from nhanvien where HoTen like'" + ma + "%'";
            }
            state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Model_NhanVien temp = new Model_NhanVien();
                temp.setMaNV(rs.getString("MaNV"));
                temp.setHoTen(rs.getString("HoTen"));
                temp.setGioiTinh(rs.getString("GioiTinh"));
                temp.setNgaySinh(rs.getString("NgaySinh"));
                temp.setDiaChi(rs.getString("DiaChi"));
                temp.setDienThoai(rs.getString("DienThoai"));
                temp.setEmail(rs.getString("Email"));
                temp.setChucVu(rs.getString("ChucVu"));
                temp.setNgayVaoLam(rs.getString("NgayVaoLam"));

                arr.add(temp);
            }
            state.close();

        } catch (SQLException ex) {
            Logger.getLogger(NhanVien_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }

    // 1.insert : Thêm độc giả
    public static void ThemMoi(Model_NhanVien nv) {

        pstate = null;

        try {

            conn = ConnectionQLTV.getInstance().getConnection();
            sql = "Insert into nhanvien values(?,?,?,?,?,?,?,?,?)";
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, nv.getMaNV());
            pstate.setString(2, nv.getHoTen());
            pstate.setString(3, nv.getGioiTinh());
            pstate.setString(4, nv.getNgaySinh());
            pstate.setString(5, nv.getDiaChi());
            pstate.setString(6, nv.getDienThoai());
            pstate.setString(7, nv.getEmail());
            pstate.setString(8, nv.getChucVu());
            pstate.setString(9, nv.getNgayVaoLam());

            pstate.executeUpdate();
            pstate.close();

        } catch (SQLException ex) {
            Logger.getLogger(DocGia_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // 2.update : Cập nhật Độc Gia
    public static void CapNhat(Model_NhanVien nv) {

        pstate = null;
        try {
            conn = ConnectionQLTV.getInstance().getConnection();
            sql = "Update nhanvien set HoTen=?,GioiTinh=?,NgaySinh=?,DiaChi=?,DienThoai=?,Email=?,ChucVu=?,NgayVaoLam = ? Where MaNV=?";
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, nv.getHoTen());
            pstate.setString(2, nv.getGioiTinh());
            pstate.setString(3, nv.getNgaySinh());
            pstate.setString(4, nv.getDiaChi());
            pstate.setString(5, nv.getDienThoai());
            pstate.setString(6, nv.getEmail());
            pstate.setString(7, nv.getChucVu());
            pstate.setString(8, nv.getNgayVaoLam());
            pstate.setString(9, nv.getMaNV());

            pstate.executeUpdate();
            pstate.close();

        } catch (SQLException ex) {
            Logger.getLogger(NhanVien_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //4. delete : xóa tài khoản
    public static void Delete(String macu) {

        pstate = null;

        try {
            conn = ConnectionQLTV.getInstance().getConnection();
            pstate = conn.prepareStatement("Delete from nhanvien where MaNV=?");
            pstate.setString(1, macu);
            pstate.executeUpdate();
            pstate.close();

        } catch (SQLException ex) {
            Logger.getLogger(NhanVien_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // 5. check : Kiểm tra dữ liệu PK
    public static boolean KiemTraTrungMa(String manhap, boolean ktThem, String macu) {
        boolean kq = false;

        state = null;
        try {
            conn = ConnectionQLTV.getInstance().getConnection();
            sql = "Select MaNV from nhanvien where MaNV='" + manhap + "'";
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
            Logger.getLogger(NhanVien_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return kq;

    }

    //6 Tìm kiếm theo tên
    public static List<Model_NhanVien> TimKiemTheoTen(String ten) {
        List<Model_NhanVien> arr = new ArrayList<>();
        pstate = null;

        try {
            conn = ConnectionQLTV.getInstance().getConnection();
            sql = "SELECT * FROM nhanvien WHERE HoTen LIKE ?";
            pstate = conn.prepareStatement(sql);
            pstate.setString(1, "%" + ten + "%");
            rs = pstate.executeQuery();

            while (rs.next()) {
                Model_NhanVien temp = new Model_NhanVien();
                temp.setMaNV(rs.getString("MaNV"));
                temp.setHoTen(rs.getString("HoTen"));
                temp.setGioiTinh(rs.getString("GioiTinh"));
                temp.setNgaySinh(rs.getString("NgaySinh"));
                temp.setDiaChi(rs.getString("DiaChi"));
                temp.setEmail(rs.getString("Email"));
                temp.setChucVu(rs.getString("ChucVu"));
                temp.setNgayVaoLam(rs.getString("NgayVaoLam"));
                arr.add(temp);
            }

            pstate.close();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }

}
