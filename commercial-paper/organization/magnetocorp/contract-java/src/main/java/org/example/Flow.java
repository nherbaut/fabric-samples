package org.example;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.ledgerapi.State;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

@DataType()
public class Flow extends State {

    private final static Logger LOG = Logger.getLogger(Flow.class.getName());
    @Property
    String event = "";
    @Property
    int table = -1;
    @Property
    boolean icmp = false;
    @Property
    String reason = "";
    @Property
    boolean arp = false;
    @Property
    int in_port = -1;
    @Property
    String dl_src = "";
    @Property
    String dl_dst = "";
    @Property
    String arp_spa = "";
    @Property
    String arp_tpa = "";
    @Property
    int arp_op = -1;
    @Property
    int nw_tos = -1;
    @Property
    int icmp_type = -1;
    @Property
    int icmp_code = -1;
    @Property
    String actions = "";
    @Property
    String device = "";
    @Property
    double timestamp = -1.0;

    public Flow setKey() {
        this.key = State.makeKey(new String[] { this.getDevice(), "" + this.getTimestamp() });
        return this;
    }

    public String getKey() {
        return String.join(":", this.getSplitKey());
    }

    public static Flow deserialize(byte[] data) {
        LOG.log(Level.INFO, "@*@*@*@*@*" + new String(data, UTF_8));
        JSONObject json = new JSONObject(new String(data, UTF_8));

        String event = json.getString("event");

        int table = json.getInt("table");

        boolean icmp = json.getBoolean("icmp");

        String reason = json.getString("reason");

        boolean arp = json.getBoolean("arp");

        int in_port = json.getInt("in_port");

        String dl_src = json.getString("dl_src");

        String dl_dst = json.getString("dl_dst");

        String arp_spa = json.getString("arp_spa");
        String arp_tpa = json.getString("arp_tpa");

        int arp_op = json.getInt("arp_op");

        int nw_tos = json.getInt("nw_tos");

        int icmp_type = json.getInt("icmp_type");

        int icmp_code = json.getInt("icmp_code");

        String actions = json.getString("actions");

        String device = json.getString("device");

        double timestamp = json.getDouble("timestamp");

        return createInstance(device, timestamp, event, table, icmp, reason, arp, in_port, dl_src, dl_dst, arp_spa,
                arp_tpa, arp_op, nw_tos, icmp_type, icmp_code, actions);
    }

    public static byte[] serialize(Flow flow) {
        byte[] data = State.serialize(flow);

        return data;
    }

    /**
     * Factory method to create a flow object
     */
    public static Flow createInstance(String deviceId, double timestamp, String event, int table, boolean icmp,
            String reason, boolean arp, int in_port, String dl_src, String dl_dst, String arp_spa, String arp_tpa,
            int arp_op, int nw_tos, int icmp_type, int icmp_code, String actions) {
        Flow res = (Flow) new Flow().setDevice(deviceId).setTimestamp(timestamp).setEvent(event).setTable(table)
                .setIcmp(icmp).setReason(reason).setArp(arp).setIn_port(in_port).setDl_src(dl_src).setDl_dst(dl_dst)
                .setArp_spa(arp_spa).setArp_tpa(arp_tpa).setArp_op(arp_op).setNw_tos(nw_tos).setIcmp_type(icmp_type)
                .setIcmp_code(icmp_code).setActions(actions).setKey();

        return res;
    }

    public String getEvent() {
        return event;
    }

    public int getTable() {
        return table;
    }

    public boolean isIcmp() {
        return icmp;
    }

    public String getReason() {
        return reason;
    }

    public boolean isArp() {
        return arp;
    }

    public int getIn_port() {
        return in_port;
    }

    public String getDl_src() {
        return dl_src;
    }

    public String getDl_dst() {
        return dl_dst;
    }

    public String getArp_spa() {
        return arp_spa;
    }

    public String getArp_tpa() {
        return arp_tpa;
    }

    public int getArp_op() {
        return arp_op;
    }

    public int getNw_tos() {
        return nw_tos;
    }

    public int getIcmp_type() {
        return icmp_type;
    }

    public int getIcmp_code() {
        return icmp_code;
    }

    public String getActions() {
        return actions;
    }

    public Flow setEvent(String event) {
        this.event = event;
        return this;
    }

    public Flow setTable(int table) {
        this.table = table;
        return this;
    }

    public Flow setIcmp(boolean icmp) {
        this.icmp = icmp;
        return this;
    }

    public Flow setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public Flow setArp(boolean arp) {
        this.arp = arp;
        return this;
    }

    public Flow setIn_port(int in_port) {
        this.in_port = in_port;
        return this;
    }

    public Flow setDl_src(String dl_src) {
        this.dl_src = dl_src;
        return this;
    }

    public Flow setDl_dst(String dl_dst) {
        this.dl_dst = dl_dst;
        return this;
    }

    public Flow setArp_spa(String arp_spa) {
        this.arp_spa = arp_spa;
        return this;
    }

    public Flow setArp_tpa(String arp_tpa) {
        this.arp_tpa = arp_tpa;
        return this;
    }

    public Flow setArp_op(int arp_op) {
        this.arp_op = arp_op;
        return this;
    }

    public Flow setNw_tos(int nw_tos) {
        this.nw_tos = nw_tos;
        return this;
    }

    public Flow setIcmp_type(int icmp_type) {
        this.icmp_type = icmp_type;
        return this;
    }

    public Flow setIcmp_code(int icmp_code) {
        this.icmp_code = icmp_code;
        return this;
    }

    public Flow setActions(String actions) {
        this.actions = actions;
        return this;
    }

    public String getDevice() {
        return device;
    }

    public Flow setDevice(String device) {
        this.device = device;
        return this;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public Flow setTimestamp(double timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return "Flow [event=" + event + ", table=" + table + ", icmp=" + icmp + ", reason=" + reason + ", arp=" + arp
                + ", in_port=" + in_port + ", dl_src=" + dl_src + ", dl_dst=" + dl_dst + ", arp_spa=" + arp_spa
                + ", arp_tpa=" + arp_tpa + ", arp_op=" + arp_op + ", nw_tos=" + nw_tos + ", icmp_type=" + icmp_type
                + ", icmp_code=" + icmp_code + ", actions=" + actions + ", device=" + device + ", timestamp="
                + timestamp + "]";
    }
}
