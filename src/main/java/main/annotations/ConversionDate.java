package main.annotations;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Component
public class ConversionDate {

    public ConversionDate(@Value("${custom-date}") Date date) {
        System.out.println("ConversionDate.ConversionDate()" + date);
    }

    @Bean
    public static ConversionService conversionService(GenericConverter stringToDate) {
        var c = new DefaultConversionService();
        c.addConverter(stringToDate);
        return c;
    }

    @Bean
    public static GenericConverter stringToDateConverter() {
        return new GenericConverter() {

            private static final Set<ConvertiblePair> pair =
                    Set.of(new ConvertiblePair(String.class, Date.class));

            @Override
            public @Nullable Set<ConvertiblePair> getConvertibleTypes() {
                return pair;
            }

            @Override
            public @Nullable Object convert(
                    @Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

                if (sourceType.getType() != String.class || targetType.getType() != Date.class) {
                    throw new IllegalArgumentException("Cannot convert the types");
                }

                try {
                    return new SimpleDateFormat("yyy-MM-dd").parse((String) source);
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };
    }
}
