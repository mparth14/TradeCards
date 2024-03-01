package dal.asdc.tradecards.Model.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * ReviewDao represents a data access object (DAO) for managing reviews
 * in the Trade Cards application.
 *
 * <p>Annotated with JPA for database persistence and Lombok for automatic
 * getter and setter generation. Corresponds to the "review" table in the database.</p>
 *
 * <p>Properties include reviewId, rating, content, reviewDate, reviewerUser, and reviewedUser.
 * The class also includes many-to-one relationships with UserDao for reviewer and reviewed users.</p>
 *
 * @author Harshpreet Singh
 * @author Parth Modi
 */

@Entity
@Getter
@Setter
@Table(name = "review")
public class ReviewDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReviewID")
    private Long reviewId;

    @Column(name = "Rating")
    private Float rating;

    @Column(name = "Content", length = 200)
    private String content;

    @Column(name = "ReviewDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "reviewer_user_id", referencedColumnName = "userid")
    private UserDao reviewerUser;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "reviewed_user_id", referencedColumnName = "userid")
    private UserDao reviewedUser;
}