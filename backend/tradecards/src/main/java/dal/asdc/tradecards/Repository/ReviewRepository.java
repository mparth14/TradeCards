package dal.asdc.tradecards.Repository;

import dal.asdc.tradecards.Model.DAO.ReviewDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<ReviewDao, String> {
    List<ReviewDao> findByReviewedUserUserid(Long revieweduserid);
}