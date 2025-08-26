package com.pahanaedu.servlet;

import com.pahanaedu.model.Bill;
import com.pahanaedu.model.Bill.BillItem;
import com.pahanaedu.model.Item;
import com.pahanaedu.model.User;
import com.pahanaedu.service.BillingService;
import com.pahanaedu.service.CustomerService;
import com.pahanaedu.service.ItemService;
import com.pahanaedu.util.SessionUtil;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

@WebServlet("/billing")
public class BillServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2753698822347175145L;
	private BillingService billingService;
    private CustomerService customerService;
    private ItemService itemService;

    public void init() {
        billingService = new BillingService();
        customerService = new CustomerService();
        itemService = new ItemService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "new";
        }
        
        switch (action) {
            case "new":
                showBillingForm(request, response);
                break;
            case "view":
                viewBill(request, response);
                break;
            case "list":
                listBills(request, response);
                break;
            case "print":
                printBill(request, response);
                break;
            default:
                showBillingForm(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("create".equals(action)) {
            createBill(request, response);
        }
    }

    private void showBillingForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String billNumber = billingService.generateBillNumber();
        request.setAttribute("billNumber", billNumber);
        request.setAttribute("customers", customerService.getAllCustomers());
        request.setAttribute("items", itemService.getAllItems());
        request.getRequestDispatcher("/billing.jsp").forward(request, response);
    }

    private void createBill(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            Bill bill = new Bill();
            bill.setBillNumber(request.getParameter("billNumber"));
            bill.setAccountNumber(request.getParameter("accountNumber"));
            bill.setBillDate(new Date(System.currentTimeMillis()));
            
            // Get discount
            String discountStr = request.getParameter("discount");
            BigDecimal discount = discountStr != null && !discountStr.isEmpty() 
                ? new BigDecimal(discountStr) : BigDecimal.ZERO;
            bill.setDiscount(discount);
            
            // Get user from session
            User user = SessionUtil.getCurrentUser(request);
            bill.setCreatedBy(user.getUserId());
            
            // Process bill items
            String[] itemIds = request.getParameterValues("itemId");
            String[] quantities = request.getParameterValues("quantity");
            
            if (itemIds != null && quantities != null) {
                for (int i = 0; i < itemIds.length; i++) {
                    if (!itemIds[i].isEmpty() && !quantities[i].isEmpty()) {
                        int itemId = Integer.parseInt(itemIds[i]);
                        int quantity = Integer.parseInt(quantities[i]);
                        
                        Item item = itemService.getItemById(itemId);
                        if (item != null) {
                            BillItem billItem = new BillItem(itemId, quantity, item.getPrice());
                            bill.addBillItem(billItem);
                        }
                    }
                }
            }
            
            bill.calculateTotal();
            
            if (billingService.createBill(bill)) {
                response.sendRedirect("billing?action=view&billNumber=" + bill.getBillNumber());
            } else {
                response.sendRedirect("billing?action=new&error=Failed to create bill");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("billing?action=new&error=Error creating bill");
        }
    }

    private void viewBill(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String billNumber = request.getParameter("billNumber");
        Bill bill = billingService.getBillByNumber(billNumber);
        request.setAttribute("bill", bill);
        request.getRequestDispatcher("/bill-view.jsp").forward(request, response);
    }

    private void listBills(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setAttribute("bills", billingService.getAllBills());
        request.getRequestDispatcher("/bill-list.jsp").forward(request, response);
    }

    private void printBill(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String billNumber = request.getParameter("billNumber");
        Bill bill = billingService.getBillByNumber(billNumber);
        request.setAttribute("bill", bill);
        request.getRequestDispatcher("/bill-print.jsp").forward(request, response);
    }
}