package com.gracetech.gestionimmoback.service;

import com.gracetech.gestionimmoback.dto.AppRoleDto;
import com.gracetech.gestionimmoback.exception.ElementNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
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
        assertNotNull(service.getAppRole(saved.getId()));
        assertEquals(initRole().getDescription(), saved.getDescription());
    }

    @Test
    public void deleteRoleByExistingId() {
        AppRoleDto deleted = service.getAppRole(saved.getId());
        assertNotNull(deleted);
    }

    @Test
    public void deleteRoleWithNoExistingId_throws_ElementNotFoundException() {
        service.delete(saved.getId());
        Long id = saved.getId();
        assertThrows(ElementNotFoundException.class, () -> service.getAppRole(id));
    }

    @Test
    public void getAllRoles() {
        assertEquals( 1, service.findAll().size(), "Should have 1 role");
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
