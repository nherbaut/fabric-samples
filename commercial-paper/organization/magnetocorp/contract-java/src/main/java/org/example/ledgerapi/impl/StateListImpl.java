package org.example.ledgerapi.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.example.Flow;
import org.example.FlowList;
import org.example.ledgerapi.State;
import org.example.ledgerapi.StateDeserializer;
import org.example.ledgerapi.StateList;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.CompositeKey;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import org.hyperledger.fabric.shim.ledger.QueryResultsIteratorWithMetadata;
import org.json.JSONObject;

/*
SPDX-License-Identifier: Apache-2.0
*/

/**
 * StateList provides a named virtual container for a set of ledger states. Each
 * state has a unique key which associates it with the container, rather than
 * the container containing a link to the state. This minimizes collisions for
 * parallel transactions on different states.
 */
public class StateListImpl implements StateList {

    private final static Logger LOG = Logger.getLogger(StateListImpl.class.getName());
    private Context ctx;
    private String name;
    private Object supportedClasses;
    private StateDeserializer deserializer;

    /**
     * Store Fabric context for subsequent API access, and name of list
     *
     * @param deserializer
     */
    public StateListImpl(Context ctx, String listName, StateDeserializer deserializer) {
        this.ctx = ctx;
        this.name = listName;
        this.deserializer = deserializer;

    }

    /**
     * Add a state to the list. Creates a new state in worldstate with appropriate
     * composite key. Note that state defines its own key. State object is
     * serialized before writing.
     */
    @Override
    public StateList addState(State state) {

        LOG.severe("1.");
        ChaincodeStub stub = this.ctx.getStub();
        LOG.severe("2.");
        String[] splitKey = state.getSplitKey();
        LOG.severe("3.");
        CompositeKey ledgerKey = stub.createCompositeKey(this.name, splitKey);
        LOG.severe("4.");
        byte[] data = State.serialize(state);
        LOG.severe("5.");
        this.ctx.getStub().putState(ledgerKey.toString(), data);
        LOG.severe("6.");
        
        
        return this;
    }

    /**
     * Get a state from the list using supplied keys. Form composite keys to
     * retrieve state from world state. State data is deserialized into JSON object
     * before being returned.
     */
    @Override
    public State getState(String key) {

        CompositeKey ledgerKey = this.ctx.getStub().createCompositeKey(this.name, State.splitKey(key));

        byte[] data = this.ctx.getStub().getState(ledgerKey.toString());
        if (data != null) {
            State state = this.deserializer.deserialize(data);
            return state;
        } else {
            return null;
        }
    }

    /**
     * Update a state in the list. Puts the new state in world state with
     * appropriate composite key. Note that state defines its own key. A state is
     * serialized before writing. Logic is very similar to addState() but kept
     * separate becuase it is semantically distinct.
     */
    @Override
    public StateList updateState(State state) {
        CompositeKey ledgerKey = this.ctx.getStub().createCompositeKey(this.name, state.getSplitKey());
        byte[] data = State.serialize(state);
        this.ctx.getStub().putState(ledgerKey.toString(), data);

        return this;
    }

    @Override
    public String getAllStates() {
        QueryResultsIterator<KeyValue> it = this.ctx.getStub().getStateByRange("", "");

        List<Flow> queryResults = new ArrayList<>();

        LOG.info("looping on keyvalue");
        for (KeyValue result : it) {
            LOG.info("here's one" + result.getStringValue());
            Flow flow = Flow.deserialize(result.getStringValue().getBytes());
            queryResults.add(flow);
            LOG.info("here's one deserialized " + flow.toString());
        }

        return new JSONObject(queryResults).toString();
    }

}
