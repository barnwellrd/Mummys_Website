package domain;

public class Location {
	
	String locationId;
	String street;
	String city;
	String country;
	String state;
	String zip;
        
        String user_id;
        double tax_rate;
	
	public Location() {
		super();
	}
	
	public Location(String locationId, String street, String city, String state, String country, String zip) {
		super();
		this.locationId = locationId;
		this.street = street;
		this.city = city;
		this.country = country;
		this.state = state;
		this.zip = zip;
	}
        
        	public Location(String locationId,String user_id, double tax_rate, String street, String city, String state, String country, String zip) {
		super();
		this.locationId = locationId;
                this.user_id = user_id;
                this.tax_rate = tax_rate;
		this.street = street;
		this.city = city;
		this.country = country;
		this.state = state;
		this.zip = zip;
	}

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(double tax_rate) {
        this.tax_rate = tax_rate;
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

    @Override
    public String toString() {
        return "Location{" + "locationId=" + locationId + ", street=" + street + ", city=" + city + ", state=" + state + ", country=" + country + ", zip=" + zip + '}';
    }

	
}

