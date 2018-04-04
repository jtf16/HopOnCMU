package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command;

import java.io.Serializable;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public interface Command extends Serializable {
    Response handle(CommandHandler ch);
}
