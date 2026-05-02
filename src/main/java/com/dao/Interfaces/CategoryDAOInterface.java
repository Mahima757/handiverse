package com.dao.Interfaces;
import java.util.List;
import com.model.Category;

public interface CategoryDAOInterface {

    boolean addCategory(Category c);

    List<Category> getAllCategories();

    Category getCategoryById(int id);

    boolean updateCategory(Category c);

    boolean deleteCategory(int id);
}
