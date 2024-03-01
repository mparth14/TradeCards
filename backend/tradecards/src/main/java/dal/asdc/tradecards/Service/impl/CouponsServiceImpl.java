package dal.asdc.tradecards.Service.impl;
import dal.asdc.tradecards.Model.DAO.CouponsDao;
import dal.asdc.tradecards.Model.DTO.CouponsDTO;
import dal.asdc.tradecards.Repository.CouponsRepository;
import dal.asdc.tradecards.Service.CouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Implementation of the {@link dal.asdc.tradecards.Service.CouponsService} interface
 * providing functionality related to coupon management.
 *
 * // Create a new coupon
 *
 * @author Harshpreet Singh
 * @author Parth Modi
 */

@Service
public class CouponsServiceImpl implements CouponsService {

    @Autowired
    CouponsRepository couponsRepository;

    public CouponsServiceImpl(CouponsRepository couponsRepository) {
        this.couponsRepository = couponsRepository;
    }

    @Override
    @Transactional
    public CouponsDao createCoupon(CouponsDTO couponsDTO) {
        CouponsDao couponsDao = new CouponsDao();

        couponsDao.setCouponName(couponsDTO.getCouponName());
        couponsDao.setCouponDesc(couponsDTO.getCouponDesc());
        couponsDao.setCouponBrand(couponsDTO.getCouponBrand());
        couponsDao.setExpiryDate(couponsDTO.getExpiryDate());
        couponsDao.setCouponValue(couponsDTO.getCouponValue());
        couponsDao.setCouponSellingPrice(couponsDTO.getCouponSellingPrice());
        couponsDao.setIsSold(couponsDTO.getSold());
        couponsDao.setIsOnline(couponsDTO.getOnline());
        couponsDao.setCouponCategory(couponsDTO.getCouponCategory());
        couponsDao.setCouponListingDate(couponsDTO.getCouponListingDate());
        couponsDao.setCouponLocation(couponsDTO.getCouponLocation());
        couponsDao.setUserid(couponsDTO.getUserid());
        couponsDao.setCategoryID(couponsDTO.getCategoryID());


        // Decoding and storing the image
        byte[] imageBytes = null;
        if (couponsDTO.getCouponImage() != null && !couponsDTO.getCouponImage().isEmpty()) {
            try {
                imageBytes = Base64.getDecoder().decode(couponsDTO.getCouponImage());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        couponsDao.setCouponImage(imageBytes);


        couponsDao = couponsRepository.save(couponsDao);
        return couponsDao;
    }

    @Transactional
    public boolean deleteCouponById(int id){
        Optional<CouponsDao> coupon = couponsRepository.findById(id);
        if (coupon.isPresent()) {
            couponsRepository.delete(coupon.get());
            return true;
        } else {
            return false; // Coupon not found, deletion failed.
        }
    }

    @Transactional
    public List<CouponsDao> getAllCoupons(){
        return (List<CouponsDao>) couponsRepository.findAll();
    }
    public CouponsDao getCouponById(int couponId) {
        return couponsRepository.findById(couponId).orElse(null);
    }

    public CouponsDao updateCoupon(int couponId, CouponsDao updatedCoupon){
        CouponsDao existingCoupon = getCouponById(couponId);
        if (existingCoupon != null) {
            existingCoupon.setCouponName(updatedCoupon.getCouponName());
            existingCoupon.setCouponDesc(updatedCoupon.getCouponDesc());
            existingCoupon.setCouponBrand(updatedCoupon.getCouponBrand());
            existingCoupon.setExpiryDate(updatedCoupon.getExpiryDate());
            existingCoupon.setCouponValue(updatedCoupon.getCouponValue());
            existingCoupon.setCouponSellingPrice(updatedCoupon.getCouponSellingPrice());
            existingCoupon.setIsSold(updatedCoupon.isSold());
            existingCoupon.setIsOnline(updatedCoupon.isOnline());
            existingCoupon.setCouponCategory(updatedCoupon.getCouponCategory());
            existingCoupon.setCouponListingDate(updatedCoupon.getCouponListingDate());
            existingCoupon.setCouponLocation(updatedCoupon.getCouponLocation());
            existingCoupon.setUserid(updatedCoupon.getUserid());
            existingCoupon.setCategoryID(updatedCoupon.getCategoryID());
            existingCoupon.setCouponImage(updatedCoupon.getCouponImage());

            return couponsRepository.save(existingCoupon);
        } else {
            return null;
        }
    }
}
