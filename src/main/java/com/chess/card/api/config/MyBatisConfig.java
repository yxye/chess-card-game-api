package com.chess.ws.api.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@MapperScan("com.chess.ws.api.game.mapper")
public class MyBatisConfig {
}
