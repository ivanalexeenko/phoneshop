package com.es.core.model.dao;

import com.es.core.dao.ColorDao;
import com.es.core.model.phone.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context/test-config.xml")
public class JdbcColorDaoTest {
    private static final String COLORS_DO_NOT_MATCH_MESSAGE = "Found color list components do not match actual colors";
    private static final Object[][] colorsInfo = {
            {1000L, "Black"}, {1001L, "White"}, {1002L, "Yellow"},
            {1003L, "Blue"}, {1004L, "Red"}, {1005L, "Purple"},
            {1006L, "Gray"}, {1007L, "Green"}, {1008L, "Pink"},
            {1009L, "Gold"}, {1010L, "Silver"}, {1011L, "Orange"},
            {1012L, "Brown"}, {1013L, "256"}
    };

    private List<Color> colorList;

    @Autowired
    private ColorDao colorDao;

    @Before
    public void init() {
        colorList = new ArrayList<Color>();
        for (Object[] colorInfo : colorsInfo) {
            Long id = (Long) (colorInfo[0]);
            String code = (String) colorInfo[1];
            colorList.add(new Color(id, code));
        }
    }

    @Test
    @DirtiesContext
    public void shouldVerifyAllColorsFoundCorrectlyWhenGetColors() {
        List<Color> colorDaoList = colorDao.getColors();

        Assert.isTrue(colorList.containsAll(colorDaoList), COLORS_DO_NOT_MATCH_MESSAGE);
    }
}
