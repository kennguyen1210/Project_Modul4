package ra.academy.configs;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import ra.academy.dao.catalog.CatalogDao;
import ra.academy.dao.deliverInfo.DeliverInfoDao;
import ra.academy.dao.order.OrderDao;
import ra.academy.dao.orderDetail.OrderDetailDao;
import ra.academy.dao.user.UserDao;
import ra.academy.dao.product.ProductDao;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;



import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"ra.academy"})
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    // CSDL config
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/project_model4");
        dataSource.setUsername("root");
        dataSource.setPassword("Phuong123456");
        return dataSource;
    }
    // upload file config
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(52428800);//50M
        return resolver;
    }
    // thymeleaf config
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }


    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setContentType("UTF-8");
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/admin/js/**","/admin/css/**","/admin/img/**",
                        "/user/js/**","/user/css/**","/user/images/**","/user/fonts/**",
                        "/login/**","/login/imge/**")
                .addResourceLocations("classpath:access/admin/js/","classpath:access/admin/css/",
                        "classpath:access/admin/img/","classpath:access/user/js/",
                        "classpath:access/user/css/","classpath:access/user/images/",
                        "classpath:access/user/fonts/","classpath:access/login/css/","classpath:access/login/imge/");
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource message = new ResourceBundleMessageSource();
        message.setBasename("application");
        message.setDefaultEncoding("utf-8");
        return message;
    }
    // jdbc template
    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }
    @Bean
    public ProductDao productDao(){
        ProductDao st = new ProductDao();
        st.setJdbcTemplate(jdbcTemplate());
        return  st;
    }
    @Bean
    public CatalogDao catalogDao(){
        CatalogDao cat = new CatalogDao();
        cat.setJdbcTemplate(jdbcTemplate());
        return cat;
    }
    @Bean
    public UserDao userDao(){
        UserDao acc = new UserDao();
        acc.setJdbcTemplate(jdbcTemplate());
        return acc;
    }
    @Bean
    public OrderDao orderDao(){
        OrderDao acc = new OrderDao();
        acc.setJdbcTemplate(jdbcTemplate());
        return acc;
    }
    @Bean
    public OrderDetailDao orderDetailDao(){
        OrderDetailDao acc = new OrderDetailDao();
        acc.setJdbcTemplate(jdbcTemplate());
        return acc;
    }
    @Bean
    public DeliverInfoDao deliverInfoDao(){
        DeliverInfoDao dao = new DeliverInfoDao();
        dao.setJdbcTemplate(jdbcTemplate());
        return dao;
    }
    @Bean
    public IDialect conditionalCommentDialect() {
        return new Java8TimeDialect();
    }
    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new Java8TimeDialect());
        return templateEngine;
    }
    @Bean
    public JavaMailSender mailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);  // Use port 465 for SMTPS with SSL

        mailSender.setUsername("tammaoubqn@gmail.com");
        mailSender.setPassword("vdbcomowlcqeehos"); // Replace with your Gmail app password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }
    @Override
    public void  addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor( new AuthInterceptor()).addPathPatterns("/admin/**");
    }

}
