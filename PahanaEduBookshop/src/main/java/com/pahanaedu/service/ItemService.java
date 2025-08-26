package com.pahanaedu.service;

import com.pahanaedu.dao.ItemDAO;
import com.pahanaedu.model.Item;
import java.math.BigDecimal;
import java.util.List;

public class ItemService {
    private ItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    public boolean addItem(Item item) {
        if (!validateItem(item)) {
            return false;
        }
        
        // Generate item code if not provided
        if (item.getItemCode() == null || item.getItemCode().isEmpty()) {
            item.setItemCode(itemDAO.generateItemCode());
        }
        
        return itemDAO.addItem(item);
    }

    public boolean updateItem(Item item) {
        if (!validateItem(item)) {
            return false;
        }
        return itemDAO.updateItem(item);
    }

    public boolean deleteItem(String itemCode) {
        if (itemCode == null || itemCode.isEmpty()) {
            return false;
        }
        return itemDAO.deleteItem(itemCode);
    }

    public Item getItemByCode(String itemCode) {
        if (itemCode == null || itemCode.isEmpty()) {
            return null;
        }
        return itemDAO.getItemByCode(itemCode);
    }

    public Item getItemById(int itemId) {
        return itemDAO.getItemById(itemId);
    }

    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }

    public List<Item> getLowStockItems() {
        return itemDAO.getLowStockItems();
    }

    public List<Item> getItemsByCategory(String category) {
        return itemDAO.getItemsByCategory(category);
    }

    public boolean updateStock(int itemId, int quantity) {
        if (quantity < 0) {
            return false;
        }
        return itemDAO.updateStock(itemId, quantity);
    }

    public boolean checkStock(int itemId, int requiredQuantity) {
        Item item = itemDAO.getItemById(itemId);
        if (item == null) {
            return false;
        }
        return item.getQuantity() >= requiredQuantity;
    }

    private boolean validateItem(Item item) {
        if (item == null) {
            return false;
        }
        
        // Validate required fields
        if (item.getItemName() == null || item.getItemName().trim().isEmpty()) {
            return false;
        }
        
        // Validate price
        if (item.getPrice() == null || item.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        
        // Validate quantity
        if (item.getQuantity() < 0) {
            return false;
        }
        
        return true;
    }

    public String generateItemCode() {
        return itemDAO.generateItemCode();
    }

    public List<String> getAllCategories() {
        return itemDAO.getAllCategories();
    }
}