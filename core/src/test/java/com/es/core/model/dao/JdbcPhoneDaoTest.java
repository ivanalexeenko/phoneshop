package com.es.core.model.dao;

import com.es.core.dao.PhoneDao;
import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context/test-config.xml")
public class JdbcPhoneDaoTest {
    private static final String SELECT_PHONES_COUNT_QUERY = "SELECT COUNT (*) FROM phones";
    private static final String SELECT_PHONES_QUERY = "SELECT * FROM phones";
    private static final String SELECT_MAX_PHONE_ID_QUERY = "SELECT MAX(id)FROM phones";
    private static final String ERROR_PHONE_SAVE = "Error occurred with phone addition: [added,found] = ";
    private static final String ERROR_ID_GENERATE = "ID generation failure: [expected,found] = ";
    private static final String ERROR_OUT_OF_RANGE_KEY = "Result for out of range key should be empty";
    private static final String ERROR_IN_RANGE_KEY = "Result for in range key should be not empty";
    private static final String ERROR_EMPTY_PHONELIST = "Empty phoneList expected";
    private static final String ERROR_PHONES_FIND_ALL = "Phones [expected,found] = ";
    private static final String ERROR_PHONES_UNSORTED = "Phones are unsorted";
    private static final String ERROR_PHONES_SEARCH = "Problem with search occured";
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
    private static final int ALL_RANGE_START = 0;
    private static final Long ADDED_PHONE_AMOUNT = 1L;
    private static final String STANDARD_ORDER = "";
    private static final String STANDARD_SEARCH = "";
    private static final Boolean IS_ASCEND = true;
    private static final Boolean IS_DESCEND = false;
    private static final Boolean STANDARD_ASCEND = true;
    private static final int NEGATIVE_NUM = -1;
    private static final int POSITIVE_NUM = 1;
    private static final int NEUTRAL_NUM = 0;
    private static final String ORDER_BY_BRAND = "brand";
    private static final String ORDER_BY_MODEL = "model";
    private static final String ORDER_BY_DISPLAY = "displaySizeInches";
    private static final String ORDER_BY_PRICE = "price";
    private static final String ORDER_BY_OS = "os";
    private static final String SEARCH_STRING = "Nokia";
    private static final String SEARCH_STRING_NO_MATCH = "No match string";
    private Phone phone, phone1;
    private List phones;
    private Optional<Phone> optionalPhone;
    private List<Phone> ascendPhones;
    private List<Phone> descendPhones;

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private JdbcTemplate jdbcTemplateTest;

    @Before
    public void init() {
        phone = new Phone();
        phone1 = new Phone();
        ascendPhones = jdbcTemplateTest.query(SELECT_PHONES_QUERY, new BeanPropertyRowMapper<>(Phone.class));
        descendPhones = jdbcTemplateTest.query(SELECT_PHONES_QUERY, new BeanPropertyRowMapper<>(Phone.class));
    }

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void shouldThrowIllegalArgumentExceptionWhenSaveSameModelBrandPhones() {
        phone.setModel(REPEAT_MODEL);
        phone.setBrand(REPEAT_BRAND);

        phone1.setModel(phone.getModel());
        phone1.setBrand(phone.getBrand());

        phoneDao.save(phone);
        phoneDao.save(phone1);
    }

