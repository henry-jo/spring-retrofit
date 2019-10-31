package com.henry.springretrofit.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration
import java.io.IOException
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Configuration
class BeanConfig : ApplicationContextAware {
    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

    companion object {
        private lateinit var context: ApplicationContext

        private val mapper: ObjectMapper by lazy {
            val mapper = ObjectMapper()
            val module = SimpleModule()
            module.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
            module.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())
            module.addSerializer(LocalDate::class.java, LocalDateSerializer())
            module.addDeserializer(LocalDate::class.java, LocalDateDeserializer())

            mapper.registerModule(module)
            mapper
        }

        fun getObjectMapper(): ObjectMapper = mapper

        fun <T> getBean(beanClass: Class<T>): T {
            return context.getBean(beanClass)
        }
    }

    private class LocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {
        @Throws(IOException::class)
        override fun serialize(dateTime: LocalDateTime, g: JsonGenerator, sp: SerializerProvider) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            g.writeString(dateTime.format(formatter))
        }
    }

    private class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {
        @Throws(IOException::class)
        override fun deserialize(jp: JsonParser, ctx: DeserializationContext): LocalDateTime {
            try {
                val instant = jp.longValue
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(instant), ZoneId.systemDefault())
            } catch (e: IOException) {
                // ignore
            }

            val s = jp.text
            return if (s.endsWith("Z")) {
                LocalDateTime.ofInstant(Instant.parse(s), ZoneId.systemDefault())
            } else {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                return LocalDateTime.parse(s, formatter)
            }
        }
    }

    private class LocalDateSerializer : JsonSerializer<LocalDate>() {
        @Throws(IOException::class)
        override fun serialize(date: LocalDate, g: JsonGenerator, sp: SerializerProvider) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            g.writeString(date.format(formatter))
        }
    }

    private class LocalDateDeserializer : JsonDeserializer<LocalDate>() {
        @Throws(IOException::class)
        override fun deserialize(jp: JsonParser, ctx: DeserializationContext): LocalDate {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return LocalDate.parse(jp.text, formatter)
        }
    }
}