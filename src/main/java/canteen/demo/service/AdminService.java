package canteen.demo.service;

import canteen.demo.database.AdminDatabase;
import canteen.demo.entity.Admin;

public class AdminService {
    private AdminDatabase adminDatabase;
    
    public AdminService(AdminDatabase adminDatabase) {
        this.adminDatabase = adminDatabase;
    }
    
    public Admin validateLogin(String email, String password) throws Exception {
        Admin admin = adminDatabase.findAdminByEmail(email);
        if (admin != null && admin.getAdminPassword().equals(password)) {
            return admin;
        }
        return null;
    }
}
