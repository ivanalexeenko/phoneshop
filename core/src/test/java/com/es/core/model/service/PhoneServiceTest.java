package com.es.core.model.service;

import com.es.core.dao.PhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.service.PhoneServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

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
    private static Object[] validFindAllArgs = {0, 3, "X", "brand", true};
    private static Object[] notValidFindAllArgs = {-1, 13, "Ha", "Samsung", true};
    private List<Phone> phones;
    private Phone nullPhone = null;

    @Mock
    private PhoneDao phoneDao;

    @InjectMocks
    private PhoneServiceImpl phoneService;

    @Before
    public void init() {
        initMocks(this);
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
    public void shouldAssertPhoneWithIdFoundWhenGetExistedId() {
        Optional optionalPhone = phoneService.get(phoneIds[existedIdIndex]);

        assertTrue(optionalPhone.isPresent());
        assertEquals(optionalPhone.get(), phones.get(existedIdIndex));
    }

    @Test
    public void shouldAssertEmptyResultWhenGetNotExistedId() {
        Optional optionalPhone = phoneService.get(notExistedPhoneId);

        assertTrue(!optionalPhone.isPresent());
    }

    @Test
    public void shouldSavePhoneSuccessfullyWhenSavePhoneWithOrdinaryPhoneId() {
        phoneService.save(phones.get(ordinaryPhoneIndex));

        verify(phoneDao, VerificationModeFactory.times(ORDINARY_PHONE_SAVE_METHOD_INVOCATIONS)).save(phones.get(ordinaryPhoneIndex));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenSaveNull() {
        phoneService.save(nullPhone);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenSaveAlreadySavedPhone() {
        Phone savedPhone = phones.get(phoneAlreadySavedIndex);
        phoneService.save(savedPhone);
        phoneService.save(savedPhone);
    }

    @Test
    public void shouldAssertAllPhonesFoundWhenFindAllWithValidArguments() {
        List findAllPhones = phoneService.findAll((int) validFindAllArgs[0], (int) validFindAllArgs[1],
                (String) validFindAllArgs[2], (String) validFindAllArgs[3], (Boolean) validFindAllArgs[4]);

        assertTrue(findAllPhones.containsAll(phones));
    }

    @Test
    public void shouldAssertZeroPhonesFoundWhenFindAllWithNotValidArguments() {
        List findAllPhones = phoneService.findAll((int) notValidFindAllArgs[0], (int) notValidFindAllArgs[1],
                (String) notValidFindAllArgs[2], (String) notValidFindAllArgs[3], (Boolean) notValidFindAllArgs[4]);

        assertTrue(findAllPhones.isEmpty());
    }

    @Test
    public void shouldAssertPhonesAmountFullWhenCountPhonesValidSearch() {
        Integer count = phoneService.countPhonesStockBiggerZero((String) validFindAllArgs[3]);

        assertEquals((int) count, phones.size());
    }

    @Test
    public void shouldAssertPhonesAmountEmptyWhenCountPhonesNotValidSearch() {
        Integer count = phoneService.countPhonesStockBiggerZero((String) notValidFindAllArgs[3]);

        assertEquals((int) count, EMPTY_COUNT);
    }

    private void setMockBehaviourForVoidMethods() {
        doNothing().when(phoneDao).save(phones.get(ordinaryPhoneIndex));
        doThrow(IllegalArgumentException.class).when(phoneDao).save(nullPhone);
        doThrow(IllegalArgumentException.class).when(phoneDao).save(phones.get(phoneAlreadySavedIndex));
    }

    private void setMockBehaviour() {
        when(phoneDao.get(phoneIds[existedIdIndex])).thenReturn(Optional.of(phones.get(existedIdIndex)));
        when(phoneDao.get(notExistedPhoneId)).thenReturn(Optional.empty());
        when(phoneDao.get(phones.get(ordinaryPhoneIndex).getId())).thenReturn(Optional.empty());

        when(phoneDao.findAll((int) validFindAllArgs[0], (int) validFindAllArgs[1],
                (String) validFindAllArgs[2], (String) validFindAllArgs[3], (Boolean) validFindAllArgs[4])).thenReturn(phones);
        when(phoneDao.findAll((int) notValidFindAllArgs[0], (int) notValidFindAllArgs[1],
                (String) notValidFindAllArgs[2], (String) notValidFindAllArgs[3], (Boolean) notValidFindAllArgs[4])).thenReturn(new ArrayList());
        when(phoneDao.countPhonesStockBiggerZero((String) validFindAllArgs[3])).thenReturn(phones.size());
        when(phoneDao.countPhonesStockBiggerZero((String) notValidFindAllArgs[3])).thenReturn(EMPTY_COUNT);
    }

    private void setPhoneMockBehaviour(Phone phone) {
        when(phone.getId()).thenReturn(phoneIds[phones.size()]);
        when(phone.getModel()).thenReturn(phoneModels[phones.size()]);
        when(phone.getBrand()).thenReturn(phoneBrands[phones.size()]);
        when(phone.getPrice()).thenReturn(phonePrices[phones.size()]);
    }
}
