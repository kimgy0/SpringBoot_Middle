package alpacaCorp.shopSite;

import com.oreilly.servlet.MultipartRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.support.MultipartFilter;

@Slf4j
@Configuration
@ComponentScan
/*
 * (basePackageClasses = ShopConfig.class)
 * basePackages = {"alpacaCorp"}
 *  excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION,
 *                 classes = Configuration.class)
 */
public class ShopConfig {
    @Bean
    public DataSource getDataSource()
    {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/healthdb?serverTimezone=UTC");
        dataSourceBuilder.username("jspstudy");
        dataSourceBuilder.password("jspstudy");
        return dataSourceBuilder.build();
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

}