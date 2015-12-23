/**
 * 
 */
package de.haproxyhq.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Johannes Hiemer, Maximilian Büttner
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class CustomMvcConfiguration {

}
