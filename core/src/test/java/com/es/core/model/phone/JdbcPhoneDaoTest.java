package com.es.core.model.phone;

import com.es.core.exception.GetterInvokerException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context/test-config.xml")

public class JdbcPhoneDaoTest {
    private static final String SELECT_PHONES_COUNT_QUERY = "SELECT COUNT (*) FROM phones";
    private static final String SELECT_MAX_PHONE_ID_QUERY = "SELECT MAX(id)FROM phones";
    private static final String ERROR_PHONE_SAVE = "Error occurred with phone addition: [added,found] = ";
    private static final String ERROR_ID_GENERATE = "ID generation failure: [expected,found] = ";
    private static final String ERROR_OUT_OF_RANGE_KEY = "Result for out of range key should be empty";
    private static final String ERROR_IN_RANGE_KEY = "Result for in range key should be not empty";
    private static final String ERROR_EMPTY_PHONELIST = "Empty phoneList expected";
    private static final String ERROR_PHONES_FIND_ALL = "Phones [expected,found] = ";
    private static final String REPEAT_MODEL = "Modely Model";
    private static final String REPEAT_BRAND = "Brandy Brand";
    private static final String ORDINARY_MODEL = "XYZ";
    private static final String ORDINARY_BRAND = "Apple";
    private static final Long NEGATIVE_PRIMARY_KEY = -1234L;
    private static final Long POSITIVE_PRIMARY_KEY = 1009L;
    private static final int IN_RANGE_OFFSET = 3;
    private static final int IN_RANGE_LIMIT = 5;
    private static final int EMPTY_LIMIT = 0;
    private static final int OUT_RANGE_OFFSET = 12;
    private static final int OUT_RANGE_LIMIT = 12;
    private static final Long ADDED_PHONE_AMOUNT = 1L;
    private Phone phone, phone1;
    private List phones;
    private Optional<Phone> optionalPhone;
    @Resource
    private PhoneDao phoneDao;

    @Resource
    private JdbcTemplate jdbcTemplateTest;
/*
    @Before
    public void init() {
        phone = new Phone();
        phone1 = new Phone();
    }

    @After
    public void destroy() {
        phone = null;
        phone1 = null;
    }

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void shouldThrowIllegalArgumentExceptionWhenSaveSameModelBrandPhones() throws GetterInvokerException {
        phone.setModel(REPEAT_MODEL);
        phone.setBrand(REPEAT_BRAND);

        phone1.setModel(phone.getModel());
        phone1.setBrand(phone.getBrand());

        phoneDao.save(phone);
        phoneDao.save(phone1);
    }

    @Test
    @DirtiesContext
    public void shouldAssertOnePhoneAddedNextIdGeneratedSuccess() throws GetterInvokerException {
        Long amountStart = jdbcTemplateTest.queryForObject(SELECT_PHONES_COUNT_QUERY, Long.class);
        Long maxIdStart = jdbcTemplateTest.queryForObject(SELECT_MAX_PHONE_ID_QUERY, Long.class);

        phone.setModel(ORDINARY_MODEL);
        phone.setBrand(ORDINARY_BRAND);

        phoneDao.save(phone);

        Long amountFinish = jdbcTemplateTest.queryForObject(SELECT_PHONES_COUNT_QUERY, Long.class);
        Long maxIdFinish = jdbcTemplateTest.queryForObject(SELECT_MAX_PHONE_ID_QUERY, Long.class);
        Long differenceAmount = amountFinish - amountStart;
        Long differenceId = maxIdFinish - maxIdStart;

        Assert.isTrue(differenceAmount.equals(ADDED_PHONE_AMOUNT), ERROR_PHONE_SAVE + Arrays.toString(new Object[]{ADDED_PHONE_AMOUNT, differenceAmount}));
        Assert.isTrue(differenceId.equals(ADDED_PHONE_AMOUNT), ERROR_ID_GENERATE + Arrays.toString(new Object[]{maxIdStart + ADDED_PHONE_AMOUNT, maxIdFinish}));

    }

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void shouldThrowIllegalArgumentExceptionWhenSaveNullBrand() throws GetterInvokerException {
        phone.setBrand(null);

        phoneDao.save(phone);
    }

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void shouldThrowIllegalArgumentExceptionWhenSaveNullModel() throws GetterInvokerException {
        phone.setModel(null);

        phoneDao.save(phone);
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhonesNotFoundWhenGetKeyOutRange() {
        optionalPhone = phoneDao.get(NEGATIVE_PRIMARY_KEY);

        Assert.isTrue(!optionalPhone.isPresent(), ERROR_OUT_OF_RANGE_KEY);
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhoneIsFoundWhenGetKeyInRange() {
        optionalPhone = phoneDao.get(POSITIVE_PRIMARY_KEY);

        Assert.isTrue(optionalPhone.isPresent(), ERROR_IN_RANGE_KEY);
    }

    @Test
    @DirtiesContext
    public void shouldAssertLimitedPhonesFromOffsetFoundWhenFindAllInRange() {
        phones = phoneDao.findAll(IN_RANGE_OFFSET, IN_RANGE_LIMIT);

        Assert.isTrue(phones.size() == IN_RANGE_LIMIT, ERROR_PHONES_FIND_ALL + Arrays.toString(new Object[]{IN_RANGE_LIMIT, phones.size()}));
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhonesNotFoundWhenFindAllZeroLimit() {
        phones = phoneDao.findAll(IN_RANGE_OFFSET, EMPTY_LIMIT);

        Assert.isTrue(phones.isEmpty(), ERROR_EMPTY_PHONELIST);
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhonesNotFoundWhenFindAllOffsetOutRange() {
        phones = phoneDao.findAll(OUT_RANGE_OFFSET, OUT_RANGE_LIMIT);

        Assert.isTrue(phones.isEmpty(), ERROR_EMPTY_PHONELIST);
    }

*/
}
