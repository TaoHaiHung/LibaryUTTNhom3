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
import model.Model_NhaXuatBan;

/**
 *
 * @author Think Pad
 */
public class NhaXuatBan_Controller {
    public static Connection conn  =ConnectionQLTV.getInstance().getConnection();
    public static Statement state;
    public static PreparedStatement pstate;
    public static String sql;
    public static ResultSet rs;
    
    // Lấy nguồn : Dùng để lấy nguồn dữ liệu từ CSDL và đưa vào mảng ArrayList
    public static List<Model_NhaXuatBan> LayNguonNXB(String ma) throws SQLException{
        List<Model_NhaXuatBan> arr= new ArrayList<>();
        conn =null;
        state = null;
        try {
            conn =ConnectionQLTV.getInstance().getConnection();
            if(ma.equals("")){
                sql="Select * from nhaxuatban";
                
            }
            else
                sql="Select * from nhaxuatban where TenNXB like '"+ma+"%'";
                
            
            state =conn.createStatement();
            ResultSet rs= state.executeQuery(sql);
            while(rs.next()){
                Model_NhaXuatBan temp= new Model_NhaXuatBan();
                temp.setMaNXB(rs.getString("MaNXB"));
                temp.setTenNXB(rs.getString("TenNXB"));
                temp.setDiaChi(rs.getString("DiaChi"));                              
                temp.setEmail(rs.getString("Email"));  
                temp.setDienThoai(rs.getString("DienThoai")); 
                arr.add(temp);
}
            state.close();
           
            
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBan_Controller.class.getName()).log(Level.SEVERE, null, ex); 
        }
        
        
        
        return arr;
    }
    // 1.insert : Thêm độc giả
    public static void ThemMoi(Model_NhaXuatBan nxb){
        
        pstate=null;

        try {
            
            conn =ConnectionQLTV.getInstance().getConnection();
            sql="Insert into nhaxuatban values(?,?,?,?,?)";
            pstate=conn.prepareStatement(sql);
            pstate.setString(1, nxb.getMaNXB());
            pstate.setString(2, nxb.getTenNXB());
            pstate.setString(3, nxb.getDiaChi());
            pstate.setString(4, nxb.getEmail());            
            pstate.setString(5, nxb.getDienThoai());
            
            pstate.executeUpdate();
            pstate.close(); 
            
            
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBan_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    // 2.update : Cập nhật 
    public static void CapNhat(Model_NhaXuatBan nxb){
        
        
        pstate=null;
        try {
            conn =ConnectionQLTV.getInstance().getConnection();
            sql="Update nhaxuatban set TenNXB=?,DiaChi=?,Email=?,DienThoai=? Where MaNXB=?";
            pstate=conn.prepareStatement(sql);
            pstate.setString(1, nxb.getTenNXB());
            pstate.setString(2, nxb.getDiaChi());
            pstate.setString(3, nxb.getEmail());            
            pstate.setString(4, nxb.getDienThoai());
            pstate.setString(5,nxb.getMaNXB());
            
            pstate.execute();
            pstate.close(); 
            
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBan_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //4. delete : xóa tài khoản
    public static void Delete(String macu){
        
        pstate=null;
        //Connection conn = ConnectionTaoHaiHung.getInstance().getConnection();
        try {
            conn =ConnectionQLTV.getInstance().getConnection();
            pstate = conn.prepareStatement("Delete from nhaxuatban where MaNXB=?");
            pstate.setString(1, macu);
            pstate.executeUpdate();
            pstate.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBan_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // 5. check : Kiểm tra dữ liệu PK
    public static boolean KiemTraTrungMa(String manhap, boolean ktThem, String macu){
        boolean kq=false;
        
        state=null;
        try {
            conn =ConnectionQLTV.getInstance().getConnection();
                sql="Select MaNXB from nhaxuatban where MaNXB='"+manhap+"'";
            state=conn.createStatement();
            rs=state.executeQuery(sql);
            if(rs.next())
                kq=true;
            else 
                kq=false;
            state.close(); 
//        
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBan_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return kq;
        
    }
    
public static List<Model_NhaXuatBan> TimKiemTheoTen(String ten) {
    List<Model_NhaXuatBan> arr = new ArrayList<>();
    pstate = null;

    try {
        conn = ConnectionQLTV.getInstance().getConnection();
        sql = "SELECT * FROM nhaxuatban WHERE TenNXB LIKE ?";
        pstate = conn.prepareStatement(sql);
        pstate.setString(1, "%" + ten + "%");
        rs = pstate.executeQuery();

        while (rs.next()) {
            Model_NhaXuatBan temp = new Model_NhaXuatBan();
                temp.setMaNXB(rs.getString("MaNXB"));
                temp.setTenNXB(rs.getString("TenNXB"));
                temp.setDiaChi(rs.getString("DiaChi"));                              
                temp.setEmail(rs.getString("Email"));  
                temp.setDienThoai(rs.getString("DienThoai")); 
                arr.add(temp);
            
        }

        pstate.close();
    } catch (SQLException ex) {
        Logger.getLogger(NhaXuatBan_Controller.class.getName()).log(Level.SEVERE, null, ex);
    }

    return arr;
}
}
