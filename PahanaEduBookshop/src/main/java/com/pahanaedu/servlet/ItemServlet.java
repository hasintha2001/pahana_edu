package com.pahanaedu.servlet;

import com.pahanaedu.model.Item;
import com.pahanaedu.service.ItemService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/items")
public class ItemServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5916779640684833925L;
	private ItemService itemService;

    public void init() {
        itemService = new ItemService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                listItems(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteItem(request, response);
                break;
            case "lowstock":
                showLowStock(request, response);
                break;
            default:
                listItems(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            addItem(request, response);
        } else if ("update".equals(action)) {
            updateItem(request, response);
        }
    }

    private void listItems(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Item> items = itemService.getAllItems();
        request.setAttribute("items", items);
        request.getRequestDispatcher("/items.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String itemCode = itemService.generateItemCode();
        List<String> categories = itemService.getAllCategories();
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/item-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String itemCode = request.getParameter("code");
        Item item = itemService.getItemByCode(itemCode);
        List<String> categories = itemService.getAllCategories();
        request.setAttribute("item", item);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/item-form.jsp").forward(request, response);
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        Item item = new Item();
        item.setItemCode(request.getParameter("itemCode"));
        item.setItemName(request.getParameter("itemName"));
        item.setCategory(request.getParameter("category"));
        item.setAuthor(request.getParameter("author"));
        item.setPublisher(request.getParameter("publisher"));
        item.setPrice(new BigDecimal(request.getParameter("price")));
        item.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        item.setReorderLevel(Integer.parseInt(request.getParameter("reorderLevel")));
        
        if (itemService.addItem(item)) {
            response.sendRedirect("items?action=list&success=Item added successfully");
        } else {
            response.sendRedirect("items?action=list&error=Failed to add item");
        }
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        Item item = new Item();
        item.setItemCode(request.getParameter("itemCode"));
        item.setItemName(request.getParameter("itemName"));
        item.setCategory(request.getParameter("category"));
        item.setAuthor(request.getParameter("author"));
        item.setPublisher(request.getParameter("publisher"));
        item.setPrice(new BigDecimal(request.getParameter("price")));
        item.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        item.setReorderLevel(Integer.parseInt(request.getParameter("reorderLevel")));
        
        if (itemService.updateItem(item)) {
            response.sendRedirect("items?action=list&success=Item updated successfully");
        } else {
            response.sendRedirect("items?action=list&error=Failed to update item");
        }
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String itemCode = request.getParameter("code");
        
        if (itemService.deleteItem(itemCode)) {
            response.sendRedirect("items?action=list&success=Item deleted successfully");
        } else {
            response.sendRedirect("items?action=list&error=Failed to delete item");
        }
    }

    private void showLowStock(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Item> items = itemService.getLowStockItems();
        request.setAttribute("items", items);
        request.setAttribute("lowStockView", true);
        request.getRequestDispatcher("/items.jsp").forward(request, response);
    }
}