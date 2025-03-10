package seedu.duke.command;

import seedu.duke.ClientList;
import seedu.duke.PairingList;
import seedu.duke.PropertyList;
import seedu.duke.Storage;
import seedu.duke.Ui;

import seedu.duke.CommandStructure;

public class CommandListPropertiesWithTags extends Command {
    private final String commandFlag;

    public CommandListPropertiesWithTags(String commandFlag) {
        this.commandFlag = commandFlag;
    }

    @Override
    public void execute(Ui ui, Storage storage, PropertyList propertyList, ClientList clientList,
                        PairingList pairingList) {
        switch (commandFlag) {
        case CommandStructure.ADDRESS_FLAG:
            displayPropertyAddresses(propertyList, ui);
            break;
        case CommandStructure.NAME_FLAG:
            displayPropertyLandlordNames(propertyList, ui);
            break;
        case CommandStructure.PRICE_FLAG:
            displayPropertyPrices(propertyList, ui);
            break;
        case CommandStructure.TYPE_FLAG:
            displayPropertyTypes(propertyList, ui);
            break;
        default:
            break;
        }
    }

    private void displayPropertyAddresses(PropertyList propertyList, Ui ui) {
        for (int i = 0; i < propertyList.getCurrentListSize(); i++) {
            ui.displayOnePropertyAddress(propertyList.getPropertyList().get(i), i + 1);
        }
        System.out.println("There are " + propertyList.getCurrentListSize()
                + " properties in this list");
    }

    private void displayPropertyLandlordNames(PropertyList propertyList, Ui ui) {
        for (int i = 0; i < propertyList.getCurrentListSize(); i++) {
            ui.displayOnePropertyLandlordName(propertyList.getPropertyList().get(i), i + 1);
        }
        System.out.println("There are " + propertyList.getCurrentListSize()
                + " properties in this list");
    }

    private void displayPropertyPrices(PropertyList propertyList, Ui ui) {
        for (int i = 0; i < propertyList.getCurrentListSize(); i++) {
            ui.displayOnePropertyRentingPrice(propertyList.getPropertyList().get(i), i + 1);
        }
        System.out.println("There are " + propertyList.getCurrentListSize()
                + " properties in this list");
    }

    private void displayPropertyTypes(PropertyList propertyList, Ui ui) {
        for (int i = 0; i < propertyList.getCurrentListSize(); i++) {
            ui.displayOnePropertyUnitType(propertyList.getPropertyList().get(i), i + 1);
        }
        System.out.println("There are " + propertyList.getCurrentListSize()
                + " properties in this list");
    }
}

