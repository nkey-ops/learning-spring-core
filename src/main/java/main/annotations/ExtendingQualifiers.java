package main.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import main.annotations.Movie.Format;

@Configuration
public class ExtendingQualifiers {

    public static class BeanClass {
        String genre;
        Format format;

        public BeanClass(String genre, Format format) {
            this.genre = genre;
            this.format = format;
        }

        @Override
        public String toString() {
            return "BeanClass [genre=" + genre + ", format=" + format + "]";
        }
    }

    @Bean
    @Movie(genre = "horror", format=  Format.VHS)
    public BeanClass bean1() {
        System.out.println("horror VHS");
        return new BeanClass("horror", Format.VHS);
        
    }

    @Bean
    @Movie(genre = "comedy", format=  Format.DVD)
    public BeanClass bean2() {
        System.out.println("comedy DVD");
        return new BeanClass("comedy", Format.DVD);
    }

    @Autowired
    public void beans(
            @Movie(genre = "horror", format = Format.VHS) 
            BeanClass bean) {
        System.out.println(bean);
        
    }
}
