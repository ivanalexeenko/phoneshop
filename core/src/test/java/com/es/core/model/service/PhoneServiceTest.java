package com.es.core.model.service;

import com.es.core.dao.PhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.service.PhoneService;
import com.es.core.service.PhoneServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PhoneServiceTest {
    private static final Integer ORDINARY_PHONE_SAVE_METHOD_INVOCATIONS = 1;
    private static final int EMPTY_COUNT = 0;
    private static Long[] phoneIds = {10001L, 2402L, 3900L};
    private static String[] phoneModels = {"X", "XY", "XYZ"};
    private static String[] phoneBrands = {"Apple", "Xiaomi", "Huawei"};
    private static BigDecimal[] phonePrices = {BigDecimal.valueOf(100), BigDecimal.valueOf(200), BigDecimal.valueOf(123.321)};
    private static Long notExistedPhoneId = 191L;
    private static Integer phoneAlreadySavedIndex = 2;
    private static Integer ordinaryPhoneIndex = 0;
    private static int existedIdIndex = 1;
    private List<Phone> phones;
    private Phone nullPhone = null;
    private static Object[] validFindAllArgs = {0, 3, "X", "brand", true};
    private static Object[] notValidFindAllArgs = {-1, 13, "Ha", "Samsung", true};

    @Configuration
    static class PhoneServiceTestContextConfiguration {
        @Bean
        public PhoneDao getPhoneDao() {
            return Mockito.mock(PhoneDao.class);
        }

        @Bean
        public PhoneService getPhoneService() {
            return new PhoneServiceImpl(getPhoneDao());
        }
    }

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private PhoneService phoneService;

    @Before
    public void init() {
        phones = new ArrayList<>();
        for (Long phoneId : phoneIds) {
            Phone phone = new Phone();
            phone = Mockito.mock(Phone.class);
            setPhoneMockBehaviour(phone);
            phones.add(phone);
        }
        setMockBehaviour();
        setMockBehaviourForVoidMethods();
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhoneWithIdFoundWhenGetExistedId() {
        Optional optionalPhone = phoneService.get(phoneIds[existedIdIndex]);

        assertTrue(optionalPhone.isPresent());
        assertEquals(optionalPhone.get(), phones.get(existedIdIndex));
    }

    @Test
    @DirtiesContext
    public void shouldAssertEmptyResultWhenGetNotExistedId() {
        Optional optionalPhone = phoneService.get(notExistedPhoneId);

        assertTrue(!optionalPhone.isPresent());
    }

    @Test
    @DirtiesContext
    public void shouldSavePhoneSuccessfullyWhenSavePhoneWithOrdinaryPhoneId() {
        phoneService.save(phones.get(ordinaryPhoneIndex));

        Mockito.verify(phoneDao, VerificationModeFactory.times(ORDINARY_PHONE_SAVE_METHOD_INVOCATIONS)).save(phones.get(ordinaryPhoneIndex));
    }

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void shouldThrowIllegalArgumentExceptionWhenSaveNull() {
        phoneService.save(nullPhone);
    }

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void shouldThrowIllegalArgumentExceptionWhenSaveAlreadySavedPhone() {
        Phone savedPhone = phones.get(phoneAlreadySavedIndex);
        phoneService.save(savedPhone);
        phoneService.save(savedPhone);
    }

    @Test
    @DirtiesContext
    public void shouldAssertAllPhonesFoundWhenFindAllWithValidArguments() {
        List findAllPhones = phoneService.findAll((int) validFindAllArgs[0], (int) validFindAllArgs[1],
                (String) validFindAllArgs[2], (String) validFindAllArgs[3], (Boolean) validFindAllArgs[4]);

        assertTrue(findAllPhones.containsAll(phones));
    }

    @Test
    @DirtiesContext
    public void shouldAssertZeroPhonesFoundWhenFindAllWithNotValidArguments() {
        List findAllPhones = phoneService.findAll((int) notValidFindAllArgs[0], (int) notValidFindAllArgs[1],
                (String) notValidFindAllArgs[2], (String) notValidFindAllArgs[3], (Boolean) notValidFindAllArgs[4]);

        assertTrue(findAllPhones.isEmpty());
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhonesAmountFullWhenCountPhonesValidSearch() {
        Integer count = phoneService.countPhonesStockBiggerZero((String) validFindAllArgs[3]);

        assertEquals((int) count, phones.size());
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhonesAmountEmptyWhenCountPhonesNotValidSearch() {
        Integer count = phoneService.countPhonesStockBiggerZero((String) notValidFindAllArgs[3]);

        assertEquals((int) count, EMPTY_COUNT);
    }

    private void setMockBehaviourForVoidMethods() {
        Mockito.doNothing().when(phoneDao).save(phones.get(ordinaryPhoneIndex));
        Mockito.doThrow(IllegalArgumentException.class).when(phoneDao).save(nullPhone);
        Mockito.doThrow(IllegalArgumentException.class).when(phoneDao).save(phones.get(phoneAlreadySavedIndex));
    }

    private void setMockBehaviour() {
        Mockito.when(phoneDao.get(phoneIds[existedIdIndex])).thenReturn(Optional.of(phones.get(existedIdIndex)));
        Mockito.when(phoneDao.get(notExistedPhoneId)).thenReturn(Optional.empty());
        Mockito.when(phoneDao.get(phones.get(ordinaryPhoneIndex).getId())).thenReturn(Optional.empty());

        Mockito.when(phoneDao.findAll((int) validFindAllArgs[0], (int) validFindAllArgs[1],
                (String) validFindAllArgs[2], (String) validFindAllArgs[3], (Boolean) validFindAllArgs[4])).thenReturn(phones);
        Mockito.when(phoneDao.findAll((int) notValidFindAllArgs[0], (int) notValidFindAllArgs[1],
                (String) notValidFindAllArgs[2], (String) notValidFindAllArgs[3], (Boolean) notValidFindAllArgs[4])).thenReturn(new ArrayList());
        Mockito.when(phoneDao.countPhonesStockBiggerZero((String) validFindAllArgs[3])).thenReturn(phones.size());
        Mockito.when(phoneDao.countPhonesStockBiggerZero((String) notValidFindAllArgs[3])).thenReturn(EMPTY_COUNT);
    }

    private void setPhoneMockBehaviour(Phone phone) {
        Mockito.when(phone.getId()).thenReturn(phoneIds[phones.size()]);
        Mockito.when(phone.getModel()).thenReturn(phoneModels[phones.size()]);
        Mockito.when(phone.getBrand()).thenReturn(phoneBrands[phones.size()]);
        Mockito.when(phone.getPrice()).thenReturn(phonePrices[phones.size()]);
    }
}
