package com.joniks.lotalty.api.config;

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

import com.joniks.lotalty.api.interceptors.SessionInterceptor;

@Configuration
@ComponentScan({ "com.joniks.lotalty.api.controller", "com.joniks.lotalty.api.service", "com.joniks.lotalty.api.aop" })
@EntityScan("com.joniks.lotalty.api.entity")
@EnableJpaRepositories(basePackages = { "com.joniks.lotalty.api.repository", "com.joniks.lotalty.api.repository.custom" })
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
