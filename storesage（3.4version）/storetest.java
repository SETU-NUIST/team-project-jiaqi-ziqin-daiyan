import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StoreFullTest {

    // Test Products
    plus03.Goods p1 = new plus03.Goods("apple", 2.99, true);
    plus03.Goods p2 = new plus03.Goods("banana", 3.99, true);
    plus03.Goods p3 = new plus03.Goods("Expired Milk", 5.99, false);
    plus03.Goods productZero = new plus03.Goods("Test Product", 10.00, true);

    // Store with products
    Store storeWithProducts = new Store() {{
        add(p1);
        add(p2);
        add(p3);
    }};

    // Empty Store
    Store storeEmpty = new Store();


    // 1. Find Product
    @Test
    void findProductByIndexReturnsCorrectProduct() {
        assertEquals(p1, storeWithProducts.findProduct(0));
        assertEquals(p2, storeWithProducts.findProduct(1));
        assertEquals(p3, storeWithProducts.findProduct(2));
        assertNull(storeWithProducts.findProduct(-1));
        assertNull(storeWithProducts.findProduct(99));
    }

    // 2. Cheapest Product
    @Test
    void getCheapGoodsReturnsCheapestProduct() {
        plus03.Goods cheap = storeWithProducts.getCheapGoods();
        assertEquals(2.99, cheap.price);
        assertEquals("apple", cheap.name);
    }

    // 3. Average Price
    @Test
    void averagePriceIsCalculatedCorrectly() {
        Double avg = storeWithProducts.getAvgPrice();
        assertEquals(4.323333333333333, avg);
    }

    // 4. On-sale Products Filter
    @Test
    void getSaleGoodsOnlyShowsOnSale() {
        String saleStr = storeWithProducts.getSaleGoods();
        assertTrue(saleStr.contains("apple"));
        assertTrue(saleStr.contains("banana"));
        assertFalse(saleStr.contains("Expired Milk"));
    }

    // 5. High Price Products Filter
    @Test
    void highPriceGoodsShowOnlyAboveThreshold() {
        String high = storeWithProducts.getHighPriceGoods(4.0);
        assertTrue(high.contains("Expired Milk"));
        assertFalse(high.contains("apple"));
        assertFalse(high.contains("banana"));
    }

    // 6. Delete Product
    @Test
    void removeProductDecreasesCount() {
        assertEquals(3, storeWithProducts.numberOfProducts());
        assertTrue(storeWithProducts.removeProduct(0));
        assertEquals(2, storeWithProducts.numberOfProducts());
        assertEquals(p2, storeWithProducts.findProduct(0));
    }

    @Test
    void removeInvalidIndexDoesNothing() {
        assertEquals(3, storeWithProducts.numberOfProducts());
        assertFalse(storeWithProducts.removeProduct(-1));
        assertFalse(storeWithProducts.removeProduct(99));
        assertEquals(3, storeWithProducts.numberOfProducts());
    }



    // 7. Modify Product Price / Status
    @Test
    void modifyProductPriceAndStatus() {
        plus03.Goods g = storeWithProducts.findProduct(0);
        g.setPrice(9.99);
        g.setSale(false);

        assertEquals(9.99, g.price);
        assertFalse(g.isSale);
    }
}