package com.main.classroomy.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TeacherDataLoader implements ApplicationRunner {
    private static final Logger logger = LogManager.getLogger(TeacherDataLoader.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Started to populate teacher data...");

        // TODO implement code here

        logger.info("Teacher data was successfully populated!");
    }

}
