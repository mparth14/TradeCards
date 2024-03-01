package dal.asdc.tradecards.Service;

import dal.asdc.tradecards.Model.DAO.ReviewDao;
import dal.asdc.tradecards.Model.DTO.NewReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing reviews in the Trade Cards application.
 * Implemented by classes providing review-related functionality.
 * Annotated with {@link org.springframework.stereotype.Service}.
 *
 *
 * <p>Implementations of this interface provide the logic for
 * review creation, retrieval by user ID, and deletion by ID.</p>
 *
 * @author Harshpreet Singh
 * @author Parth Modi
 */

@Service
public interface ReviewService {
    public ReviewDao createReview(NewReviewDTO newReviewDTO);

    public List<ReviewDao> getReviewsByReviewedUserId(Long reviewedUserId);

    public boolean deleteReviewById(Long reviewId);
}