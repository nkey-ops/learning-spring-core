package main.xmlconfig;

import org.springframework.context.annotation.Bean;

import main.xmlconfig.MovieQ.Format;

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

    public static BeanClass bean1() {
        return new BeanClass("horror", Format.VHS);
    }

    public static BeanClass bean2() {
        return new BeanClass("comedy", Format.DVD);
    }

    public void setBeans(
            // @MovieQ(genre = "horror", format = Format.VHS) 
            BeanClass bean) {
        System.out.println(bean);
    }
}
