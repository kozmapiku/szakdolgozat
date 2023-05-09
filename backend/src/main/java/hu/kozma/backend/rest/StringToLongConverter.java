package hu.kozma.backend.rest;

import org.springframework.core.convert.converter.Converter;

public class StringToLongConverter implements Converter<String, Long> {
    @Override
    public Long convert(String s) {
        return s.equals("null") || s.equals("NaN") || s.equals("undefined") ? null : Long.parseLong(s);
    }
}
