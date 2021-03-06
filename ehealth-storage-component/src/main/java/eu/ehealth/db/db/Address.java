package eu.ehealth.db.db;
// Generated Oct 1, 2013 11:39:06 AM by Hibernate Tools 3.2.4.GA



/**
 * Address generated by hbm2java
 */
public class Address  implements java.io.Serializable {


     private Integer id;
     private String Street;
     private String StreetNo;
     private String City;
     private String County;
     private String Country;
     private String ZipCode;
     private String Notes;
     private Boolean IsPrimary;
     private Integer persondata;
     private PersonData m_PersonData;

    public Address() {
    }

    public Address(String Street, String StreetNo, String City, String County, String Country, String ZipCode, String Notes, Boolean IsPrimary, Integer persondata, PersonData m_PersonData) {
       this.Street = Street;
       this.StreetNo = StreetNo;
       this.City = City;
       this.County = County;
       this.Country = Country;
       this.ZipCode = ZipCode;
       this.Notes = Notes;
       this.IsPrimary = IsPrimary;
       this.persondata = persondata;
       this.m_PersonData = m_PersonData;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getStreet() {
        return this.Street;
    }
    
    public void setStreet(String Street) {
        this.Street = Street;
    }
    public String getStreetNo() {
        return this.StreetNo;
    }
    
    public void setStreetNo(String StreetNo) {
        this.StreetNo = StreetNo;
    }
    public String getCity() {
        return this.City;
    }
    
    public void setCity(String City) {
        this.City = City;
    }
    public String getCounty() {
        return this.County;
    }
    
    public void setCounty(String County) {
        this.County = County;
    }
    public String getCountry() {
        return this.Country;
    }
    
    public void setCountry(String Country) {
        this.Country = Country;
    }
    public String getZipCode() {
        return this.ZipCode;
    }
    
    public void setZipCode(String ZipCode) {
        this.ZipCode = ZipCode;
    }
    public String getNotes() {
        return this.Notes;
    }
    
    public void setNotes(String Notes) {
        this.Notes = Notes;
    }
    public Boolean getIsPrimary() {
        return this.IsPrimary;
    }
    
    public void setIsPrimary(Boolean IsPrimary) {
        this.IsPrimary = IsPrimary;
    }
    public Integer getPersondata() {
        return this.persondata;
    }
    
    public void setPersondata(Integer persondata) {
        this.persondata = persondata;
    }
    public PersonData getM_PersonData() {
        return this.m_PersonData;
    }
    
    public void setM_PersonData(PersonData m_PersonData) {
        this.m_PersonData = m_PersonData;
    }




}


