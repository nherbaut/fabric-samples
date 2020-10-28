package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class FlowMonitoringContext extends Context {

    public FlowList flows;

    public FlowMonitoringContext(ChaincodeStub stub) {
        super(stub);
        this.flows = new FlowList(this);
    }

}
