package com.example.campaignmanagerbackend.service;

import com.example.campaignmanagerbackend.exception.CampaignNotFoundException;
import com.example.campaignmanagerbackend.model.Campaign;
import com.example.campaignmanagerbackend.repository.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private BalanceService balanceService;

    @InjectMocks
    private CampaignService campaignService;

    @Test
    void getAllCampaigns_ReturnsCampaignList() {
        // Given
        Campaign campaign1 = new Campaign();
        Campaign campaign2 = new Campaign();
        when(campaignRepository.findAll()).thenReturn(Arrays.asList(campaign1, campaign2));

        // When
        var campaigns = campaignService.getAllCampaigns();

        // Then
        assertEquals(2, campaigns.size());
        verify(campaignRepository, times(1)).findAll();
    }

    @Test
    void getCampaignById_ExistingId_ReturnsCampaign() {
        // Given
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));

        // When
        Campaign result = campaignService.getCampaignById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(campaignRepository, times(1)).findById(1L);
    }

    @Test
    void getCampaignById_NonExistingId_ThrowsException() {
        // Given
        when(campaignRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        CampaignNotFoundException exception = assertThrows(CampaignNotFoundException.class, () -> campaignService.getCampaignById(1L));

        assertEquals("Campaign not found", exception.getMessage());
    }

    @Test
    void createCampaign_ValidCampaign_SavesAndDeductsBalance() {
        // Given
        Campaign campaign = new Campaign();
        campaign.setCampaignFund(new BigDecimal("100.00"));
        when(campaignRepository.save(any(Campaign.class))).thenReturn(campaign);

        // When
        Campaign createdCampaign = campaignService.createCampaign(campaign);

        // Then
        assertNotNull(createdCampaign);
        verify(balanceService, times(1)).deductBalance(new BigDecimal("100.00"));
        verify(campaignRepository, times(1)).save(campaign);
    }

    @Test
    void updateCampaign_ValidId_UpdatesCampaignAndAdjustsBalance() {
        // Given
        Campaign existingCampaign = new Campaign();
        existingCampaign.setId(1L);
        existingCampaign.setCampaignFund(new BigDecimal("100.00"));

        Campaign updatedCampaign = new Campaign();
        updatedCampaign.setCampaignFund(new BigDecimal("150.00"));

        when(campaignRepository.findById(1L)).thenReturn(Optional.of(existingCampaign));
        when(campaignRepository.save(any(Campaign.class))).thenReturn(updatedCampaign);

        // When
        Campaign result = campaignService.updateCampaign(1L, updatedCampaign);

        // Then
        assertNotNull(result);
        verify(balanceService, times(1)).addBalance(new BigDecimal("100.00"));
        verify(balanceService, times(1)).deductBalance(new BigDecimal("150.00"));
        verify(campaignRepository, times(1)).save(existingCampaign);
    }

    @Test
    void deleteCampaign_ValidId_DeletesCampaignAndRestoresBalance() {
        // Given
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setCampaignFund(new BigDecimal("100.00"));
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));

        // When
        campaignService.deleteCampaign(1L);

        // Then
        verify(balanceService, times(1)).addBalance(new BigDecimal("100.00"));
        verify(campaignRepository, times(1)).deleteById(1L);
    }
}
