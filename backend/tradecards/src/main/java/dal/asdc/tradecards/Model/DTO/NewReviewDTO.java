package dal.asdc.tradecards.Model.DTO;

/**
 * The NewReviewDTO class represents a data transfer object (DTO)
 * for creating a new review in the Trade Cards application.
 *
 * <p>This class encapsulates the details of a new review, including the rating,
 * content, reviewer user ID, and reviewed user ID.</p>
 *
 * <p>The class provides getter and setter methods for accessing and modifying these properties.</p>
 *
 * <p>Additionally, two constructors are provided: a default constructor and a parameterized
 * constructor that allows initializing the rating and content at the time of creation.</p>
 *
 * @author Parth Modi
 */

public class NewReviewDTO {
    private Float rating;
    private String content;

    private int reviewerUserID;

    private int reviewedUserID;

    public int getReviewerUserID() {
        return reviewerUserID;
    }

    public void setReviewerUserID(int reviewerUserID) {
        this.reviewerUserID = reviewerUserID;
    }

    public int getReviewedUserID() {
        return reviewedUserID;
    }

    public void setReviewedUserID(int reviewedUserID) {
        this.reviewedUserID = reviewedUserID;
    }

    public NewReviewDTO() {
    }

    public NewReviewDTO(Float rating, String content) {
        this.rating = rating;
        this.content = content;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}