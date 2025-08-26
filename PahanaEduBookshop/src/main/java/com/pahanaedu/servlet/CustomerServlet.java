package com.pahanaedu.servlet;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.model.Customer;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4578771978778245700L;
	private CustomerDAO customerDAO;

    public void init() {
        customerDAO = new CustomerDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                listCustomers(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteCustomer(request, response);
                break;
            default:
                listCustomers(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            addCustomer(request, response);
        } else if ("update".equals(action)) {
            updateCustomer(request, response);
        }
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Customer> customers = customerDAO.getAllCustomers();
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("/customers.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accountNumber = customerDAO.generateAccountNumber();
        request.setAttribute("accountNumber", accountNumber);
        request.getRequestDispatcher("/customer-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accountNumber = request.getParameter("id");
        Customer customer = customerDAO.getCustomerByAccountNumber(accountNumber);
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("/customer-form.jsp").forward(request, response);
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        Customer customer = new Customer();
        customer.setAccountNumber(request.getParameter("accountNumber"));
        customer.setName(request.getParameter("name"));
        customer.setAddress(request.getParameter("address"));
        customer.setTelephone(request.getParameter("telephone"));
        customer.setEmail(request.getParameter("email"));
        
        if (customerDAO.addCustomer(customer)) {
            response.sendRedirect("customers?action=list&success=Customer added successfully");
        } else {
            response.sendRedirect("customers?action=list&error=Failed to add customer");
        }
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        Customer customer = new Customer();
        customer.setAccountNumber(request.getParameter("accountNumber"));
        customer.setName(request.getParameter("name"));
        customer.setAddress(request.getParameter("address"));
        customer.setTelephone(request.getParameter("telephone"));
        customer.setEmail(request.getParameter("email"));
        
        if (customerDAO.updateCustomer(customer)) {
            response.sendRedirect("customers?action=list&success=Customer updated successfully");
        } else {
            response.sendRedirect("customers?action=list&error=Failed to update customer");
        }
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String accountNumber = request.getParameter("id");
        
        if (customerDAO.deleteCustomer(accountNumber)) {
            response.sendRedirect("customers?action=list&success=Customer deleted successfully");
        } else {
            response.sendRedirect("customers?action=list&error=Failed to delete customer");
        }
    }
}