package cyclechronicles;

import java.io.IOException;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

/** A small bike shop. */
public class Shop {

    // Initialize the Logger
    static Logger logger = Logger.getLogger(Shop.class.getName());

    private final Queue<OrderRecord> pendingOrderRecordClasses = new LinkedList<>();
    private final Set<OrderRecord> completedOrderRecordClasses = new HashSet<>();

    // Initialize the Logger Block
    static {
        // Define the file, where we want to write the Data
        try {
            FileHandler fileHandler = new FileHandler("log-details.csv", true);
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    String log = record.getLevel() + " , " + record.getSourceMethodName() + " , " + record.getSourceClassName() + " , " + record.getMessage() +  System.lineSeparator();
                    return log;
                }
            });
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Accept a repair order.
     *
     * <p>The order will only be accepted if all conditions are met:
     *
     * <ul>
     *   <li>Gravel bikes cannot be repaired in this shop.
     *   <li>E-bikes cannot be repaired in this shop.
     *   <li>There can be no more than one pending order per customer.
     *   <li>There can be no more than five pending orders at any time.
     * </ul>
     *
     * <p>Implementation note: Accepted orders are added to the end of {@code pendingOrders}.
     *
     * @param o order to be accepted
     * @return {@code true} if all conditions are met and the order has been accepted, {@code false}
     *     otherwise
     */
    public boolean accept(OrderRecord o) {
        if (o.bicycleType() == Type.GRAVEL) return false;
        if (o.bicycleType() == Type.EBIKE) return false;
        if (pendingOrderRecordClasses.stream().anyMatch(x -> x.customer().equals(o.customer())))
            return false;
        if (pendingOrderRecordClasses.size() > 4) return false;
        logger.logp(Level.INFO,"accept",Shop.class.getName() ,o.customer() + " : " + o.bicycleType() + " : " + "PendingOrderRecords");

        return pendingOrderRecordClasses.add(o);
    }

    /**
     * Take the oldest pending order and repair this bike.
     *
     * <p>Implementation note: Take the top element from {@code pendingOrders}, "repair" the bicycle
     * and put this order in {@code completedOrders}.
     *
     * @return finished order
     */
    public Optional<OrderRecord> repair() {

        // Take the oldest pending Order from the Pending List
        OrderRecord orderRecord = pendingOrderRecordClasses.poll();

        if (orderRecord != null){
            logger.logp(Level.INFO,"repair",Shop.class.getName() ,orderRecord.customer() + " : " + orderRecord.bicycleType() + " : " + "PendingOrderRecords");
            completedOrderRecordClasses.add(orderRecord);
            logger.logp(Level.INFO,"repair",Shop.class.getName() ,orderRecord.customer() + " : " + orderRecord.bicycleType() + " : " + "CompletedOrderRecords");
            return Optional.of(orderRecord);
        }
        return Optional.empty();

    }

    /**
     * Deliver a repaired bike to a customer.
     *
     * <p>Implementation note: Find any order in {@code completedOrders} with matching customer and
     * deliver this order. Will remove the order from {@code completedOrders}.
     *
     * @param c search for any completed orders of this customer
     * @return any finished order for given customer, {@code Optional.empty()} if none found
     */
    public Optional<OrderRecord> deliver(String c) {

        Optional<OrderRecord> order = completedOrderRecordClasses.stream().filter(o -> o.customer().equals(c)).findAny();

        if (order.isPresent()){
            logger.logp(Level.INFO,"deliver",Shop.class.getName() ,order.get().customer() + " : " + order.get().bicycleType() + " : " + "PendingOrderRecords");
            completedOrderRecordClasses.remove(order.get());
            return order;
        }

        return Optional.empty();
    }
}
