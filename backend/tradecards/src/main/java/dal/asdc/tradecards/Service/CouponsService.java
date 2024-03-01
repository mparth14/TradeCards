package dal.asdc.tradecards.Service;
import dal.asdc.tradecards.Model.DAO.CouponsDao;
import dal.asdc.tradecards.Model.DTO.CouponsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing coupons in the Trade Cards application.
 * Implemented by classes providing coupon-related functionality.
 * Annotated with {@link org.springframework.stereotype.Service}.
 *
 * @author Harshpreet Singh
 * @author Parth Modi
 */

@Service
public interface CouponsService {
    public CouponsDao createCoupon(CouponsDTO couponsDTO) ;

    public  boolean deleteCouponById(int id);
    public List<CouponsDao> getAllCoupons();

    public CouponsDao getCouponById(int couponId);

    public CouponsDao updateCoupon(int couponId, CouponsDao updatedCoupon);
}
