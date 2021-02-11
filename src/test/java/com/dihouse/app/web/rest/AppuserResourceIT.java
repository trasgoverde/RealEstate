package com.dihouse.app.web.rest;

import com.dihouse.app.RealEstateApp;
import com.dihouse.app.domain.Appuser;
import com.dihouse.app.repository.AppuserRepository;
import com.dihouse.app.repository.search.AppuserSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AppuserResource} REST controller.
 */
@SpringBootTest(classes = RealEstateApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AppuserResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CIF = "AAAAAAAAAA";
    private static final String UPDATED_CIF = "BBBBBBBBBB";

    @Autowired
    private AppuserRepository appuserRepository;

    /**
     * This repository is mocked in the com.dihouse.app.repository.search test package.
     *
     * @see com.dihouse.app.repository.search.AppuserSearchRepositoryMockConfiguration
     */
    @Autowired
    private AppuserSearchRepository mockAppuserSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppuserMockMvc;

    private Appuser appuser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appuser createEntity(EntityManager em) {
        Appuser appuser = new Appuser()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .cif(DEFAULT_CIF);
        return appuser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appuser createUpdatedEntity(EntityManager em) {
        Appuser appuser = new Appuser()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .cif(UPDATED_CIF);
        return appuser;
    }

    @BeforeEach
    public void initTest() {
        appuser = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppuser() throws Exception {
        int databaseSizeBeforeCreate = appuserRepository.findAll().size();
        // Create the Appuser
        restAppuserMockMvc.perform(post("/api/appusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appuser)))
            .andExpect(status().isCreated());

        // Validate the Appuser in the database
        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeCreate + 1);
        Appuser testAppuser = appuserList.get(appuserList.size() - 1);
        assertThat(testAppuser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAppuser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAppuser.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testAppuser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAppuser.getCif()).isEqualTo(DEFAULT_CIF);

        // Validate the Appuser in Elasticsearch
        verify(mockAppuserSearchRepository, times(1)).save(testAppuser);
    }

    @Test
    @Transactional
    public void createAppuserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appuserRepository.findAll().size();

        // Create the Appuser with an existing ID
        appuser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppuserMockMvc.perform(post("/api/appusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appuser)))
            .andExpect(status().isBadRequest());

        // Validate the Appuser in the database
        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeCreate);

        // Validate the Appuser in Elasticsearch
        verify(mockAppuserSearchRepository, times(0)).save(appuser);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appuserRepository.findAll().size();
        // set the field null
        appuser.setFirstName(null);

        // Create the Appuser, which fails.


        restAppuserMockMvc.perform(post("/api/appusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appuser)))
            .andExpect(status().isBadRequest());

        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appuserRepository.findAll().size();
        // set the field null
        appuser.setLastName(null);

        // Create the Appuser, which fails.


        restAppuserMockMvc.perform(post("/api/appusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appuser)))
            .andExpect(status().isBadRequest());

        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = appuserRepository.findAll().size();
        // set the field null
        appuser.setPhone(null);

        // Create the Appuser, which fails.


        restAppuserMockMvc.perform(post("/api/appusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appuser)))
            .andExpect(status().isBadRequest());

        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = appuserRepository.findAll().size();
        // set the field null
        appuser.setEmail(null);

        // Create the Appuser, which fails.


        restAppuserMockMvc.perform(post("/api/appusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appuser)))
            .andExpect(status().isBadRequest());

        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCifIsRequired() throws Exception {
        int databaseSizeBeforeTest = appuserRepository.findAll().size();
        // set the field null
        appuser.setCif(null);

        // Create the Appuser, which fails.


        restAppuserMockMvc.perform(post("/api/appusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appuser)))
            .andExpect(status().isBadRequest());

        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppusers() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get all the appuserList
        restAppuserMockMvc.perform(get("/api/appusers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appuser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].cif").value(hasItem(DEFAULT_CIF)));
    }
    
    @Test
    @Transactional
    public void getAppuser() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        // Get the appuser
        restAppuserMockMvc.perform(get("/api/appusers/{id}", appuser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appuser.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.cif").value(DEFAULT_CIF));
    }
    @Test
    @Transactional
    public void getNonExistingAppuser() throws Exception {
        // Get the appuser
        restAppuserMockMvc.perform(get("/api/appusers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppuser() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        int databaseSizeBeforeUpdate = appuserRepository.findAll().size();

        // Update the appuser
        Appuser updatedAppuser = appuserRepository.findById(appuser.getId()).get();
        // Disconnect from session so that the updates on updatedAppuser are not directly saved in db
        em.detach(updatedAppuser);
        updatedAppuser
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .cif(UPDATED_CIF);

        restAppuserMockMvc.perform(put("/api/appusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppuser)))
            .andExpect(status().isOk());

        // Validate the Appuser in the database
        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeUpdate);
        Appuser testAppuser = appuserList.get(appuserList.size() - 1);
        assertThat(testAppuser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAppuser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAppuser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAppuser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAppuser.getCif()).isEqualTo(UPDATED_CIF);

        // Validate the Appuser in Elasticsearch
        verify(mockAppuserSearchRepository, times(1)).save(testAppuser);
    }

    @Test
    @Transactional
    public void updateNonExistingAppuser() throws Exception {
        int databaseSizeBeforeUpdate = appuserRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppuserMockMvc.perform(put("/api/appusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appuser)))
            .andExpect(status().isBadRequest());

        // Validate the Appuser in the database
        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Appuser in Elasticsearch
        verify(mockAppuserSearchRepository, times(0)).save(appuser);
    }

    @Test
    @Transactional
    public void deleteAppuser() throws Exception {
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);

        int databaseSizeBeforeDelete = appuserRepository.findAll().size();

        // Delete the appuser
        restAppuserMockMvc.perform(delete("/api/appusers/{id}", appuser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Appuser> appuserList = appuserRepository.findAll();
        assertThat(appuserList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Appuser in Elasticsearch
        verify(mockAppuserSearchRepository, times(1)).deleteById(appuser.getId());
    }

    @Test
    @Transactional
    public void searchAppuser() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        appuserRepository.saveAndFlush(appuser);
        when(mockAppuserSearchRepository.search(queryStringQuery("id:" + appuser.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(appuser), PageRequest.of(0, 1), 1));

        // Search the appuser
        restAppuserMockMvc.perform(get("/api/_search/appusers?query=id:" + appuser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appuser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].cif").value(hasItem(DEFAULT_CIF)));
    }
}
