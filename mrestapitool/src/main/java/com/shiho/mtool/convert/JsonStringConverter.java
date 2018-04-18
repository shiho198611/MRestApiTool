package com.shiho.mtool.convert;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by david5_huang on 2016/7/27.
 */
public class JsonStringConverter extends StringConverter {

    public JsonStringConverter() {
        MediaType mediaType = MediaType.parse("application/json");
        this.setMediaType(mediaType);
    }

}
