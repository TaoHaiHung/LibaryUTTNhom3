/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

/**
 *
 * @author Think Pad
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import model.Model_NhaXuatBan;
import model.Model_TheLoai;

/**
 *
 * @author Think Pad
 */
public class TheLoai_Controller {
    public static Connection conn  =ConnectionQLTV.getInstance().getConnection();
    public static Statement state;
    public static PreparedStatement pstate;
    public static String sql;
    public static ResultSet rs;
    
    // Lấy nguồn : Dùng để lấy nguồn dữ liệu từ CSDL và đưa vào mảng ArrayList
    public static List<Model_TheLoai> LayNguonTL() throws SQLException{
        List<Model_TheLoai> arr= new ArrayList<>();
        conn =null;
        state = null;
        try {
            conn =ConnectionQLTV.getInstance().getConnection();
            sql="Select * from theloai";
            state =conn.createStatement();
            ResultSet rs= state.executeQuery(sql);
            while(rs.next()){
                Model_TheLoai temp= new Model_TheLoai();
                temp.setMaTL(rs.getString("MaTL"));
                temp.setTenTL(rs.getString("TenTL"));
                arr.add(temp);
}
            state.close();
           
            
        } catch (SQLException ex) {
            Logger.getLogger(TheLoai_Controller.class.getName()).log(Level.SEVERE, null, ex); 
        }
        
        
        
        return arr;
    }
    // 1.insert : Thêm độc giả
    public static void ThemMoi(String matl, String tentl){
        
        pstate=null;

        try {
            
            conn =ConnectionQLTV.getInstance().getConnection();
            sql="Insert into theloai values(?,?)";
            pstate=conn.prepareStatement(sql);
            pstate.setString(1, matl);
            pstate.setString(2, tentl);
            pstate.executeUpdate();
            pstate.close(); 
            
            
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBan_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public static String automatl() throws SQLException {
        String mas = "", maht = "";
        conn = ConnectionQLTV.getInstance().getConnection();
        sql = "SELECT MAX(MaTL) FROM theloai";
        state = conn.createStatement();
        ResultSet rs = state.executeQuery(sql);
        if (rs.next()) {
            maht = rs.getString(1);
        }
        if (maht.isEmpty()) {
            mas = "TL001"; // Nếu không có mã thể loại hiện tại, bắt đầu từ mã TL001
        } else {
            // Tách phần số của mã thể loại hiện tại
            String so = maht.substring(2);
            // Chuyển phần số thành số nguyên và tăng giá trị lên 1
            int soMoi = Integer.parseInt(so) + 1;
            // Format số mới để có độ dài 3 chữ số (ví dụ: 001, 010, 100)
            String soMoiFormatted = String.format("%03d", soMoi);
            // Tạo mã thể loại mới bằng cách kết hợp "TL" với số mới đã định dạng
            mas = "TL" + soMoiFormatted;
        }
        return mas;
    }
 
    // 5. check : Kiểm tra dữ liệu PK
    public static boolean KiemTraTrungMaTen(String tennhap){
        boolean kq=false;
        
        state=null;
        try {
            conn =ConnectionQLTV.getInstance().getConnection();
                sql="Select * from theloai where TenTL='"+tennhap+"'";
            state=conn.createStatement();
            rs=state.executeQuery(sql);
            if(rs.next())
                kq=true;
            else 
                kq=false;
            state.close(); 
//        
        } catch (SQLException ex) {
            Logger.getLogger(TheLoai_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return kq;
        
    }
    
//public static List<Model_NhaXuatBan> TimKiemTheoTen(String ten) {
//    List<Model_NhaXuatBan> arr = new ArrayList<>();
//    pstate = null;
//
//    try {
//        conn = ConnectionQLTV.getInstance().getConnection();
//        sql = "SELECT * FROM nhaxuatban WHERE TenNXB LIKE ?";
//        pstate = conn.prepareStatement(sql);
//        pstate.setString(1, "%" + ten + "%");
//        rs = pstate.executeQuery();
//
//        while (rs.next()) {
//            Model_NhaXuatBan temp = new Model_NhaXuatBan();
//                temp.setMaNXB(rs.getString("MaNXB"));
//                temp.setTenNXB(rs.getString("TenNXB"));
//                temp.setDiaChi(rs.getString("DiaChi"));                              
//                temp.setEmail(rs.getString("Email"));  
//                temp.setDienThoai(rs.getString("DienThoai")); 
//                arr.add(temp);
//            
//        }
//
//        pstate.close();
//    } catch (SQLException ex) {
//        Logger.getLogger(NhaXuatBan_Controller.class.getName()).log(Level.SEVERE, null, ex);
//    }
//
//    return arr;
//}
}

