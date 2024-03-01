package dal.asdc.tradecards.Model.DAO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * CouponsDao represents a data access object (DAO) for managing coupons in the Trade Cards application.
 * Annotated with JPA for database persistence and Lombok for automatic getter and setter generation.
 * Corresponds to the "coupon" table in the database.
 *
 * <p>Properties include CouponID, CouponName, CouponDesc, ExpiryDate, CouponValue, CouponSellingPrice, isSold, isOnline,
 * CouponCategory, CouponListingDate, CouponLocation, userid, CategoryID, and CouponImage.
 * Includes methods for setting sold and online status.</p>
 *
 * @author Harshpreet Singh
 * @author Parth Modi
 */

@Entity
@Getter
@Setter
@Table(name = "coupons")
public class CouponsDao {
    @Id
    @Column(name = "CouponID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CouponID;

    @Column(name = "CouponName")
    private String CouponName;

    @Column(name = "CouponDesc")
    private String CouponDesc;

    @Column(name = "CouponBrand")
    private String CouponBrand;

    @Column(name = "ExpiryDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ExpiryDate;

    @Column(name = "CouponValue")
    private int CouponValue;

    @Column(name = "CouponSellingPrice")
    private int CouponSellingPrice;

    @Column(name = "isSold")
    private boolean isSold;

    @Column(name = "isOnline")
    private boolean isOnline;

    @Column(name = "CouponCategory")
    private String CouponCategory;

    @Column(name = "CouponListingDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date CouponListingDate;

    @Column(name = "CouponLocation")
    private String CouponLocation;

    @Column(name = "userid")
    private int userid;

    @Column(name = "CategoryID")
    private int CategoryID;

    @Column(name = "CouponImage")
    @Lob
    private byte[] CouponImage;

    public CouponsDao() {
    }

    public CouponsDao(String couponCategory, String couponLocation) {
        this.CouponCategory = couponCategory;
        this.CouponLocation = couponLocation;
    }

    public void setIsSold(boolean sold) {
        this.isSold = sold;
    }

    public void setIsOnline(boolean online) {
        this.isOnline = online;
    }
}
