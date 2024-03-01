package dal.asdc.tradecards.Controller;
import dal.asdc.tradecards.Model.DAO.ReviewDao;
import dal.asdc.tradecards.Model.DTO.NewReviewDTO;
import dal.asdc.tradecards.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The ReviewController class handles seller review-related operations
 * such as create review, get review and delete review
 *
 * @author Harshpreet Singh
 * @author Parth Modi
 */

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createReview(@RequestBody NewReviewDTO newReviewDto) {
        try {
            ReviewDao createdReview = reviewService.createReview(newReviewDto);
            if (createdReview != null) {
                return new ResponseEntity<>("Review added successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to add the review", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add the coupon", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-review/{reviewedUserID}")
    public ResponseEntity<List<ReviewDao>> getReviewsByReviewedUser(@PathVariable Long reviewedUserID) {
        try {
            List<ReviewDao> reviews = reviewService.getReviewsByReviewedUserId(reviewedUserID);

            if (reviews != null && !reviews.isEmpty()) {
                return new ResponseEntity<>(reviews, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-review/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        try {
            boolean isDeleted = reviewService.deleteReviewById(reviewId);

            if (isDeleted) {
                return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Review not found or deletion failed", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete the review", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}