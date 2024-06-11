package com.chess.card.api.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@MapperScan("com.chess.card.api.game.mapper")
public class MyBatisConfig {
}
