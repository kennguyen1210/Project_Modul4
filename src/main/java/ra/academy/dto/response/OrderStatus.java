package ra.academy.dto.response;

public class OrderStatus {
    private String status;
    private int count;

    public OrderStatus(String status, int count) {
        this.status = status;
        this.count = count;
    }

    public OrderStatus() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
