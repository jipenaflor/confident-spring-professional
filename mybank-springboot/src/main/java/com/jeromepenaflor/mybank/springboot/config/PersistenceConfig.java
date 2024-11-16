package com.jeromepenaflor.mybank.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

@Configuration
public class PersistenceConfig extends AbstractJdbcConfiguration {

    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    @Override
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(Arrays.asList(
                TimestampToZonedDateTimeConverter.INSTANCE,
                ZonedDateTimeToTimestampConverter.INSTANCE));
    }

    @ReadingConverter
    enum TimestampToZonedDateTimeConverter implements Converter<Timestamp, ZonedDateTime> {
        INSTANCE;

        @Override
        public ZonedDateTime convert(Timestamp source) {
            return source.toLocalDateTime().atZone(DEFAULT_ZONE);
        }

    }

    @WritingConverter
    enum ZonedDateTimeToTimestampConverter implements Converter<ZonedDateTime, Timestamp> {
        INSTANCE;

        @Override
        public Timestamp convert(ZonedDateTime source) {
            return Timestamp.valueOf(source.withZoneSameInstant(DEFAULT_ZONE).toLocalDateTime());
        }
    }

}
