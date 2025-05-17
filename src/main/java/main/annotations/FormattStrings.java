package main.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Locale;
import java.util.Set;

@Component
public class FormattStrings {
    @Bean
    public static FormattingConversionService formatter(
            AnnotationFormatterFactory<WeirdString> weirdFactory,
            @Qualifier("parser") Parser<String> parser) {

        var formatter = new FormattingConversionService();
        formatter.addFormatterForFieldAnnotation(weirdFactory);

        return formatter;
    }

    @Bean
    public static Printer<String> weirdStringPrinter() {
        return (text, locale) -> {
            if (text != null && text.contains("weird")) {
                return String.format(locale, "@<<--%s->>@", text);
            } else {
                return String.format(locale, text);
            }
        };
    }

    @Bean
    @Qualifier("parser")
    public static Parser<String> parser() {
        return new Parser<>() {
            public String parse(String text, Locale locale) {
                if (text != null && text.contains("weird")) {
                    return String.format(locale, "@->>%s<<-@", text);
                } else {
                    return String.format(locale, text);
                }
            }
        };
    }

    @Bean
    public static AnnotationFormatterFactory<WeirdString> annotationFactoryFormatter(
            Printer<String> weirdStringPrinter, Parser<String> weirdStringParser) {

        return new AnnotationFormatterFactory<WeirdString>() {
            private static final Set<Class<?>> types = Set.of(String.class);

            @Override
            public Set<Class<?>> getFieldTypes() {
                return types;
            }

            @Override
            public Printer<String> getPrinter(WeirdString annotation, Class<?> fieldType) {
                return weirdStringPrinter;
            }

            @Override
            public Parser<String> getParser(WeirdString annotation, Class<?> fieldType) {
                return weirdStringParser;
            }
        };
    }

    @Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.ANNOTATION_TYPE
    })
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface WeirdString {}
}
