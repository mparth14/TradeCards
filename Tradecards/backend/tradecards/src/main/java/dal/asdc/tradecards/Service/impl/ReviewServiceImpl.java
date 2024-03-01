package dal.asdc.tradecards.Service.impl;

import dal.asdc.tradecards.Model.DAO.ReviewDao;
import dal.asdc.tradecards.Model.DAO.UserDao;
import dal.asdc.tradecards.Model.DTO.NewReviewDTO;
import dal.asdc.tradecards.Repository.ReviewRepository;
import dal.asdc.tradecards.Service.ReviewService;
import dal.asdc.tradecards.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link dal.asdc.tradecards.Service.CouponsService} interface
 * providing functionality related to user review management.
 *
 * @author Harshpreet Singh
 * @author Parth Modi
 */

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    private final UserService userService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public ReviewDao createReview(NewReviewDTO newReviewDTO){
        UserDao reviewerUser = userService.getUserByUserId(newReviewDTO.getReviewerUserID());
        UserDao reviewedUser = userService.getUserByUserId(newReviewDTO.getReviewedUserID());

        ReviewDao reviewDao = new ReviewDao();

        reviewDao.setReviewDate(new Date());
        reviewDao.setRating(newReviewDTO.getRating());
        reviewDao.setContent(newReviewDTO.getContent());
        reviewDao.setReviewerUser(reviewerUser);
        reviewDao.setReviewedUser(reviewedUser);

        reviewDao = reviewRepository.save(reviewDao);
        return reviewDao;
    }

    @Override
    public List<ReviewDao> getReviewsByReviewedUserId(Long reviewedUserID) {
        return reviewRepository.findByReviewedUserUserid(reviewedUserID);
    }

    @Override
    @Transactional
    public boolean deleteReviewById(Long reviewId) {
        Optional<ReviewDao> review = reviewRepository.findById(String.valueOf(reviewId));

        if (review.isPresent()) {
            reviewRepository.delete(review.get());
            return true;
        } else {
            return false;
        }
    }
}
