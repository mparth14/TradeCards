package dal.asdc.tradecards.Controller;

import dal.asdc.tradecards.Model.DAO.CategoryDao;
import dal.asdc.tradecards.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The CategoryController class handles category-related operations
 * such as fetching all categories.
 *
 * @author Parth Modi
 */
@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryDao> getAllCategories(){
        return categoryService.getAllCategories();
    }

}