    @Test
    @DirtiesContext
    public void shouldAssertOnePhoneAddedNextIdGeneratedSuccess() {
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
    public void shouldThrowIllegalArgumentExceptionWhenSaveNullBrand() {
        phone.setBrand(null);

        phoneDao.save(phone);
    }

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void shouldThrowIllegalArgumentExceptionWhenSaveNullModel() {
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
        phones = phoneDao.findAll(IN_RANGE_OFFSET, IN_RANGE_LIMIT, STANDARD_SEARCH, STANDARD_ORDER, STANDARD_ASCEND);

        Assert.isTrue(phones.size() == IN_RANGE_LIMIT, ERROR_PHONES_FIND_ALL + Arrays.toString(new Object[]{IN_RANGE_LIMIT, phones.size()}));
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhonesNotFoundWhenFindAllZeroLimit() {
        phones = phoneDao.findAll(EMPTY_LIMIT, EMPTY_LIMIT, STANDARD_SEARCH, STANDARD_ORDER, STANDARD_ASCEND);

        Assert.isTrue(phones.isEmpty(), ERROR_EMPTY_PHONELIST);
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhonesNotFoundWhenFindAllOffsetOutRange() {
        phones = phoneDao.findAll(OUT_RANGE_OFFSET, OUT_RANGE_LIMIT, STANDARD_SEARCH, STANDARD_ORDER, STANDARD_ASCEND);

        Assert.isTrue(phones.isEmpty(), ERROR_EMPTY_PHONELIST);
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhonesOrderedByBrandAscendDescendWhenFindAll() {
        ascendPhones.sort((o1, o2) -> {
            String brandOne = ((Phone) o1).getBrand();
            String brandTwo = ((Phone) o2).getBrand();
            return compareObjects(brandOne, brandTwo);
        });
        descendPhones.sort((o1, o2) -> {
            String brandOne = ((Phone) o1).getBrand();
            String brandTwo = ((Phone) o2).getBrand();
            return -compareObjects(brandOne, brandTwo);
        });

        Assert.isTrue(findAllCheckIfSorted(ascendPhones, ORDER_BY_BRAND, IS_ASCEND), ERROR_PHONES_UNSORTED);
        Assert.isTrue(findAllCheckIfSorted(descendPhones, ORDER_BY_BRAND, IS_DESCEND), ERROR_PHONES_UNSORTED);
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhonesOrderedByModelAscendDescendWhenFindAll() {
        ascendPhones.sort((o1, o2) -> {
            String modelOne = ((Phone) o1).getModel();
            String modelTwo = ((Phone) o2).getModel();
            return compareObjects(modelOne, modelTwo);
        });
        descendPhones.sort((o1, o2) -> {
            String modelOne = ((Phone) o1).getModel();
            String modelTwo = ((Phone) o2).getModel();
            return -compareObjects(modelOne, modelTwo);
        });

        Assert.isTrue(findAllCheckIfSorted(ascendPhones, ORDER_BY_MODEL, IS_ASCEND), ERROR_PHONES_UNSORTED);
        Assert.isTrue(findAllCheckIfSorted(descendPhones, ORDER_BY_MODEL, IS_DESCEND), ERROR_PHONES_UNSORTED);
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhonesOrderedByOsAscendDescendWhenFindAll() {
        ascendPhones.sort((o1, o2) -> {
            String osOne = ((Phone) o1).getOs();
            String osTwo = ((Phone) o2).getOs();
            return compareObjects(osOne, osTwo);
        });
        descendPhones.sort((o1, o2) -> {
            String osOne = ((Phone) o1).getOs();
            String osTwo = ((Phone) o2).getOs();
            return -compareObjects(osOne, osTwo);
        });

        Assert.isTrue(findAllCheckIfSorted(ascendPhones, ORDER_BY_OS, IS_ASCEND), ERROR_PHONES_UNSORTED);
        Assert.isTrue(findAllCheckIfSorted(descendPhones, ORDER_BY_OS, IS_DESCEND), ERROR_PHONES_UNSORTED);
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhonesOrderedByDisplaySizeAscendDescendWhenFindAll() {
        ascendPhones.sort((o1, o2) -> {
            BigDecimal displayOne = ((Phone) o1).getDisplaySizeInches();
            BigDecimal displayTwo = ((Phone) o2).getDisplaySizeInches();
            return compareObjects(displayOne, displayTwo);
        });
        descendPhones.sort((o1, o2) -> {
            BigDecimal displayOne = ((Phone) o1).getDisplaySizeInches();
            BigDecimal displayTwo = ((Phone) o2).getDisplaySizeInches();
            return -compareObjects(displayOne, displayTwo);
        });

        Assert.isTrue(findAllCheckIfSorted(ascendPhones, ORDER_BY_DISPLAY, IS_ASCEND), ERROR_PHONES_UNSORTED);
        Assert.isTrue(findAllCheckIfSorted(descendPhones, ORDER_BY_DISPLAY, IS_DESCEND), ERROR_PHONES_UNSORTED);
    }

    @Test
    @DirtiesContext
    public void shouldAssertPhonesOrderedByPriceAscendDescendWhenFindAll() {
        ascendPhones.sort((o1, o2) -> {
            BigDecimal priceOne = ((Phone) o1).getPrice();
            BigDecimal priceTwo = ((Phone) o2).getPrice();
            return compareObjects(priceOne, priceTwo);
        });
        descendPhones.sort((o1, o2) -> {
            BigDecimal priceOne = ((Phone) o1).getPrice();
            BigDecimal priceTwo = ((Phone) o2).getPrice();
            return -compareObjects(priceOne, priceTwo);
        });

        Assert.isTrue(findAllCheckIfSorted(ascendPhones, ORDER_BY_PRICE, IS_ASCEND), ERROR_PHONES_UNSORTED);
        Assert.isTrue(findAllCheckIfSorted(descendPhones, ORDER_BY_PRICE, IS_DESCEND), ERROR_PHONES_UNSORTED);
    }

    @Test
    @DirtiesContext
    public void shouldAssertMatchesTwoPhonesFindAllSearch() {
        phones = phoneDao.findAll(ALL_RANGE_START, ascendPhones.size(), SEARCH_STRING, STANDARD_ORDER, STANDARD_ASCEND);

        Assert.isTrue(phones.size() == 2, ERROR_PHONES_SEARCH);
    }

    @Test
    @DirtiesContext
    public void shouldAssertMatchesNoPhonesFindAllSearch() {
        phones = phoneDao.findAll(ALL_RANGE_START, ascendPhones.size(), SEARCH_STRING_NO_MATCH, STANDARD_ORDER, STANDARD_ASCEND);

        Assert.isTrue(phones.isEmpty(), ERROR_PHONES_SEARCH);
    }


    private boolean findAllCheckIfSorted(List<Phone> compareList, String order, boolean orderAscend) {
        phones = phoneDao.findAll(ALL_RANGE_START, compareList.size(), STANDARD_SEARCH, order, orderAscend);
        boolean isSorted = true;
        for (int i = 0; i < compareList.size(); i++) {
            Phone tempPhone = (Phone) (phones.get(i));
            if (!compareList.get(i).getId().equals(tempPhone.getId())) {
                isSorted = false;
                break;
            }
        }
        return isSorted;
    }

    private <T extends Comparable<T>> int compareObjects(T objectOne, T objectTwo) {
        if (objectOne == null && objectTwo != null) {
            return NEGATIVE_NUM;
        }
        if (objectOne != null && objectTwo == null) {
            return POSITIVE_NUM;
        }
        if (objectOne == null) {
            return NEUTRAL_NUM;
        }
        return objectOne.compareTo(objectTwo);
    }
}
