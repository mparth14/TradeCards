package dal.asdc.tradecards.Model.DAO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.util.List;

import java.util.Date;

/**
 * {@code UserDao} represents a data access object (DAO) for managing user information
 * in the Trade Cards application.
 *
 * <p>Annotated with JPA for database persistence and Lombok for automatic
 * getter and setter generation. Corresponds to the "user" table in the database.</p>
 *
 * <p>Properties include userid, emailID, password, is_deleted, is_verified, updated_at,
 * FirstName, LastName, givenReviews, and receivedReviews. The class includes one-to-many
 * relationships with ReviewDao for given and received reviews.</p>
 *
 * @author Harshpreet Singh
 * @author Parth Modi
 */

@Entity
@Getter
@Setter
@Table(name="user")
public class UserDao {
    @Id
    @Column(name="userid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;

    @Column(name = "email_id", unique=true)
    private String emailID;

    @Column(name = "password")
    private String password;

    @Column(name="is_deleted")
    private Integer is_deleted = 0;

    @Column(name="is_verified")
    private Integer is_verified = 0;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date updated_at;

    @Column(name = "first_name")
    private String FirstName;

    @Column(name = "last_name")
    private String LastName;

    @OneToMany(mappedBy = "reviewerUser")
    private List<ReviewDao> givenReviews;

    @OneToMany(mappedBy = "reviewedUser")
    private List<ReviewDao> receivedReviews;

    public UserDao() {
    }

    public UserDao(String FirstName, String LastName, String emailID, String password) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.emailID = emailID;
        this.password = password;
    }

    public UserDao(int userID, String emailID, String lastName, String firstName, List<ReviewDao> receivedReviews, List<ReviewDao> givenReviews) {
        this.userid = userID;
        this.FirstName = lastName;
        this.LastName = firstName;
        this.emailID = emailID;
        this.receivedReviews = receivedReviews;
        this.givenReviews = givenReviews;
    }

}

