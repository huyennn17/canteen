package canteen.demo.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import canteen.demo.entity.*;
import java.time.LocalDate;

public class MenuDatabase {
    private DataSource dataSource;
    
    public MenuDatabase(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public List<DailyMenu> getWeeklyMenu(Date startDate, Date endDate) throws SQLException {
        List<DailyMenu> menuList = new ArrayList<>();
        String sql = "SELECT dm.daily_menu_id, dm.menu_date, " +
                    "s.snack_id, s.snack_name, " +
                    "a.appetizer_id, a.appetizer_name, " +
                    "v.vegetables_id, v.vegetables_name, " +
                    "p.proteins_id, p.proteins_name, p.proteins_type, " +
                    "c.carb_id, c.carb_name, " +
                    "d.dessert_id, d.dessert_name " +
                    "FROM daily_menu dm " +
                    "LEFT JOIN snacks s ON dm.snack_id = s.snack_id " +
                    "LEFT JOIN appetizer a ON dm.appetizer_id = a.appetizer_id " +
                    "LEFT JOIN vegetables v ON dm.vegetable_id = v.vegetables_id " +
                    "LEFT JOIN proteins p ON dm.protein_id = p.proteins_id " +
                    "LEFT JOIN carbohydrates c ON dm.carb_id = c.carb_id " +
                    "LEFT JOIN dessert d ON dm.dessert_id = d.dessert_id " +
                    "WHERE dm.menu_date BETWEEN ? AND ? " +
                    "ORDER BY dm.menu_date";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DailyMenu menu = new DailyMenu();
                    menu.setDailyMenuId(rs.getInt("daily_menu_id"));
                    menu.setMenuDate(rs.getDate("menu_date"));
                    menu.setSnack(new Snack(rs.getInt("snack_id"), rs.getString("snack_name")));
                    menu.setAppetizer(new Appetizer(rs.getInt("appetizer_id"), rs.getString("appetizer_name")));
                    menu.setVegetable(new Vegetable(rs.getInt("vegetables_id"), rs.getString("vegetables_name")));
                    menu.setProtein(new Protein(rs.getInt("proteins_id"), rs.getString("proteins_name"), rs.getString("proteins_type")));
                    menu.setCarb(new Carbohydrate(rs.getInt("carb_id"), rs.getString("carb_name")));
                    menu.setDessert(new Dessert(rs.getInt("dessert_id"), rs.getString("dessert_name")));
                    
                    menuList.add(menu);
                }
            }
        }
        return menuList;
    }

    public List<DailyMenu> getWeeklyMenu() throws SQLException {
        throw new UnsupportedOperationException("Use getWeeklyMenu(Date, Date) instead");
    }
    
    public void createMenu(DailyMenu menu) throws SQLException {
        String sql = "INSERT INTO daily_menu (menu_date, snack_id, appetizer_id, vegetable_id, protein_id, carb_id, dessert_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, menu.getMenuDate());
            stmt.setInt(2, menu.getSnack().getId());
            stmt.setInt(3, menu.getAppetizer().getId());
            stmt.setInt(4, menu.getVegetable().getId());
            stmt.setInt(5, menu.getProtein().getId());
            stmt.setInt(6, menu.getCarb().getId());
            stmt.setInt(7, menu.getDessert().getId());
            
            stmt.executeUpdate();
        }
    }

    public List<Snack> getAllSnacks() throws SQLException {
        List<Snack> snacks = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT snack_id, snack_name FROM snacks");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                snacks.add(new Snack(
                    rs.getInt("snack_id"),
                    rs.getString("snack_name")
                ));
            }
        }
        return snacks;
    }

    public List<Appetizer> getAllAppetizers() throws SQLException {
        List<Appetizer> appetizers = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT appetizer_id, appetizer_name FROM appetizer");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                appetizers.add(new Appetizer(
                    rs.getInt("appetizer_id"),
                    rs.getString("appetizer_name")
                ));
            }
        }
        return appetizers;
    }

    public List<Vegetable> getAllVegetables() throws SQLException {
        List<Vegetable> vegetables = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT vegetables_id, vegetables_name FROM vegetables");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                vegetables.add(new Vegetable(
                    rs.getInt("vegetables_id"),
                    rs.getString("vegetables_name")
                ));
            }
        }
        return vegetables;
    }

    public List<Protein> getAllProteins() throws SQLException {
        List<Protein> proteins = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT proteins_id, proteins_name, proteins_type FROM proteins");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                proteins.add(new Protein(
                    rs.getInt("proteins_id"),
                    rs.getString("proteins_name"),
                    rs.getString("proteins_type")
                ));
            }
        }
        return proteins;
    }

    public List<Carbohydrate> getAllCarbohydrates() throws SQLException {
        List<Carbohydrate> carbs = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT carb_id, carb_name FROM carbohydrates");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                carbs.add(new Carbohydrate(
                    rs.getInt("carb_id"),
                    rs.getString("carb_name")
                ));
            }
        }
        return carbs;
    }

    public List<Dessert> getAllDesserts() throws SQLException {
        List<Dessert> desserts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT dessert_id, dessert_name FROM dessert");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                desserts.add(new Dessert(
                    rs.getInt("dessert_id"),
                    rs.getString("dessert_name")
                ));
            }
        }
        return desserts;
    }

    public DailyMenu getMenuById(int menuId) throws SQLException {
        DailyMenu menu = null;
        String sql = "SELECT dm.daily_menu_id, dm.menu_date, " +
                    "s.snack_id, s.snack_name, " +
                    "a.appetizer_id, a.appetizer_name, " +
                    "v.vegetables_id, v.vegetables_name, " +
                    "p.proteins_id, p.proteins_name, p.proteins_type, " +
                    "c.carb_id, c.carb_name, " +
                    "d.dessert_id, d.dessert_name " +
                    "FROM daily_menu dm " +
                    "JOIN snacks s ON dm.snack_id = s.snack_id " +
                    "JOIN appetizer a ON dm.appetizer_id = a.appetizer_id " +
                    "JOIN vegetables v ON dm.vegetable_id = v.vegetables_id " +
                    "JOIN proteins p ON dm.protein_id = p.proteins_id " +
                    "JOIN carbohydrates c ON dm.carb_id = c.carb_id " +
                    "JOIN dessert d ON dm.dessert_id = d.dessert_id " +
                    "WHERE dm.daily_menu_id = ?";
                    
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, menuId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    menu = new DailyMenu();
                    menu.setDailyMenuId(rs.getInt("daily_menu_id"));
                    menu.setMenuDate(rs.getDate("menu_date"));
                    menu.setSnack(new Snack(rs.getInt("snack_id"), rs.getString("snack_name")));
                    menu.setAppetizer(new Appetizer(rs.getInt("appetizer_id"), rs.getString("appetizer_name")));
                    menu.setVegetable(new Vegetable(rs.getInt("vegetables_id"), rs.getString("vegetables_name")));
                    menu.setProtein(new Protein(rs.getInt("proteins_id"), rs.getString("proteins_name"), rs.getString("proteins_type")));
                    menu.setCarb(new Carbohydrate(rs.getInt("carb_id"), rs.getString("carb_name")));
                    menu.setDessert(new Dessert(rs.getInt("dessert_id"), rs.getString("dessert_name")));
                }
            }
        }
        return menu;
    }

    public void updateMenu(DailyMenu menu) throws SQLException {
        String sql = "UPDATE daily_menu SET menu_date=?, snack_id=?, appetizer_id=?, " +
                    "vegetable_id=?, protein_id=?, carb_id=?, dessert_id=? WHERE daily_menu_id=?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, menu.getMenuDate());
            stmt.setInt(2, menu.getSnack().getId());
            stmt.setInt(3, menu.getAppetizer().getId());
            stmt.setInt(4, menu.getVegetable().getId());
            stmt.setInt(5, menu.getProtein().getId());
            stmt.setInt(6, menu.getCarb().getId());
            stmt.setInt(7, menu.getDessert().getId());
            stmt.setInt(8, menu.getDailyMenuId());
            
            stmt.executeUpdate();
        }
    }

    public boolean menuExistsForDate(LocalDate date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM daily_menu WHERE menu_date = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(date));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
