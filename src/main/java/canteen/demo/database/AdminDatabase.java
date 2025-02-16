package canteen.demo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import canteen.demo.entity.Admin;

public class AdminDatabase {
    private DataSource dataSource;

    public AdminDatabase(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Admin findAdminByEmail(String email) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM canteen_admin WHERE admin_email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Admin(
                    rs.getInt("admin_id"),
                    rs.getString("admin_email"),
                    rs.getString("admin_password")
                );
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}
