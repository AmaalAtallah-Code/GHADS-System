package com.amaal.ghads.model;

import java.time.LocalDate;

public class AidDistribution {
    private int distributionId;
    private int familyId;
    private int orgId;
    private int distributedBy; // userId للمنسق
    private LocalDate distributionDate;
    private String aidType; // حقل الـ Bonus (مثال: طرود غذائية، مساعدات نقدية)

    // Constructors
    public AidDistribution() {}

    public AidDistribution(int distributionId, int familyId, int orgId,
                           int distributedBy, LocalDate distributionDate, String aidType) {
        this.distributionId = distributionId;
        this.familyId = familyId;
        this.orgId = orgId;
        this.distributedBy = distributedBy;
        this.distributionDate = distributionDate;
        this.aidType = aidType;
    }

    // Getters and Setters
    public int getDistributionId() { return distributionId; }
    public void setDistributionId(int distributionId) { this.distributionId = distributionId; }

    public int getFamilyId() { return familyId; }
    public void setFamilyId(int familyId) { this.familyId = familyId; }

    public int getOrgId() { return orgId; }
    public void setOrgId(int orgId) { this.orgId = orgId; }

    public int getDistributedBy() { return distributedBy; }
    public void setDistributedBy(int distributedBy) { this.distributedBy = distributedBy; }

    public LocalDate getDistributionDate() { return distributionDate; }
    public void setDistributionDate(LocalDate distributionDate) { this.distributionDate = distributionDate; }

    public String getAidType() { return aidType; }
    public void setAidType(String aidType) { this.aidType = aidType; }
}