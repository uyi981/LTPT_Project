/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.DAO;

import java.util.ArrayList;

/**
 *
 * @author KHANH PC
 */
public interface DAOInterface<T> {
    // Phương thức để lấy một bản ghi cụ thể từ cơ sở dữ liệu theo id
    public T get(String id);
    
    // Phương thức để lấy tất cả các bản ghi từ cơ sở dữ liệu
    public ArrayList<T> getAll();

    public boolean insert(T t);

    // Phương thức để cập nhật một bản ghi trong cơ sở dữ liệu
    public boolean update(T t);

    // Phương thức để xóa một bản ghi khỏi cơ sở dữ liệu theo id
    public boolean deleteById(String id);
    
}

