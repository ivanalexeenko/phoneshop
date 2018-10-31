package com.es.core.model.phone;

import com.es.core.model.getter.GetterInvoker;
import com.es.core.model.row_mapper.ColorRowMapper;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String ILLEGAL_ARGUMENT_MESSAGE = "Item with current ID already exists";
    private static final String DUPLICATE_ENTRY_MESSAGE = " Duplicate entry, such kind of item already exists";

    public Optional<Phone> get(final Long key) {

        List<Phone> phones = jdbcTemplate.query("select * from phones where id = " + key, new BeanPropertyRowMapper(Phone.class));

        if(phones.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(phones.get(0));
    }

    public void save(final Phone phone) throws InvocationTargetException, IllegalAccessException, IntrospectionException,IllegalArgumentException {

        if(get(phone.getId()).isPresent()) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
        GetterInvoker getterInvoker = new GetterInvoker();
        Field[] fields = Phone.class.getDeclaredFields();
        Object[] values = new Object[fields.length];

        invokeGetters(fields,values,getterInvoker,phone);
        insert(fields,values);

    }

    public List<Phone> findAll(int offset, int limit) throws IllegalAccessException, IntrospectionException, InvocationTargetException {

        List<Phone> phones = jdbcTemplate.query("select * from phones limit " + limit + " offset " + offset, new BeanPropertyRowMapper(Phone.class));
        List<Color> colors = jdbcTemplate.query("select * from colors", new BeanPropertyRowMapper(Color.class));

        setPhonesColors(phones,colors);

        return phones;
    }

    private void invokeGetters(Field[] fields,Object[] values,GetterInvoker getterInvoker,final Phone phone) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            values[i] = getterInvoker.invokeGetter(phone, fieldName);
        }
    }

    private void fillParameters(Field[] fields,Object[] values,Map<String,Object> parameters) {
        for(int i = 0;i < fields.length;i++) {
            parameters.put(fields[i].getName(),values[i]);
        }
    }

    private void executeInsertion(SimpleJdbcInsert simpleJdbcInsert,Map<String,Object> parameters) {
        try {
            simpleJdbcInsert.execute(parameters);
        }
        catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(DUPLICATE_ENTRY_MESSAGE);
        }
    }

    private void insert(Field[] fields,Object[] values) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("phones")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        fillParameters(fields,values,parameters);
        executeInsertion(simpleJdbcInsert,parameters);
    }

    private void setPhoneColors(List<Long> colorIds,List<Color> colors,Phone phone) {
        Set<Color> colors2phones = new HashSet<>();
        for (Long colorId : colorIds) {
            Optional<Color> any = colors.stream().filter(color -> color.getId().equals(colorId)).findAny();
            any.ifPresent(colors2phones::add);
        }
        phone.setColors(colors2phones);
    }

    private void setPhonesColors(List<Phone> phones,List<Color> colors) {
        for (Phone phone : phones) {
            List<Long> colorIds = jdbcTemplate.query("select * from phone2color where phoneId = " + phone.getId(), new ColorRowMapper());
            if (colorIds != null) {
                setPhoneColors(colorIds,colors,phone);
            }
        }
    }

}