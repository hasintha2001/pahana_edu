package com.pahanaedu.dao;

import com.pahanaedu.model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private Connection connection;

    public ItemDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean addItem(Item item) {
        String query = "INSERT INTO items (item_code, item_name, category, author, publisher, price, quantity, reorder_level) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, item.getItemCode());
            pstmt.setString(2, item.getItemName());
            pstmt.setString(3, item.getCategory());
            pstmt.setString(4, item.getAuthor());
            pstmt.setString(5, item.getPublisher());
            pstmt.setBigDecimal(6, item.getPrice());
            pstmt.setInt(7, item.getQuantity());
            pstmt.setInt(8, item.getReorderLevel());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateItem(Item item) {
        String query = "UPDATE items SET item_name=?, category=?, author=?, publisher=?, " +
                      "price=?, quantity=?, reorder_level=? WHERE item_code=?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getCategory());
            pstmt.setString(3, item.getAuthor());
            pstmt.setString(4, item.getPublisher());
            pstmt.setBigDecimal(5, item.getPrice());
            pstmt.setInt(6, item.getQuantity());
            pstmt.setInt(7, item.getReorderLevel());
            pstmt.setString(8, item.getItemCode());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteItem(String itemCode) {
        String query = "UPDATE items SET status='DISCONTINUED' WHERE item_code=?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, itemCode);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Item getItemByCode(String itemCode) {
        Item item = null;
        String query = "SELECT * FROM items WHERE item_code = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, itemCode);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                item = extractItemFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items WHERE status = 'AVAILABLE' ORDER BY item_name";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                items.add(extractItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Item> getLowStockItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items WHERE quantity <= reorder_level AND status = 'AVAILABLE'";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                items.add(extractItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    private Item extractItemFromResultSet(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setItemId(rs.getInt("item_id"));
        item.setItemCode(rs.getString("item_code"));
        item.setItemName(rs.getString("item_name"));
        item.setCategory(rs.getString("category"));
        item.setAuthor(rs.getString("author"));
        item.setPublisher(rs.getString("publisher"));
        item.setPrice(rs.getBigDecimal("price"));
        item.setQuantity(rs.getInt("quantity"));
        item.setReorderLevel(rs.getInt("reorder_level"));
        item.setStatus(rs.getString("status"));
        item.setCreatedAt(rs.getTimestamp("created_at"));
        item.setUpdatedAt(rs.getTimestamp("updated_at"));
        return item;
    }

    public String generateItemCode() {
        String query = "SELECT MAX(item_code) as max_code FROM items";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                String maxCode = rs.getString("max_code");
                if (maxCode != null) {
                    int num = Integer.parseInt(maxCode.substring(2)) + 1;
                    return String.format("BK%03d", num);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "BK001";
    }

    public boolean updateStock(int itemId, int quantity) {
        String query = "UPDATE items SET quantity = quantity - ? WHERE item_id = ? AND quantity >= ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, itemId);
            pstmt.setInt(3, quantity);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Item getItemById(int itemId) {
        Item item = null;
        String query = "SELECT * FROM items WHERE item_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                item = extractItemFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public List<Item> getItemsByCategory(String category) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items WHERE category = ? AND status = 'AVAILABLE'";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                items.add(extractItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT DISTINCT category FROM items WHERE status = 'AVAILABLE'";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public int getTotalItems() {
        String sql = "SELECT COUNT(*) FROM items WHERE status = 'AVAILABLE'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getLowStockItemCount() {
        String sql = "SELECT COUNT(*) FROM items WHERE quantity <= reorder_level AND status = 'AVAILABLE'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}