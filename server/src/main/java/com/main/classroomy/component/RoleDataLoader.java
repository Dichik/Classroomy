package com.main.classroomy.component;

import com.main.classroomy.entity.role.ERole;
import com.main.classroomy.entity.role.Role;
import com.main.classroomy.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleDataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleDataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (ERole eRole : ERole.values()) {
            if (!this.roleRepository.existsByName(eRole)) {
                this.roleRepository.save(new Role(eRole));
            }
        }
    }

}
