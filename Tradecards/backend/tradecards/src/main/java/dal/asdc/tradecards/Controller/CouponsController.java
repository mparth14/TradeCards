package dal.asdc.tradecards.Controller;

import dal.asdc.tradecards.Model.DAO.CouponsDao;
import dal.asdc.tradecards.Model.DTO.CouponsDTO;

import dal.asdc.tradecards.Service.CouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The AuthenticationController class handles coupon-related operations
 * such as create coupon, get all coupons, get coupon by id, delete coupon and edit coupon
 *
 * @author Harshpreet Singh
 * @author Parth Modi
 */

@RestController
@RequestMapping("/api")
public class CouponsController {

    @Autowired
    private CouponsService couponsService;

    public CouponsController(CouponsService couponsService) {
        this.couponsService = couponsService;
    }

    public CouponsController(){}

    @PostMapping("/coupon/create-coupons")
    public ResponseEntity<String> createCoupon(@RequestBody CouponsDTO couponsDTO) {
        try {
            CouponsDao createdCoupon = couponsService.createCoupon(couponsDTO);
            if (createdCoupon != null) {
                return new ResponseEntity<>("Coupon created successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to create the coupon", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create the coupon", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/coupon/delete-coupon/{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable int id){
        try{
            boolean deleted = couponsService.deleteCouponById(id);
            if (deleted) {
                return new ResponseEntity<>("Coupon with ID " + id + " deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Coupon with ID " + id + " not found.", HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>("Coupon with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/coupons")
    public List<CouponsDao> getAllCoupons(){
        return couponsService.getAllCoupons();
    }


    @GetMapping("/coupon/get-coupon/{couponId}")
    public CouponsDao getCouponById(@PathVariable int couponId) {
        return couponsService.getCouponById(couponId);
    }

    @PutMapping("/coupon/update-coupon/{couponId}")
    public CouponsDao updateCouponById(@PathVariable int couponId, @RequestBody CouponsDao updatedCoupon) {
        return couponsService.updateCoupon(couponId, updatedCoupon);
    }
}
