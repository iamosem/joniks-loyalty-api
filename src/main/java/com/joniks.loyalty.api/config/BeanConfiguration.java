package com.joniks.loyalty.api.config;

import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import com.joniks.loyalty.api.interceptors.SessionInterceptor;

@Configuration
@ComponentScan({ "com.joniks.loyalty.api.controller", "com.joniks.loyalty.api.service", "com.joniks.loyalty.api.aop" })
@EntityScan("com.joniks.loyalty.api.entity")
@EnableJpaRepositories(basePackages = { "com.joniks.loyalty.api.repository", "com.joniks.loyalty.api.repository.custom" })
public class BeanConfiguration extends WebMvcConfigurerAdapter {

	// @Bean
	// public UrlBasedViewResolver viewResolver() {
	// UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
	// tilesViewResolver.setViewClass(TilesView.class);
	// return tilesViewResolver;
	// }
	//
	// @Bean
	// public TilesConfigurer tilesConfigurer() {
	// TilesConfigurer tiles = new TilesConfigurer();
	// tiles.setDefinitions(new String[] { "/WEB-INF/tiles/tiles.xml" });
	// return tiles;
	//
	// }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

	@Bean
	public UrlBasedViewResolver viewResolver() {
		UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		return tilesViewResolver;
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tiles = new TilesConfigurer();
		tiles.setDefinitions(new String[] { "WEB-INF/tiles/tiles.xml" });
		return tiles;

	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
		registry.addViewController("/**").setViewName("forward:/");
	}

	@Bean
	public SchedulerFactoryBean factory() {
		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		schedulerFactory.setOverwriteExistingJobs(false);
		return schedulerFactory;
	}

	@Bean
	public Scheduler scheduler(SchedulerFactoryBean factory) {
		Scheduler scheduler = factory.getScheduler();
		return scheduler;
	}
}
