package dal.asdc.tradecards.Repository;

import dal.asdc.tradecards.Model.DAO.CategoryDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryDao, String> {

}
