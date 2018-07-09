package domain;

public class DeliveryMethod {

	String deliveryMethodId;
	String deliveryMethod;
	
	public DeliveryMethod() {
		super();
	}

	public DeliveryMethod(String deliveryMethodId, String deliveryMethod) {
		super();
		this.deliveryMethodId = deliveryMethodId;
		this.deliveryMethod = deliveryMethod;
	}
	
	@Override
	public String toString() {
		return "DeliveryMethod [deliveryMethodId=" + deliveryMethodId + 
                        ", deliveryMethod=" + deliveryMethod + "]";
	}
	public String getDeliveryMethodId() {
		return deliveryMethodId;
	}
	public void setDeliveryMethodId(String deliveryMethodId) {
		this.deliveryMethodId = deliveryMethodId;
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	
	
}
