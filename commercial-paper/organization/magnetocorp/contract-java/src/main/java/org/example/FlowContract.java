/*
SPDX-License-Identifier: Apache-2.0
*/
package org.example;

import java.util.logging.Logger;

import org.example.ledgerapi.State;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;

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

    @Transaction
    public Flow logflow(FlowMonitoringContext ctx, String device, double timestamp, String dummy) {

        System.out.println("################## " + dummy);
        Flow flow = Flow.createInstance(device, timestamp, dummy, -1, true, "", false, -1, "", "", "", "", -1, -1, -1,
                -1, "");
        flow.setEvent(dummy);
        ctx.flows.addFlow(flow);
        return flow;

    }

}
