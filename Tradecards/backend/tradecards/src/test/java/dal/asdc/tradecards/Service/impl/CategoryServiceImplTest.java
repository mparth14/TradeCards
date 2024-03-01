package dal.asdc.tradecards.Service.impl;

import dal.asdc.tradecards.Model.DAO.CategoryDao;
import dal.asdc.tradecards.Repository.CategoryRepository;
import dal.asdc.tradecards.Service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CategoryService.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("testing get all categories")
    public void testGetAllCategories() {
        List<CategoryDao> categoryList = new ArrayList<>();
        CategoryDao categoryDao1 = new CategoryDao();
        categoryDao1.setCategoryID(1);
        categoryDao1.setCategoryName("Shoes");
        CategoryDao categoryDao2 = new CategoryDao();
        categoryDao2.setCategoryID(2);
        categoryDao2.setCategoryName("Suits");
        categoryList.add(categoryDao1);
        categoryList.add(categoryDao2);

        Mockito.when(categoryService.getAllCategories()).thenReturn(categoryList);

        List<CategoryDao> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
    }
}
