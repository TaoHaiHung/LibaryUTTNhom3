
package controler;

import Connection.ConnectionQLTV;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Model_TaiKhoan;

public class controler_QLTaiKhoan {
    public static void getData123(DefaultTableModel defaultTableModel, String a) throws SQLException{
        String sql = "select * from taikhoan where " + a;
        Connection conn = ConnectionQLTV.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        List<Model_TaiKhoan> listTK = new ArrayList<>();
        while(rs.next()){
           Model_TaiKhoan tk = new Model_TaiKhoan(rs.getString(1), rs.getString(2), rs.getString(3));
                        
            listTK.add(tk);
        }
        String[] columnNames = {"Tên Tài Khoản", "Mật Khẩu", "Mã Nhân Vien"};
        defaultTableModel = new DefaultTableModel(columnNames, 0);
        
        for (Model_TaiKhoan tk : listTK) {
            Object[] rowData = {tk.getTaiKhoan(), tk.getMatKhau(), tk.getMaNV()};
            defaultTableModel.addRow(rowData);
        }
    }
}
