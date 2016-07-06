package com.h3c.idcloud.core.adapter.facade.util;

import org.junit.Test;

/**
 * Created by qct on 2016/2/19.
 */
public class FreeMarkerHelperTest {

    @Test
    public void testConvert() throws Exception {
        String json = "{\n" +
                "  \"restore_backup\":{\n" +
                "      \"backup_id\": \"adf\"\n" +
                "  }\n" +
                "}";
        System.out.println(json);
    }
}