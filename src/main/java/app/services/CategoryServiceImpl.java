package app.services;

import app.models.Category;
import app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl {

    @Autowired
    CategoryRepository categoryRepository;

    public Category findById(long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Returns the categories as text.
     * @return
     */
    public String getCategoryAsText() {
        List<Category> categories = categoryRepository.findAll();
        String result = "";
        for (Category c : categories) {

            result += "\n" + c.getId() + " - " + c.getName();

        }
        return result;
    }

}
