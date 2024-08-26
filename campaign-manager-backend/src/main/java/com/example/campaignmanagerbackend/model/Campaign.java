package com.example.campaignmanagerbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "campaigns")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campaign name is mandatory")
    @Column(nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "campaign_keywords", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "keyword", nullable = false)
    @NotEmpty(message = "Keywords are mandatory")
    private List<String> keywords;

    @DecimalMin(value = "1.00", message = "Bid amount must be at least 1.00")
    @Column(nullable = false)
    private BigDecimal bidAmount;

    @DecimalMin(value = "0.00", message = "Campaign fund must be a positive amount")
    @Column(nullable = false)
    private BigDecimal campaignFund;

    @NotNull(message = "Status is mandatory")
    @Column(nullable = false)
    private Boolean status;

    @NotBlank(message = "Town is mandatory")
    @Column(nullable = false)
    private String town;

    @Min(value = 1, message = "Radius must be at least 1 km")
    @Column(nullable = false)
    private Integer radius;
}