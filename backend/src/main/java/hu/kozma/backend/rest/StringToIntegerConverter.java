package hu.kozma.backend.rest;

import org.springframework.core.convert.converter.Converter;

public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String s) {
        return s.equals("null") || s.equals("NaN") || s.equals("undefined") ? null : Integer.parseInt(s);
    }
}
