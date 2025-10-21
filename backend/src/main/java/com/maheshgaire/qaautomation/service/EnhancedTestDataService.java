package com.maheshgaire.qaautomation.service;

import com.maheshgaire.qaautomation.model.enhanced.*;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class EnhancedTestDataService {

    private final Faker faker;
    private final Random random;
    
    // Predefined data for consistency
    private final List<String> orderStatuses = Arrays.asList(
        "pending", "confirmed", "processing", "shipped", "delivered", "cancelled"
    );
    
    private final List<String> paymentMethods = Arrays.asList(
        "credit_card", "debit_card", "paypal", "apple_pay", "google_pay", "bank_transfer"
    );
    
    private final List<String> cardTypes = Arrays.asList(
        "Visa", "MasterCard", "American Express", "Discover"
    );
    
    private final List<String> currencies = Arrays.asList(
        "USD", "EUR", "GBP", "CAD", "AUD"
    );
    
    private final List<String> productCategories = Arrays.asList(
        "Electronics", "Clothing", "Books", "Home & Garden", "Sports", "Toys", "Beauty", "Food"
    );

    public EnhancedTestDataService() {
        this.faker = new Faker();
        this.random = new Random();
    }

    /**
     * Generate enhanced users with complete profile information
     */
    public List<EnhancedUser> generateEnhancedUsers(int count, boolean includeOrders) {
        return IntStream.range(0, count)
                .mapToObj(i -> generateSingleUser(includeOrders))
                .toList();
    }

    /**
     * Generate users with their orders (relational data)
     */
    public List<EnhancedUser> generateUsersWithOrders(int userCount, int minOrders, int maxOrders) {
        return IntStream.range(0, userCount)
                .mapToObj(i -> {
                    EnhancedUser user = generateSingleUser(false);
                    int orderCount = faker.number().numberBetween(minOrders, maxOrders + 1);
                    user.setOrders(generateOrdersForUser(user.getUserId(), orderCount));
                    return user;
                })
                .toList();
    }

    /**
     * Generate orders with realistic items and pricing
     */
    public List<Order> generateEnhancedOrders(int count, boolean includeItems) {
        return IntStream.range(0, count)
                .mapToObj(i -> generateSingleOrder(null, includeItems))
                .toList();
    }

    private EnhancedUser generateSingleUser(boolean includeOrders) {
        EnhancedUser user = new EnhancedUser();
        
        // Basic information
        user.setUserId(generateUserId());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(generateEmail(user.getFirstName(), user.getLastName()));
        user.setPhoneNumber(faker.phoneNumber().phoneNumber());
        user.setDateOfBirth(generateDateOfBirth());
        
        // Address
        user.setAddress(generateAddress("home"));
        
        // Profile
        user.setProfile(generateUserProfile());
        
        // Orders (if requested)
        if (includeOrders) {
            int orderCount = faker.number().numberBetween(0, 6);
            user.setOrders(generateOrdersForUser(user.getUserId(), orderCount));
        }
        
        return user;
    }

    private Address generateAddress(String type) {
        Address address = new Address();
        address.setStreet(faker.address().streetAddress());
        address.setCity(faker.address().city());
        address.setState(faker.address().state());
        address.setPostalCode(faker.address().zipCode());
        address.setCountry(faker.address().country());
        address.setType(type);
        return address;
    }

    private UserProfile generateUserProfile() {
        UserProfile profile = new UserProfile();
        profile.setCompany(faker.company().name());
        profile.setJobTitle(faker.job().title());
        profile.setBio(faker.lorem().sentence(10, 20));
        profile.setWebsite(faker.internet().url());
        profile.setLanguage(faker.nation().language());
        profile.setTimezone(faker.options().option("UTC", "EST", "PST", "GMT", "CET"));
        profile.setAvatarUrl("https://i.pravatar.cc/150?u=" + faker.internet().uuid().substring(0, 8));
        return profile;
    }

    private List<Order> generateOrdersForUser(String userId, int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> generateSingleOrder(userId, true))
                .toList();
    }

    private Order generateSingleOrder(String userId, boolean includeItems) {
        Order order = new Order();
        
        order.setOrderId(generateOrderId());
        order.setUserId(userId != null ? userId : generateUserId());
        order.setStatus(faker.options().option(orderStatuses.toArray(new String[0])));
        order.setCurrency(faker.options().option(currencies.toArray(new String[0])));
        
        // Generate order items
        if (includeItems) {
            int itemCount = faker.number().numberBetween(1, 6);
            List<OrderItem> items = generateOrderItems(itemCount);
            order.setItems(items);
            
            // Calculate total from items
            BigDecimal total = items.stream()
                    .map(OrderItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            order.setTotalAmount(total);
        } else {
            order.setTotalAmount(generateRandomAmount());
        }
        
        // Addresses
        order.setShippingAddress(generateAddress("shipping"));
        order.setBillingAddress(generateAddress("billing"));
        
        // Payment info
        order.setPaymentInfo(generatePaymentInfo());
        
        return order;
    }

    private List<OrderItem> generateOrderItems(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    OrderItem item = new OrderItem();
                    item.setProductId(generateProductId());
                    item.setProductName(generateProductName());
                    item.setSku(generateSKU());
                    item.setQuantity(faker.number().numberBetween(1, 5));
                    item.setUnitPrice(generateRandomAmount());
                    item.setTotalPrice(item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())));
                    item.setCategory(faker.options().option(productCategories.toArray(new String[0])));
                    return item;
                })
                .toList();
    }

    private PaymentInfo generatePaymentInfo() {
        PaymentInfo payment = new PaymentInfo();
        payment.setPaymentMethod(faker.options().option(paymentMethods.toArray(new String[0])));
        payment.setCardType(faker.options().option(cardTypes.toArray(new String[0])));
        payment.setMaskedCardNumber("****-****-****-" + faker.number().digits(4));
        payment.setPaymentStatus(faker.options().option("pending", "completed", "failed", "refunded"));
        payment.setTransactionId(generateTransactionId());
        payment.setProcessor(faker.options().option("Stripe", "PayPal", "Square", "Braintree"));
        return payment;
    }

    // Helper methods for generating IDs and specific formats
    private String generateUserId() {
        return "user_" + faker.internet().uuid().substring(0, 8);
    }

    private String generateOrderId() {
        return "order_" + faker.number().digits(8);
    }

    private String generateProductId() {
        return "prod_" + faker.number().digits(6);
    }

    private String generateTransactionId() {
        return "txn_" + faker.internet().uuid().substring(0, 12);
    }

    private String generateSKU() {
        return faker.commerce().productName().replaceAll("[^A-Za-z0-9]", "").substring(0, 6).toUpperCase() 
               + "-" + faker.number().digits(4);
    }

    private String generateProductName() {
        return faker.commerce().productName();
    }

    private String generateEmail(String firstName, String lastName) {
        String baseEmail = (firstName + "." + lastName).toLowerCase().replaceAll("[^a-z.]", "");
        return baseEmail + "@" + faker.internet().domainName();
    }

    private String generateDateOfBirth() {
        LocalDate birthDate = LocalDate.now().minusYears(faker.number().numberBetween(18, 80));
        return birthDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private BigDecimal generateRandomAmount() {
        double amount = faker.number().randomDouble(2, 5, 1000);
        return BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
    }
}