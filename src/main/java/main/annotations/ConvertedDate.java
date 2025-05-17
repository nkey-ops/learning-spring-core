package main.annotations;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ConvertedDate {

    public ConvertedDate(@Value("${custom-date}") Date date) {
        System.out.println("ConvertedDate.ConvertedDate() " + date);
    }

    // @Bean
    // public static CustomEditorConfigurer customEditor(CustomDateEditor dateEditor) {
    //     var customEditorConfigurer = new CustomEditorConfigurer();
    //
    //     customEditorConfigurer.setPropertyEditorRegistrars(
    //             new PropertyEditorRegistrar[] {
    //                 (registry) -> {
    //                     registry.registerCustomEditor(Date.class, dateEditor);
    //                 }
    //             });
    //
    //     return customEditorConfigurer;
    // }
    //
    // @Bean
    // public static CustomDateEditor dateEditor() {
    //     return new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false);
    // }
}
