package com.es.core.helping;

public class ConstantsCore {
    public static final String ILLEGAL_ARGUMENT_MESSAGE = "Item with current ID already exists";
    public static final String DUPLICATE_ENTRY_MESSAGE = " Duplicate entry, such kind of item already exists";
    public static final String SELECT_PHONE_BY_ID_QUERY = "select * from phones where id = ?";
    public static final String SELECT_PHONES_BY_LIMIT_AND_OFFSET_QUERY = "select * from phones limit ? offset ?";
    public static final String SELECT_COLORS_QUERY = "select * from colors";
    public static final String SELECT_PHONE2COLOR_BY_ID_QUERY = "select * from phone2color where phoneId = ?";
    public static final String PHONES_TABLE_NAME = "phones";
    public static final String GENERATED_KEY_NAME = "id";

    public static final String TEST_CONFIG_LOCATION = "/context/test-config.xml";
    public static final String SELECT_PHONES_COUNT_QUERY = "SELECT COUNT (*) FROM phones";
    public static final String SELECT_MAX_PHONE_ID_QUERY = "SELECT MAX(id)FROM phones";
    public static final String ERROR_PHONE_SAVE = "Error occurred with phone addition: [added,found] = ";
    public static final String ERROR_ID_GENERATE = "ID generation failure: [expected,found] = ";
    public static final String ERROR_OUT_OF_RANGE_KEY = "Result for out of range key should be empty";
    public static final String ERROR_IN_RANGE_KEY  = "Result for in range key should be not empty";
    public static final String ERROR_EMPTY_PHONELIST = "Empty phoneList expected";
    public static final String ERROR_PHONES_FIND_ALL = "Phones [expected,found] = ";

}
