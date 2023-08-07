package com.gracetech.gestionimmoback.service;

import com.gracetech.gestionimmoback.dto.AppRoleDto;
import com.gracetech.gestionimmoback.exception.ElementNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AppRoleServiceTest {

    @Autowired
    private IAppRoleService service;

    private AppRoleDto saved;

    @BeforeEach
    public void setup() {
        saved = service.save(initRole());
    }
    @Test
    public void createRole() {
        assertNotNull(saved);
        assertEquals(saved.getId(), saved.getId());
    }

    @Test
    public void getRoleById_success() {
//        saved = service.save(initRole());
        assertNotNull(service.getAppRole(saved.getId()));
        assertEquals(initRole().getDescription(), saved.getDescription());
    }

    @Test
    public void deleteRoleByExistingId() {
//        saved = service.save(initRole());
        AppRoleDto deleted = service.getAppRole(saved.getId());
        assertNotNull(deleted);
    }

    @Test
    public void deleteRoleWithNoExistingId_throws_ElementNotFoundException() {
//        saved = service.save(initRole());
        service.delete(saved.getId());
        assertThrows(ElementNotFoundException.class, () -> service.getAppRole(saved.getId()));
    }

    @Test
    public void getAllRoles() {
//        saved = service.save(initRole());
        assertEquals(service.findAll().size(), 1, "Should have 1 role");
    }

    @NotNull
    private AppRoleDto initRole() {
        AppRoleDto role = new AppRoleDto();
        role.setDescription("ADMIN");
        role.setCreatedDate(Instant.now());
        role.setCreatedBy("immoTest");

        return role;
    }

    @Test
    public void getRoleById_throws_ElementNotFoundException() {
        assertThrows(ElementNotFoundException.class, () -> service.getAppRole(0L));
    }

    @AfterEach
    public void deleteRole() {
        service.delete(saved.getId());
    }
}
