package dal.asdc.tradecards.Service;

import dal.asdc.tradecards.Model.DAO.CategoryDao;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service interface for managing categories in the Trade Cards application.
 * Implemented by classes providing category-related functionality.
 * Annotated with {@link org.springframework.stereotype.Service}.
 *
 * @author Parth Modi
 */

@Service
public interface CategoryService {
    public List<CategoryDao> getAllCategories();
}