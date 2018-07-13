package domain;

public class Location {

    String locationId;
    String userId;
    String taxRate;
    String street;
    String city;
    String state;
    String country;
    String zip;

    public Location() {
        super();
    }

    public Location(String locationId, String userId, String taxRate, String street, String city, String state, String country, String zip) {
        super();
        this.locationId = locationId;
        this.userId = userId;
        this.taxRate = taxRate;
        this.street = street;
        this.country = country;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public String toString() {
        return "Location{" + "locationId=" + locationId + ", userId=" + userId + ", taxRate=" + taxRate + ", street=" + street + ", city=" + city + ", state=" + state + ", country=" + country + ", zip=" + zip + '}';
    }
}
