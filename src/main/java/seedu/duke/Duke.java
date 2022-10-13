package seedu.duke;

import seedu.duke.command.Command;
import seedu.duke.command.CommandBye;

import seedu.duke.exception.AlreadyPairedException;
import seedu.duke.exception.ClientAlreadyPairedException;
import seedu.duke.exception.EmptyClientDetailException;
import seedu.duke.exception.EmptyClientIndexDeleteException;
import seedu.duke.exception.EmptyCommandAddDetailException;
import seedu.duke.exception.EmptyCommandCheckDetailException;
import seedu.duke.exception.EmptyCommandDeleteDetailException;
import seedu.duke.exception.EmptyCommandPairUnpairDetailsException;
import seedu.duke.exception.EmptyPropertyDetailException;
import seedu.duke.exception.EmptyPropertyIndexDeleteException;
import seedu.duke.exception.IncorrectAddClientFlagOrderException;
import seedu.duke.exception.IncorrectAddPropertyFlagOrderException;
import seedu.duke.exception.IncorrectPairUnpairFlagOrderException;
import seedu.duke.exception.InvalidBudgetFormatException;
import seedu.duke.exception.InvalidClientIndexDeleteException;
import seedu.duke.exception.InvalidClientIndexFlagFormatException;
import seedu.duke.exception.InvalidContactNumberException;
import seedu.duke.exception.InvalidEmailException;
import seedu.duke.exception.MissingCheckPropertyFlagException;
import seedu.duke.exception.InvalidPriceFormatException;
import seedu.duke.exception.InvalidPropertyIndexDeleteException;
import seedu.duke.exception.InvalidPropertyIndexFlagFormatException;
import seedu.duke.exception.InvalidSingaporeAddressException;
import seedu.duke.exception.MissingClientDetailException;
import seedu.duke.exception.MissingClientFlagException;
import seedu.duke.exception.MissingClientIndexFlagException;
import seedu.duke.exception.MissingPairUnpairFlagException;
import seedu.duke.exception.MissingPropertyDetailException;
import seedu.duke.exception.MissingPropertyFlagException;
import seedu.duke.exception.MissingPropertyIndexFlagException;
import seedu.duke.exception.NoExistingPairException;
import seedu.duke.exception.NotIntegerException;
import seedu.duke.exception.InvalidIndexException;
import seedu.duke.exception.UndefinedSubCommandAddTypeException;
import seedu.duke.exception.UndefinedSubCommandCheckTypeException;
import seedu.duke.exception.UndefinedSubCommandDeleteTypeException;
import seedu.duke.exception.ByeParametersPresentException;
import seedu.duke.exception.IncorrectListDetailsException;
import seedu.duke.exception.MissingListDetailException;

import java.io.IOException;

public class Duke {
    private Parser parser;
    private Ui ui;
    private Storage storage;
    private PropertyList propertyList;
    private ClientList clientList;
    private PairingList pairingList;


    public void run() throws IOException {
        this.ui = new Ui();
        this.propertyList = new PropertyList();
        this.clientList = new ClientList();
        this.pairingList = new PairingList();
        this.parser = new Parser(clientList, propertyList, pairingList);
        this.storage = new Storage(clientList, propertyList, pairingList);

        Command command;
        boolean isCommandBye = false;

        ui.showWelcomeMessage();

        do {
            try {
                System.exit(0); //to pass CI
                String userInputText = ui.readCommand();
                command = parser.parseCommand(userInputText);
                command.execute(ui, storage, propertyList, clientList, pairingList);
                isCommandBye = (command instanceof CommandBye);
            } catch (EmptyCommandAddDetailException e) {
                ui.showMissingCommandAddDetailMessage();
            } catch (UndefinedSubCommandAddTypeException e) {
                ui.showUndefinedSubCommandAddTypeMessage();
            } catch (EmptyPropertyDetailException e) {
                ui.showEmptyPropertyDetailMessage();
            } catch (MissingPropertyFlagException | IncorrectAddPropertyFlagOrderException
                    | MissingPropertyDetailException e) {
                ui.showAddPropertyWrongFormatMessage();
            } catch (InvalidSingaporeAddressException e) {
                ui.showInvalidSingaporeAddressMessage();
            } catch (InvalidPriceFormatException e) {
                ui.showInvalidPriceFormatMessage();
            } catch (EmptyClientDetailException e) {
                ui.showEmptyClientDetailMessage();
            } catch (MissingClientFlagException | IncorrectAddClientFlagOrderException
                    | MissingClientDetailException e) {
                ui.showAddClientWrongFormatMessage();
            } catch (InvalidContactNumberException e) {
                ui.showInvalidContactNumberMessage();
            } catch (InvalidEmailException e) {
                ui.showInvalidEmailMessage();
            } catch (InvalidBudgetFormatException e) {
                ui.showInvalidBudgetFormatMessage();
            } catch (EmptyCommandDeleteDetailException e) {
                ui.showMissingCommandDeleteDetailMessage();
            } catch (UndefinedSubCommandDeleteTypeException e) {
                ui.showUndefinedSubCommandDeleteTypeMessage();
            } catch (InvalidPropertyIndexDeleteException e) {
                ui.showInvalidPropertyIndexDeleteMessage();
            } catch (InvalidPropertyIndexFlagFormatException e) {
                ui.showInvalidPropertyIndexFlagFormatMessage();
            } catch (EmptyPropertyIndexDeleteException e) {
                ui.showEmptyPropertyIndexDeleteMessage();
            } catch (MissingPropertyIndexFlagException e) {
                ui.showMissingPropertyIndexFlagMessage();
            } catch (InvalidClientIndexDeleteException e) {
                ui.showInvalidClientIndexDeleteMessage();
            } catch (EmptyClientIndexDeleteException e) {
                ui.showEmptyClientIndexDeleteMessage();
            } catch (MissingClientIndexFlagException e) {
                ui.showMissingClientIndexFlagMessage();
            } catch (InvalidClientIndexFlagFormatException e) {
                ui.showInvalidClientIndexFlagFormatMessage();
            } catch (EmptyCommandPairUnpairDetailsException e) {
                ui.showEmptyCommandPairUnpairDetailsMessage();
            } catch (MissingPairUnpairFlagException | IncorrectPairUnpairFlagOrderException e) {
                ui.showPairUnpairWrongFormatMessage();
            } catch (InvalidIndexException e) {
                ui.showNotValidIndexMessage();
            } catch (NotIntegerException e) {
                ui.showNotIntegerMessage();
            } catch (ClientAlreadyPairedException e) {
                ui.showClientAlreadyPairedMessage();
            } catch (NoExistingPairException e) {
                ui.showNoExistingPairMessage();
            } catch (AlreadyPairedException e) {
                ui.showAlreadyPairedMessage();
            } catch (MissingCheckPropertyFlagException e) {
                ui.showCheckPropertyWrongFormatMessage();
            } catch (UndefinedSubCommandCheckTypeException e) {
                ui.showUndefinedSubCommandCheckTypeMessage();
            } catch (EmptyCommandCheckDetailException e) {
                ui.showEmptyCommandCheckDetailException();
            } catch (IncorrectListDetailsException e) {
                ui.showIncorrectListDetailsMessage();
            } catch (MissingListDetailException e) {
                ui.showMissingListDetailsMessage();
            } catch (ByeParametersPresentException e) {
                ui.showByeParametersPresentMessage();
            }
        } while (!isCommandBye);
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) throws IOException {
        new Duke().run();
    }
}
