package com.es.core.dao;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import com.es.core.model.rowmapper.ColorRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcPhoneDao implements PhoneDao {
    private static final String SELECT_PHONE_BY_ID_QUERY = "select * from phones where id = ?";
    private static final String SELECT_PHONE2COLOR_BY_ID_QUERY = "select * from phone2color where phoneId = ?";
    private static final String COUNT_PHONES_STOCK_BIGGER_ZERO_BY_LIMIT_AND_OFFSET_QUERY = "select count(pointFour.id) from (select * from (select * from phones pointTwo" +
            " where pointTwo.id in " +
            "(select phoneId from stocks pointOne" +
            " where pointOne.stock > 0)) pointThree" +
            " where ((pointThree.brand like ? or pointThree.model like ? " +
            "or pointThree.os like ? or pointThree.displaySizeInches like ? or pointThree.price like ?) and pointThree.price is not null )) pointFour";
    private static final String SELECT_PHONES_STOCK_BIGGER_ZERO_BY_LIMIT_AND_OFFSET_ORDERED_QUERY_FIRST = "select * from(select * from(select * from (select * from phones pointTwo" +
            " where pointTwo.id in " +
            "(select phoneId from stocks pointOne" +
            " where pointOne.stock > 0)) pointThree" +
            " where ((pointThree.brand like ? or pointThree.model like ? " +
            "or pointThree.os like ? or pointThree.displaySizeInches like ? or pointThree.price like ?) and pointThree.price is not null )) pointFour ";
    private static final String ORDERED_TABLE_NAME = " order by pointFour.";
    private static final String ASC_ORDERED_QUERY_PART = " asc";
    private static final String DESC_ORDERED_QUERY_PART = " desc";
    private static final String SELECT_PHONES_STOCK_BIGGER_ZERO_BY_LIMIT_AND_OFFSET_ORDERED_QUERY_LAST = " ) pointFive limit ? offset ?";
    private static final String DUPLICATE_ENTRY_MESSAGE = " Duplicate entry, such kind of item already exists";
    private static final String PHONES_TABLE_NAME = "phones";
    private static final String GENERATED_KEY_NAME = "id";
    private static final String[] FIELD_NAMES = {"id", "brand", "model", "price", "displaySizeInches", "weightGr", "lengthMm", "widthMm",
            "heightMm", "announced", "deviceType", "os", "displayResolution", "pixelDensity", "displayTechnology", "backCameraMegapixels",
            "frontCameraMegapixels", "ramGb", "internalStorageGb", "batteryCapacityMah", "talkTimeHours", "standByTimeHours", "bluetooth",
            "positioning", "imageUrl", "description"};
    private static final String DELIMITER = "";
    private static final String ORDER_BY_PREFIX = "%";
    private final JdbcTemplate jdbcTemplate;
    private final ColorDao colorDao;

    @Autowired
    public JdbcPhoneDao(JdbcTemplate jdbcTemplate,ColorDao colorDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.colorDao = colorDao;
    }

    public Optional<Phone> get(Long key) {
        List<Phone> phones = jdbcTemplate.query(SELECT_PHONE_BY_ID_QUERY, new Object[]{key}, new BeanPropertyRowMapper<Phone>(Phone.class));
        if (phones == null || phones.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(phones.get(0));
    }

    @Override
    public <T> void save(T phoneParam) {
        Phone phone = (Phone) phoneParam;
        Object[] values = new Object[FIELD_NAMES.length];
        invokeGetters(values, phone);
        insert(FIELD_NAMES, values);
    }

    public List findAll(int offset, int limit, String search, String orderBy, Boolean isAscend) {
        String partSearch = fullSearchToPartSearch(search);
        List<Phone> phones = jdbcTemplate.query(prepareOrderedQuery(orderBy, isAscend),
                new Object[]{partSearch, partSearch, partSearch, partSearch, partSearch, limit, offset},
                new BeanPropertyRowMapper<Phone>(Phone.class));

        List<Color> colors = colorDao.getColors();
        setPhonesColors(phones, colors);
        return phones;
    }

    public Integer countPhonesStockBiggerZero(String search) {
        String partSearch = fullSearchToPartSearch(search);
        return jdbcTemplate.queryForObject(COUNT_PHONES_STOCK_BIGGER_ZERO_BY_LIMIT_AND_OFFSET_QUERY,
                new Object[]{partSearch, partSearch, partSearch, partSearch, partSearch}, Integer.class);
    }

    private void invokeGetters(Object[] values, Phone phone) {
        values[0] = phone.getId();
        values[1] = phone.getBrand();
        values[2] = phone.getModel();
        values[3] = phone.getPrice();
        values[4] = phone.getDisplaySizeInches();
        values[5] = phone.getWeightGr();
        values[6] = phone.getLengthMm();
        values[7] = phone.getWidthMm();
        values[8] = phone.getHeightMm();
        values[9] = phone.getAnnounced();
        values[10] = phone.getDeviceType();
        values[11] = phone.getOs();
        values[12] = phone.getDisplayResolution();
        values[13] = phone.getPixelDensity();
        values[14] = phone.getDisplayTechnology();
        values[15] = phone.getBackCameraMegapixels();
        values[16] = phone.getFrontCameraMegapixels();
        values[17] = phone.getRamGb();
        values[18] = phone.getInternalStorageGb();
        values[19] = phone.getBatteryCapacityMah();
        values[20] = phone.getTalkTimeHours();
        values[21] = phone.getStandByTimeHours();
        values[22] = phone.getBluetooth();
        values[23] = phone.getPositioning();
        values[24] = phone.getImageUrl();
        values[25] = phone.getDescription();
    }

    private void fillParameters(String[] fieldNames, Object[] values, Map<String, Object> parameters) {
        for (int i = 0; i < fieldNames.length; i++) {
            parameters.put(fieldNames[i], values[i]);
        }
    }

    private void executeInsertion(SimpleJdbcInsert simpleJdbcInsert, Map<String, Object> parameters) {
        try {
            simpleJdbcInsert.execute(parameters);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(DUPLICATE_ENTRY_MESSAGE, e);
        }
    }

    private void insert(String[] fieldNames, Object[] values) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName(PHONES_TABLE_NAME)
                .usingGeneratedKeyColumns(GENERATED_KEY_NAME);
        Map<String, Object> parameters = new HashMap<>();
        fillParameters(fieldNames, values, parameters);
        executeInsertion(simpleJdbcInsert, parameters);
    }

    private void setPhoneColors(List<Long> colorIds, List<Color> colors, Phone phone) {
        Set<Color> colors2phones = new HashSet<>();
        for (Long colorId : colorIds) {
            Optional<Color> optionalColor = colors.stream().filter(color -> color.getId().equals(colorId)).findAny();
            optionalColor.ifPresent(colors2phones::add);
        }
        phone.setColors(colors2phones);
    }

    private void setPhonesColors(List<Phone> phones, List<Color> colors) {
        for (Phone phone : phones) {
            List<Long> colorIds = jdbcTemplate.query(SELECT_PHONE2COLOR_BY_ID_QUERY, new Object[]{phone.getId()}, new ColorRowMapper());
            if (colorIds != null) {
                setPhoneColors(colorIds, colors, phone);
            }
        }
    }

    private String fullSearchToPartSearch(String search) {
        StringBuffer partSearch = new StringBuffer("");
        partSearch.append(ORDER_BY_PREFIX);
        partSearch.append(search);
        partSearch.append(ORDER_BY_PREFIX);
        return partSearch.toString();
    }

    private String prepareOrderedQuery(String order, boolean isAscend) {
        if (order == null || order.isEmpty()) {
            order = "";
            return String.join(DELIMITER, SELECT_PHONES_STOCK_BIGGER_ZERO_BY_LIMIT_AND_OFFSET_ORDERED_QUERY_FIRST,
                    order, SELECT_PHONES_STOCK_BIGGER_ZERO_BY_LIMIT_AND_OFFSET_ORDERED_QUERY_LAST);
        }
        String criteria = (isAscend) ? ASC_ORDERED_QUERY_PART : DESC_ORDERED_QUERY_PART;
        return String.join(DELIMITER, SELECT_PHONES_STOCK_BIGGER_ZERO_BY_LIMIT_AND_OFFSET_ORDERED_QUERY_FIRST, ORDERED_TABLE_NAME
                , order, criteria, SELECT_PHONES_STOCK_BIGGER_ZERO_BY_LIMIT_AND_OFFSET_ORDERED_QUERY_LAST);
    }
}