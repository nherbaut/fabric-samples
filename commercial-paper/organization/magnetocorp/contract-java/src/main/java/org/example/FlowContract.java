/*
SPDX-License-Identifier: Apache-2.0
*/
package org.example;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.CompositeKey;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import org.hyperledger.fabric.shim.ledger.QueryResultsIteratorWithMetadata;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * A custom context provides easy access to list of all commercial papers
 */

/**
 * Define commercial paper smart contract by extending Fabric Contract class
 *
 */
@Contract(name = "fr.pantheonsorbonne.cri", info = @Info(title = "MyAsset contract", description = "", version = "0.0.1", license = @License(name = "SPDX-License-Identifier: Apache-2.0", url = ""), contact = @Contact(email = "java-contract@example.com", name = "java-contract", url = "http://java-contract.me")))
@Default
public class FlowContract implements ContractInterface {

    // use the classname for the logger, this way you can refactor
    private final static Logger LOG = Logger.getLogger(FlowContract.class.getName());

    @Override
    public Context createContext(ChaincodeStub stub) {
        return new FlowMonitoringContext(stub);
    }

    public FlowContract() {

    }

    /**
     * Define a custom context for commercial paper
     */

    /**
     * Instantiate to perform any setup of the ledger that might be required.
     *
     * @param {Context} ctx the transaction context
     */
    @Transaction
    public void instantiate(FlowMonitoringContext ctx) {
        // No implementation required with this example
        // It could be where data migration is performed, if necessary
        LOG.info("No data migration to perform");
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Flow logflow(FlowMonitoringContext ctx, String deviceId, double timestamp, String event, int table,
            boolean icmp, String reason, boolean arp, int in_port, String dl_src, String dl_dst, String arp_spa,
            String arp_tpa, int arp_op, int nw_tos, int icmp_type, int icmp_code, String actions) {

        Flow flow = Flow.createInstance(deviceId, timestamp, event, table, icmp, reason, arp, in_port, dl_src, dl_dst,
                arp_spa, arp_tpa, arp_op, nw_tos, icmp_type, icmp_code, actions);

        try {
            ctx.getStub().putStringState(flow.getKey(), new String(Flow.serialize(flow), "UTF-8"));
            LOG.severe("serialized flow" + new String(Flow.serialize(flow), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return flow;

    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String listFlowsForDevice(FlowMonitoringContext ctx, String device) {

        QueryResultsIterator<KeyValue> keys = ctx.getStub()
                .getStateByPartialCompositeKey(CompositeKey.parseCompositeKey(device));
        StringWriter writer = new StringWriter();
        keys.forEach(k -> writer.append(k.getStringValue()));

        return writer.toString();

    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String listAllFlows(FlowMonitoringContext ctx) {

        QueryResultsIterator<KeyValue> it = ctx.getStub().getStateByRange("", "");

        List<Flow> queryResults = new ArrayList<>();

        LOG.info("looping on keyvalue");
        for (KeyValue result : it) {
            LOG.info("here's one" + result.getStringValue());
            Flow flow = Flow.deserialize(result.getStringValue().getBytes());
            queryResults.add(flow);
            LOG.info("here's one deserialized " + flow.toString());
        }
        Gson gson = new Gson();
        return gson.toJson(queryResults);

    }

}
