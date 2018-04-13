package cpsc3770.easyinventory;

class IceCream {
    private int productID;
    private String productName;
    private float price;
    private int size;
    private String units;
    private String description;
    private int stock;
    private int imageUrl;

    public IceCream(int productID, String productName, float price, int size, String units, String description, int stock, int imageUrl) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.size = size;
        this.units = units;
        this.description = description;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public float getPrice() {
        return price;
    }

    public int getSize() {
        return size;
    }

    public String getUnits() {
        return units;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public String[] getMyValues(){
        return new String[] {Integer.toString(productID), productName, String.format("%.2f ", price), Integer.toString(size), units, description, Integer.toString(stock)};
    }
}
