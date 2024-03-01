package dal.asdc.tradecards.Model.DAO;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * CategoryDao represents a data access object (DAO) for managing
 * categories in the Trade Cards application.
 *
 * <p>Annotated with JPA for database persistence and Lombok for automatic
 * getter and setter generation. Corresponds to the "category" table in the database.</p>
 *
 * @author Harshpreet Singh
 * @author Parth Modi
 */

@Entity
@Getter
@Setter
@Table(name = "category")
public class CategoryDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private int CategoryID;

    @Column(name = "CategoryName")
    private String CategoryName;
}

