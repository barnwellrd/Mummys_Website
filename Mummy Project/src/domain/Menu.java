package domain;

import java.util.Objects;

public class Menu {

    String id;
    String name;
    char vegetarian;
    String description;
    String type;
    String slot_ID;
    String photo;
    double price;

    public Menu(String id, String name, char vegetarian, String type, String description, String slot_ID, String photo, double price) {
        super();
        this.id = id;
        this.name = name;
        this.vegetarian = vegetarian;
        this.type = type;
        this.description = description;
        this.slot_ID = slot_ID;
        this.photo = photo;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(char vegetarian) {
        this.vegetarian = vegetarian;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlot_ID() {
        return slot_ID;
    }

    public void setSlot_ID(String slot_ID) {
        this.slot_ID = slot_ID;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Menu [id=" + id + ", name=" + name + ", vegetarian=" + vegetarian + ", description=" + description
                + ", type=" + type + ", slot_ID=" + slot_ID + ", photo=" + photo + ", price=" + price + "]";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + this.vegetarian;
        hash = 13 * hash + Objects.hashCode(this.description);
        hash = 13 * hash + Objects.hashCode(this.type);
        hash = 13 * hash + Objects.hashCode(this.slot_ID);
        hash = 13 * hash + Objects.hashCode(this.photo);
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Menu other = (Menu) obj;
        if (this.vegetarian != other.vegetarian) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.slot_ID, other.slot_ID)) {
            return false;
        }
        return Objects.equals(this.photo, other.photo);
    }

}
