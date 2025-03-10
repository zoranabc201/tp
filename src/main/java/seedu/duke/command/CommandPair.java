package seedu.duke.command;


import seedu.duke.Client;
import seedu.duke.ClientList;
import seedu.duke.PairingList;
import seedu.duke.Property;
import seedu.duke.PropertyList;
import seedu.duke.Storage;
import seedu.duke.Ui;

import java.util.ArrayList;

/**
 * Represents a pair-type command.
 */
public class CommandPair extends Command {

    private int clientIndex;
    private int propertyIndex;


    /**
     * Constructs CommandPair object.
     * @param commandPairDetails Parsed client and property indexes from the user's input.
     */
    public CommandPair(ArrayList<Integer> commandPairDetails) {
        this.propertyIndex = commandPairDetails.get(0);
        this.clientIndex = commandPairDetails.get(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Ui ui, Storage storage, PropertyList propertyList, ClientList clientList,
                        PairingList pairingList) {
        Client client = clientList.getClientList().get(clientIndex);
        Property property = propertyList.getPropertyList().get(propertyIndex);

        String clientFormat = pairingList.convertToPairingData(client);
        String propertyFormat = pairingList.convertToPairingData(property);

        pairingList.addPairing(client, property);

        storage.addToPairFile(clientFormat, propertyFormat);

        ui.showPairedConfirmationMessage(client, property);
    }
}
