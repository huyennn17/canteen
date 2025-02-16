package canteen.demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


import canteen.demo.service.PriceService;
import canteen.demo.entity.ConfigPrice;

public class PriceController extends HttpServlet {
    @Resource(name="jdbc/canteen")
    private DataSource dataSource;
    private PriceService priceService;
    
    @Override
    public void init() throws ServletException {
        priceService = new PriceService(dataSource);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String command = request.getParameter("command");
            if (command == null) command = "VIEW";
            
            List<ConfigPrice> currentPrices = priceService.getCurrentPrices();
            request.setAttribute("currentPrices", currentPrices);

            Map<String, List<ConfigPrice>> priceHistory = new HashMap<>();
            priceHistory.put("LUNCH", priceService.getPriceHistory("LUNCH"));
            priceHistory.put("SNACK", priceService.getPriceHistory("SNACK"));
            request.setAttribute("priceHistory", priceHistory);
            
            request.getRequestDispatcher("/admin/price-config.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String command = request.getParameter("command");
            
            if ("UPDATE".equals(command)) {
                String mealType = request.getParameter("mealType");
                BigDecimal price = new BigDecimal(request.getParameter("price"));
                Timestamp effectiveDateTime = new Timestamp(System.currentTimeMillis());
                
                ConfigPrice config = new ConfigPrice(mealType, price, effectiveDateTime);
                priceService.updatePrice(config);
                
                request.getSession().setAttribute("PRICE_MESSAGE", "Price updated successfully!");
                request.getSession().setAttribute("PRICE_STATUS", "success");
            }
            
            response.sendRedirect(request.getContextPath() + "/prices?command=VIEW");
        } catch (Exception e) {
            request.getSession().setAttribute("PRICE_MESSAGE", "Error: " + e.getMessage());
            request.getSession().setAttribute("PRICE_STATUS", "danger");
            response.sendRedirect(request.getContextPath() + "/prices?command=VIEW");
        }
    }
}
