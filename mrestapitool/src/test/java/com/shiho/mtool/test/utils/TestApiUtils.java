package com.shiho.mtool.test.utils;

import com.shiho.mtool.convert.StringConverter;
import com.shiho.mtool.utils.BaseApiUtils;

public class TestApiUtils extends BaseApiUtils {
    public TestApiUtils(String url) {
        super(url, new StringConverter());
    }
}
