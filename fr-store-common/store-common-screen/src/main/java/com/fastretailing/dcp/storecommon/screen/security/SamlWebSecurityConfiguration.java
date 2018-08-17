/**
 * @(#)SamlWebSecurityConfiguration.java
 *
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.saml2.metadata.provider.ResourceBackedMetadataProvider;
import org.opensaml.util.resource.ResourceException;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.SAMLBootstrap;
import org.springframework.security.saml.SAMLEntryPoint;
import org.springframework.security.saml.SAMLLogoutFilter;
import org.springframework.security.saml.SAMLLogoutProcessingFilter;
import org.springframework.security.saml.SAMLProcessingFilter;
import org.springframework.security.saml.SAMLWebSSOHoKProcessingFilter;
import org.springframework.security.saml.context.SAMLContextProviderLB;
import org.springframework.security.saml.key.EmptyKeyManager;
import org.springframework.security.saml.key.KeyManager;
import org.springframework.security.saml.log.SAMLDefaultLogger;
import org.springframework.security.saml.metadata.CachingMetadataManager;
import org.springframework.security.saml.metadata.ExtendedMetadata;
import org.springframework.security.saml.metadata.ExtendedMetadataDelegate;
import org.springframework.security.saml.metadata.MetadataDisplayFilter;
import org.springframework.security.saml.metadata.MetadataGenerator;
import org.springframework.security.saml.metadata.MetadataGeneratorFilter;
import org.springframework.security.saml.parser.ParserPoolHolder;
import org.springframework.security.saml.processor.HTTPPostBinding;
import org.springframework.security.saml.processor.HTTPRedirectDeflateBinding;
import org.springframework.security.saml.processor.SAMLBinding;
import org.springframework.security.saml.processor.SAMLProcessorImpl;
import org.springframework.security.saml.util.VelocityFactory;
import org.springframework.security.saml.websso.SingleLogoutProfile;
import org.springframework.security.saml.websso.SingleLogoutProfileImpl;
import org.springframework.security.saml.websso.WebSSOProfile;
import org.springframework.security.saml.websso.WebSSOProfileConsumer;
import org.springframework.security.saml.websso.WebSSOProfileConsumerHoKImpl;
import org.springframework.security.saml.websso.WebSSOProfileConsumerImpl;
import org.springframework.security.saml.websso.WebSSOProfileECPImpl;
import org.springframework.security.saml.websso.WebSSOProfileHoKImpl;
import org.springframework.security.saml.websso.WebSSOProfileImpl;
import org.springframework.security.saml.websso.WebSSOProfileOptions;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.RequestContextFilter;
import com.fastretailing.dcp.storecommon.screen.authentication.AuditSecurityContextLogoutHandler;
import com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionHttpSessionRequestCache;
import com.fastretailing.dcp.storecommon.screen.authentication.saml.OpenSamlResourceLoader;
import com.fastretailing.dcp.storecommon.screen.authentication.saml.SamlUserDetailsServiceImpl;
import com.fastretailing.dcp.storecommon.screen.authorization.ScreenRoleDatabaseVoter;
import com.fastretailing.dcp.storecommon.screen.config.DevelopmentConfiguration;
import com.fastretailing.dcp.storecommon.screen.config.SamlConfiguration;
import com.fastretailing.dcp.storecommon.screen.util.HeaderUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring security configuration for SAML authentication.
 */
