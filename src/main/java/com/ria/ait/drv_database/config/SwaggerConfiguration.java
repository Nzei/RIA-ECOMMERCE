package com.ria.ait.drv_database.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    public static final Contact CUSTOM_CONTACT = new Contact("Uche Jude Nzei", "uche.com","A00277158@student.ait.ie");
    public static final ApiInfo CUSTOM_API_INFO = new ApiInfo("Uche's REST Api Documentation", "E-commerce rest API", "1.0", "urn.tos",
            CUSTOM_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(CUSTOM_API_INFO);

    }
}
