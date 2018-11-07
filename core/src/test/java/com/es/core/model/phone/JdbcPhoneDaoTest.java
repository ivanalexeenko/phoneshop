package com.es.core.model.phone;

import com.es.core.exception.GetterInvokerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.es.core.helping.ConstantsCore.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(TEST_CONFIG_LOCATION)
public class JdbcPhoneDaoTest {

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private JdbcTemplate jdbcTemplateTest;

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void saveSameModelBrandTest() throws GetterInvokerException {
        Phone phone = new Phone();
        phone.setModel("Modely Model");
        phone.setBrand("Brandy Brand");

        Phone phone1 = new Phone();
        phone1.setModel(phone.getModel());
        phone1.setBrand(phone.getBrand());

        phoneDao.save(phone);
        phoneDao.save(phone1);
    }

    @Test
    @DirtiesContext
    public void saveSuccessTest() throws GetterInvokerException {
        Long amountStart = jdbcTemplateTest.queryForObject( SELECT_PHONES_COUNT_QUERY,Long.class);
        Long maxIdStart = jdbcTemplateTest.queryForObject( SELECT_MAX_PHONE_ID_QUERY,Long.class);

        Phone phone = new Phone();
        phone.setModel("Modely Model");
        phone.setBrand("Brandy Brand");

        phoneDao.save(phone);

        Long amountFinish = jdbcTemplateTest.queryForObject( SELECT_PHONES_COUNT_QUERY,Long.class);
        Long maxIdFinish = jdbcTemplateTest.queryForObject( SELECT_MAX_PHONE_ID_QUERY,Long.class);
        Long differenceAmount = amountFinish - amountStart;
        Long differenceId = maxIdFinish - maxIdStart;

        Assert.isTrue(differenceAmount.equals(1L),ERROR_PHONE_SAVE + Arrays.toString(new Object[]{1, differenceAmount}));
        Assert.isTrue(differenceId.equals(1L),ERROR_ID_GENERATE + Arrays.toString(new Object[]{maxIdStart + 1,maxIdFinish}));

    }

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void saveNullBrandTest() throws GetterInvokerException {
        Phone phone = new Phone();
        phone.setBrand(null);

        phoneDao.save(phone);
    }

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void saveNullModelTest() throws GetterInvokerException {
        Phone phone = new Phone();
        phone.setModel(null);

        phoneDao.save(phone);
    }

    @Test
    @DirtiesContext
    public void getKeyOutRangeTest() {
        final Long key = -1234L;

        Optional<Phone> phones = phoneDao.get(key);

        Assert.isTrue(!phones.isPresent(),ERROR_OUT_OF_RANGE_KEY);
    }

    @Test
    @DirtiesContext
    public void getKeyInRangeTest() {
        Long key = 1009L;

        Optional<Phone> phones = phoneDao.get(key);

        Assert.isTrue(phones.isPresent(),ERROR_IN_RANGE_KEY);
    }

    @Test
    @DirtiesContext
    public void findAllInRangeTest() {
        int offset = 3;
        int limit = 5;

        List phones = phoneDao.findAll(offset,limit);

        Assert.isTrue(phones.size() == limit,ERROR_PHONES_FIND_ALL + Arrays.toString(new Object[]{limit,phones.size()}));
    }

    @Test
    @DirtiesContext
    public void findAllZeroLimitTest() {
        List phones = phoneDao.findAll(7,0);

        Assert.isTrue(phones.isEmpty(),ERROR_EMPTY_PHONELIST);
    }

    @Test
    @DirtiesContext
    public void findAllOffsetOutRangeTest() {
        List phones = phoneDao.findAll(12,12);

        Assert.isTrue(phones.isEmpty(),ERROR_EMPTY_PHONELIST);
    }







}
