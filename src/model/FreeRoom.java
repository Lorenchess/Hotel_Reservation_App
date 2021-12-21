package model;

public class FreeRoom extends Room{
    private Double price;

    public FreeRoom(String roomNumber, Double price, RoomType enumeration) {
        super(roomNumber, price, enumeration);
        this.price = 0.0;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "FreeRoom{" +
                "price=" + price +
                '}';
    }
}
