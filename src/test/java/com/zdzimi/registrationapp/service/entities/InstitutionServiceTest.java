package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.exception.InstitutionNotFoundException;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.repository.InstitutionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class InstitutionServiceTest {
    
    private static final String INSTITUTION_NAME = "name";
    private static final String USER_EMAIL_COM = "user@email.com";
    
    private InstitutionService service;
    
    private InstitutionRepo repo;

    @BeforeEach
    void setUp() {
        repo = mock(InstitutionRepo.class);
        initMocks(this);
        service = new InstitutionService(repo);
    }
    
    @Test
    void shouldFindAllInstitutions() {
        //given
        Institution institution = new Institution();
        institution.setInstitutionId(1);
        when(repo.findAll()).thenReturn(newArrayList(institution));
        //when
        List<Institution> result = service.findAll();
        //then
        assertEquals(1, result.size());
        assertEquals(institution, result.get(0));
        verify(repo, times(1)).findAll();
        verifyNoMoreInteractions(repo);
    }
    
    @Test
    void shouldFindByInstitutionName() {
        //given
        Institution institution = new Institution();
        institution.setInstitutionId(1);
        institution.setInstitutionName(INSTITUTION_NAME);
        when(repo.findByInstitutionName(INSTITUTION_NAME)).thenReturn(Optional.of(institution));
        //when
        Institution result = service.findByInstitutionName(INSTITUTION_NAME);
        //then
        assertEquals(1, result.getInstitutionId());
        assertEquals(INSTITUTION_NAME, result.getInstitutionName());
        verify(repo, times(1)).findByInstitutionName(INSTITUTION_NAME);
        verifyNoMoreInteractions(repo);
    }
    
    @Test
    void shouldThrowExceptionWhenInstitutionNotFoundByName() {
        //given
        when(repo.findByInstitutionName(INSTITUTION_NAME)).thenReturn(Optional.empty());
        //when
        InstitutionNotFoundException exception = assertThrows(InstitutionNotFoundException.class, () -> {
            service.findByInstitutionName(INSTITUTION_NAME);
        });
        //then
        assertEquals("Could not find institution: " + INSTITUTION_NAME, exception.getMessage());
        verify(repo, times(1)).findByInstitutionName(INSTITUTION_NAME);
        verifyNoMoreInteractions(repo);
    }
    
    @Test
    void shouldSaveInstitution() {
        //given
        Institution institutionBeforeSave = new Institution();
        institutionBeforeSave.setInstitutionName(INSTITUTION_NAME);
        Institution institutionAfterSave = new Institution();
        institutionAfterSave.setInstitutionId(1);
        institutionAfterSave.setInstitutionName(INSTITUTION_NAME);
        when(repo.save(ArgumentMatchers.any(Institution.class))).thenReturn(institutionAfterSave);
        //when
        Institution result = service.save(institutionBeforeSave);
        //then
        assertEquals(institutionAfterSave, result);
        verify(repo, times(1)).save(institutionBeforeSave);
        verifyNoMoreInteractions(repo);
    }
    
    @Test
    void shouldFindByUser() {
        //given
        Institution institution = new Institution();
        institution.setInstitutionId(1);
        User user = new User();
        user.setEmail(USER_EMAIL_COM);
        when(repo.findByUsers(user)).thenReturn(newArrayList(institution));
        //when
        List<Institution> result = service.findByUsers(user);
        //then
        assertEquals(1, result.size());
        assertEquals(institution, result.get(0));
        verify(repo, times(1)).findByUsers(user);
        verifyNoMoreInteractions(repo);
    }
}
