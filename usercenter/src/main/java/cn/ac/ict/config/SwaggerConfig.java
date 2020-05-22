package cn.ac.ict.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger ui 配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 对该包下的api进行监控
                .apis(RequestHandlerSelectors.basePackage("cn.ac.ict.controller"))
                // 只对 app接口 生成swagger文档
                .paths(PathSelectors.regex(".*app.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("急救SOS管理系统")// 设置文档的标题
                .description("Api接口")// 设置文档的描述->1.Overview
                .version("1.0.1")// 设置文档的版本信息-> 1.1 Version information
                .termsOfServiceUrl("www.icbms.com")// 设置文档的License信息->1.3 License information
                .build();
    }
}
