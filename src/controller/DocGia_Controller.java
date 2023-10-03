/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;


import Connection.ConnectionQLTV;
import static Connection.ConnectionQLTV.DB_URL;
import static Connection.ConnectionQLTV.PASSWORD;
import static Connection.ConnectionQLTV.USER_NAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Model_DocGia;



/**
 *
 * @author Think Pad
 */
import java.sql.SQLException;import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Think Pad
 */
public class DocGia_Controller {
    public static Connection conn  =ConnectionQLTV.getInstance().getConnection();
    public static Statement state;
    public static PreparedStatement pstate;
    public static String sql;
    public static ResultSet rs;
    
    // Lấy nguồn : Dùng để lấy nguồn dữ liệu từ CSDL và đưa vào mảng ArrayList
    public static List<Model_DocGia> LayNguonDG(String sMaDG) throws SQLException{
        List<Model_DocGia> arr= new ArrayList<>();
        conn =null;
        state = null;
        try {
            conn =ConnectionQLTV.getInstance().getConnection();
            sql="Select * from docgia";
            if (!sMaDG.equals(""))
                sql = sql + " Where MaDG ='" + sMaDG + "'";
            
            state =conn.createStatement();
            ResultSet rs= state.executeQuery(sql);
            while(rs.next()){
                Model_DocGia temp= new Model_DocGia();
                temp.setMaDG(rs.getString("MaDG"));
                temp.setTenDG(rs.getString("TenDG"));
                temp.setNgaySinh(rs.getString("NgaySinh"));
                temp.setGioiTinh(rs.getString("GioiTinh"));
                temp.setDienThoai(rs.getString("DienThoai"));                
                temp.setEmail(rs.getString("Email"));  
                temp.setHanDung(rs.getString("HanDung"));
                arr.add(temp);
}
            state.close();
           
            
        } catch (SQLException ex) {
            Logger.getLogger(DocGia_Controller.class.getName()).log(Level.SEVERE, null, ex); 
        }
        
        
        
        return arr;
    }
    // 1.insert : Thêm độc giả
    public static void ThemMoi(Model_DocGia dg){
        
        pstate=null;

        try {
            System.out.println("iuahfiu");
            conn =ConnectionQLTV.getInstance().getConnection();
            sql="Insert into docgia values(?,?,?,?,?,?,?)";
            pstate=conn.prepareStatement(sql);
            pstate.setString(1, dg.getMaDG());
            pstate.setString(2, dg.getTenDG());
            pstate.setString(3, dg.getNgaySinh());
            pstate.setString(4, dg.getGioiTinh());            
            pstate.setString(5, dg.getDienThoai());
            pstate.setString(6, dg.getEmail());
            pstate.setString(7, dg.getHanDung());
            pstate.executeUpdate();
            pstate.close(); 
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DocGia_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    // 2.update : Cập nhật Độc Gia
    public static void CapNhat(Model_DocGia dg){
        
        
        pstate=null;
        try {
            conn =ConnectionQLTV.getInstance().getConnection();
            sql="Update docgia set TenDG=?,NgaySinh=?,GioiTinh=?,DienThoai=?,Email=?,HanDung=? Where MaDG=?";
            pstate=conn.prepareStatement(sql);
            pstate.setString(1, dg.getTenDG());
            pstate.setString(2, dg.getNgaySinh());
            pstate.setString(3, dg.getGioiTinh());            
            pstate.setString(4, dg.getDienThoai());
            pstate.setString(5,dg.getEmail());
            pstate.setString(6,dg.getHanDung());   
            pstate.setString(7, dg.getMaDG());
            pstate.execute();
            pstate.close(); 
            
        } catch (SQLException ex) {
            Logger.getLogger(DocGia_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //4. delete : xóa tài khoản
    public static void Delete(String macu){
        
        pstate=null;
        //Connection conn = ConnectionTaoHaiHung.getInstance().getConnection();
        try {
            conn =ConnectionQLTV.getInstance().getConnection();
            pstate = conn.prepareStatement("Delete from docgia where MaDG=?");
            pstate.setString(1, macu);
            pstate.executeUpdate();
            pstate.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DocGia_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // 5. check : Kiểm tra dữ liệu PK
    public static boolean KiemTraTrungMa(String manhap, boolean ktThem, String macu){
        boolean kq=false;
        
        state=null;
        try {
            conn =ConnectionQLTV.getInstance().getConnection();
                sql="Select MaDG from docgia where MaDG='"+manhap+"'";
            state=conn.createStatement();
            rs=state.executeQuery(sql);
            if(rs.next())
                kq=true;
            else 
                kq=false;
            state.close(); 
//        
        } catch (SQLException ex) {
            Logger.getLogger(DocGia_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return kq;
        
    }
    
public static List<Model_DocGia> TimKiemTheoTen(String ten) {
    List<Model_DocGia> arr = new ArrayList<>();
    pstate = null;

    try {
        conn = ConnectionQLTV.getInstance().getConnection();
        sql = "SELECT * FROM docgia WHERE TenDG LIKE ?";
        pstate = conn.prepareStatement(sql);
        pstate.setString(1, "%" + ten + "%");
        rs = pstate.executeQuery();

        while (rs.next()) {
            Model_DocGia temp = new Model_DocGia();
            temp.setMaDG(rs.getString("MaDG"));
            temp.setTenDG(rs.getString("TenDG"));
            temp.setNgaySinh(rs.getString("NgaySinh"));
            temp.setGioiTinh(rs.getString("GioiTinh"));
            temp.setDienThoai(rs.getString("DienThoai"));
            temp.setEmail(rs.getString("Email"));
            temp.setHanDung(rs.getString("HanDung"));
            arr.add(temp);
        }

        pstate.close();
    } catch (SQLException ex) {
        Logger.getLogger(DocGia_Controller.class.getName()).log(Level.SEVERE, null, ex);
    }

    return arr;
}

    

        
        
        
    
   
    
    
}
