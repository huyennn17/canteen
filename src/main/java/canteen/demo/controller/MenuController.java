package canteen.demo.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Random;
import java.time.format.DateTimeFormatter;
import canteen.demo.database.MenuDatabase;
import canteen.demo.entity.*;
import canteen.demo.service.MenuService;

public class MenuController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Resource(name="jdbc/canteen")
    private DataSource dataSource;
    private MenuService menuService;
    
    @Override
    public void init() throws ServletException {
        MenuDatabase menuDb = new MenuDatabase(dataSource);
        menuService = new MenuService(menuDb);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String command = request.getParameter("command");
            if (command == null) command = "VIEW";
            
            switch (command) {
                case "CHECK_WEEK":
                    checkWeek(request, response);
                    break;
                case "VIEW":
                    viewMenu(request, response);
                    break;
                case "LOAD":
                    loadMenu(request, response);
                    break;
                default:
                    viewMenu(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String command = request.getParameter("command");
            System.out.println("POST Command received: " + command); // Debug log
            
            if (command == null || command.trim().isEmpty()) {
                throw new ServletException("Command parameter is required");
            }
            
            switch (command) {
                case "CREATE":
                    createMenu(request, response);
                    break;
                case "UPDATE":
                    updateMenu(request, response);
                    break;
                case "GENERATE_WEEK":
                    generateWeeklyMenu(request, response);
                    break;
                default:
                    throw new ServletException("Invalid command: " + command);
            }
        } catch (Exception e) {
            request.getSession().setAttribute("NOTIFICATION_MESSAGE", "Error: " + e.getMessage());
            request.getSession().setAttribute("NOTIFICATION_TYPE", "danger");
            response.sendRedirect(request.getContextPath() + "/menu?command=VIEW");
        }
    }

    private void loadMenu(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        int menuId = Integer.parseInt(request.getParameter("menuId"));
        DailyMenu menu = menuService.getMenuById(menuId);
        
        request.setAttribute("MENU_TO_EDIT", menu);
        request.setAttribute("snacksList", menuService.getAllSnacks());
        request.setAttribute("appetizersList", menuService.getAllAppetizers());
        request.setAttribute("vegetablesList", menuService.getAllVegetables());
        request.setAttribute("proteinsList", menuService.getAllProteins());
        request.setAttribute("carbsList", menuService.getAllCarbohydrates());
        request.setAttribute("dessertsList", menuService.getAllDesserts());
        
        request.getRequestDispatcher("/admin/edit-menu.jsp").forward(request, response);
    }

    private void createMenu(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        Date menuDate = Date.valueOf(request.getParameter("menuDate"));
        
        if (menuService.menuExistsForDate(menuDate.toLocalDate())) {
            request.getSession().setAttribute("NOTIFICATION_MESSAGE", "A menu already exists for this date");
            request.getSession().setAttribute("NOTIFICATION_TYPE", "warning");
            response.sendRedirect("menu?command=VIEW");
            return;
        }
        
        Snack snack = new Snack(Integer.parseInt(request.getParameter("snackId")), "");
        Appetizer appetizer = new Appetizer(Integer.parseInt(request.getParameter("appetizerId")), "");
        Vegetable vegetable = new Vegetable(Integer.parseInt(request.getParameter("vegetableId")), "");
        Protein protein = new Protein(Integer.parseInt(request.getParameter("proteinId")), "", "");
        Carbohydrate carb = new Carbohydrate(Integer.parseInt(request.getParameter("carbId")), "");
        Dessert dessert = new Dessert(Integer.parseInt(request.getParameter("dessertId")), "");
        
        DailyMenu menu = new DailyMenu(menuDate, snack, appetizer, vegetable, protein, carb, dessert);
        menuService.createMenu(menu);
        
        request.getSession().setAttribute("NOTIFICATION_MESSAGE", "New menu created successfully!");
        request.getSession().setAttribute("NOTIFICATION_TYPE", "success");
        response.sendRedirect("menu?command=VIEW");
    }

    private void updateMenu(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        int menuId = Integer.parseInt(request.getParameter("menuId"));
        Date menuDate = Date.valueOf(request.getParameter("menuDate"));
        
        Snack snack = new Snack(Integer.parseInt(request.getParameter("snackId")), "");
        Appetizer appetizer = new Appetizer(Integer.parseInt(request.getParameter("appetizerId")), "");
        Vegetable vegetable = new Vegetable(Integer.parseInt(request.getParameter("vegetableId")), "");
        Protein protein = new Protein(Integer.parseInt(request.getParameter("proteinId")), "", "");
        Carbohydrate carb = new Carbohydrate(Integer.parseInt(request.getParameter("carbId")), "");
        Dessert dessert = new Dessert(Integer.parseInt(request.getParameter("dessertId")), "");
        
        DailyMenu menu = new DailyMenu(menuDate, snack, appetizer, vegetable, protein, carb, dessert);
        menu.setDailyMenuId(menuId);
        menuService.updateMenu(menu);
        
        request.getSession().setAttribute("NOTIFICATION_MESSAGE", "Menu updated successfully!");
        request.getSession().setAttribute("NOTIFICATION_TYPE", "success");
        response.sendRedirect("menu?command=VIEW");
    }

    private void viewMenu(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        String weekStartStr = request.getParameter("weekStart");
        LocalDate weekStart;
        
        if (weekStartStr != null && !weekStartStr.isEmpty()) {
            weekStart = LocalDate.parse(weekStartStr);
            while (weekStart.getDayOfWeek().getValue() != 1) {
                weekStart = weekStart.minusDays(1);
            }
        } else {
            weekStart = LocalDate.now();
            while (weekStart.getDayOfWeek().getValue() != 1) {
                weekStart = weekStart.plusDays(1);
            }
        }
        
        LocalDate weekEnd = weekStart.plusDays(5);
        
        java.sql.Date sqlWeekStart = java.sql.Date.valueOf(weekStart);
        java.sql.Date sqlWeekEnd = java.sql.Date.valueOf(weekEnd);
        
        List<DailyMenu> weeklyMenu = menuService.getWeeklyMenu(sqlWeekStart, sqlWeekEnd);
        
        for (int i = 0; i < 6; i++) {
            LocalDate currentDate = weekStart.plusDays(i);
            boolean hasMenu = weeklyMenu.stream()
                .anyMatch(m -> m.getMenuDate().toLocalDate().equals(currentDate));
                
            if (!hasMenu) {
                DailyMenu emptyMenu = new DailyMenu();
                emptyMenu.setMenuDate(Date.valueOf(currentDate));
                weeklyMenu.add(i, emptyMenu);
            }
        }
        
        request.setAttribute("weeklyMenu", weeklyMenu);
        request.setAttribute("currentWeekStart", sqlWeekStart);
        request.setAttribute("currentWeekEnd", sqlWeekEnd);
        request.setAttribute("currentWeekStartStr", weekStart.toString());
        
        request.setAttribute("snacksList", menuService.getAllSnacks());
        request.setAttribute("appetizersList", menuService.getAllAppetizers());
        request.setAttribute("vegetablesList", menuService.getAllVegetables());
        request.setAttribute("proteinsList", menuService.getAllProteins());
        request.setAttribute("carbsList", menuService.getAllCarbohydrates());
        request.setAttribute("dessertsList", menuService.getAllDesserts());
        
        request.getRequestDispatcher("/view-menu.jsp").forward(request, response);
    }

    private void generateWeeklyMenu(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        String weekStartStr = request.getParameter("selectedWeekStart");
        System.out.println("Generating menu for week: " + weekStartStr); // Debug log

        if (weekStartStr == null || weekStartStr.trim().isEmpty()) {
            throw new Exception("Week start date is required");
        }

        try {
            LocalDate weekStart = LocalDate.parse(weekStartStr);
            
            // Ensure we start from Monday
            while (weekStart.getDayOfWeek().getValue() != 1) {
                weekStart = weekStart.minusDays(1);
            }

            // Generate menus for Monday through Saturday
            for (int i = 0; i < 6; i++) {
                LocalDate currentDate = weekStart.plusDays(i);
                System.out.println("Generating menu for: " + currentDate); // Debug log
                
                if (!menuService.menuExistsForDate(currentDate)) {
                    DailyMenu menu = createRandomMenu(currentDate);
                    menuService.createMenu(menu);
                }
            }

            request.getSession().setAttribute("NOTIFICATION_MESSAGE", 
                "Weekly menu generated successfully for week starting " + 
                weekStart.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
            request.getSession().setAttribute("NOTIFICATION_TYPE", "success");
            
        } catch (Exception e) {
            throw new Exception("Failed to generate weekly menu: " + e.getMessage());
        }
        
        // Always redirect back to view with the selected week
        response.sendRedirect(request.getContextPath() + "/menu?command=VIEW&weekStart=" + weekStartStr);
    }

    private DailyMenu createRandomMenu(LocalDate date) throws SQLException {
        Random random = new Random();
        
        // Get all menu items
        List<Snack> snacks = menuService.getAllSnacks();
        List<Appetizer> appetizers = menuService.getAllAppetizers();
        List<Vegetable> vegetables = menuService.getAllVegetables();
        List<Protein> proteins = menuService.getAllProteins();
        List<Carbohydrate> carbs = menuService.getAllCarbohydrates();
        List<Dessert> desserts = menuService.getAllDesserts();
        
        // Validate lists are not empty
        if (snacks.isEmpty() || appetizers.isEmpty() || vegetables.isEmpty() || 
            proteins.isEmpty() || carbs.isEmpty() || desserts.isEmpty()) {
            throw new SQLException("Cannot generate menu: Some menu items are missing from the database");
        }
        
        // Create new menu with random items
        DailyMenu menu = new DailyMenu();
        menu.setMenuDate(Date.valueOf(date));
        menu.setSnack(snacks.get(random.nextInt(snacks.size())));
        menu.setAppetizer(appetizers.get(random.nextInt(appetizers.size())));
        menu.setVegetable(vegetables.get(random.nextInt(vegetables.size())));
        menu.setProtein(proteins.get(random.nextInt(proteins.size())));
        menu.setCarb(carbs.get(random.nextInt(carbs.size())));
        menu.setDessert(desserts.get(random.nextInt(desserts.size())));
        
        return menu;
    }

    private void checkWeek(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        String weekStartStr = request.getParameter("weekStart");
        LocalDate weekStart = LocalDate.parse(weekStartStr);
        
        List<LocalDate> existingDays = menuService.getExistingDaysInWeek(weekStart);
        if (!existingDays.isEmpty()) {
            StringBuilder message = new StringBuilder("Cannot generate menu. The following dates already have menus: ");
            for (int i = 0; i < existingDays.size(); i++) {
                if (i > 0) {
                    message.append(", ");
                }
                message.append(existingDays.get(i).format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
            }
            
            request.getSession().setAttribute("NOTIFICATION_MESSAGE", message.toString());
            request.getSession().setAttribute("NOTIFICATION_TYPE", "warning");
            response.sendRedirect("menu?command=VIEW");
        } else {
            response.sendRedirect("menu?command=CHECK_WEEK&weekStart=" + weekStartStr);
        }
    }
}