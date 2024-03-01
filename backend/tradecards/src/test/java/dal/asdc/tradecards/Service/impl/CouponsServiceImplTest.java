package dal.asdc.tradecards.Service.impl;

import dal.asdc.tradecards.Model.DAO.CouponsDao;
import dal.asdc.tradecards.Model.DTO.CouponsDTO;
import dal.asdc.tradecards.Repository.CouponsRepository;
import dal.asdc.tradecards.Service.CouponsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CouponsServiceImplTest {

    @Mock
    private CouponsRepository couponsRepository;

    @InjectMocks
    private CouponsService couponsService = new CouponsServiceImpl(couponsRepository);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("testing create coupon")
    void testCreateCoupon() {
        CouponsDTO couponsDTO = new CouponsDTO();

        CouponsDao expectedCoupon = new CouponsDao();

        when(couponsRepository.save(any())).thenReturn(expectedCoupon);

        CouponsDao result = couponsService.createCoupon(couponsDTO);

        assertNotNull(result);
    }

    @Test
    @DisplayName("testing delete coupon by passing ID")
    void testDeleteCouponById() {
        int couponId = 1;
        CouponsDao couponToDelete = new CouponsDao();

        when(couponsRepository.findById(anyInt())).thenReturn(Optional.of(couponToDelete));

        boolean result = couponsService.deleteCouponById(couponId);

        assertTrue(result);
    }

    @Test
    @DisplayName("testing delete coupon by passing invalid ID")
    void testDeleteCouponByIdCouponNotFound() {
        int couponId = 1;

        when(couponsRepository.findById(anyInt())).thenReturn(Optional.empty());

        boolean result = couponsService.deleteCouponById(couponId);

        assertFalse(result);
    }

    @Test
    @DisplayName("testing get all coupons")
    void testGetAllCoupons() {
        CouponsDao coupon1 = new CouponsDao();
        CouponsDao coupon2 = new CouponsDao();

        List<CouponsDao> expectedCoupons = Arrays.asList(coupon1, coupon2);

        when(couponsRepository.findAll()).thenReturn(expectedCoupons);

        List<CouponsDao> result = couponsService.getAllCoupons();

        assertEquals(expectedCoupons, result);
    }

    @Test
    @DisplayName("testing get coupon by passing specific coupon ID")
    void testGetCouponById() {
        int couponId = 1;
        CouponsDao expectedCoupon = new CouponsDao();

        when(couponsRepository.findById(anyInt())).thenReturn(Optional.of(expectedCoupon));

        CouponsDao result = couponsService.getCouponById(couponId);

        assertNotNull(result);
        assertEquals(expectedCoupon, result);
    }

    @Test
    @DisplayName("testing get coupon by passing invalid ID")
    void testGetCouponByIdCouponNotFound() {
        int couponId = 1;

        when(couponsRepository.findById(anyInt())).thenReturn(Optional.empty());

        CouponsDao result = couponsService.getCouponById(couponId);

        assertNull(result);
    }

    @Test
    @DisplayName("testing update coupon")
    void testUpdateCoupon() {
        int couponId = 1;
        CouponsDao existingCoupon = new CouponsDao();

        CouponsDao updatedCoupon = new CouponsDao();

        when(couponsRepository.findById(anyInt())).thenReturn(Optional.of(existingCoupon));
        when(couponsRepository.save(any())).thenReturn(updatedCoupon);

        CouponsDao result = couponsService.updateCoupon(couponId, updatedCoupon);

        assertNotNull(result);
    }

    @Test
    @DisplayName("testing update coupon by passing non-existent coupon")
    void testUpdateCouponCouponNotFound() {
        int couponId = 1;
        CouponsDao updatedCoupon = new CouponsDao();
        when(couponsRepository.findById(anyInt())).thenReturn(Optional.empty());

        CouponsDao result = couponsService.updateCoupon(couponId, updatedCoupon);
        assertNull(result);
    }
}