@Configuration
@ComponentScan({"com.fastretailing.dcp.common.util", "com.fastretailing.dcp.common.api.client",
        "com.fastretailing.dcp.common.api.hmac", "com.fastretailing.dcp.common.api.uri",
        "org.springframework.security.saml"})
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(50)
@Slf4j
public class SamlWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /** Application configuration for SAML authentication. */
    @Autowired
    private SamlConfiguration samlConfiguration;

    /** Configuration for development. */
    @Autowired
    private DevelopmentConfiguration developmentConfiguration;

    /** User details service for SAML authentication. */
    @Autowired
    private SamlUserDetailsServiceImpl samlUserDetailsServiceImpl;

    /** Authorization voter for screen role on database. */
    @Autowired
    private ScreenRoleDatabaseVoter screenRoleDatabaseVoter;

    /** Background task timer. */
    private Timer backgroundTaskTimer;

    /** HTTP connection manager for multi-thread. */
    private MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager;

    /**
     * Initialization processing to be executed after constructor processing.
     */
    @PostConstruct
    public void initialize() {
        this.backgroundTaskTimer = new Timer(true);
        this.multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();
    }

    /**
     * Destroy processing to be executed before destroy of own.
     */
    @PreDestroy
    public void destroy() {
        this.backgroundTaskTimer.purge();
        this.backgroundTaskTimer.cancel();
        this.multiThreadedHttpConnectionManager.shutdown();
    }

    /**
     * Initialization of the velocity engine.
     * 
     * @return velocity engine.
     */
    @Bean
    public VelocityEngine velocityEngine() {
        return VelocityFactory.getEngine();
    }

    /**
     * Initialization of XML parser pool needed for OpenSAML parsing.
     * 
     * @return XML parser pool.
     */
    @Bean(initMethod = "initialize")
    public StaticBasicParserPool parserPool() {
        return new StaticBasicParserPool();
    }

    /**
     * Initialization of parser pool holder.
     * 
     * @return Parser pool holder.
     */
    @Bean(name = "parserPoolHolder")
    public ParserPoolHolder parserPoolHolder() {
        return new ParserPoolHolder();
    }

    /**
     * Initialization of HTTP client. Bindings, encoders and decoders used for creating and parsing
     * messages.
     * 
     * @return HTTP client.
     */
    @Bean
    public HttpClient httpClient() {
        return new HttpClient(this.multiThreadedHttpConnectionManager);
    }

    /**
     * Initialization of SAML authentication provider responsible for validating of received SAML
     * messages.
     * 
     * @return SAML authentication provider.
     */
    @Bean
    public SAMLAuthenticationProvider samlAuthenticationProvider() {
        SAMLAuthenticationProvider samlAuthenticationProvider = new SAMLAuthenticationProvider();
        samlAuthenticationProvider.setUserDetails(samlUserDetailsServiceImpl);
        samlAuthenticationProvider.setForcePrincipalAsString(false);
        return samlAuthenticationProvider;
    }

    /**
     * In order to get SAML to work for SP servers doing SSL termination, `SAMLContextProviderLB`
     * has to be used instead of `SAMLContextProviderImpl` to prevent the following exception:- <br>
     * "SAML message intended destination endpoint 'https://server/app/saml/SSO' did not match the
     * recipient endpoint 'http://server/app/saml/SSO'" <br>
     * 
     * @return SAML context provider for load balancer.
     */
    @Bean
    public SAMLContextProviderLB contextProvider() {
        SAMLContextProviderLB contextProviderLb = new SAMLContextProviderLB();
        contextProviderLb.setScheme(samlConfiguration.getSpSchema());
        contextProviderLb.setServerName(samlConfiguration.getSpServerName());
        contextProviderLb.setServerPort(samlConfiguration.getSpHttpsPort());
        contextProviderLb
                .setIncludeServerPortInRequestURL(samlConfiguration.getSpHttpsPort() != 443);
        contextProviderLb.setContextPath(samlConfiguration.getSpContextPath());
        return contextProviderLb;
    }

    /**
     * Initialization of OpenSAML library, must be static to prevent "ObjectPostProcessor is a
     * required bean" exception. By default, Spring Security SAML uses SHA-1. So, use
     * `DefaultSAMLBootstrap` to use SHA-256.
     * 
     * @return SAML bootstrap.
     */
    @Bean
    public static SAMLBootstrap sAMLBootstrap() {
        return new SAMLBootstrap();
    }

    /**
     * Initialization of logger for SAML messages and events.
     * 
     * @return Logger for SAML messages and events.
     */
    @Bean
    public SAMLDefaultLogger samlLogger() {
        return new SAMLDefaultLogger();
    }

    /**
     * Initialization of SAML 2.0 WebSSO Assertion Consumer.
     * 
     * @return SAML 2.0 WebSSO Assertion Consumer.
     */
    @Bean
    public WebSSOProfileConsumer webSSOprofileConsumer() {
        WebSSOProfileConsumerImpl ssoProfileConsumer = new WebSSOProfileConsumerImpl();
        ssoProfileConsumer.setMaxAssertionTime(samlConfiguration.getMaxAssertionTime());
        ssoProfileConsumer.setResponseSkew(samlConfiguration.getResponseSkew());
        ssoProfileConsumer.setMaxAuthenticationAge(samlConfiguration.getMaxAuthenticationAge());
        return ssoProfileConsumer;
    }

    /**
     * Initialization of SAML 2.0 Holder-of-Key WebSSO Assertion Consumer.
     * 
     * @return SAML 2.0 Holder-of-Key WebSSO Assertion Consumer.
     */
    @Bean
    public WebSSOProfileConsumerHoKImpl hokWebSSOprofileConsumer() {
        WebSSOProfileConsumerHoKImpl ssoProfileConsumerHoK = new WebSSOProfileConsumerHoKImpl();
        ssoProfileConsumerHoK.setMaxAssertionTime(samlConfiguration.getMaxAssertionTime());
        ssoProfileConsumerHoK.setResponseSkew(samlConfiguration.getResponseSkew());
        ssoProfileConsumerHoK.setMaxAuthenticationAge(samlConfiguration.getMaxAuthenticationAge());
        return ssoProfileConsumerHoK;
    }

    /**
     * Initialization of SAML 2.0 Web SSO profile.
     * 
     * @return SAML 2.0 Web SSO profile.
     */
    @Bean
    public WebSSOProfile webSSOprofile() {
        WebSSOProfileImpl webSsoProfile = new WebSSOProfileImpl();
        return webSsoProfile;
    }

    /**
     * Initialization of SAML 2.0 Holder-of-Key Web SSO profile.
     * 
     * @return SAML 2.0 Holder-of-Key Web SSO profile.
     */
    @Bean
    public WebSSOProfileHoKImpl hokWebSSOprofile() {
        return new WebSSOProfileHoKImpl();
    }

    /**
     * Initialization of SAML 2.0 ECP profile.
     * 
     * @return SAML 2.0 ECP profile.
     */
    @Bean
    public WebSSOProfileECPImpl ecpprofile() {
        return new WebSSOProfileECPImpl();
    }

    /**
     * Initialization of logout profile.
     * 
     * @return Logout profile.
     */
    @Bean
    public SingleLogoutProfile logoutprofile() {
        SingleLogoutProfileImpl logoutProfile = new SingleLogoutProfileImpl();
        logoutProfile.setMaxAssertionTime(samlConfiguration.getMaxAssertionTime());
        logoutProfile.setResponseSkew(samlConfiguration.getResponseSkew());
        return logoutProfile;
    }

    /**
     * Initialization of central storage of cryptographic keys.
     * 
     * @return Central storage of cryptographic keys.
     */
    @Bean
    public KeyManager keyManager() {
        return new EmptyKeyManager();
    }

    /**
     * Initialization of WebSSO profile options.
     * 
     * @return WebSSO profile options.
     */
    @Bean
    public WebSSOProfileOptions defaultWebSSOProfileOptions() {

        WebSSOProfileOptions webSSOProfileOptions = new WebSSOProfileOptions();

        // Disable element scoping when sending requests to IdP to prevent
        // "Response has invalid status code urn:oasis:names:tc:SAML:2.0:status:Responder, status
        // message is null"
        // exception
        webSSOProfileOptions.setIncludeScoping(false);

        return webSSOProfileOptions;

    }

    /**
     * Initialization of entry point to initialize authentication.
     * 
     * @return Entry point to initialize authentication.
     */
    @Bean
    public SAMLEntryPoint samlEntryPoint() {
        SAMLEntryPoint samlEntryPoint = new SAMLEntryPoint();
        samlEntryPoint.setDefaultProfileOptions(defaultWebSSOProfileOptions());
        return samlEntryPoint;
    }

    /**
     * Initialization of extended metadata.
     * 
     * @return Extended metadata.
     */
    @Bean
    public ExtendedMetadata extendedMetadata() {
        ExtendedMetadata extendedMetadata = new ExtendedMetadata();
        extendedMetadata.setIdpDiscoveryEnabled(false);
        extendedMetadata.setSignMetadata(false);
        return extendedMetadata;
    }

    /**
     * Initialization of extended metadata delegate.
     * 
     * @return extended metadata delegate.
     * @throws MetadataProviderException When the metadata file is invalid.
     * @throws ResourceException When metadata file can not be found or can not be read.
     */
    @Bean
    @Qualifier("idp-ssocircle")
    public ExtendedMetadataDelegate ssoCircleExtendedMetadataProvider()
            throws MetadataProviderException, ResourceException {
        ExtendedMetadataDelegate extendedMetadataDelegate = null;
        if (!developmentConfiguration.isDevelopModeEnabled()) {
            org.opensaml.util.resource.Resource resource =
                    OpenSamlResourceLoader.getResource(samlConfiguration.getIdpMetadataPath());
            ResourceBackedMetadataProvider resourceBackedMetadataProvider =
                    new ResourceBackedMetadataProvider(this.backgroundTaskTimer, resource);
            resourceBackedMetadataProvider.setParserPool(parserPool());
            extendedMetadataDelegate = new ExtendedMetadataDelegate(resourceBackedMetadataProvider,
                    extendedMetadata());
            // Disable metadata trust check to prevent "Signature trust establishment failed for
            // metadata entry" exception
            extendedMetadataDelegate.setMetadataTrustCheck(false);
            // extendedMetadataDelegate.setMetadataRequireSignature(false);
            backgroundTaskTimer.purge();
        }
        return extendedMetadataDelegate;
    }

    /**
     * Initialization of IDP metadata configuration.
     * 
     * @return IDP metadata configuration.
     * @throws MetadataProviderException When the metadata file is invalid.
     * @throws ResourceException When metadata file can not be found or can not be read.
     */
    @Bean
    @Qualifier("metadata")
    public CachingMetadataManager metadata() throws MetadataProviderException, ResourceException {
        List<MetadataProvider> providers = new ArrayList<>();
        ExtendedMetadataDelegate extendedMetadataDelegate = ssoCircleExtendedMetadataProvider();
        if (extendedMetadataDelegate != null) {
            providers.add(extendedMetadataDelegate);
        }
        return new CachingMetadataManager(providers);
    }

    /**
     * Initialization of filter automatically generates default SP metadata.
     * 
     * @return Filter automatically generates default SP metadata.
     */
    @Bean
    public MetadataGenerator metadataGenerator() {
        MetadataGenerator metadataGenerator = new MetadataGenerator();
        log.debug("Entity base URL={}", samlConfiguration.getEntityBaseUrl());
        metadataGenerator.setEntityBaseURL(samlConfiguration.getEntityBaseUrl());
        metadataGenerator.setEntityId(samlConfiguration.getEntityId());
        metadataGenerator.setExtendedMetadata(extendedMetadata());
        metadataGenerator.setIncludeDiscoveryExtension(false);
        metadataGenerator.setRequestSigned(false);
        return metadataGenerator;
    }

    /**
     * Initialization of metadata display filter. The filter is waiting for connections on URL
     * suffixed with filterSuffix and presents SP metadata there.
     * 
     * @return metadata display filter.
     */
    @Bean
    public MetadataDisplayFilter metadataDisplayFilter() {
        return new MetadataDisplayFilter();
    }

    /**
     * Initialization of handler deciding where to redirect user after successful login.
     * 
     * @return Handler deciding where to redirect user after successful login.
     */
    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler successRedirectHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successRedirectHandler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        successRedirectHandler.setRequestCache(new DynamicRedirectionHttpSessionRequestCache(
                samlConfiguration.getLoginUrlMap(), samlConfiguration.getSuccessLoginDefaultUrl()));
        return successRedirectHandler;
    }

    /**
     * Initialization of handler deciding where to redirect user after failed login.
     * 
     * @return Handler deciding where to redirect user after failed login.
     */
    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler failureHandler =
                new SimpleUrlAuthenticationFailureHandler();
        failureHandler.setUseForward(true);
        failureHandler.setDefaultFailureUrl(samlConfiguration.getFailedLoginDefaultUrl());
        return failureHandler;
    }

    /**
     * Initialization of processing filter for Holder-of-Key WebSSO profile messages.
     * 
     * @return Processing filter for Holder-of-Key WebSSO profile messages.
     * @throws Exception If an error occurs.
     */
    @Bean
    public SAMLWebSSOHoKProcessingFilter samlWebSSOHoKProcessingFilter() throws Exception {
        SAMLWebSSOHoKProcessingFilter samlWebSsoHoKProcessingFilter =
                new SAMLWebSSOHoKProcessingFilter();
        samlWebSsoHoKProcessingFilter.setAuthenticationSuccessHandler(successRedirectHandler());
        samlWebSsoHoKProcessingFilter.setAuthenticationManager(authenticationManager());
        samlWebSsoHoKProcessingFilter
                .setAuthenticationFailureHandler(authenticationFailureHandler());
        return samlWebSsoHoKProcessingFilter;
    }

    /**
     * Initialization of processing filter for WebSSO profile messages.
     * 
     * @return Processing filter for WebSSO profile messages.
     * @throws Exception If an error occurs.
     */
    @Bean
    public SAMLProcessingFilter samlWebSSOProcessingFilter() throws Exception {
        SAMLProcessingFilter samlWebSSOProcessingFilter = new SAMLProcessingFilter();
        samlWebSSOProcessingFilter.setAuthenticationManager(authenticationManager());
        samlWebSSOProcessingFilter.setAuthenticationSuccessHandler(successRedirectHandler());
        samlWebSSOProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return samlWebSSOProcessingFilter;
    }

    /**
     * Initialization of metadata generator filter.
     * 
     * @return Metadata generator filter.
     */
    @Bean
    public MetadataGeneratorFilter metadataGeneratorFilter() {
        return new MetadataGeneratorFilter(metadataGenerator());
    }

    /**
     * Initialization of handler for successful logout.
     * 
     * @return Handler for successful logout.
     */
    @Bean
    public SimpleUrlLogoutSuccessHandler successLogoutHandler() {
        SimpleUrlLogoutSuccessHandler successLogoutHandler = new SimpleUrlLogoutSuccessHandler();
        successLogoutHandler.setDefaultTargetUrl(samlConfiguration.getSuccessLogoutUrl());
        return successLogoutHandler;
    }

    /**
     * Initialization of logout handler terminating local session.
     * 
     * @return Logout handler.
     */
    @Bean
    public SecurityContextLogoutHandler logoutHandler() {
        AuditSecurityContextLogoutHandler logoutHandler = new AuditSecurityContextLogoutHandler();
        logoutHandler.setInvalidateHttpSession(true);
        logoutHandler.setClearAuthentication(true);
        return logoutHandler;
    }

    /**
     * Initialization of logout processing filter with the one processing SAML Single Logout
     * messages.
     * 
     * @return Logout processing filter.
     */
    @Bean
    public SAMLLogoutProcessingFilter samlLogoutProcessingFilter() {
        return new SAMLLogoutProcessingFilter(successLogoutHandler(), logoutHandler());
    }

    /**
     * Initialization of logout processing filter with the one processing SAML messages.
     * 
     * @return Logout processing filter.
     */
    @Bean
    public SAMLLogoutFilter samlLogoutFilter() {
        return new SAMLLogoutFilter(successLogoutHandler(), new LogoutHandler[] {logoutHandler()},
                new LogoutHandler[] {logoutHandler()});
    }

    /**
     * Initialization of HTTP POST binding.
     * 
     * @return HTTP POST binding.
     */
    @Bean
    public HTTPPostBinding httpPostBinding() {
        return new HTTPPostBinding(parserPool(), velocityEngine());
    }

    /**
     * Initialization of HTTP redirect binding.
     * 
     * @return HTTP redirect binding.
     */
    @Bean
    public HTTPRedirectDeflateBinding httpRedirectDeflateBinding() {
        return new HTTPRedirectDeflateBinding(parserPool());
    }

    /**
     * Initialization of SAML processor that use redirect binding and POST binding.
     * 
     * @return SAML processor.
     */
    @Bean
    public SAMLProcessorImpl processor() {
        Collection<SAMLBinding> bindings = new ArrayList<>();
        bindings.add(httpRedirectDeflateBinding());
        bindings.add(httpPostBinding());
        return new SAMLProcessorImpl(bindings);
    }

    /**
     * Define the security filter chain in order to support SSO Authentication by using SAML 2.0.
     * 
     * @return Filter chain proxy.
     * @throws Exception If an error occurs.
     */
    @Bean
    public FilterChainProxy samlFilter() throws Exception {
        List<SecurityFilterChain> chains = new ArrayList<>();
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/login/**"),
                samlEntryPoint()));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/logout/**"),
                samlLogoutFilter()));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/metadata/**"),
                metadataDisplayFilter()));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SSO/**"),
                samlWebSSOProcessingFilter()));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SSOHoK/**"),
                samlWebSSOHoKProcessingFilter()));
        chains.add(new DefaultSecurityFilterChain(
                new AntPathRequestMatcher("/saml/SingleLogout/**"), samlLogoutProcessingFilter()));
        return new FilterChainProxy(chains);
    }

    /**
     * Returns the authentication manager currently used by Spring. It represents a bean definition
     * with the aim allow wiring from other classes performing the Inversion of Control (IoC).
     * 
     * @return Authentication manager.
     * @throws Exception If an error occurs.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Defines the web based security configuration.
     * 
     * @param http It allows configuring web based security for specific HTTP requests.
     * @throws Exception If an error occurs.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().authenticationEntryPoint(samlEntryPoint());
        http.csrf().disable();
        HeaderUtility.setSecureHeaders(http.headers());
        FilterChainProxy samlFilter = samlFilter();
        http.addFilterBefore(metadataGeneratorFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(new RequestContextFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(samlFilter, BasicAuthenticationFilter.class);
        http.authorizeRequests()
                .antMatchers(samlConfiguration.getPermitAllPaths())
                .permitAll()
                .antMatchers("/saml/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .accessDecisionManager(accessDecisionManager());
        http.logout().logoutSuccessUrl(samlConfiguration.getSuccessLogoutUrl());
    }

    /**
     * Initialization of access decision manager. Add a ScreenRoleDatabaseVoter to the manager.
     * 
     * @return Access decision manager.
     * @see ScreenRoleDatabaseVoter
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters =
                Arrays.asList(screenRoleDatabaseVoter, new WebExpressionVoter(), new RoleVoter(),
                        new AuthenticatedVoter());
        return new UnanimousBased(decisionVoters);
    }

    /**
     * Sets a custom authentication provider.
     * 
     * @param auth SecurityBuilder used to create an AuthenticationManager.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(samlAuthenticationProvider());
    }

}
