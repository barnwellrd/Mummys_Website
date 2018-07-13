package domain;

import java.util.Objects;

public class DeliveryStatus {

    String deliveryStatusId;
    String deliveryStatus;

    public DeliveryStatus() {
        super();
    }

    public DeliveryStatus(String deliveryStatusId, String deliveryStatus) {
        super();
        this.deliveryStatusId = deliveryStatusId;
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public String toString() {
        return "DeliveryStatus [deliveryStatusID=" + deliveryStatusId
                + ", deliveryStatus=" + deliveryStatus + "]";
    }

    public String getDeliveryStatusId() {
        return deliveryStatusId;
    }

    public void setDeliveryStatusId(String deliveryStatusId) {
        this.deliveryStatusId = deliveryStatusId;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
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
        DeliveryStatus other = (DeliveryStatus) obj;
        if (deliveryStatus == null) {
            if (other.deliveryStatus != null) {
                return false;
            }
        } else if (!deliveryStatus.equals(other.deliveryStatus)) {
            return false;
        }
        if (deliveryStatusId == null) {
            if (other.deliveryStatusId != null) {
                return false;
            }
        } else if (!deliveryStatusId.equals(other.deliveryStatusId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.deliveryStatusId);
        hash = 71 * hash + Objects.hashCode(this.deliveryStatus);
        return hash;
    }
}
