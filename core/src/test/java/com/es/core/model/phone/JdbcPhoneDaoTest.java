package com.es.core.model.phone;

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
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context/test-config.xml")
public class JdbcPhoneDaoTest {

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private JdbcTemplate jdbcTemplateTest;

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void saveSameModelBrandTest() throws IllegalAccessException, IntrospectionException, InvocationTargetException {
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
    public void saveSuccessTest() throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Long amountStart = jdbcTemplateTest.queryForObject( "SELECT COUNT (*) FROM phones",Long.class);
        Long maxIdStart = jdbcTemplateTest.queryForObject( "SELECT MAX(id)FROM phones",Long.class);

        Phone phone = new Phone();
        phone.setModel("Modely Model");
        phone.setBrand("Brandy Brand");

        phoneDao.save(phone);

        Long amountFinish = jdbcTemplateTest.queryForObject( "SELECT COUNT (*) FROM phones",Long.class);
        Long maxIdFinish = jdbcTemplateTest.queryForObject( "SELECT MAX(id) FROM phones",Long.class);
        Long differenceAmount = amountFinish - amountStart;
        Long differenceId = maxIdFinish - maxIdStart;

        Assert.isTrue(differenceAmount.equals(1L),"Error occurred with phone addition: one added, " + differenceAmount + " found");
        Assert.isTrue(differenceId.equals(1L),"ID generation failure: " + (maxIdStart + 1) + " expected, " + maxIdFinish + " found");

    }

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void saveNullBrandTest() throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Phone phone = new Phone();
        phone.setBrand(null);

        phoneDao.save(phone);
    }

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void saveNullModelTest() throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Phone phone = new Phone();
        phone.setModel(null);

        phoneDao.save(phone);
    }

    @Test
    @DirtiesContext
    public void getKeyOutRangeTest() {
        final Long key = -1234L;

        Optional<Phone> phones = phoneDao.get(key);

        Assert.isTrue(!phones.isPresent(),"Result for out of range key should be empty");
    }

    @Test
    @DirtiesContext
    public void getKeyInRangeTest() {
        final Long key = 1010L;

        Optional<Phone> phones = phoneDao.get(key);

        Assert.isTrue(phones.isPresent(),"Result for in range key should be not empty");
    }

    @Test
    @DirtiesContext
    public void findAllInRangeTest() throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        int offset = 3;
        int limit = 5;

        List phones = phoneDao.findAll(offset,limit);

        Assert.isTrue(phones.size() == limit,"Expected " + limit + " phones, " + phones.size() + " found");
    }

    @Test
    @DirtiesContext
    public void findAllZeroLimitTest() throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        List phones = phoneDao.findAll(7,0);

        Assert.isTrue(phones.isEmpty(),"Empty phoneList expected");
    }

    @Test
    @DirtiesContext
    public void findAllOffsetOutRangeTest() throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        List phones = phoneDao.findAll(12,12);

        Assert.isTrue(phones.isEmpty(),"Empty phoneList expected");
    }







}
