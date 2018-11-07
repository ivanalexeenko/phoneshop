package com.es.core.model.phone;

import com.es.core.model.getter.GetterInvoker;
import com.es.core.model.row_mapper.ColorRowMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.es.core.helping.ConstantsCore.*;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(Long key) {
        List <Phone> phones = jdbcTemplate.query(SELECT_PHONE_BY_ID_QUERY,new Object[] {key},new BeanPropertyRowMapper<Phone>(Phone.class));
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

    public List findAll(int offset, int limit) throws IllegalAccessException, IntrospectionException, InvocationTargetException {

        List phones = jdbcTemplate.query(SELECT_PHONES_BY_LIMIT_AND_OFFSET_QUERY,new Object[]{limit,offset}, new BeanPropertyRowMapper<Phone>(Phone.class));
        List colors = jdbcTemplate.query(SELECT_COLORS_QUERY, new BeanPropertyRowMapper<Color>(Color.class));

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
                .withTableName(PHONES_TABLE_NAME)
                .usingGeneratedKeyColumns(GENERATED_KEY_NAME);

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
            List<Long> colorIds = jdbcTemplate.query( SELECT_PHONE2COLOR_BY_ID_QUERY,new Object[] {phone.getId()}, new ColorRowMapper());
            if (colorIds != null) {
                setPhoneColors(colorIds,colors,phone);
            }
        }
    }

}