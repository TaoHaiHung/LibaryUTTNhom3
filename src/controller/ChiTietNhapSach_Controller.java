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
import model.Model_ChiTietNhapSach;

/**
 *
 * @author Administrator
 */
public class ChiTietNhapSach_Controller {

    public static Connection conn = ConnectionQLTV.getInstance().getConnection();
    public static Statement state;
    public static PreparedStatement pstate;
    public static String sql;
    public static ResultSet rs;
    
    public static List<Model_ChiTietNhapSach> LayNguonCTNS(String ma) throws SQLException {
        List<Model_ChiTietNhapSach> arr = new ArrayList<>();
        conn = null;
        state = null;
        try {
            conn = ConnectionQLTV.getInstance().getConnection();
            if(ma.equals("")){
                sql ="Select MaNhap, sach.MaSach, sach.TenSach ,chitietnhapsach.SoLuong from chitietnhapsach inner join sach on chitietnhapsach.MaSach= sach.MaSach  ";
            }
            if(!ma.equals("")){
                sql="Select MaNhap, sach.MaSach, sach.TenSach ,chitietnhapsach.SoLuong from chitietnhapsach inner join sach on chitietnhapsach.MaSach= sach.MaSach  where MaNhap='"+ma+"'";
            }
            

            

            state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Model_ChiTietNhapSach temp = new Model_ChiTietNhapSach();
                temp.setMaNhap(rs.getString("MaNhap"));
                temp.setMaSach(rs.getString("MaSach"));
                temp.setTenSach(rs.getString("TenSach"));
                temp.setSoLuong(rs.getString("SoLuong"));
                arr.add(temp);
            }
            state.close();

        } catch (SQLException ex) {
            Logger.getLogger(ChiTietNhapSach_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }


    public static void ThemMoi(String sql) {
        pstate = null;
        try {
            conn = ConnectionQLTV.getInstance().getConnection();
            pstate = conn.prepareStatement(sql);
            pstate.executeUpdate();
            pstate.close();

        } catch (SQLException ex) {
            Logger.getLogger(ChiTietNhapSach_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
