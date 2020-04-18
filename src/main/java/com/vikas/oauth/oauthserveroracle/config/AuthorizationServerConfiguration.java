package com.vikas.oauth.oauthserveroracle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;

import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

@Configuration
@EnableSwagger2
public class AuthorizationServerConfiguration implements AuthorizationServerConfigurer {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Value("${app.client.id}")
    private String clientId;
    @Value("${app.client.secret}")
    private String clientSecret;
//    @Value("${info.build.name}")
//    private String infoBuildName;

    @Value("${host.full.dns.auth.link}")
    private String authLink;

    
//    @Autowired
//    PasswordConfig passwordConfig;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
	UserDetailsService userDetailsService;

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Bean
    TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jdbcTokenStore());
        endpoints.userDetailsService(userDetailsService);
        endpoints.authenticationManager(authenticationManager)
        .approvalStoreDisabled();
    }
    
    @Bean
    public Docket api() {
    	
    	 List<ResponseMessage> list = new java.util.ArrayList<>();
         list.add(new ResponseMessageBuilder().code(500).message("500 message")
                 .responseModel(new ModelRef("Result")).build());
         list.add(new ResponseMessageBuilder().code(401).message("Unauthorized")
                 .responseModel(new ModelRef("Result")).build());
         list.add(new ResponseMessageBuilder().code(406).message("Not Acceptable")
                 .responseModel(new ModelRef("Result")).build());
         
         
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors
                .basePackage("com.vikas.oauth.oauthserveroracle"))
            .paths(PathSelectors.regex("/.*"))
            .build().securitySchemes(Collections.singletonList(securitySchema()))
            .securityContexts(Collections.singletonList(securityContext())).pathMapping("/")
            .useDefaultResponseMessages(false).apiInfo(apiEndPointsInfo()).globalResponseMessage(RequestMethod.GET, list)
            .globalResponseMessage(RequestMethod.POST, list);
    }
    
    private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        authorizationScopeList.add(new AuthorizationScope("READ WRITE", "read all"));
//        authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
//        authorizationScopeList.add(new AuthorizationScope("write", "access all"));

        List<GrantType> grantTypes =  new ArrayList<>();
        GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(authLink+"/oauth/token");

        grantTypes.add(creGrant);

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/user/**"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("READ WRITE", "read all");
//        authorizationScopes[1] = new AuthorizationScope("WRITE", "trust all");

        return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
    }
    
    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(clientId, clientSecret, "", "", "", ApiKeyVehicle.HEADER, "", " ");
    }
    
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Spring Boot REST API")
            .description("Employee Management REST API")
            .contact(new Contact("Ramesh Fadatare", "www.javaguides.net", "ramesh24fadatare@gmail.com"))
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0.0")
            .build();
    }
}
