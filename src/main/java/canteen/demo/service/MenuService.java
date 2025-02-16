package canteen.demo.service;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import canteen.demo.database.MenuDatabase;
import canteen.demo.entity.*;
import java.time.LocalDate;
import java.sql.Date;

public class MenuService {
    private MenuDatabase menuDatabase;
    
    public MenuService(MenuDatabase menuDatabase) {
        this.menuDatabase = menuDatabase;
    }
    
    public List<DailyMenu> getWeeklyMenu() throws SQLException {
        Date today = new Date(System.currentTimeMillis());
        Date sixDaysLater = new Date(today.getTime() + (6 * 24 * 60 * 60 * 1000));
        return getWeeklyMenu(today, sixDaysLater);
    }

    public List<DailyMenu> getWeeklyMenu(Date startDate, Date endDate) throws SQLException {
        LocalDate start = startDate.toLocalDate();
        while (start.getDayOfWeek().getValue() != 1) {
            start = start.minusDays(1);
        }
        
        LocalDate end = start.plusDays(5);
        
        List<DailyMenu> dbMenus = menuDatabase.getWeeklyMenu(
            Date.valueOf(start), 
            Date.valueOf(end)
        );
        
        List<DailyMenu> fullWeekMenu = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            LocalDate currentDate = start.plusDays(i);
            Date sqlCurrentDate = Date.valueOf(currentDate);
            
            DailyMenu dayMenu = dbMenus.stream()
                .filter(m -> m.getMenuDate().equals(sqlCurrentDate))
                .findFirst()
                .orElse(new DailyMenu(sqlCurrentDate));
            
            fullWeekMenu.add(dayMenu);
        }
        
        return fullWeekMenu;
    }

    public void createMenu(DailyMenu menu) throws SQLException {
        menuDatabase.createMenu(menu);
    }
    
    public List<Snack> getAllSnacks() throws SQLException {
        return menuDatabase.getAllSnacks();
    }
    
    public List<Appetizer> getAllAppetizers() throws SQLException {
        return menuDatabase.getAllAppetizers();
    }
    
    public List<Vegetable> getAllVegetables() throws SQLException {
        return menuDatabase.getAllVegetables();
    }
    
    public List<Protein> getAllProteins() throws SQLException {
        return menuDatabase.getAllProteins();
    }
    
    public List<Carbohydrate> getAllCarbohydrates() throws SQLException {
        return menuDatabase.getAllCarbohydrates();
    }
    
    public List<Dessert> getAllDesserts() throws SQLException {
        return menuDatabase.getAllDesserts();
    }
    
    public DailyMenu getMenuById(int menuId) throws SQLException {
        return menuDatabase.getMenuById(menuId);
    }
    
    public void updateMenu(DailyMenu menu) throws SQLException {
        menuDatabase.updateMenu(menu);
    }

    public boolean menuExistsForDate(LocalDate date) throws SQLException {
        return menuDatabase.menuExistsForDate(date);
    }

    public boolean isWeekAlreadyGenerated(LocalDate weekStart) throws SQLException {
        for (int i = 0; i < 6; i++) {
            LocalDate currentDate = weekStart.plusDays(i);
            if (menuDatabase.menuExistsForDate(currentDate)) {
                return true;
            }
        }
        return false;
    }

    public List<LocalDate> getExistingDaysInWeek(LocalDate weekStart) throws SQLException {
        List<LocalDate> existingDays = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            LocalDate currentDate = weekStart.plusDays(i);
            if (menuDatabase.menuExistsForDate(currentDate)) {
                existingDays.add(currentDate);
            }
        }
        return existingDays;
    }
}
